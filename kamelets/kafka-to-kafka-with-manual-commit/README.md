# Kafka to Kafka with manual commit

- Use the quickstart for https://strimzi.io/quickstarts/ and follow the minikube guide.

- Install camel-k on the kafka namespace

- Run the following commands

kubectl apply -f kafka-not-secured-source.kamelet.yaml -n kafka
kubectl apply -f log-sink.kamelet.yaml -n kafka
kubectl apply -f flow-binding.yaml -n kafka

- Check logs

kamel logs kafka-to-kafka-with-manual-commit -n kafka
