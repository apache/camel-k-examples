# Camel K Container Trait

In this section you will find examples about fine-tuning your `Integration` using **Container** `trait` capability.

The Container trait is a platform trait, it is **enabled** by default.

## Before you begin

Read the general instructions in the [root README.md file](../../README.md) for setting up your environment and the Kubernetes cluster before looking at this example.

Make sure you've read the [installation instructions](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific cluster before starting the example.

## Basic usage

To configure some custom values, run the integration

```sh
kamel run \
  --name container \
  Container.java \
  --trait container.image-pull-policy=Always \
  --trait container.request-cpu=0.005 \
  --trait container.limit-cpu=0.2 \
  --trait container.request-memory=100Mi \
  --trait container.limit-memory=500Mi
```

When you check the values declared by the pod spec

```sh
kubectl get pods --selector="camel.apache.org/integration"="container" -o yaml
```

You should get a result with the values you defined

```yaml
...
                "imagePullPolicy": "Always",
...
                "resources": {
                    "limits": {
                        "cpu": "200m",
                        "memory": "500Mi"
                    },
                    "requests": {
                        "cpu": "5m",
                        "memory": "100Mi"
                    }
                }
...
```

## Advanced usages

The container and service port configuration needs the presence of a service, else it will be ignored.

For these example, we use an example route exposing some rest endpoint. This will enable the service and expose the container port by default.

> **Warning**
> 
> Be careful when changing the default ports value and/or name as it can have some side effects.

### Service Port

To define a custom service port, run the integration

```sh
kamel run --name restcontainer \
  RestDSL.java \
  --trait service.enabled=true \
  --trait container.service-port=8082 \
  --trait container.service-port-name=myserviceport
```
When you check the values declared by the service spec

```sh
kubectl get service restcontainer -o jsonpath='{.spec.ports}'
```

You should get a result with the values you defined

```json
[{"name":"myserviceport","port":8082,"protocol":"TCP","targetPort":"http"}]
```

For more details on the Service trait, see the [example README.md file](../service/README.md)

### Container Port

The definition of a custom container port need some modification of quarkus default property to be effective.

```sh
kamel run \
  --property quarkus.http.port=8081 \
  --name restcontainer \
  RestDSL.java \
  --trait container.port=8081 \
  --trait container.port-name=mycontainerport
```

```sh
kubectl get pods --selector="camel.apache.org/integration"="restcontainer" -o jsonpath='{.items[*].spec.containers[*].ports}'
```

You should get a result with the values you defined

```json
[{"containerPort":8081,"name":"mycontainerport","protocol":"TCP"}]
```
