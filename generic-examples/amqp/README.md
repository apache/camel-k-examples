# AMQP Camel K examples

These examples demonstrate how to use AMQP in a Camel K integration.

You can find more information about Apache Camel and Apache Camel K on the [official Camel website](https://camel.apache.org).

## Before you begin

Read the general instructions in the [root README.md file](/README.md) for setting up your environment and the Kubernetes cluster before looking at this example.

Make sure you've read the [installation instructions](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific
cluster before starting the example.

## Additional Requirements for running this example

**An AMQP Broker**: needed MoM for running the examples. For installation instructions, see [how to install a JMS/AMQP Broker on Kubernetes](./artemis/) for demo purposes.

## Understanding the Example

- [`AmqpConnectionBeanProducer.java`](./AmqpConnectionBeanProducer.java) defines a route that generates a message every second and sends it to an amqp queue.
- [`AmqpConnectionBeanConsumer.java`](./AmqpConnectionBeanConsumer.java) consumes messages from the amqp queue, logs them to the logger using the info level
- [`amqp.groovy`](./amqp.groovy) generates a message every second and sends to amqp topic
- [`amqp.properties`](./amqp.properties) holds required credentials to connect to broker.

## Running the Example

You should have an amqp broker running in a namespace, if not, see [how to install a JMS/AMQP Broker on Kubernetes](./artemis/)

To run an integration that sends/consumes message to amqp queue, run:
```
kubectl create secret generic my-amqp --from-file=amqp.properties
kamel run --dev --config secret:my-amqp amqp.yaml
```

The terminal should show the logged messages:
```console
[1] 2024-05-23 07:12:27,778 INFO  [org.apa.qpi.jms.JmsConnection] (AmqpProvider :(8):[amqp://my-amqp-service:5672]) Connection ID:e04a1ef1-d167-4aed-b525-6959666be4a8:8 connected to server: amqp://my-amqp-service:5672
[1] 2024-05-23 07:12:27,788 INFO  [info] (Camel (camel-1) thread #1 - JmsConsumer[example]) Exchange[ExchangePattern: InOnly, BodyType: String, Body: Hello Camel K]
[1] 2024-05-23 07:12:28,772 INFO  [org.apa.qpi.jms.JmsConnection] (AmqpProvider :(9):[amqp://my-amqp-service:5672]) Connection ID:34387545-ef55-429a-976f-a28cd2d5e038:9 connected to server: amqp://my-amqp-service:5672
[1] 2024-05-23 07:12:28,783 INFO  [info] (Camel (camel-1) thread #1 - JmsConsumer[example]) Exchange[ExchangePattern: InOnly, BodyType: String, Body: Hello Camel K]
```