# Camel K Volumes examples

In this section you will find examples about usage of `Persistent Volumes`.

```
kubectl apply -f minikube-pvc.yaml

kamel run --dev --volume my-pv-claim:/tmp/data pvc-producer.yaml

kamel run --dev --volume my-pv-claim:/tmp/data pvc-consumer.yaml
```