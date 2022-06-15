# Camel K Basic Example

This example demonstrates how to get started with Camel K by showing you some of the most important 
features that you should know before trying to develop more complex examples.

You can find more information about Apache Camel and Apache Camel K on the [official Camel website](https://camel.apache.org).

## Available online

If you don't want to setup your own Kubernetes cluster or your IDE and you just want to get started quickly, this example is also available online in the following places:

- [Camel K Basic Example at learn.openshift.com](https://learn.openshift.com/middleware/courses/middleware-camelk/camel-k-basic).

You don't need to follow the remainder of this readme if you choose to follow the online version.

## Before you begin

Read the general instructions in the [root README.md file](../README.md) for setting up your environment and the Kubernetes cluster before looking at this example.

Make sure you've read the [installation instructions](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific 
cluster before starting the example.

You should open this file with [Didact](https://marketplace.visualstudio.com/items?itemName=redhat.vscode-didact) if available on your IDE.

## Requirements

<a href='didact://?commandId=vscode.didact.validateAllRequirements' title='Validate all requirements!'><button>Validate all Requirements at Once!</button></a>

**Kubectl CLI**

The Kubernetes `kubectl` CLI tool will be used to interact with the Kubernetes cluster.

[Check if the Kubectl CLI is installed](didact://?commandId=vscode.didact.cliCommandSuccessful&text=kubectl-requirements-status$$kubectl%20help&completion=Checked%20kubectl%20tool%20availability "Tests to see if `kubectl help` returns a 0 return code"){.didact}

*Status: unknown*{#kubectl-requirements-status}

**Connection to a Kubernetes cluster**

You need to connect to a Kubernetes cluster in order to run the example.

[Check if you're connected to a Kubernetes cluster](didact://?commandId=vscode.didact.cliCommandSuccessful&text=cluster-requirements-status$$kubectl%20get%20pod&completion=Checked%20Kubernetes%20connection "Tests to see if `kubectl get pod` returns a 0 return code"){.didact}

*Status: unknown*{#cluster-requirements-status}

**Apache Camel K CLI ("kamel")**

You need the Apache Camel K CLI ("kamel") in order to access all Camel K features.

[Check if the Apache Camel K CLI ("kamel") is installed](didact://?commandId=vscode.didact.requirementCheck&text=kamel-requirements-status$$kamel%20version$$Camel%20K%20Client&completion=Checked%20if%20Camel%20K%20CLI%20is%20available%20on%20this%20system. "Tests to see if `kamel version` returns a result"){.didact}

*Status: unknown*{#kamel-requirements-status}

### Optional Requirements

The following requirements are optional. They don't prevent the execution of the demo, but may make it easier to follow.

**VS Code Extension Pack for Apache Camel**

The VS Code Extension Pack for Apache Camel provides a collection of useful tools for Apache Camel K developers,
such as code completion and integrated lifecycle management. They are **recommended** for the tutorial, but they are **not**
required.

You can install it from the VS Code Extensions marketplace.

[Check if the VS Code Extension Pack for Apache Camel by Red Hat is installed](didact://?commandId=vscode.didact.extensionRequirementCheck&text=extension-requirement-status$$redhat.apache-camel-extension-pack&completion=Camel%20extension%20pack%20is%20available%20on%20this%20system. "Checks the VS Code workspace to make sure the extension pack is installed"){.didact}

*Status: unknown*{#extension-requirement-status}

## 1. Preparing the namespace

Let's open a terminal and go to the example directory:

```
cd 01-basic
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$cd%2001-basic&completion=Executed%20command. "Opens a new terminal and sends the command above"){.didact})


We're going to create a namespace named `camel-basic` for running the example. To create it, execute the following command:

```
kubectl create namespace camel-basic
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20create%20namespace%20camel-basic&completion=New%20project%20creation. "Opens a new terminal and sends the command above"){.didact})


Now we can set the `camel-basic` namespace as default namespace for the following commands:

```
kubectl config set-context --current --namespace=camel-basic
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20config%20set-context%20--current%20--namespace%3Dcamel-basic&completion=New%20project%20creation. "Opens a new terminal and sends the command above"){.didact})

You need to install Camel K in the `camel-basic` namespace (or globally in the whole cluster).
In many settings (e.g. OpenShift, CRC), it's sufficient to execute the following command to install Camel K:

```
kamel install
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kamel%20install&completion=Installing%20Camel%20K. "Opens a new terminal and sends the command above"){.didact})

NOTE: The `kamel install` command requires some prerequisites to be successful in some situations, e.g. you need to enable the registry addon on Minikube. Refer to the [Camel K install guide](https://camel.apache.org/camel-k/latest/installation/installation.html) for cluster-specific instructions.

To check that Camel K is installed we'll retrieve the IntegrationPlatform object from the namespace:

```
kubectl get integrationplatform
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20get%20integrationplatform&completion=Executed%20Command. "Opens a new terminal and sends the command above"){.didact})

You should find an IntegrationPlatform in status `Ready`.

You can now proceed to the next section.

## 2. Running a basic integration

This repository contains a simple Camel K integration that periodically prints 
a "Hello World..." message.

The integration is all contained in a single file named `Basic.java` ([open](didact://?commandId=vscode.openFolder&projectFilePath=01-basic/Basic.java&completion=Opened%20the%20Basic.java%20file "Opens the Basic.java file"){.didact}).

> **Note:** the `Basic.java` file contains a simple integration that uses the `timer` and `log` components.
> Dependency management is automatically handled by Camel K that imports all required libraries from the Camel
> catalog via code inspection. This means you can use all 300+ Camel components directly in your routes.

We're ready to run the integration on our `camel-basic` project in the cluster.

Use the following command to run it in "dev mode", in order to see the logs in the integration terminal:

```
kamel run Basic.java --dev
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kamel%20run%20Basic.java%20--dev&completion=Camel%20K%20basic%20integration%20run%20in%20dev%20mode. "Opens a new terminal and sends the command above"){.didact})

If everything is ok, after the build phase finishes, you should see the Camel integration running and continuously printing "Hello World!..." in the terminal window.

When running in dev mode, you can change the integration code and let Camel K redeploy the changes automatically.

To try this feature,
[open the `Basic.java` file](didact://?commandId=vscode.openFolder&projectFilePath=01-basic/Basic.java&completion=Opened%20the%20Basic.java%20file "Opens the Basic.java file"){.didact} 
and change "Hello World" into "Ciao Mondo", then save the file.
You should see the new integration starting up in the terminal window and replacing the old one.

[**To exit dev mode and terminate the execution**, just click here](didact://?commandId=vscode.didact.sendNamedTerminalCtrlC&text=camelTerm&completion=Camel%20K%20basic%20integration%20interrupted. "Interrupt the current operation on the terminal"){.didact} 
or hit `ctrl+c` on the terminal window.

> **Note:** When you terminate a "dev mode" execution, also the remote integration will be deleted. This gives the experience of a local program execution, but the integration is actually running in the remote cluster.

To keep the integration running and not linked to the terminal, you can run it without "dev mode", just run:

```
kamel run Basic.java
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kamel%20run%20Basic.java&completion=Camel%20K%20basic%20integration%20run. "Opens a new terminal and sends the command above"){.didact})



After executing the command, you should be able to see it among running integrations:

```
kubectl get integrations
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20get%20integrations&completion=Getting%20running%20integrations. "Opens a new terminal and sends the command above"){.didact})

An integration named `basic` should be present in the list and it should be in status `Running`. There's also a `kamel get` command which is an alternative way to list all running integrations.

> **Note:** the first time you've run the integration, an IntegrationKit (basically, a container image) has been created for it and 
> it took some time for this phase to finish. When you run the integration a second time, the existing IntegrationKit is reused 
> (if possible) and the integration reaches the "Running" state much faster.
>


Even if it's not running in dev mode, you can still see the logs of the integration using the following command:

```
kamel log basic
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kamel%20log%20basic&completion=Show%20integration%20logs. "Opens a new terminal and sends the command above"){.didact})

The last parameter ("basic") is the name of the running integration for which you want to display the logs.

[**Click here to terminate the log stream**](didact://?commandId=vscode.didact.sendNamedTerminalCtrlC&text=camelTerm&completion=Camel%20K%20basic%20integration%20interrupted. "Interrupt the current operation on the terminal"){.didact} 
or hit `ctrl+c` on the terminal window.

> **Note:** Your IDE may provide an "Apache Camel K Integrations" panel where you can see the list of running integrations and also open a window to display the logs.


## 3. Applying configuration and routing

The second example is a bit more complex as it shows how to configure the integration using external properties and 
also a simple content-based router.

The integration is contained in a file named `Routing.java` ([open](didact://?commandId=vscode.openFolder&projectFilePath=01-basic/Routing.java&completion=Opened%20the%20Routing.java%20file "Opens the Routing.java file"){.didact}).

The routes use two configuration properties named `items` and `priority-marker` that should be provided using an external file such
as the `routing.properties` ([open](didact://?commandId=vscode.openFolder&projectFilePath=01-basic/routing.properties&completion=Opened%20the%20routing.properties%20file "Opens the routing.properties file"){.didact}).

The `Routing.java` file shows how to inject properties into the routes via property placeholders and also the usage of the `@PropertyInject` annotation.

To run the integration, we should link the integration to the property file providing configuration for it:

```
kamel run Routing.java --property file:./routing.properties --dev
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kamel%20run%20Routing.java%20--property%20file:./routing.properties%20--dev&completion=Run%20Routing.java%20integration. "Opens a new terminal and sends the command above"){.didact})

Wait for the integration to be running (you should see the logs streaming in the terminal window).

You can now open both the [Routing.java](didact://?commandId=vscode.openFolder&projectFilePath=01-basic/Routing.java&completion=Opened%20the%20Routing.java%20file "Opens the Routing.java file"){.didact} file or
the [routing.properties](didact://?commandId=vscode.openFolder&projectFilePath=01-basic/routing.properties&completion=Opened%20the%20routing.properties%20file "Opens the routing.properties file"){.didact}
file, make some changes and see the integration redeployed.
For example, change the word `door` with `*door` to see it sent to the priority queue.

[**Click here to exit dev mode and terminate the execution**](didact://?commandId=vscode.didact.sendNamedTerminalCtrlC&text=camelTerm&completion=Camel%20K%20basic%20integration%20interrupted. "Interrupt the current operation on the terminal"){.didact}, 
or hit `ctrl+c` on the terminal window.

This will also terminate the execution of the integration.

## 4. Running integrations as Kubernetes CronJobs

The previous example can be automatically deployed as a Kubernetes CronJob if the delay between executions is changed into a value that can be expressed by a cron tab expression.

For example, you can change the first endpoint (`timer:java?period=3000`) into the following: `timer:java?period=60000` (1 minute between executions). [Open the Routing.java file](didact://?commandId=vscode.openFolder&projectFilePath=01-basic/Routing.java&completion=Opened%20the%20Routing.java%20file "Opens the Routing.java file"){.didact} to apply the changes.

Now you can run the integration again:

```
kamel run Routing.java --property file:./routing.properties
```

([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kamel%20run%20Routing.java%20--property%20file:./routing.properties&completion=Run%20Routing.java%20integration%20as%20CronJob. "Opens a new terminal and sends the command above"){.didact})

Now you'll see that Camel K has materialized a cron job:

```
kubectl get cronjob
```

([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20get%20cronjob&completion=Get%20CronJobs. "Opens a new terminal and sends the command above"){.didact})

You'll find a Kubernetes CronJob named "routing".

The running behavior changes, because now there's no pod always running (beware you should not store data in memory when using the cronJob strategy).

You can see the pods starting and being destroyed by watching the namespace:

```
kubectl get pod -w
```

([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20get%20pod%20-w&completion=Watch%20Pods. "Opens a new terminal and sends the command above"){.didact})

[**Click here to exit the current command**](didact://?commandId=vscode.didact.sendNamedTerminalCtrlC&text=camelTerm&completion=Camel%20K%20basic%20integration%20interrupted. "Interrupt the current operation on the terminal"){.didact},
or hit `ctrl+c` on the terminal window.

To see the logs of each integration starting up, you can use the `kamel log` command:

```
kamel log routing
```

([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kamel%20log%20routing&completion=Watch%20integration%20logs. "Opens a new terminal and sends the command above"){.didact})

You should see every minute a JVM starting, executing a single operation and terminating.

[**Click here to exit the current command**](didact://?commandId=vscode.didact.sendNamedTerminalCtrlC&text=camelTerm&completion=Camel%20K%20basic%20integration%20interrupted. "Interrupt the current operation on the terminal"){.didact},
or hit `ctrl+c` on the terminal window.

The CronJob behavior is controlled via a Trait called `cron`. Traits are the main way to configure high level Camel K features, to 
customize how integrations are rendered.

To disable the cron feature and use the deployment strategy, you can run the integration with:

```
kamel run Routing.java --property file:./routing.properties -t cron.enabled=false
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kamel%20run%20Routing.java%20--property%20file:./routing.properties%20-t%20cron.enabled=false&completion=Run%20Routing.java%20integration%20without%20CronJobs. "Opens a new terminal and sends the command above"){.didact})

This will disable the cron trait and restore the classic behavior (always running pod).

You should see it reflected in the logs (which will be printed every minute by the same JVM):

```
kamel log routing
```

([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kamel%20log%20routing&completion=Watch%20integration%20logs. "Opens a new terminal and sends the command above"){.didact})


[**Click here to exit the current command**](didact://?commandId=vscode.didact.sendNamedTerminalCtrlC&text=camelTerm&completion=Camel%20K%20basic%20integration%20interrupted. "Interrupt the current operation on the terminal"){.didact},
or hit `ctrl+c` on the terminal window.

You can continue to hack on the examples.

## 5. Uninstall

To cleanup everything, execute the following command:

```kubectl delete namespace camel-basic```

([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20delete%20namespace%20camel-basic&completion=Removed%20the%20namespace%20from%20the%20cluster. "Cleans up the cluster after running the example"){.didact})
