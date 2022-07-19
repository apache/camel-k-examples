# Examples showing how to use Camel K to connect to an IBM MQ Server

The examples show the ways we can connect to an IBM MQ Server using Camel K

You can find more information about Apache Camel and Apache Camel K on the [official Camel website](https://camel.apache.org).

## Before you begin

Read the general instructions in the [root README.md file](/README.md) for setting up your environment and the Kubernetes cluster before looking at this example.

Make sure you've read the [installation instructions](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific
cluster before starting the example.

## Additional Requirements for running the examples
**An IBM MQ Server**: Queue manager for providing queuing services to clients. For installation instructions, see [How to deploy an IBM MQ Server to a Kubernetes cluster](./ibm-mq-server-deploy/) for demo purposes.

## Running the examples

* Deploy the IBM MQ Server, as described in the [ibm-mq-server-deploy/README.md](./ibm-mq-server-deploy/README.md)

* Change the IBM MQ Server address in the MQRoute.java class file

```
ibmip=`kubectl get svc/ibm-mq-server -ojsonpath="{.spec.clusterIP}"`; sed -i "/mqHost/s/\".*\"/\"$ibmip\"/g" MQRoute.java
```

For licensing reasons, the IBM MQ Java libraries are not defined in the routes themselves, but you can declare the dependency while running the integration. Alternatively, you can use Kamel modeline to add the dependency in the route file as a header.

* Run the MQRoute.java. It is a producer and consumer of messages.

```
kamel run --dev MQRoute.java -d mvn:com.ibm.mq:com.ibm.mq.allclient:9.2.5.0
```

It will print the following output in the console

```
JmsConsumer[DEV.QUEUE.1]) Exchange[ExchangePattern: InOnly, BodyType: String, Body: Hello Camel K! #2]
```

* If you want to have a more streamlined declarative approach to run the integration using Kamelets, you can use the routes in YAML format.


The kamel commands below set the following configurations:
1. As previously mentioned, declare as a dependency, the IBM MQ Java library.
2. Use the IBM MQ Server password set in a kubernetes `Secret` object.
3. Set the IBM MQ Server IP address as a property to run the integration, so the IBM MQ Client can connect to the server.


Run the integration to generate messages and send them to the IBM MQ Queue (there is no output in the console)
```
kamel run --dev jms-ibm-mq-sink-binding.yaml -d mvn:com.ibm.mq:com.ibm.mq.allclient:9.2.5.0 --config secret:ibm-mq/ibm-mq-password -p serverName=`kubectl get svc/ibm-mq-server -ojsonpath="{.spec.clusterIP}"`
```

Run the integration to retrieve messages from the IBM MQ Queue and print in the console.
```
kamel run --dev jms-ibm-mq-source-binding.yaml -d mvn:com.ibm.mq:com.ibm.mq.allclient:9.2.5.0 --config secret:ibm-mq/ibm-mq-password -p serverName=`kubectl get svc/ibm-mq-server -ojsonpath="{.spec.clusterIP}"`
```
