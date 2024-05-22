# Modeline Camel K examples

Find useful examples about how to use Modeline in a Camel K integration.

```
kamel run --dev modeline-build-property-route.yaml

kamel run --dev modeline-build-property-file-route.yaml

kamel run --dev modeline-property-route.yaml

kamel run --dev modeline-property-file-route.yaml

kubectl create secret generic my-sec --from-literal=my-secret-key="very top secret"
kamel run --dev modeline-config-secret-route.yaml

kubectl create configmap my-cm-file --from-file=resources-data.txt
kamel run --dev modeline-config-file-route.yaml

kamel run --dev modeline-resource-file-route.yaml
```