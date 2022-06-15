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

### Optional Requirements

The following requirements are optional. They don't prevent the execution of the demo, but may make it easier to follow.

**VS Code Extension Pack for Apache Camel**
The [VS Code Extension Pack for Apache Camel](https://marketplace.visualstudio.com/items?itemName=redhat.apache-camel-extension-pack) provides a collection of useful tools for Apache Camel K developers,
such as code completion and integrated lifecycle management. They are **recommended** for the tutorial, but they are **not**
required.
You can install it from the VS Code Extensions marketplace.

## Preparing the cluster
In order to follow this example: 
- Create a namespace called `camel-cron` and set that namespace as default.
- Install the Camel K operator in that namespace. In most cases, running `kamel install` should be enough. If not, you can read the [installation instructions for your specific cluster here](https://camel.apache.org/camel-k/latest/installation/installation.html).

## Understanding the Example
This example uses the cron component to trigger a route at a specific time interval specified in a cron expression. The route transforms data from the cron source and sends it to the logger.

The integration is all contained in a single file named `cron.groovy`

## Running the Example

This example is a Camel K integration that is scheduled to run periodically, printing "Hello Camel K using CronJob" once every minute.

Use the following command to run it:

```
kamel run cron.groovy
```

The build phase may take some seconds to complete. When it finishes, if everything is okay, the integration should be running periodically every minute. \
To view the logs of the running integration, run the command:

```
kamel log cron
```

You should see the Camel K integration start, print "Hello Camel K using CronJob" and shutdown periodically every minute in the terminal window.

You can terminate the log stream with `Ctrl+C`

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

Run the integration in "dev mode" in order to see the logs in the terminal:
```
kamel run cron.groovy --dev
```

Since the integration is running in dev mode, you can change the integration code and Camel K will redeploy the changes automatically.

Change the cron expression: \
[open the `cron.groovy` file](./cron.groovy)
and change the first endpoint from `cron:tab?schedule=*+*+*+*+?` to `cron:tab?schedule=*/2+*+*+*+?`, then save the file.

You should see the new integration starting up in the terminal window and replacing the old one.
It should print "Hello Camel K using CronJob" once every 2 minutes in the terminal window. \
**Note:** *Cron only allows for a minimum of 1 minute apart between jobs*

Change the cron expression again: \
[open the `cron.groovy` file](./cron.groovy)
and change the first endpoint to `cron:tab?schedule=*/3+*+*+*+?`, then save the file.

Now, after a few seconds, it should print "Hello Camel K using CronJob" once every 3 minutes in the terminal.

Hit `Ctrl+C` to exit dev mode and terminate execution

## Uninstall

To cleanup everything, execute the following command:
```
kubectl delete namespace camel-cron
```