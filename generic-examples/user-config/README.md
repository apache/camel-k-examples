# Camel K user configuration examples

Find useful examples about how to include a configuration property or a configuration file in Camel K.

These examples use the Camel-k CLI `kamel run ...` command.

## Configuration properties

These examples show the different ways you can use **properties** to configure integrations.

* provide build time properties with `--build-property`
  * `kamel run --dev --build-property=quarkus.application.name=my-super-application build-property-route.yaml`
  * `kamel run --dev --build-property=file:quarkus.properties build-property-file-route.yaml`

* provide runtime properties with `-p`
  * `kamel run --dev -p file:my.properties property-file-route.yaml`
  * `kamel run --dev -p my.message=test-property property-route.yaml`

## Configuration Configmap

These examples show the different ways you can use **ConfigMaps** to configure integrations.

* provide properties from a configmap with `--config`

```
kubectl create configmap my-cm --from-literal=my-configmap-key="configmap content"
kamel run --dev --config configmap:my-cm config-configmap-route.yaml
```

* provide properties from a configmap with `--resource`

```
kubectl create configmap my-cm --from-literal=my-configmap-key="configmap content"
kamel run --dev --resource configmap:my-cm resource-configmap-route.yaml

kamel run --dev --resource configmap:my-cm@/tmp/app/data resource-configmap-location-route.yaml

kubectl create configmap my-cm-multi --from-literal=my-configmap-key="configmap content" --from-literal=my-configmap-key-2="another content"
kamel run --dev --resource configmap:my-cm-multi/my-configmap-key-2@/tmp/app/data/test.txt resource-configmap-key-location-route.yaml
```

## Configuration Secret

These examples show the different ways you can use **Secrets** to configure integrations.

* provide properties from a secret with `--config`

```
kubectl create secret generic my-sec --from-literal=my-secret-key="very top secret"
kamel run --dev --config secret:my-sec config-secret-route.yaml

kubectl create secret generic my-secret-properties --from-file=my.properties
kamel run --dev --config secret:my-secret-properties config-secret-property-route.yaml

kubectl create secret generic my-sec-multi --from-literal=my-secret-key="very top secret" --from-literal=my-secret-key-2="even more secret"
kamel run --dev --config secret:my-sec-multi/my-secret-key-2 config-secret-key-route.yaml
```

* provide properties from a secret with `--resource`

```
kubectl create secret generic my-sec --from-literal=my-secret-key="very top secret"
kamel run --dev --resource secret:my-sec resource-secret-route.yaml

kamel run --dev --resource secret:my-sec@/tmp/app/data resource-secret-location-route.yaml
```

## Configuration Resource file

These examples show the different ways you can use **resource files** to configure integrations.

* provide properties from a resource file with `--config`

```
kubectl create configmap my-cm-file --from-file=resources-data.txt
kamel run --dev --config configmap:my-cm-file config-file-route.yaml
```

* provide properties from a resource file with `--resource`

```
kubectl create configmap my-cm-file --from-file=resources-data.txt
kamel run --dev --resource configmap:my-cm-file resource-file-route.yaml

kamel run --dev --resource configmap:my-cm-file@/tmp/input resource-file-location-route.yaml

kubectl create configmap my-cm-zip --from-file=resources-data.zip
kamel run --dev --resource configmap:my-cm-zip -d camel-zipfile resource-file-binary-route.yaml
```
