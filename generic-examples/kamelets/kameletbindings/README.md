# KameletBindings examples

Find useful examples about how to use KameletBindings.

## Understanding the examples
- [env-var-writer.kamelet.yaml](./env-var-writer.kamelet.yaml): sink kamelet, logs specified env variables.
- [kb-env-vars.yaml](./kb-env-vars.yaml): kamelet binding, connects the _env-var-writer_ kamelet directly to a Camel URI, adds env variables to the integration container using the `environment` trait.
- [kb-log-trait-annotation.yaml](./kb-log-trait-annotation.yaml): kamelet binding, connects two Camel URIs, uses the `logging` trait to customize the integration behaviour.

## Running the examples
### Timer-to-Log kamelet binding
First, we apply the kamelet used in this binding:
```
kubectl apply -f env-var-writer.kamelet.yaml
```

Next, we apply the kamelet binding
```
kubectl apply -f kb-env-vars.yaml
```
A _timer-to-log_ integration is created that implements the binding. To view the logs, run:
```
kamel log timer-to-log
...
[1] 2022-08-18 22:25:22,083 INFO  [bar] (Camel (camel-1) thread #1 - timer://foo) Exchange[ExchangePattern: InOnly, BodyType: String, Body: value1 / value2]
[1] 2022-08-18 22:25:23,083 INFO  [bar] (Camel (camel-1) thread #1 - timer://foo) Exchange[ExchangePattern: InOnly, BodyType: String, Body: value1 / value2]
...
```

### Timer-2-Log-Annotation kamelet binding
Apply the kamelet binding:
```
kubectl apply -f kb-log-trait-annotation.yaml
```
A _timer-2-log-annotation_ integration is created that implements the binding. You should see information logged at the `DEBUG` level. The messages should NOT use color. To view the logs:
```
kamel log timer-2-log-annotation
...
[1] 2022-08-19 12:38:46,871 DEBUG [org.apa.cam.pro.SendProcessor] (Camel (camel-1) thread #1 - timer://foo) >>>> log://bar Exchange[]
[1] 2022-08-19 12:38:46,871 INFO  [bar] (Camel (camel-1) thread #1 - timer://foo) Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
...
```
