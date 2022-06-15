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

### Have your Kubernetes cluster ready

All examples require that you are connected to a Kubernetes/OpenShift cluster, even a local instance such as [Minikube](https://github.com/kubernetes/minikube) or [CRC](https://github.com/code-ready/crc). Some advanced examples may have additional requirements.

Ensure that you've followed the [Camel K installation guide](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific cluster before looking at the examples.

### Install the CLI tools

All examples need at least the following CLI tools installed on your system:

- `kubectl`: the Kubernetes default CLI tool that can be downloaded from the [Kubernetes releases page](https://github.com/kubernetes/kubernetes/releases).
- `kamel`: the Apache Camel K CLI that can be downloaded from the [Camel K release page](https://github.com/apache/camel-k/releases).

## Generic Examples

Here is a selected list of generic examples (to find more examples, see [generic-examples](./generic-examples/) folder):

| Type  |  Description |
|---|---|
| [Languages](./generic-examples/languages/) | Simple integrations developed in various supported languages |
| [Basic](./generic-examples/basic/) | Simple integrations with basic configuration |
| [Cron](./generic-examples/cron/) | How to create a `cron` integration |
| [Kafka](./generic-examples/kafka/) | Component usage |
| [Knative](./generic-examples/knative/) | Component usage |
| [OLM](./generic-examples/olm/) | OPERATOR Lifecycle manager installation example |

## Kamelets

Here is a selected list of examples based on Kamelets (in the [Kamelets](./kamelets) folder, you'll find a lot more kamelet based examples):

| Name  |  Description |
|---|---|
| [AWS S3 to Log with Secret](./kamelets/aws-s3-to-log-with-secret/) | Create a Kamelet binding between an AWS S3 Source Kamelet and a Log Sink Kamelet and define S3 credentials through Kubernetes secret |
| [Kafka to AWS S3 Streaming Upload](./kamelets/kafka-to-s3-streaming-upload/) | Create a Kamelet binding between a Kafka Source Kamelet and a AWS S3 Streaming Upload Sink Kamelet |
| [Kafka to Kafka with Regex router](./kamelets/kafka-to-kafka-with-regex-router/) | Create a Kamelet binding between a Kafka Source Kamelet and a Kafka Sink Kamelet, with the usage of the Regex Router Action |
| [Kafka to SQL Server](./kamelets/kafka-to-sqlserver/) | Create a Kamelet binding between a Kafka Source Kamelet and a SQL Sink Kamelet |