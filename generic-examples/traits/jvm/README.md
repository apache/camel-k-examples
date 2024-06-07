# Camel K JVM Trait

In this section you will find examples about fine-tuning your `Integration` using **JVM** `trait` capability.

Create a configmap holding a jar in order to simulate the presence of a dependency on the runtime image

```shell
kubectl create configmap my-dep --from-file=sample-1.0.jar
```

Run the integration
```shell
kamel run --dev --resource configmap:my-dep --trait jvm.classpath=/etc/camel/resources/my-dep/sample-1.0.jar Classpath.java

[1] 2024-06-07 09:17:06,422 INFO  [route1] (Camel (camel-1) thread #1 - timer://tick) Hello World!
[1] 2024-06-07 09:17:07,410 INFO  [route1] (Camel (camel-1) thread #1 - timer://tick) Hello World!
[1] 2024-06-07 09:17:08,410 INFO  [route1] (Camel (camel-1) thread #1 - timer://tick) Hello World!
```
