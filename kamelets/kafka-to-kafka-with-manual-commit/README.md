# Kafka to Kafka with manual commit

- Use the quickstart for https://strimzi.io/quickstarts/ and follow the minikube guide.

- The Log Sink Kamelet is not available out of the box in 1.5.0 Camel-K release so you'll have to install it before installing the flow binding.

- Install camel-k on the kafka namespace

- Run the following commands

      kubectl apply -f log-sink.kamelet.yaml -n kafka
      kubectl apply -f flow-binding.yaml -n kafka

- Check logs

      kamel logs kafka-to-kafka-with-manual-commit -n kafka
