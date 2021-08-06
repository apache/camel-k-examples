# Kafka to Log with Value To Key action

- Use the quickstart for https://strimzi.io/quickstarts/ and follow the minikube guide.

- The Log Sink Kamelet is not available out of the box in 1.5.0 Camel-K release so you'll have to install it before installing the flow binding.

- If camel-k has been installed in a specific namespace different from the default one, you'll need to add a parameter to all the commands (`-n <namespace_name>`)

- Run the following commands

      kubectl apply -f log-sink.kamelet.yaml -n kafka
      kubectl apply -f flow-binding.yaml -n kafka

- Check logs

      kamel logs kafka-to-log-with-value-to-key

You should data ingesting into the `test-topic` topic, you should see logged a record with key composed of foo and bar values.
