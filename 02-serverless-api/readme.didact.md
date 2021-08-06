# Camel K Serverless API Example

This example demonstrates how to write an API based Camel K integration, from the design of the OpenAPI definition 
to the implementation of the specific endpoints up to the deployment as serverless API in Knative.

In this specific example, the API enables users to store generic objects, such as files, in a backend system, allowing all CRUD operation on them.

The backend is an Amazon AWS S3 bucket that you might provide. In alternative, you'll be given instructions on how to 
create a simple [Minio](https://min.io/) backend, which uses a S3 compatible protocol.

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

[Check if the Knative is installed](didact://?commandId=vscode.didact.requirementCheck&text=kservice-project-check$$kubectl%20api-resources%20--api-group=serving.knative.dev$$kservice%2Cksvc&completion=Verified%20Knative%20services%20installation. "Verifies if Knative is installed"){.didact}

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
cd 02-serverless-api
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$cd%2002-serverless-api&completion=Executed%20command. "Opens a new terminal and sends the command above"){.didact})


We're going to create a namespace named `camel-api` for running the example. To create it, execute the following command:

```
kubectl create namespace camel-api
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20create%20namespace%20camel-api&completion=New%20project%20creation. "Opens a new terminal and sends the command above"){.didact})


Now we can set the `camel-api` namespace as default namespace for the following commands:

```
kubectl config set-context --current --namespace=camel-api
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20config%20set-context%20--current%20--namespace%3Dcamel-api&completion=New%20project%20creation. "Opens a new terminal and sends the command above"){.didact})

You need to install Camel K in the `camel-api` namespace (or globally in the whole cluster).
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

## 2. Configuring the object storage backend

You have two alternative options for setting up the S3 backend that will be used to store the objects via the Camel K API: 
you can use an existing S3 bucket of your own or you can set up a local S3 compatible object storage.

### 2.1 [Alternative 1] I don't have a S3 bucket: let's install a Minio backend

The `test` directory contains an all-in-one configuration file for creating a Minio backend that will provide a S3 compatible protocol
for storing the objects.

Open the ([test/minio.yaml](didact://?commandId=vscode.open&projectFilePath=02-serverless-api/test/minio.yaml "Opens the Minio configuration"){.didact}) file to check its content before applying.

To create the minio backend, just apply the provided file:

```
kubectl apply -f test/minio.yaml
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20apply%20-f%20test/minio.yaml&completion=Created%20Minio%20backend. "Opens a new terminal and sends the command above"){.didact})

That's enough to have a test object storage to use with the API integration.

### 2.1 [Alternative 2] I have a S3 bucket

If you have a S3 bucket and you want to use it instead of the test backend, you can do it. The only 
things that you need to provide are a **AWS Access Key ID and Secret** that you can obtain from the Amazon AWS console.

Edit the ([s3.properties](didact://?commandId=vscode.open&projectFilePath=02-serverless-api/s3.properties "Opens the S3 configuration"){.didact}) to set the right value for the properties `camel.component.aws-s3.access-key` and `camel.component.aws-s3.secret-key`.
Those properties will be automatically injected into the Camel `aw3-s3` component.

## 3. Designing the API

An object store REST API is provided in the [openapi.yaml](didact://?commandId=vscode.open&projectFilePath=02-serverless-api/openapi.yaml "Opens the OpenAPI definition"){.didact} file.

It contains operations for:
- Listing the name of the contained objects
- Creating a new object
- Getting the content of an object
- Deleting an object

The file can be edited manually or better using an online editor, such as [Apicurio](https://studio.apicur.io/).

## 4. Running the API integration

The endpoints defined in the API can be implemented in a Camel K integration using a `direct:<operationId>` endpoint.
This has been implemented in the [API.java](didact://?commandId=vscode.open&projectFilePath=02-serverless-api/API.java "Opens the integration file"){.didact} file.

To run the integration, you need to link it to the proper configuration, that depends on what configuration you've chosen.

### 4.1 [Alternative 1] Using the test Minio server

As alternative, to connect the integration to the **test Minio server** deployed before using the [test/MinioCustomizer.java](didact://?commandId=vscode.open&projectFilePath=02-serverless-api/test/MinioCustomizer.java "Opens the customizer file"){.didact} class:

```
kamel run API.java --open-api openapi.yml --source test/MinioCustomizer.java --property file:test/minio.properties
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kamel%20run%20API.java%20--source%20test%2FMinioCustomizer.java%20--property%20file%3Atest%2Fminio.properties&completion=Integration%20run. "Opens a new terminal and sends the command above"){.didact})

