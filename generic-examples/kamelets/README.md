# Kamelets Camel K examples

Find useful examples about how to use Kamelets.

To learn more about Kamelets, see the [Kamelets Guide](https://camel.apache.org/camel-k/next/kamelets/kamelets.html) <br>
You can find more information about Apache Camel and Apache Camel K on the [official Camel website](https://camel.apache.org)

## Before you begin

Read the general instructions in the [root README.md file](/README.md) for setting up your environment and the Kubernetes cluster before looking at this example.

Make sure you've read the [installation instructions](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific
cluster before starting the example.

## Understanding the Examples
The examples are grouped into the following sub-directories:
- [chuck-norris](./chuck-norris/): contains quick demonstration on how to use an already existing kamelet in a Camel application.
- [error-handler](./error-handler/): contains examples on how to:
    - create event source kamelet and sink kamelet
    - bind the source and sink kamelets using KameletBinding
    - handle error arising from sending events
- [kameletbindings](./kameletbindings/): contains more examples on KamletBindings and how to use `traits` to customize your integration.
- [timer-source](./timer-source/): contains examples on how to: 
    - create and use a timer source kamelet
    - bind a source kamelet to a Knative destination using KamletBinding
- [timer-source-log-sink](./timer-source-log-sink/): 
    - create and use a timer source kamelet
    - bind a source kamelet to a Knative destination using KamletBinding
    - create a log sink kamlet and bind it to the Knative destination using KamletBinding