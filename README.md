# Camel K Examples

This repository contains a collection of Camel K examples useful to understand how it works, common use cases and the idiomatic programming model.

You can find more information about Apache Camel and Apache Camel K on the [official Camel website](https://camel.apache.org).

## Before you begin

### Open the examples in the IDE

To better work on all examples, make sure you have all them locally by checking out the git repository:

```
git clone git@github.com:apache/camel-k-examples.git
```

### Have your Kubernetes cluster ready

All examples require that you are connected to a Kubernetes/OpenShift cluster, even a local instance such as [Minikube](https://github.com/kubernetes/minikube) or [CRC](https://github.com/code-ready/crc). Some advanced examples may have additional requirements.

Ensure that you've followed the [Camel K installation guide](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific cluster before looking at the examples.

### Install the CLI tools

All examples need at least the following CLI tools installed on your system:

- `kubectl`: the Kubernetes default CLI tool that can be downloaded from the [Kubernetes releases page](https://github.com/kubernetes/kubernetes/releases).
- `kamel`: the Apache Camel K CLI that can be downloaded from the [Camel K release page](https://github.com/apache/camel-k/releases).

## Generic Examples

In [Generic Examples](./generic-examples) we are providing multiple generic examples folder.

## Kamelets

In [Kamelets](./kamelets) we have a set of examples based on Kamelets.
