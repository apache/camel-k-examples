# Kamelet Pipe examples

Find useful examples about how to use Pipes.

## Understanding the examples
- [env-var-writer.kamelet.yaml](./env-var-writer.kamelet.yaml): sink kamelet, logs specified env variables.
- [kamelet-pipe-env-vars.yaml](./kamelet-pipe-env-vars.yaml): kamelet pipe, connects the _env-var-writer_ kamelet directly to a Camel URI, adds env variables to the integration container using the `environment` trait.
- [kamelet-pipe-log-trait-annotation.yaml](./kamelet-pipe-log-trait-annotation.yaml): kamelet pipe, connects two Camel URIs, uses the `logging` trait to customize the integration behaviour.

## Running the examples

### Timer-to-Log kamelet pipe

First, we apply the sink kamelet used in this example:
```
kubectl apply -f env-var-writer.kamelet.yaml
```

Next, we apply the source kamelet that connects to the sink via a pipe
```
kubectl apply -f kamelet-pipe-env-vars.yaml
```
A _timer-to-log_ integration is created. To view the logs, run:
```
kamel log timer-to-log
...
[1] 2022-08-18 22:25:22,083 INFO  [bar] (Camel (camel-1) thread #1 - timer://foo) Exchange[ExchangePattern: InOnly, BodyType: String, Body: value1 / value2]
[1] 2022-08-18 22:25:23,083 INFO  [bar] (Camel (camel-1) thread #1 - timer://foo) Exchange[ExchangePattern: InOnly, BodyType: String, Body: value1 / value2]
...
```

### Timer-2-Log-Annotation kamelet pipe

Apply the kamelet binding:
```
kubectl apply -f kamelet-pipe-log-trait-annotation.yaml
```

A _timer-2-log-annotation_ integration is created that implements the binding. You should see information logged at the `DEBUG` level. The messages should NOT use color. To view the logs:
```
kamel log timer-2-log-annotation
...
[1] 2022-08-19 12:38:46,871 DEBUG [org.apa.cam.pro.SendProcessor] (Camel (camel-1) thread #1 - timer://foo) >>>> log://bar Exchange[]
[1] 2022-08-19 12:38:46,871 INFO  [bar] (Camel (camel-1) thread #1 - timer://foo) Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
...
```
