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

## Multiple datasources example

An additional example using multiple datasources with an in-memory H2 database is available as a standalone. It does not requires the PostgreSQL instance.

It shows how to configure and use multiple datasources.

[`H2DBMultiDatasources.java`](./H2DBMultiDatasources.java) contains the integration code. It defines 2 datasources, with 3 routes:
- initialise a table with each datasource only once
- insert periodically datas via each datasource
- queries periodically bot datasources and logs the result.

You will find most of the configuration parameters inside the integration code.


To run it you only need the following :
```
kamel run H2DBMultiDatasources.java --dev
```

If successful, the following should be logged to the terminal every 10 seconds:
```console
[1] 2023-10-13 14:14:48,310 INFO  [Init] (Camel (camel-1) thread #1 - timer://initCamel) Create table defaultcamel
[1] 2023-10-13 14:14:48,470 INFO  [DEBUG] (Camel (camel-1) thread #1 - timer://initCamel) Exchange[Headers: {CamelMessageTimestamp=1697206488298, CamelSqlUpdateCount=0, firedTime=Fri Oct 13 14:14:48 UTC 2023}, BodyType: null, Body: [Body is null]]
[1] 2023-10-13 14:14:48,470 INFO  [Init] (Camel (camel-1) thread #1 - timer://initCamel) SUCCESS Create table defaultcamel
[1] 2023-10-13 14:14:48,470 INFO  [Init] (Camel (camel-1) thread #1 - timer://initCamel) Create table testingcamel
[1] 2023-10-13 14:14:48,474 INFO  [DEBUG] (Camel (camel-1) thread #1 - timer://initCamel) Exchange[Headers: {CamelMessageTimestamp=1697206488298, CamelSqlUpdateCount=0, firedTime=Fri Oct 13 14:14:48 UTC 2023}, BodyType: null, Body: [Body is null]]
[1] 2023-10-13 14:14:48,475 INFO  [Init] (Camel (camel-1) thread #1 - timer://initCamel) SUCCESS Create table testingcamel
[1] 2023-10-13 14:14:52,299 INFO  [Insert] (Camel (camel-1) thread #2 - timer://insertCamel) Inserting defaultcamel 1697206492298
[1] 2023-10-13 14:14:52,310 INFO  [Insert] (Camel (camel-1) thread #2 - timer://insertCamel) Inserted defaultcamel 1697206492298
[1] 2023-10-13 14:14:52,311 INFO  [Insert] (Camel (camel-1) thread #2 - timer://insertCamel) Inserting testingcamel 1697206492298
[1] 2023-10-13 14:14:52,312 INFO  [Insert] (Camel (camel-1) thread #2 - timer://insertCamel) Inserted testingcamel 1697206492298
[1] 2023-10-13 14:14:52,342 INFO  [DEBUG] (Camel (camel-1) thread #3 - timer://selectCamel) Exchange[Headers: {CamelMessageTimestamp=1697206492298, CamelSqlRowCount=1, firedTime=Fri Oct 13 14:14:52 UTC 2023}, BodyType: java.util.ArrayList, Body: [{ID=1, TIMESTAMP=1697206492298}]]
[1] 2023-10-13 14:14:52,345 INFO  [DEBUG] (Camel (camel-1) thread #3 - timer://selectCamel) Exchange[Headers: {CamelMessageTimestamp=1697206492298, CamelSqlRowCount=1, firedTime=Fri Oct 13 14:14:52 UTC 2023}, BodyType: java.util.ArrayList, Body: [{ID=1, TIMESTAMP=1697206492298}]]
[1] 2023-10-13 14:14:53,299 INFO  [Insert] (Camel (camel-1) thread #2 - timer://insertCamel) Inserting defaultcamel 1697206493299
[1] 2023-10-13 14:14:53,301 INFO  [DEBUG] (Camel (camel-1) thread #3 - timer://selectCamel) Exchange[Headers: {CamelMessageTimestamp=1697206493299, CamelSqlRowCount=1, firedTime=Fri Oct 13 14:14:53 UTC 2023}, BodyType: java.util.ArrayList, Body: [{ID=1, TIMESTAMP=1697206492298}]]
[1] 2023-10-13 14:14:53,302 INFO  [Insert] (Camel (camel-1) thread #2 - timer://insertCamel) Inserted defaultcamel 1697206493299
[1] 2023-10-13 14:14:53,303 INFO  [Insert] (Camel (camel-1) thread #2 - timer://insertCamel) Inserting testingcamel 1697206493299
[1] 2023-10-13 14:14:53,304 INFO  [DEBUG] (Camel (camel-1) thread #3 - timer://selectCamel) Exchange[Headers: {CamelMessageTimestamp=1697206493299, CamelSqlRowCount=1, firedTime=Fri Oct 13 14:14:53 UTC 2023}, BodyType: java.util.ArrayList, Body: [{ID=1, TIMESTAMP=1697206492298}]]
[1] 2023-10-13 14:14:53,304 INFO  [Insert] (Camel (camel-1) thread #2 - timer://insertCamel) Inserted testingcamel 1697206493299

```

