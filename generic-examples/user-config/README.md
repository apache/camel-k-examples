# Camel K user configuration examples

Find useful examples about how to include a configuration property or a configuration file in Camel K.

These examples use the Camel-k CLI `kamel run ...` command.

## Configuration properties

These examples show the different  ways you can **properties** to configure integrations.

* provide build time properties with `--build-property`
  * `build-property-route.groovy`
  * `build-property-file-route.groovy`

* provide runtime properties with `-p`
  * `property-file-route.groovy`
  * `property-route.groovy`

## Configuration Configmap

These examples show the different ways you can use **ConfigMaps** to configure integrations.

* provide properties from a configmap with `--config`
  * `config-configmap-route.groovy`

* provide properties from a configmap with `--resource`
  * `resource-configmap-route.groovy`
  * `resource-configmap-location-route.groovy`
  * `resource-configmap-key-location-route.groovy`

## Configuration Secret

These examples show the different ways you can use **Secrets** to configure integrations.

* provide properties from a secret with `--config`
* `config-secret-route.groovy`
* `config-secret-property-route.groovy`
* `config-secret-key-route.groovy`

* provide properties from a secret with `--resource`
* `resource-secret-route.groovy`
* `resource-secret-location-route.groovy`



## Configuration Resource file (Camel-K <= 1.12.1)

**These examples only work with a version of  Camel-K <= 1.12.1**

These examples show the different ways you can use **resource files** to configure integrations.

* provide properties from a resource file with `--config`
  * `config-file-route.groovy`

* provide properties from a resource file with `--resource`
* `resource-file-route.groovy`
* `resource-file-location-route.groovy`
* `resource-file-binary-route.groovy`
* `resource-file-base64-route.groovy`
```
