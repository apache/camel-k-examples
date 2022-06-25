# Camel K basic examples

This example shows how to set an env variable to be used in a Camel K integration.

You can find more information about Apache Camel and Apache Camel K on the [official Camel website](https://camel.apache.org).

## Before you begin

Read the general instructions in the [root README.md file](/README.md) for setting up your environment and the Kubernetes cluster before looking at this example.

Make sure you've read the [installation instructions](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific
cluster before starting the example.

## Understanding the Example

- [`Env.java`](./Env.java) defines a route that logs the value of an env variable every second

## Running the Example

To both set the `MY_ENV_VAR` env variable to `hello world` and run the integration, execute the following:
```
kamel run --env MY_ENV_VAR="hello world" Env.java --dev
```
You should see `hello world` logged to the terminal every second