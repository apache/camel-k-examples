# Timer Source to Log Sink

This example shows how to create a simple timer `event source` and a log `event sink`. The timer events emitted are consumed by a simple logging connector which will print out those events.

## Additional requirement for running this example
- You should have Knative properly installed on your cluster ([see installation guide](https://knative.dev/docs/install/))

## Create events source and sink

Let's start by creating the timer event source and log event sink as `kamelets`.
```
$ kubectl apply -f timer-source.kamelet.yaml
$ kubectl apply -f log-sink.kamelet.yaml
```

You can check the newly created `kamelet`s in the list.
```
$ kubectl get kamelets

NAME           PHASE
log-sink       Ready
timer-source   Ready
```

## Create channel destination

Let's continue by creating a `knative` destination.
```
$ kubectl apply -f timer-events.yaml
```

## Binding events

We can now bind the timer event source to produce events on the destination with the `timer-source.binding.yaml` configuration.
```
$ kubectl apply -f timer-source.binding.yaml
```
In a similar fashion you can bind to the log sink in order to consume those events with the `log-sink.binding.yaml` configuration.
```
$ kubectl apply -f log-sink.binding.yaml
```
You can check the newly created bindings listing the `KameletBidings`.
```
$ kubectl get KameletBindings

NAME                 PHASE
log-event-sink       Ready
timer-event-source   Ready
```

### Watch the event sink

After a while you will be able to watch the event consumed by the underlying `log-event-sink` integration:

```
$ kamel log log-event-sink

[1] Monitoring pod log-event-sink-00001-deployment-7cf6d488c9-2nbx8
...
[1] 2022-08-20 08:56:01,284 INFO  [sink] (executor-thread-0) Exchange[ExchangePattern: InOnly, BodyType: byte[], Body: Hello world!]
[1] 2022-08-20 08:56:02,284 INFO  [sink] (executor-thread-0) Exchange[ExchangePattern: InOnly, BodyType: byte[], Body: Hello world!]
[1] 2022-08-20 08:56:03,285 INFO  [sink] (executor-thread-0) Exchange[ExchangePattern: InOnly, BodyType: byte[], Body: Hello world!]
[1] 2022-08-20 08:56:04,286 INFO  [sink] (executor-thread-0) Exchange[ExchangePattern: InOnly, BodyType: byte[], Body: Hello world!]
[1] 2022-08-20 08:56:05,285 INFO  [sink] (executor-thread-0) Exchange[ExchangePattern: InOnly, BodyType: byte[], Body: Hello world!]
```