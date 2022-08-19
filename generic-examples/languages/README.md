# Supported languages for writing Camel K integrations

Contains useful examples on how to develop a Camel K integration in `Java`, `JavaScript`, `Groovy`, `XML`, `Kotlin` and `YAML`.

You can find more information about Apache Camel and Apache Camel K on the [official Camel website](https://camel.apache.org).

## Before you begin

Read the general instructions in the [root README.md file](/README.md) for setting up your environment and the Kubernetes cluster before looking at this example.

Make sure you've read the [installation instructions](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific
cluster before starting the example.

## Understanding the examples
- [routes.kts](./routes.kts): defines a route that logs a message every second
- [routes.yaml](./hello.xml): defines a route that every 5 secs, transforms the message content to uppercase and logs it.
- [simple.js](./simple.js): logs message periodically, the `multiline` query parameter is set to true, thus each information is output on a newline
- [hello.xml](./hello.xml): defines a route that logs a message to a logger very 3 seconds.
- [simple.groovy](./simple.groovy): logs a message every second, the `showAll` query parameter is set to false, thus only a limited information is shown.
- [Sample.java](./Sample.java): defines a route that logs a message periodically.

## Running the Examples
To simply run an integration, execute `kamel run [file to run]`. For example:
```
kamel run routes.yaml
```