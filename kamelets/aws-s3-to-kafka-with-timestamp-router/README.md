# AWS S3 to Kafka with Timestamp Router

- Use the quickstart for https://strimzi.io/quickstarts/ and follow the minikube guide.

- Install camel-k on the kafka namespalce

- Open the `flow-binding.yaml` file and insert the correct credentials for AWS S3 account and the bucket name.

- The Log Sink Kamelet is not available out of the box in 1.5.0 Camel-K release so you'll have to install it before installing the flow binding.

- Set the correct credentials for S3 in the `flow-binding.yaml`

- Run the following commands

      kubectl apply -f log-sink.kamelet.yaml -n kafka
      kubectl apply -f flow-binding.yaml -n kafka

- Check logs

      kamel logs s3-to-kafka-with-timestamp-router -n kafka

- You should see the file content from AWS S3 logged and appearing in a topic based on the Last modified metadata field of the S3 file consumed. The granularity of the topic names is minutes.
