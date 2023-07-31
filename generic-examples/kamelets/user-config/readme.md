# User Config

This example shows how you can configure applications built on Kamelets as Integrations or Pipes/KameletBindings. The Integrations and Pipes are provided as Kubernetes Custom Resource and make no use of the `kamel` CLI.

This example uses use out of the box Kamelets Timer Source and Log Sink.

## Integrations using Kamelets

You have multiple options when using parameters in your integrations.

### Basic usage parameters

1. `camel` trait

You can define your kamelets parameters by adding them directly inside you integration using the `camel` trait.

Run the following command to create your [integration](./kamelets-route-embedded-parameters.yaml):
```
$ kubectl apply -f kamelets-route-embedded-parameters.yaml
```

After a while you will be able to watch your logs:
```
$ kubectl logs -l camel.apache.org/integration=kamelets-route-embedded-parameters

2023-08-01 08:18:35,119 INFO  [embeddedlog] (Camel (camel-1) thread #1 - timer://tick) Exchange[ExchangePattern: InOnly, BodyType: String, Body: embedded message]
```

2. Kubernetes Secret and `mount` trait

You can define your kamelets parameters by declaring them in a kubernetes secret and binding them to your integration using the `mount` trait.

Run the following command to create the secret related containing your parameters values:
```
$ kubectl create secret generic my-kamelets-secret --from-file=application.properties
```

Run the following command to create your [integration](./kamelets-route-secret-parameters.yaml):
```
$ kubectl apply -f kamelets-route-secret-parameters.yaml
```

After a while you will be able to watch your logs:
```
$ kubectl logs -l camel.apache.org/integration=kamelets-route-embedded-parameters

2023-08-01 08:36:11,084 INFO  [PropertySecretLogger] (Camel (camel-1) thread #1 - timer://tick) Exchange[ExchangePattern: InOnly, BodyType: String, Body: Property secret]
```

### Named configuration

The usage of a named configuration allows for different properties for the separate usages of the same kamelet in an integration.

1. `camel` trait

You can use named configuration with the `camel` trait to allow different configuration for the same kamelet.

Run the following command to create your [integration](./kamelets-route-embedded-parameters-2.yaml):
```
$ kubectl apply -f kamelets-route-embedded-parameters-2.yaml
```

After a while you will be able to watch your logs with the 2 loggers:
```
$ kubectl logs -l camel.apache.org/integration=kamelets-route-embedded-parameters-2

2023-08-01 09:17:28,818 INFO  [myconfiglog] (Camel (camel-1) thread #1 - timer://tick) Exchange[ExchangePattern: InOnly, BodyType: String, Body: My Config message]
2023-08-01 09:17:28,819 INFO  [myotherconfiglog] (Camel (camel-1) thread #1 - timer://tick) Exchange[ExchangePattern: InOnly, BodyType: String, Body: My Config message]
```

2. Kubernetes Secret and `mount` trait

You can define your kamelets parameters by declaring them in a kubernetes secret and binding them to your integration using the `mount` trait.

Run the following command to create the secret related containing your parameters values:
```
$ kubectl create secret generic my-kamelets-secret-2 --from-file=config.properties
```

Run the following command to create your [integration](./kamelets-route-secret-parameters-2.yaml):
```
$ kubectl apply -f kamelets-route-secret-parameters-2.yaml
```

After a while you will be able to watch your logs with the 2 loggers:
```
$ kubectl logs -l camel.apache.org/integration=kamelets-route-embedded-parameters

2023-08-01 09:44:38,824 INFO  [SecretLogger] (Camel (camel-1) thread #1 - timer://tick) Exchange[ExchangePattern: InOnly, BodyType: String, Body: My config secret]
2023-08-01 09:44:38,824 INFO  [SecretOtherLogger] (Camel (camel-1) thread #1 - timer://tick) Exchange[ExchangePattern: InOnly, BodyType: String, Body: My config secret]
```


## Pipes/KameletBindings

Pipes allow using the same declarative style of binding  to connect to various sources/sinks kamelets. The way to configure them is slightly different from Integrations.


