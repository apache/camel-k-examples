# Camel K Health Trait

In this section you will find examples about fine tuning your `Integration` using **Health** `trait` capability.

The Health trait can be used to activate and configure the Health Probes on the integration container.

## Before you begin

Read the general instructions in the [root README.md file](../../README.md) for setting up your environment and the Kubernetes cluster before looking at this example.

Make sure you've read the [installation instructions](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific cluster before starting the example.

## Basic usage

By default the trait is disabled so it needs to be activated.

To activate the trait and configure the liveness Probes, run the integration

```sh
kamel run \
    --name=health \
    HealthChecks.java \
    --trait health.enabled=true \
    --trait health.liveness-probe-enabled=true \
    --trait health.liveness-initial-delay=30 \
    --trait health.liveness-period=10 \
    --trait health.liveness-failure-threshold=5 \
    --trait health.liveness-success-threshold=1 \
    --trait health.liveness-timeout=2
```


When you check the values declared by the pod spec
```sh
kubectl get pods --selector="camel.apache.org/integration"="health" -o jsonpath='{.items[*].spec.containers[*].livenessProbe}'
```

You should get a result with the values you defined
```json
{"failureThreshold":5,"httpGet":{"path":"/q/health/live","port":8080,"scheme":"HTTP"},"initialDelaySeconds":30,"periodSeconds":10,"successThreshold":1,"timeoutSeconds":2}
```

## Probes with HTTPS scheme

Generate a certificate for the integration (provide at least Country Name, can use defaults for rest)
```sh
openssl req -x509 -newkey rsa:4096 -keyout /tmp/integration-key.pem -out /tmp/integration-cert.pem -days 365 -nodes
```

Create a secret containing the generated certificate
```sh
kubectl create secret tls my-tls-secret --cert=/tmp/integration-cert.pem --key=/tmp/integration-key.pem
```

Run the integration
 ```sh
 kamel run \
    --property quarkus.http.ssl.certificate.file=/etc/camel/conf.d/_secrets/my-tls-secret/tls.crt \
    --property quarkus.http.ssl.certificate.key-file=/etc/camel/conf.d/_secrets/my-tls-secret/tls.key \
    --config secret:my-tls-secret \
    --pod-template patch_probe.yaml \
    --name health \
    HealthChecks.java \
    --trait health.enabled=true \
    --trait health.liveness-probe-enabled=true \
    --trait health.liveness-scheme=HTTPS \
    --trait health.readiness-probe-enabled=true \
    --trait health.readiness-scheme=HTTPS
```


When you check the values declared by the pod spec for the readinessProbe
```sh
kubectl get pods --selector="camel.apache.org/integration"="health" -o jsonpath='{.items[*].spec.containers[*].readinessProbe}'
```

You should get a result with the scheme you defined
```json
{"failureThreshold":3,"httpGet":{"path":"/q/health/ready","port":8443,"scheme":"HTTPS"},"periodSeconds":10,"successThreshold":1,"timeoutSeconds":1}
```