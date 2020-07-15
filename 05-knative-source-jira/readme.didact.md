# Camel Knative JIRA Source Basic Example

This example demonstrates how to get started with Camel based Knative sources by showing you some of the most important
features that you should know before trying to develop more complex examples.

You can find more information about Apache Camel and Apache Camel K on the [official Camel website](https://camel.apache.org).

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

**Knative installed on the cluster**

The cluster also needs to have Knative installed and working. Refer to the [official Knative documentation](https://knative.dev/v0.15-docs/install/ for information on how to install it in your cluster.

[Check if the Knative Serving is installed](didact://?commandId=vscode.didact.requirementCheck&text=kserving-project-check$$kubectl%20api-resources%20--api-group=serving.knative.dev$$kservice%2Cksvc&completion=Verified%20Knative%20services%20installation. "Verifies if Knative Serving is installed"){.didact}

*Status: unknown*{#kserving-project-check}

[Check if the Knative Eventing is installed](didact://?commandId=vscode.didact.requirementCheck&text=keventing-project-check$$kubectl%20api-resources%20--api-group=messaging.knative.dev$$inmemorychannels&completion=Verified%20Knative%20eventing%20services%20installation. "Verifies if Knative Eventing is installed"){.didact}

*Status: unknown*{#keventing-project-check}

**Knative Camel Source installed on the cluster**

The cluster also needs to have installed the Knative Camel Source from the camel.yaml in the [Eventing Sources release page](https://github.com/knative/eventing-contrib/releases/tag/v0.15.0)

[Check if the Knative Camel Source is installed](didact://?commandId=vscode.didact.requirementCheck&text=kservice-project-check$$kubectl%20api-resources%20--api-group=sources.knative.dev$$camelsources&completion=Verified%20Knative%20Camel%20Source%20installation. "Verifies if Knative Camel Source is installed"){.didact}

*Status: unknown*{#kservice-project-check}

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
cd 05-knative-source-jira
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$cd%2005-knative-source-jira&completion=Executed%20command. "Opens a new terminal and sends the command above"){.didact})


We're going to create a namespace named `camel-jira-source` for running the example. To create it, execute the following command:

```
kubectl create namespace camel-jira-source
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20create%20namespace%20camel-jira-source&completion=New%20project%20creation. "Opens a new terminal and sends the command above"){.didact})


Now we can set the `camel-jira-source` namespace as default namespace for the following commands:

```
kubectl config set-context --current --namespace=camel-jira-source
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20config%20set-context%20--current%20--namespace%3Dcamel-jira-source&completion=New%20project%20creation. "Opens a new terminal and sends the command above"){.didact})

You need to install Camel K in the `camel-jira-source` namespace (or globally in the whole cluster).
In many settings (e.g. OpenShift, CRC), it's sufficient to execute the following command to install Camel K:

```
kamel install  --maven-repository https://maven.atlassian.com/repository/public
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kamel%20install%20--maven-repository%20https://maven.atlassian.com/repository/public&completion=Installing%20Camel-K. "Opens a new terminal and sends the command above"){.didact})

NOTE: The `kamel install` command requires some prerequisites to be successful in some situations, e.g. you need to enable the registry addon on Minikube. Refer to the [Camel K install guide](https://camel.apache.org/camel-k/latest/installation/installation.html) for cluster-specific instructions.
We need also add atlassian repository so jira client can be sucessfully downloaded. 

To check that Camel K is installed we'll retrieve the IntegrationPlatform object from the namespace:

```
kubectl get integrationplatform
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20get%20integrationplatform&completion=Executed%20Command. "Opens a new terminal and sends the command above"){.didact})

You should find an IntegrationPlatform in status `Ready`.

You can now proceed to the next section.

## 2. Preparing the environment

This repository contains a simple [jira.properties](didact://?commandId=vscode.openFolder&projectFilePath=0x-jira-service-event-source/jira.properties&completion=Opened%20the%jira.properties%20file "Opens the jira.properties file"){.didact} that contains basic configuration properties for the jira component:

 -  `jira.url`  URL to jira instance 
 - `jira.username` Username for jira 
 - `jira.password` - password/access token *
 - `jira.jql` - JQL that contains at least PROJECT (`PROJECT=CAMEL`)

NOTE: For atlassian cloud jira password can't be used so it's necessary to create an API token and use it instead of user password. https://confluence.atlassian.com/cloud/api-tokens-938839638.html

```
kubectl create secret generic jira --from-file=jira.properties
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20create%20secret%20generic%20jira%20--from-file%3Djira.properties&completion=secret%20%22jira%22%20created. "Create a secret with jira configuration"){.didact})

As the example levareges [Knative Eventing channels](https://knative.dev/docs/eventing/channels/), we need to create the one that the example will use:

```
kubectl apply -f jira-channel.yaml
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20apply%20-f%20jira-channel.yaml&completion=inmemorychannel.messaging.knative.dev/telegram$20created. "Create a Knative InMemoryChannel named jira"){.didact})

## 2. Running a Camel Source

This repository contains a simple Camel Source based on the [Jira component](https://camel.apache.org/components/latest/jira-component.html) that sends new comments/issues created in certain jira project. To forward new comments change uri defined in source definiton from ` uri: jira:newIssues` to `uri: jira:newComments`. 

Use the following command to deploy the Camel Source:

```
kubectl apply -f jira-new-issues.yaml
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20apply%20-f%20jira-new-issues.yaml&completion=camelsource.sources.knative.dev/camel-telegram-source%20created. "Opens a new terminal and sends the command above"){.didact})


## 2. Running a basic integration to see new issues in the console


```
kamel run jira-consumer.groovy --dev
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kamel%20run%20jira-consumer.groovy%20--dev&completion=Camel%20K%20jira-consumer%20integration%20run%20in%20dev%20mode. "Opens a new terminal and sends the command above"){.didact})


If everything is ok, after the build phase finishes, you should see the Camel integration running and printing the mesage you have sent to tour Telegram Bot.

## 4. Uninstall

To cleanup everything, execute the following command:

```kubectl delete namespace camel-jira-source```

([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20delete%20namespace%20camel-jira-source&completion=Removed%20the%20namespace%20from%20the%20cluster. "Cleans up the cluster after running the example"){.didact})
