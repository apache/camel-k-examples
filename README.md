# Camel K Examples

This repository contains a collection of Camel K examples useful to understand how it works, common use cases and the idiomatic programming model.

You can find more information about Apache Camel and Apache Camel K on the [official Camel website](https://camel.apache.org).

## Before you begin

### Open the examples in the IDE

To better work on all examples, make sure you have all them locally by checking out the git repository:

```
git clone git@github.com:apache/camel-k-examples.git
```

We suggest you to open the examples with [VSCode](https://code.visualstudio.com/) because it provides useful extensions for working with Camel K files.
If you've already installed it on your machine, after cloning the repository, you can open the examples on the IDE executing:

```
code camel-k-examples
```

We suggest you to install the following extensions for VSCode (The IDE should automatically prompt to ask you to install them):
- [Extension Pack for Apache Camel](https://marketplace.visualstudio.com/items?itemName=redhat.apache-camel-extension-pack): provides auto-completion, error handling as well as integrated IDE tools to manage the lifecycle of Camel K integrations
- [Didact](https://marketplace.visualstudio.com/items?itemName=redhat.vscode-didact): Provides a better getting started experience when following readme files in the various examples

### Have your Kubernetes cluster ready

All examples require that you are connected to a Kubernetes/OpenShift cluster, even a local instance such as [Minikube](https://github.com/kubernetes/minikube) or [CRC](https://github.com/code-ready/crc). Some advanced examples may have additional requirements.

Ensure that you've followed the [Camel K installation guide](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific cluster before looking at the examples.

### Install the CLI tools

All examples need at least the following CLI tools installed on your system:

- `kubectl`: the Kubernetes default CLI tool that can be downloaded from the [Kubernetes releases page](https://github.com/kubernetes/kubernetes/releases).
- `kamel`: the Apache Camel K CLI that can be downloaded from the [Camel K release page](https://github.com/apache/camel-k/releases).

## Generic Examples

We are providing also a folder containing multiple generic examples in [Generic Examples](./generic-examples) folder.

## Kamelets

In the [Kamelets](./kamelets) folder, you'll get a set of examples based on Kamelets.

## Custom Examples

Examples are contained in directories ordered by level of difficulty.

Most examples provide a `readme.didact.md` file instead of the standard readme file. For those, if you're using VSCode with Didact installed, you can **right click on the `readme.didact.md` file and hit "Didact: Start Didact Tutorial from File"**.

This is the current list of examples:

- [01 Basic](./01-basic): Getting started with Camel K by learning the most important features that you should know before trying to develop more complex examples.
- [02 Serverless API](./02-serverless-api): Learn how to design an API that manages files in a remote storage location and leverages *Knative* Serving to scale automatically (even to zero instances) based on the current load.
- [03 Knative Source Basic](./03-knative-source-basic): Getting started with Knative Camel Source by learning the most important concepts you should know before trying to develop more complex examples.
- [04 AWS Kinesis Source Basic](./04-aws-kinesis-source-basic): Learn how to consume AWS Kinesis events using Knative Camel Source.
- [10 Knative Source Salesforce](./10-knative-source-salesforce): Learn how to create a Knative Camel Source for Salesforce.
- [11 Knative Source Slack](./11-knative-source-slack): Getting started with Slack integration using Knative Camel Source.
- [90 AWS Kinesis Source With a Custom Configuration](./90-aws-kinesis-customized-event-source): Learn how to use a custom AWS Kinesis configuration when consuming AWS Kinesis events using Knative Camel Source.
