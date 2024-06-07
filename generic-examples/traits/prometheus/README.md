# Camel K Prometheus Trait

In this section you will find examples about fine-tuning your `Integration` using **Prometheus** `trait` capability.

A Prometheus-compatible endpoint is configured with the Prometheus trait. 
When utilising the Prometheus operator, it also generates a PodMonitor resource, which allows the endpoint to be scraped automatically.

To get statistics about the number of events successfully handled by the `Integration`,execute the `MyIntegration.java` route via:

In case the prometheus operator is not installed in your cluster, run:

```shell
kamel run --dev --trait prometheus.enabled=true --trait prometheus.pod-monitor=false MyIntegration.java
```

Alternatively, you can quickly deploy the Prometheus operator and then run:

```shell
kubectl create -f https://raw.githubusercontent.com/prometheus-operator/prometheus-operator/main/bundle.yaml

kamel run --dev --trait prometheus.enabled=true MyIntegration.java
```

The metrics can be retrieved by port-forwarding this service, e.g.:

```shell
kubectl port-forward svc/prometheus-operator 8080:8080

curl http://localhost:8080/metrics
```

Similarly other use cases can be to retrieve information on unprocessed events, number of retries made to process an event, etc. 
For more information on Integration monitoring refer to the [Camel K Integration Monitoring](https://camel.apache.org/camel-k/next/observability/monitoring/integration.html) documentation.

