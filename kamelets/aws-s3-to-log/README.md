# AWS S3 to Log

- Open the `flow-binding.yaml` file and insert the correct credentials for AWS S3 account and the bucket name.

- The Log Sink Kamelet is not available out of the box in 1.5.0 Camel-K release so you'll have to install it before installing the flow binding.

- If camel-k has been installed in a specific namespace different from the default one, you'll need to add a parameter to all the command (`-n <namespace_name>`)

- Run the following commands

      kubectl apply -f log-sink.kamelet.yaml
      kubectl apply -f flow-binding.yaml

- Check logs

      kamel logs aws-s3-to-log

- If you have files on your S3 bucket you should see their content consumed and the file in the bucket deleted
