# Camel Knative Source Slack Example

This example demonstrates how to use a Camel based Knative source to receive messages written to a Slack channel.

You can find more information about Apache Camel and Apache Camel K on the [official Camel website](https://camel.apache.org).

## Before you begin

Read the general instructions in the root [README.md](../README.md) file for setting up your environment and the Kubernetes cluster before looking at this example.

Make sure you've read the Camel K [installation instructions](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific
cluster before starting the example.

You should open this file with [Didact](https://marketplace.visualstudio.com/items?itemName=redhat.vscode-didact) if available on your IDE.

## Requirements

<a href='didact://?commandId=vscode.didact.validateAllRequirements' title='Validate all requirements!'><button>Validate all Requirements at Once!</button></a>

**Kubectl CLI**

The Kubernetes `kubectl` CLI tool will be used to interact with the Kubernetes cluster.

[Check if the Kubectl CLI is installed](didact://?commandId=vscode.didact.cliCommandSuccessful&text=kubectl-requirements-status$$kubectl%20help "Tests to see if `kubectl help` returns a 0 return code"){.didact}

*Status: unknown*{#kubectl-requirements-status}

**Connection to a Kubernetes cluster**

You need to connect to a Kubernetes cluster in order to run the example.

[Check if you're connected to a Kubernetes cluster](didact://?commandId=vscode.didact.cliCommandSuccessful&text=cluster-requirements-status$$kubectl%20get%20pod "Tests to see if `kubectl get pod` returns a 0 return code"){.didact}

*Status: unknown*{#cluster-requirements-status}

**Apache Camel K CLI ("kamel")**

You need the Apache Camel K CLI ("kamel") in order to access all Camel K features.

[Check if the Apache Camel K CLI ("kamel") is installed](didact://?commandId=vscode.didact.requirementCheck&text=kamel-requirements-status$$kamel%20version$$Camel%20K%20Client "Tests to see if `kamel version` returns a result"){.didact}

*Status: unknown*{#kamel-requirements-status}

**Knative installed on the cluster**

The cluster also needs to have Knative installed and working. Refer to the [official Knative documentation](https://knative.dev/v0.15-docs/install/) for information on how to install it in your cluster.

[Check if the Knative Serving is installed](didact://?commandId=vscode.didact.requirementCheck&text=kserving-project-check$$kubectl%20api-resources%20--api-group=serving.knative.dev$$kservice%2Cksvc "Verifies if Knative Serving is installed"){.didact}

*Status: unknown*{#kserving-project-check}

[Check if the Knative Eventing is installed](didact://?commandId=vscode.didact.requirementCheck&text=keventing-project-check$$kubectl%20api-resources%20--api-group=messaging.knative.dev$$inmemorychannels "Verifies if Knative Eventing is installed"){.didact}

*Status: unknown*{#keventing-project-check}

**Knative Camel Source installed on the cluster**

The cluster also needs to have installed the Knative Camel Source from the camel.yaml in the [Eventing Sources release page](https://github.com/knative/eventing-contrib/releases/tag/v0.15.0).

[Check if the Knative Camel Source is installed](didact://?commandId=vscode.didact.requirementCheck&text=kservice-project-check$$kubectl%20api-resources%20--api-group=sources.knative.dev$$camelsources "Verifies if Knative Camel Source is installed"){.didact}

*Status: unknown*{#kservice-project-check}

### Optional Requirements

The following requirements are optional. They don't prevent the execution of the demo, but may make it easier to follow.

**VS Code Extension Pack for Apache Camel**

The VS Code Extension Pack for Apache Camel provides a collection of useful tools for Apache Camel K developers,
such as code completion and integrated lifecycle management. They are **recommended** for the tutorial, but they are **not**
required.

You can install it from the VS Code Extensions marketplace.

[Check if the VS Code Extension Pack for Apache Camel by Red Hat is installed](didact://?commandId=vscode.didact.extensionRequirementCheck&text=extension-requirement-status$$redhat.apache-camel-extension-pack "Checks the VS Code workspace to make sure the extension pack is installed"){.didact}

*Status: unknown*{#extension-requirement-status}

## 1. Creating the Slack App

Slack Apps have access to the full scope of the platform, including sending messages to conversations and receiving event notifications.

1. Go to the [Slack API](https://api.slack.com/) website and create a new Slack App.
1. Go to the OAuth & Permissions page and add the `channels:history` user token scope to your app to grant it permission to view messages in the user’s public channels.
1. Install the app to your workspace.
1. Copy the generated token and set it as the value of the `slack.token` property in [slack.properties](didact://?commandId=vscode.openFolder&projectFilePath=11-knative-source-slack/slack.properties "Opens the slack.properties file"){.didact}.

## 2. Preparing the namespace

Let's open a terminal and go to the example directory:

```
cd 11-knative-source-slack
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$cd%2011-knative-source-slack "Opens a new terminal and sends the command above"){.didact})


We're going to create a namespace named `camel-source` for running the example. To create it, execute the following command:

```
kubectl create namespace camel-source
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20create%20namespace%20camel-source "Opens a new terminal and sends the command above"){.didact})


Now we can set the `camel-source` namespace as default namespace for the following commands:

```
kubectl config set-context --current --namespace=camel-source
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20config%20set-context%20--current%20--namespace%3Dcamel-source "Opens a new terminal and sends the command above"){.didact})

You need to install Camel K in the `camel-source` namespace (or globally in the whole cluster).
In many settings (e.g. OpenShift, CRC), it's sufficient to execute the following command to install Camel K:

```
kamel install
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kamel%20install "Opens a new terminal and sends the command above"){.didact})

NOTE: The `kamel install` command requires some prerequisites to be successful in some situations, e.g. you need to enable the registry addon on Minikube. Refer to the [Camel K install guide](https://camel.apache.org/camel-k/latest/installation/installation.html) for cluster-specific instructions.

To check that Camel K is installed we'll retrieve the IntegrationPlatform object from the namespace:

```
kubectl get integrationplatform
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20get%20integrationplatform "Opens a new terminal and sends the command above"){.didact})

You should find an IntegrationPlatform in status `Ready`.

You can now proceed to the next section.

## 3. Preparing the environment

Create a Kubernetes Secret with the [slack.properties](didact://?commandId=vscode.openFolder&projectFilePath=11-knative-source-slack/slack.properties "Opens the slack.properties file"){.didact} file.

```
kubectl create secret generic slack --from-file=slack.properties
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20create%20secret%20generic%20slack%20--from-file%3Dslack.properties "Create a secret with lack credentials"){.didact})

As the example levareges [Knative Eventing channels](https://knative.dev/v0.15-docs/eventing/channels/), we need to create the one that the example will use:

```
kubectl apply -f slack-channel.yaml
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20apply%20-f%20slack-channel.yaml "Create a Knative InMemoryChannel named 'slack'"){.didact})

## 4. Running a Camel Source

This repository contains a simple Camel Source based on the [Slack component](https://camel.apache.org/components/latest/slack-component.html) that forward messages written to the `generic` Slack channel to a Knative channel.

Use the following command to deploy the Camel Source:

```
kubectl apply -f slack-source.yaml
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20apply%20-f%20slack-source.yaml "Opens a new terminal and sends the command above"){.didact})

## 5. Running a basic integration to echo messages from Slack in the console

```
kamel run slack-consumer.groovy --dev
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kamel%20run%20slack-consumer.groovy%20--dev "Opens a new terminal and sends the command above"){.didact})

If everything is OK, after the build phase finishes, you should see the Camel integration running and printing the messages you write in the `generic` Slack channel.

## 6. Uninstall

To cleanup everything, execute the following command:

```kubectl delete namespace camel-source```

([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20delete%20namespace%20camel-source "Cleans up the cluster after running the example"){.didact})
