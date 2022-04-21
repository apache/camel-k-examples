# Camel AWS Secret Manager Vault Example

This example demonstrates how to use AWS Secret Manager to retrieves secret values directly from the Cloud service, in Camel K.

You can find more information about Apache Camel and Apache Camel K on the [official Camel website](https://camel.apache.org).

## Before you begin

Read the general instructions in the [root README.md file](../README.md) for setting up your environment and the Kubernetes cluster before looking at this example.

Make sure you've read the [installation instructions](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific
cluster before starting the example.

## Setting up AWS Credentials

Open the aws-secret-manager.properties file and correctly set the credentials values.

## Create a secret on AWS Secret Manager

You'll need to create a secret for using Finnhub for this example.

You can obtain a token by looking at [Finnhub.io](https://finnhub.io) website.

The secret name must be finnhub-token.

## Running the example

Once you have Camel K running you should run the example in the following way:

```
kamel run --build-property quarkus.camel.service.discovery.include-patterns=META-INF/services/org/apache/camel/properties-function/* -p file:aws-secret-manager.properties timer-finnhub-aws-secret-manager.yaml
```

Once the integration has been deployed, you should see the following in the logs:

```
2022-04-21 16:28:23,724 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main) Apache Camel 3.16.0 (camel-1) is starting
2022-04-21 16:28:23,746 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main) Routes startup (total:3 started:3)
2022-04-21 16:28:23,746 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main)     Started route1 (kamelet://timer-source)
2022-04-21 16:28:23,746 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main)     Started timer-source-1 (timer://tick)
2022-04-21 16:28:23,746 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main)     Started log-sink-2 (kamelet://source)
2022-04-21 16:28:23,746 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main) Apache Camel 3.16.0 (camel-1) started in 1s373ms (build:0ms init:1s351ms start:22ms)
2022-04-21 16:28:23,750 INFO  [io.quarkus] (main) camel-k-integration 1.9.0 on JVM (powered by Quarkus 2.8.0.Final) started in 5.803s. 
2022-04-21 16:28:23,750 INFO  [io.quarkus] (main) Profile prod activated. 
2022-04-21 16:28:23,750 INFO  [io.quarkus] (main) Installed features: [camel-aws-secrets-manager, camel-bean, camel-core, camel-http, camel-k-core, camel-k-runtime, camel-kamelet, camel-log, camel-timer, camel-yaml-dsl, cdi]
2022-04-21 16:28:25,424 INFO  [info] (Camel (camel-1) thread #1 - timer://tick) Exchange[ExchangePattern: InOnly, BodyType: org.apache.camel.converter.stream.CachedOutputStream.WrappedInputStream, Body: {"c":169.93,"d":2.7,"dp":1.6145,"h":171.53,"l":168.445,"o":168.91,"pc":167.23,"t":1650558475}]
```
