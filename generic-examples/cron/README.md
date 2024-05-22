# Camel K Cron Examples

This example demonstrates how you can create cron routes in Camel K.

You can find more information about Apache Camel and Apache Camel K on the [official Camel website](https://camel.apache.org).

## Requirements for running this example

**Kubectl CLI**
The Kubernetes `kubectl` CLI tool will be used to interact with the Kubernetes cluster.

**Connection to a Kubernetes cluster**
You need to connect to a Kubernetes cluster in order to run the example.

**Apache Camel K CLI ("kamel")**
You need the Apache Camel K CLI ("kamel") in order to access all Camel K features.

## Preparing the cluster

In order to follow this example: 
- Create a namespace called `kubectl create ns camel-cron` and set that namespace as default `kubectl config set-context --current --namespace=camel-cron`.
- Install the Camel K operator in that namespace. In most cases, running `kamel install` should be enough. If not, you can read the [installation instructions for your specific cluster here](https://camel.apache.org/camel-k/latest/installation/installation.html).

## Understanding the Example
This example uses the cron component to trigger a route at a specific time interval specified in a cron expression. The route transforms data from the cron source and sends it to the logger.

The integration is all contained in a single file named `cron.groovy`

## Running the Example

This example is a Camel K integration that is scheduled to run periodically, printing "Hello Camel K using CronJob" once every minute.

Use the following command to run it:

```
kamel run --dev cron.yaml
```

The build phase may take some seconds to complete. When it finishes, if everything is okay, the integration should be running periodically every minute.

To confirm that the integration is being run as a Kubernetes Cronjob, run:

```
kubectl get cronjob
```
You should see a Kubernetes CronJob named "cron" with the specified schedule.

To see the pods starting and being destroyed, run:

```
kubectl get pod -w
```

Hit `Ctrl+C` to exit

## Modifying the Cron expression

Since the integration is running in dev mode, you can change the integration code and Camel K will redeploy the changes automatically.

Change the cron expression: \
[open the `cron.yaml` file](./cron.yaml)
and change the first endpoint from `cron:tab?schedule=*+*+*+*+?` to `cron:tab?schedule=*/2+*+*+*+?`, then save the file.

You should see the new integration starting up in the terminal window and replacing the old one.
It should print "Hello Camel K using CronJob" once every 2 minutes in the terminal window. \
**Note:** *Cron only allows for a minimum of 1 minute apart between jobs*

Change the cron expression again: \
[open the `cron.yaml` file](./cron.yaml)
and change the first endpoint to `cron:tab?schedule=*/3+*+*+*+?`, then save the file.

Now, after a few seconds, it should print "Hello Camel K using CronJob" once every 3 minutes in the terminal.

Hit `Ctrl+C` to exit dev mode and terminate execution

## Uninstall

To clean up everything, execute the following command:

```
kubectl config set-context --current --namespace=default
kubectl delete namespace camel-cron
```