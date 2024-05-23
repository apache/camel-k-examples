# How to deploy a simple JMS/AMQP Broker to Kubernetes cluster

This is a very simple example to show how to create a JMS/AMQP broker. **Note**, this is not ready for any production purpose.

## Create a Kubernetes Deployment

You can [install ActiveMQ Artemis Operator on Kubernetes](https://artemiscloud.io/docs/tutorials/using_operator/) thanks to ArtemisCloud.io. 
It would be enough to execute steps 1 and 2 of the linked tutorial post.

**Note:** Openshift environments may require some adjustments, see [Installing the AMQ Broker Operator](https://access.redhat.com/documentation/en-us/red_hat_amq/7.4/html/deploying_amq_broker_on_openshift_container_platform/broker-operator-broker-ocp#install-broker-operator-broker-ocp)

## Verify that the Artemis Operator is running

```
$ kubectl get pod -l name=activemq-artemis-operator -w
NAME                                                   READY   STATUS              RESTARTS   AGE
activemq-artemis-controller-manager-55645cc79f-q2qbj   0/1     ContainerCreating   0          48s
activemq-artemis-controller-manager-55645cc79f-q2qbj   0/1     Running             0          56s
activemq-artemis-controller-manager-55645cc79f-q2qbj   1/1     Running             0          61s
```

## Create an AMQP broker instance

Once the operator is up and running, you can proceed by creating a basic install of a broker accepting `amqp` protocol:

```
kubectl apply -f ./artemis/artemis-amqp.yaml
```

## Expose the Broker via service

We need to make the broker accessible from the `Integration`. Let's do that via a `Service`:

```
kubectl apply -f ./artemis/artemis-service.yaml
```

We're now ready to use the `Broker`.
