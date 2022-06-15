# Camel Knative Source Salesforce Example

This example demonstrates how to create a Camel based Knative source for Salesforce and a Camel route which consumes it.

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

The cluster also needs to have Knative installed and working. Refer to the [official Knative documentation](https://knative.dev/v0.15-docs/install/) for information on how to install it in your cluster.

[Check if the Knative Serving is installed](didact://?commandId=vscode.didact.requirementCheck&text=kserving-project-check$$kubectl%20api-resources%20--api-group=serving.knative.dev$$kservice%2Cksvc&completion=Verified%20Knative%20services%20installation. "Verifies if Knative Serving is installed"){.didact}

*Status: unknown*{#kserving-project-check}

[Check if the Knative Eventing is installed](didact://?commandId=vscode.didact.requirementCheck&text=keventing-project-check$$kubectl%20api-resources%20--api-group=messaging.knative.dev$$inmemorychannels&completion=Verified%20Knative%20eventing%20services%20installation. "Verifies if Knative Eventing is installed"){.didact}

*Status: unknown*{#keventing-project-check}

**Knative Camel Source installed on the cluster**

The cluster also needs to have installed the Knative Camel Source from the camel.yaml in the [Eventing Sources release page](https://github.com/knative/eventing-contrib/releases)

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
cd 10-knative-source-salesforce
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$cd%2010-knative-source-salesforce&completion=Executed%20command. "Opens a new terminal and sends the command above"){.didact})


We're going to create a namespace named `camel-source` for running the example. To create it, execute the following command:

```
kubectl create namespace camel-source
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20create%20namespace%20camel-source&completion=New%20project%20creation. "Opens a new terminal and sends the command above"){.didact})


Now we can set the `camel-source` namespace as default namespace for the following commands:

```
kubectl config set-context --current --namespace=camel-source
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20config%20set-context%20--current%20--namespace%3Dcamel-source&completion=New%20project%20creation. "Opens a new terminal and sends the command above"){.didact})

You need to install Camel K in the `camel-source` namespace (or globally in the whole cluster).
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

## 2. Preparing the environment

### 2.1. Collecting Salesforce credentials

This examples uses OAuth 2.0 Refresh Token Flow, which requires 3 credentials collected from Salesforce - `Client ID`, `Client Secret` and `Refresh Token`.

you might want to sign up with your own Developer account at developer.salesforce.com. After you have done that, you’ll need to create a Connected Application for your integration.

Here is an example workflow of creating a Connected Application and get required credentials.For more details, please check Salesforce online manual
https://help.salesforce.com/articleView?id=extend_code_overview.htm&type=5

Note: This was written with Salesforce Lightening user interface. Classic user interface might have slightly different menu and/or workflow.

#### 2.1.1. Creating a Connected Application
1. Go to `PLATFORM TOOLS > Apps > App Manager` in a navigation tree at the left of Salesforce Setup screen
1. Click `New Connected App` button at the upper right
1. Enter `camelk_knative_source_salesforce` for Connected App Name
1. Enter your email address for Contact Email
1. Check Enable OAuth Settings
1. Enter `https://login.salesforce.com/services/oauth2/success` for Callback URL
1. Choose 2 OAuth scopes, `Access and manage your data (api)` and `Perform requests on your behalf at any time (refresh_token, offline_access)`, then click Add button
1. Click Save button and then click Continue button
1. You will be redirected to `Manage Connected Apps` page. `Consumer Key` and `Consumer Secret` are available in API section. Use `Consumer Key` as a `Client ID` and `Consumer Secret` as a `Client Secret`.
1. Go to `PLATFORM TOOLS > Apps > Connected Apps > Manage Connected Apps`
1. Click `Edit` next to the `camelk_knative_source_salesforce`
1. In OAuth Policies section,
  1. Choose `Relax IP restrictions` for IP Relaxation
  1. Choose `Refresh token is valid until revoked`
1. Click `Save`

#### 2.1.2. Getting an OAuth refresh token
In your browser go to the URL change the `__YOUR_CLIENT_ID_HERE__` with your connected application Consumer Key:

```
https://login.salesforce.com/services/oauth2/authorize?response_type=token&client_id=YOUR_CLIENT_ID_HERE&redirect_uri=https://login.salesforce.com/services/oauth2/success&display=touch
```

Allow access to the application, and you’ll end up on a page with `refresh_token`, something like:

```
https://login.salesforce.com/services/oauth2/success#access_token=..&refresh_token=<refresh_token>&instance_url=...&issued_at=...&signature=...&scope=...&token_type=Bearer
```

### 2.2. Creating a Kubernetes Secret from Salesforce credentials

This repository contains a simple [salesforce.properties](didact://?commandId=vscode.openFolder&projectFilePath=10-knative-source-salesforce/salesforce.properties&completion=Opened%20the%salesforce.properties%20file "Opens the salesforce.properties file"){.didact} that can be used to generate a Kubernetes Secret with the Salesforce credential by replacing `<Your Client ID>`, `<Your Client Secret>` and `<Your Refresh Token>` with the actual values.

```
kubectl create secret generic salesforce --from-file=salesforce.properties
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20create%20secret%20generic%20salesforce%20--from-file%3Dsalesforce.properties&completion=secret%20%22salesforce%22%20created. "Create a secret with Salesforce credentials"){.didact})

### 2.3. Creating a Knative Eventing channel

As the example levareges [Knative Eventing channels](https://knative.dev/v0.15-docs/eventing/channels/), we need to create the one that the example will use:

```
kubectl apply -f salesforce-channel.yaml
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20apply%20-f%20salesforce-channel.yaml&completion=inmemorychannel.messaging.knative.dev/salesforce$20created. "Create a Knative InMemoryChannel named Salesforce"){.didact})

## 3. Running a Camel Source

This repository contains a Camel Source based on the [Salesforce component](https://camel.apache.org/components/latest/salesforce-component.html) that forward Salesforce event notifications to a Knative channel named `salesforce`

Use the following command to deploy the Camel Source:

```
kubectl apply -f salesforce-source.yaml
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20apply%20-f%20salesforce-source.yaml&completion=camelsource.sources.knative.dev/camel-salesforce-source%20created. "Opens a new terminal and sends the command above"){.didact})


## 4. Running a basic integration to echo a message in the console when a Salesforce Contact is created


```
kamel run SalesforceConsumer.java --dev
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kamel%20run%20SalesforceConsumer.java%20--dev&completion=Camel%20K%20salesforce-consumer%20integration%20run%20in%20dev%20mode. "Opens a new terminal and sends the command above"){.didact})


If everything is ok, after the build phase finishes, you should see the Camel integration running and printing the message when you create a Contact in Salesforce.

## 5. Uninstall

To cleanup everything, execute the following command:

```kubectl delete namespace camel-source```

([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20delete%20namespace%20camel-source&completion=Removed%20the%20namespace%20from%20the%20cluster. "Cleans up the cluster after running the example"){.didact})
