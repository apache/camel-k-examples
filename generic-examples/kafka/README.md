# Camel K with Kafka examples

These examples show how to connect to a Kafka broker in a Camel K integration.

You can find more information about Apache Camel and Apache Camel K on the [official Camel website](https://camel.apache.org).

## Before you begin

Read the general instructions in the [root README.md file](/README.md) for setting up your environment and the Kubernetes cluster before looking at this example.

Make sure you've read the [installation instructions](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific
cluster before starting the example.

## Additional Requirements for running the examples
**A Kafka broker**: handles the storage and passing of messages.

## Authentication to Kafka

To use Kafka with authentication, we have a dedicated section to show [how to authenticate Camel K to Kafka](./sasl_ssl/). \
For a simple use case without client authentication, continue with this guide.

## Understanding the example
- [SampleKafkaConsumer.java](./SampleKafkaConsumer.java): contains a route that reads message from a kafka topic and logs the message
- [application.properties](./application.properties): holds properties required to connect to kafka broker and read from topic.

## Simple usage

To run this example, first set-up Kafka on your k8s cluster.
A convenient way to do so is by using the Strimzi project. Visit https://strimzi.io/quickstarts/ for set-up instructions. For the instructions on the linked site, it will suffice to only apply the Strimzi installation file and provision the kafka cluster.

**IMPORTANT:** The `kafka.host` value in `application.properties` needs to be set to the CLUSTER-IP address of the my-cluster-kafka-bootstrap service in the kafka namespace. To do this run:
```
kafkaip=`kubectl get svc/my-cluster-kafka-bootstrap -n kafka -ojsonpath="{.spec.clusterIP}"`; sed -i "/kafka\.host/s/<.*>/$kafkaip/g" application.properties
```

Create a configmap to contain the properties:
```
kubectl create configmap kafka.props  --from-file=application.properties
```



Finally run this sample using the command:
```
kamel run SampleKafkaConsumer.java --config=configmap:kafka.props
```

To create messages to be read, use the producer command from the Strimzi page. Run in another terminal:
```
kubectl -n kafka run kafka-producer -ti --image=quay.io/strimzi/kafka:0.30.0-kafka-3.2.0 --rm --restart=Never -- bin/kafka-console-producer.sh --bootstrap-server my-cluster-kafka-bootstrap:9092 --topic my-topic
```
You should see a prompt where you can type messages to be sent to the `my-topic` topic.