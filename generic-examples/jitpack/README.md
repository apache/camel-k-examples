# Using Jitpack in Camel K example

This example demonstrates how to use Jitpack in a Camel K integration.

You can find more information about Apache Camel and Apache Camel K on the [official Camel website](https://camel.apache.org).

## Before you begin

Read the general instructions in the [root README.md file](/README.md) for setting up your environment and the Kubernetes cluster before looking at this example.

Make sure you've read the [installation instructions](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific
cluster before starting the example.

## How to package a dependency

Camel K supports [Jitpack](https://jitpack.io/) to allow packaging your local code into a running Integration. The operator will recognize the major `git` repositories and provide all the needed configuration to package the dependency and locally install into Camel K artifacts repository. As an example, we can use [a sample jitpack project](https://github.com/squakez/samplejp), which contains some developments on `main` branch, a final release tagged as `v1.0` and other developments ongoing on `1.0.0` branch.

Within your route, you can import the code as you'd normally do with any other library:

```
import org.apache.camel.builder.RouteBuilder;
import acme.App;

public class Jitpack extends RouteBuilder {
  @Override
  public void configure() throws Exception {
      from("timer:tick?period=2000")
        .setBody()
          .simple(App.capitalize("hello"))
        .to("log:info");

  }
}
```

Once done, you just reference the project (dependency) in `kamel run -d` option, ie `-d github:squakez/samplejp`.

## Running the Example

### Package the default branch (main)

You can choose to use the default dependency without specifying a tag or branch. That will fetch the source code on `main` branch:
```
kamel run Jitpack.java --dev -d github:squakez/samplejp
```
Running the above command, should log `HELLO` to the terminal every 2 seconds:
```
...
[1] 2022-07-06 22:45:12,763 INFO  [info] (Camel (camel-1) thread #1 - timer://tick) Exchange[ExchangePattern: InOnly, BodyType: String, Body: HELLO]
...
```

### Package another branch (1.0.0)

You can choose to compile the source code stored on a given branch, ie, on `1.0.0` branch:
```
kamel run Jitpack.java --dev -d github:squakez/samplejp:1.0.0-SNAPSHOT
```
Executing the above command, should log `v1.0.0-SNAPSHOT:HELLO` to the terminal every 2 seconds:

```
...
[1] 2022-07-06 22:47:44,867 INFO  [info] (Camel (camel-1) thread #1 - timer://tick) Exchange[ExchangePattern: InOnly, BodyType: String, Body: v1.0.0-SNAPSHOT:HELLO]
...
```
### Package a fixed release tagged

You can also choose to package the source code released with a `tag`, ie `v1.0`:
```
kamel run Jitpack.java --dev -d github:squakez/samplejp:v1.0
```
Running the command above should log `v1.0.0:HELLO` to the terminal every 2 seconds:
```
...
[1] 2022-07-06 22:51:47,466 INFO  [info] (Camel (camel-1) thread #1 - timer://tick) Exchange[ExchangePattern: InOnly, BodyType: String, Body: v1.0.0:HELLO]
...
```
