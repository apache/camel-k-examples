# Camel K Tracing Trait

> **Warning**
> The Tracing Trait the trait has been deprecated in favor of the Telemetry Trait in camel-k 1.12+.

In this section you will find examples about fine tuning your `Integration` using **Tracing** `trait` capability.

The Tracing trait can be used to automatically publish tracing information of interactions to an OpenTracing compatible collector.

## Configure and Setup Jaeger

1. Enable Ingress addon in Minikube 

```sh
$ minikube addons enable ingress
```

2. Add Minikube IP to /etc/hosts:

```sh
$ echo "$(minikube ip) example.com" | sudo tee -a /etc/hosts
```

3. Make sure Jaeger operator is available (see https://www.jaegertracing.io/docs for installation details)

4. To use Jaeger, you can install the AllInOne image:

```sh
$ kubetcl apply -f instance.yaml
```

5. Check the presence of the Jaeger instance

```sh
$ kubectl get jaeger
NAME        STATUS    VERSION   STRATEGY   STORAGE   AGE
instances   Running   1.40.0    allinone   memory    9m16s
```

## Enable OpenTracing and trace a REST API call in Camel K Route 

Tracing is an important approach for controlling and monitoring the experience of users. We  will be creating two distributed services: `Order` which is a rest service, and `Inventory` which is also a rest service.

Quarkus OpenTracing extension in Camel automatically creates a Camel OpenTracingTracer and binds it to the Camel registry. Simply declare the traits to enable open tracing. 


```sh
kamel run InventoryService.java --name inventory \
   -d camel-jackson \
   -t tracing.enabled=true \
   -t tracing.sampler-type=const \ 
   -t tracing.sampler-param=1 
```

This will :
* enable tracing  
* automaticly discover of jaeger tracing endpoint
* sample all traces

To specify the endpoint use the following trait configuration `-t tracing.endpoint=http://instance-collector:14268/api/traces`

Let's inject the Opentracing Tracer to the camel OrderService.java application. Let's start the inventory service. 

```sh
kamel run OrderService.java --name order \
   -d camel-jackson \
   -t tracing.enabled=true \
   -t tracing.sampler-type=const \ 
   -t tracing.sampler-param=1
```

## View the Jaeger UI 

If you installed the Jaeger Operator as describred, you should be able to access Jaeger interface on minikube : http://example.com.

In the Jaeger interface we can see the details as:

![Jeager Tracing Interface](interface/jaegerInterface.png)

You can make a few requests the REST Service with custom transaction values defined by curl, provided you made the `order` and `inventory` services available (using the **Service** trait is an easy way).

```sh
curl http://<order-service-external>/place -d '
{
    "orderId":58, 
    "itemId":12, 
    "quantity":1, 
    "orderItemName":"awesome item",
    "price":99
}' -v -H "Content-Type: application/json"
```
