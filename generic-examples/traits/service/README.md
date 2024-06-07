# SERVICE EXAMPLE

This folder contains examples of how to use a trait `service`. You can use them to learn more about how to enable services for integrations deployed on the cluster.

To access integration outside the cluster you can enable a nodePort when you deploy integration. An example is `./RestDSL.java.`

To run this integrations use:
```shell
kamel run --dev --trait service.enabled=true --trait service.node-port=true RestDSL.java

kubectl port-forward svc/rest-dsl 8080:80
curl http://localhost:8080/hello
```

You can also optionally decide to just go with the default clusterIP if you do not want your integration to be directly exposed to the outside world. 

```shell
kamel run --dev --trait service.enabled=true rest-dsl.yaml
```