### 4.2 [Alternative 2] Using the S3 service

To connect the integration to the **AWS S3 service**:

```
kamel run API.java --open-api openapi.yml --property file:s3.properties
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kamel%20run%20API.java%20--property%20file%3As3.properties&completion=Integration%20run. "Opens a new terminal and sends the command above"){.didact})


## 5. Using the API

After running the integration API, you should be able to call the API endpoints to check its behavior.

Make sure the integration is running, by checking its status:

```
kubectl get integrations
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20get%20integrations&completion=Getting%20running%20integrations. "Opens a new terminal and sends the command above"){.didact})

An integration named `api` should be present in the list and it should be in status `Running`. There's also a `kamel get` command which is an alternative way to list all running integrations.

NOTE: it may take some time, the first time you run the integration, for it to reach the `Running` state.

After the integraiton has reached the running state, you can get the route corresponding to it via the following command:

```
URL=$(kubectl get routes.serving.knative.dev api -o jsonpath='{.status.url}')
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$URL%3D%24%28kubectl%20get%20routes.serving.knative.dev%20api%20-o%20jsonpath%3D%27%7B.status.url%7D%27%29&completion=Getting%20route. "Opens a new terminal and sends the command above"){.didact})

You can print the route to check if it's correct:

```
echo $URL
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$echo%20$URL&completion=Print%20route. "Opens a new terminal and sends the command above"){.didact})

NOTE: ensure that you've followed all the instructions in the Knative documentation during installation, especially the DNS part is fundamental for being able to contact the API.

You can now play with it! What follows is a list of commands that you can run to use the API and check if it's working.

Get the list of objects:
```
curl -i $URL/
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$curl%20-i%20$URL&completion=Use%20the%20API. "Opens a new terminal and sends the command above"){.didact})

Looking at the pods, you should find a pod corresponding to the API integration:

```
kubectl get pods
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20get%20pods&completion=Getting%20running%20pods. "Opens a new terminal and sends the command above"){.didact})

If you wait **at least one minute** without invoking the API, you'll find that the pod will disappear.
Calling the API again will make the pod appear to serve the request. This is done to save resources and it's one the main features of Knative Serving.

You can continue with other commands.

Upload an object:
```
curl -i -X PUT --header "Content-Type: application/octet-stream" --data-binary "@API.java" $URL/example
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$curl%20-i%20-X%20PUT%20--header%20%22Content-Type%3A%20application%2Foctet-stream%22%20--data-binary%20%22%40API.java%22%20%24URL%2Fexample&completion=Use%20the%20API. "Opens a new terminal and sends the command above"){.didact})

Get the new list of objects:
```
curl -i $URL/
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$curl%20-i%20$URL&completion=Use%20the%20API. "Opens a new terminal and sends the command above"){.didact})

Get the content of a file:
```
curl -i $URL/example
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$curl%20-i%20%24URL%2Fexample&completion=Use%20the%20API. "Opens a new terminal and sends the command above"){.didact})

Delete the file:
```
curl -i -X DELETE $URL/example
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$curl%20-i%20-X%20DELETE%20%24URL%2Fexample&completion=Use%20the%20API. "Opens a new terminal and sends the command above"){.didact})

Get (again) the new list of objects:
```
curl -i $URL/
```
([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$curl%20-i%20$URL&completion=Use%20the%20API. "Opens a new terminal and sends the command above"){.didact})


## 6. Uninstall

To cleanup everything, execute the following command:

```kubectl delete namespace camel-api```

([^ execute](didact://?commandId=vscode.didact.sendNamedTerminalAString&text=camelTerm$$kubectl%20delete%20namespace%20camel-api&completion=Removed%20the%20project%20from%20the%20cluster. "Cleans up the cluster after running the example"){.didact})
