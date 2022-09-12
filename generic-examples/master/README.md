# Using Master in Camel K example

This example shows how to start a route only on a single instance of a Camel K integration. This is done using the `Master` component.

You can find more information about Apache Camel and Apache Camel K on the [official Camel website](https://camel.apache.org).

## Before you begin

Read the general instructions in the [root README.md file](/README.md) for setting up your environment and the Kubernetes cluster before looking at this example.

Make sure you've read the [installation instructions](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific
cluster before starting the example.

## Understanding the Example
- [Master.java](./Master.java): defines a route that will be started only on a single instance/pod. This is defined by prefixing the camel endpoint with `master:someName`.
Under the hood, the pod replicas take part in a leader election where each pod races to become the leader. The pod that emerges as leader will be the only instance that is active and consumes from the specified camel endpoint. If the leader fails, another pod becomes the new leader, thus becoming the only instance that consumes from the camel endpoint.

## Running the example
**Note**: it is recommended to use a different service account other than "default" when running the integration because special permissions are added to the integration service account in order to read/write configmaps and read pods.

Run the integration:
```
kamel run Master.java
```
In this example, we will use the [stern](https://github.com/stern/stern) CLI tool to view the logs because it allows us to view multiple pod logs at the same time. <br>
Since only a single instance is created, that becomes the leader and logs the routed message: _"This message is printed by a single pod, even if you increase the number of replicas!"_.
```
stern master
```
```
...
master-6f8df78d54-lbnwp integration 2022-09-11 18:38:01,413 INFO  [org.apa.cam.com.kub.clu.loc.KubernetesLeadershipController] (Camel (camel-1) thread #2 - CamelKubernetesLeadershipController) Pod[master-6f8df78d54-lbnwp] Current pod is becoming the new leader now...
...
master-6f8df78d54-lbnwp integration 2022-09-11 18:38:01,641 INFO  [org.apa.cam.com.mas.MasterConsumer] (Camel (camel-1) thread #3 - CamelKubernetesLeaderNotifier) Leadership taken. Consumer started: timer://master?period=1000
master-6f8df78d54-lbnwp integration 2022-09-11 18:38:01,650 INFO  [org.apa.cam.com.kub.clu.loc.TimedLeaderNotifier] (Camel (camel-1) thread #3 - CamelKubernetesLeaderNotifier) The list of cluster members has changed: [master-6f8df78d54-lbnwp]
...
master-6f8df78d54-lbnwp integration 2022-09-11 18:38:02,696 INFO  [info] (Camel (camel-1) thread #4 - timer://master) Exchange[ExchangePattern: InOnly, BodyType: String, Body: This message is printed by a single pod, even if you increase the number of replicas!]
master-6f8df78d54-lbnwp integration 2022-09-11 18:38:03,641 INFO  [info] (Camel (camel-1) thread #4 - timer://master) Exchange[ExchangePattern: InOnly, BodyType: String, Body: This message is printed by a single pod, even if you increase the number of replicas!]
```

Increase the number of replicas:
```
kubectl scale it master --replicas 3
```
Still looking at the logs from `stern master` we see that although more instances have been created, the routed message is still being logged by the leader pod alone.

```
...
master-6f8df78d54-ncdl9 integration 2022-09-11 18:39:30,386 INFO  [org.apa.cam.com.kub.clu.loc.TimedLeaderNotifier] (Camel (camel-1) thread #3 - CamelKubernetesLeaderNotifier) The list of cluster members has changed: [master-6f8df78d54-lbnwp, master-6f8df78d54-ncdl9, master-6f8df78d54-2t66l]
...
master-6f8df78d54-lbnwp integration 2022-09-11 18:39:42,792 INFO  [info] (Camel (camel-1) thread #4 - timer://master) Exchange[ExchangePattern: InOnly, BodyType: String, Body: This message is printed by a single pod, even if you increase the number of replicas!]
master-6f8df78d54-lbnwp integration 2022-09-11 18:39:43,792 INFO  [info] (Camel (camel-1) thread #4 - timer://master) Exchange[ExchangePattern: InOnly, BodyType: String, Body: This message is printed by a single pod, even if you increase the number of replicas
...
```
In the event that the leader fails, the other instances race to become the new leader. Eventually, only one becomes the leader. Let's try that.

Kill the leader pod:
```
kubectl delete pod <your-leader-pod-name>
```
Looking at the logs from `stern master`, we see the other pod replicas attempt to become the new leader. Later, a new leader emerges that carries out the function of printing the message:
```
...
master-6f8df78d54-ncdl9 integration 2022-09-11 19:38:53,427 INFO  [org.apa.cam.com.kub.clu.loc.KubernetesLeadershipController] (Camel (camel-1) thread #2 - CamelKubernetesLeadershipController) Pod[master-6f8df78d54-ncdl9] Leadership has been lost by old owner. Trying to acquire the leadership...
master-6f8df78d54-2t66l integration 2022-09-11 19:38:53,945 INFO  [org.apa.cam.com.kub.clu.loc.KubernetesLeadershipController] (Camel (camel-1) thread #2 - CamelKubernetesLeadershipController) Pod[master-6f8df78d54-2t66l] Leadership has been lost by old owner. Trying to acquire the leadership...
...
master-6f8df78d54-2t66l integration 2022-09-11 19:39:11,641 INFO  [org.apa.cam.com.kub.clu.loc.KubernetesLeadershipController] (Camel (camel-1) thread #2 - CamelKubernetesLeadershipController) Pod[master-6f8df78d54-2t66l] Current pod is becoming the new leader now...
...
master-6f8df78d54-2t66l integration 2022-09-11 19:39:23,891 INFO  [info] (Camel (camel-1) thread #4 - timer://master) Exchange[ExchangePattern: InOnly, BodyType: String, Body: This message is printed by a single pod, even if you increase the number of replicas!]
master-6f8df78d54-2t66l integration 2022-09-11 19:39:24,892 INFO  [info] (Camel (camel-1) thread #4 - timer://master) Exchange[ExchangePattern: InOnly, BodyType: String, Body: This message is printed by a single pod, even if you increase the number of replicas!]
```