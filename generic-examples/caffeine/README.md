# Caffeine Camel K examples

This examples demonstrate how to use Caffeine cache in a Camel K integration.

You can find more information about Apache Camel and Apache Camel K on the [official Camel website](https://camel.apache.org).

## Before you begin

Read the general instructions in the [root README.md file](/README.md) for setting up your environment and the Kubernetes cluster before looking at this example.

Make sure you've read the [installation instructions](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific
cluster before starting the example.

## Understanding the Example

- [`CaffeineCacheSample.java`](./CaffeineCacheSample.java) defines a route that every second:
    - uses message headers to PUT a key-value pair in the cache and log the result
    - uses message headers to GET the cached value for the key and log the result
    - uses message headers to INVALIDATE the cached value for the key, logs about invalidating the data
    - uses message headers to GET the now discarded cached value for the key and log the result
- [`camel-caffeine.groovy`](./camel-caffeine.groovy) does the same thing as `CaffeineCacheSample.java` except that a bean is defined and accumulation of cache statistics is enabled.

## Running the Example
To see the logs in the terminal, run the integration in dev mode:
```
kamel run --dev CaffeineCacheSample.java
```

The following should be logged continuously to the terminal:
```console
[1] 2022-06-24 12:58:06,352 INFO  [route1] (Camel (camel-1) thread #1 - timer://tick) Result of Action PUT with key 1 is: Hello
[1] 2022-06-24 12:58:06,355 INFO  [route1] (Camel (camel-1) thread #1 - timer://tick) Result of Action GET with key 1 is: Hello
[1] 2022-06-24 12:58:06,355 INFO  [route1] (Camel (camel-1) thread #1 - timer://tick) Invalidating entry with key 1
[1] 2022-06-24 12:58:06,357 INFO  [route1] (Camel (camel-1) thread #1 - timer://tick) The Action GET with key 1 has result? false
```