### Basic usage parameters

1. Pipe integration configuration

You can define your kamelets parameters by adding them directly inside you integration using the `.spec.integration.configuration` object with type `property`.

Run the following command to create your [integration](./kamelets-pipe-embedded-parameters.yaml):
```
$ kubectl apply -f kamelets-pipe-embedded-parameters.yaml
```

After a while you will be able to watch your logs:
```
$ kubectl logs -l camel.apache.org/integration=kamelets-pipe-embedded-parameters

2023-08-01 12:20:36,168 INFO  [embeddedlog] (Camel (camel-1) thread #1 - timer://tick) Exchange[ExchangePattern: InOnly, BodyType: String, Body: embedded message]
```

2. Kubernetes Secret and `mount` trait

You can define your kamelets parameters by declaring them in a kubernetes secret and binding them to your integration using the `mount` trait.

Run the following command to create the secret related containing your parameters values:
```
$ kubectl create secret generic my-pipe-secret --from-file=application.properties
```

Run the following command to create your [integration](./kamelets-route-secret-parameters.yaml):
```
$ kubectl apply -f kamelets-route-secret-parameters.yaml
```

After a while you will be able to watch your logs:
```
$ kubectl logs -l camel.apache.org/integration=kamelets-pipe-secret-parameters

2023-08-01 08:36:11,084 INFO  [PropertySecretLogger] (Camel (camel-1) thread #1 - timer://tick) Exchange[ExchangePattern: InOnly, BodyType: String, Body: Property secret]
```

### Implicit configuration

This is a case when you inject your properties without refering them on the kamelet declaration.

1. Pipe integration configuration

You can define your kamelets parameters by adding them directly inside you integration using the `.spec.integration.configuration` object with type `property`. This version uses the default property name.

Run the following command to create your [pipe](./kamelets-pipe-embedded-parameters.yaml):
```
$ kubectl apply -f kamelets-pipe-embedded-parameters.yaml
```

After a while you will be able to watch your logs:
```
$ kubectl logs -l camel.apache.org/integration=kamelets-pipe-embedded-parameters

2023-08-01 15:38:04,919 INFO  [embeddedlog] (Camel (camel-1) thread #1 - timer://tick) Exchange[ExchangePattern: InOnly, BodyType: String, Body: embedded message]
```

2. Kubernetes Secret and `mount` trait

You can define your kamelets parameters by declaring them in a kubernetes secret and binding them to your integration using the `mount` trait.

Run the following commands to create the secrets related containing your default parameters values:
```
$ kubectl create secret generic my-timer-source-secret --from-file=timer-source.properties
$ kubectl create secret generic my-log-sink-secret --from-file=log-sink.properties
```

Run the following command to create your [pipe](./kamelets-pipe-secret-parameters-2.yaml):
```
$ kubectl apply -f kamelets-pipe-secret-parameters-2.yaml
```

After a while you will be able to watch your logs:
```
$ kubectl logs -l camel.apache.org/integration=kamelets-pipe-secret-parameters-2

2023-08-01 15:39:50,124 INFO  [SecretLogger] (Camel (camel-1) thread #1 - timer://tick) Exchange[ExchangePattern: InOnly, BodyType: String, Body: My secret]
```

The `mount` trait can also be declared as an annotation.

Run the following command to create your [pipe](./kamelets-pipe-secret-parameters-3.yaml):
```
$ kubectl apply -f kamelets-pipe-secret-parameters-3.yaml
```

After a while you will be able to watch your logs:
```
$ kubectl logs -l camel.apache.org/integration=kamelets-pipe-secret-parameters-3

2023-08-01 15:40:24,266 INFO  [SecretLogger] (Camel (camel-1) thread #1 - timer://tick) Exchange[ExchangePattern: InOnly, BodyType: String, Body: My secret]
```

## Help and contributions

If you hit any problem using Camel or have some feedback, then please see the [community support page](https://camel.apache.org/community/support/) and let us know.

We also love contributors, so [get involved](https://camel.apache.org/community/contributing/) :-)

The Camel riders!