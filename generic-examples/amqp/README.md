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

To create a secret for your credentials, run:
```
kubectl create secret generic my-amqp --from-file=amqp.properties
```

To run an integration that sends message to amqp queue, run:
```
kamel run AmqpConnectionBeanProducer.java --config secret:my-amqp
```

To consume messages from the amqp queue, run:
```
kamel run AmqpConnectionBeanConsumer.java --dev --config secret:my-amqp
```
The terminal should show the logged messages:
```console
[1] 2022-06-21 15:44:46,513 INFO  [info] (Camel (camel-1) thread #1 - JmsConsumer[example]) Exchange[ExchangePattern: InOnly, BodyType: String, Body: Hello Camel K]
[1] 2022-06-21 15:44:47,513 INFO  [info] (Camel (camel-1) thread #1 - JmsConsumer[example]) Exchange[ExchangePattern: InOnly, BodyType: String, Body: Hello Camel K]
[1] 2022-06-21 15:44:48,517 INFO  [info] (Camel (camel-1) thread #1 - JmsConsumer[example]) Exchange[ExchangePattern: InOnly, BodyType: String, Body: Hello Camel K]
```