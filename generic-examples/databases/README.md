# Camel K with database example

This example demonstrates how to use a database in a Camel K integration.

You can find more information about Apache Camel and Apache Camel K on the [official Camel website](https://camel.apache.org).

## Before you begin

Read the general instructions in the [root README.md file](/README.md) for setting up your environment and the Kubernetes cluster before looking at this example.

Make sure you've read the [installation instructions](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific
cluster before starting the example.

## Additional Requirements for running this example

**A PostgreSQL instance**: needed database for running the example. For installation instructions, see [How to deploy a simple Postgres DB to a Kubernetes cluster](./postgres-deploy/) for demo purposes.

## Understanding the Example
- [`PostgresDBAutoDatasource.java`](./PostgresDBAutoDatasource.java) contains the integration code. It defines a route that periodically queries a database and logs the result.
- [`datasource.properties`](./datasource.properties) holds your credentials for connecting to the database.

## Running the Example
You should have a PostgreSQL instance running in a namespace. If not see [How to deploy a simple Postgres DB to a Kubernetes cluster](./postgres-deploy/)

Bundle your credentials as a secret:
```
kubectl create secret generic my-datasource --from-file=datasource.properties
```

Run the integration:
```
kamel run PostgresDBAutoDatasource.java --dev --build-property quarkus.datasource.camel.db-kind=postgresql --config secret:my-datasource -d mvn:io.quarkus:quarkus-jdbc-postgresql:2.10.0.Final
```

If successful, the query result: `hello` and `world`, should be logged to the terminal every 10 seconds:
```console
[1] 2022-06-30 09:30:56,313 INFO  [info] (Camel (camel-1) thread #1 - timer://foo) Exchange[ExchangePattern: InOnly, BodyType: java.util.ArrayList, Body: [{data=hello}, {data=world}]]
[1] 2022-06-30 09:31:06,312 INFO  [info] (Camel (camel-1) thread #1 - timer://foo) Exchange[ExchangePattern: InOnly, BodyType: java.util.ArrayList, Body: [{data=hello}, {data=world}]]
[1] 2022-06-30 09:31:16,313 INFO  [info] (Camel (camel-1) thread #1 - timer://foo) Exchange[ExchangePattern: InOnly, BodyType: java.util.ArrayList, Body: [{data=hello}, {data=world}]]
```