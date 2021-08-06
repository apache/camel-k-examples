# S3 to Log with secrets

- The Log Sink Kamelet is not available out of the box in 1.5.0 Camel-K release so you'll have to install it before installing the flow binding.

- If camel-k has been installed in a specific namespace different from the default one, you'll need to add a parameter to all the command (`-n <namespace_name>`)

- Run the following command to create the secret related to AWS S3 credentials

      kubectl create secret generic aws-s3-secret --from-literal=accessKey=<accessKey> --from-literal=secretKey=<secretKey>

- Run the following commands

      kubectl apply -f log-sink.kamelet.yaml
      kubectl apply -f flow-binding.yaml

- Check logs

      kamel logs s3-to-log-with-secret

- If you have files on your S3 bucket you should see their content consumed and the file in the bucket deleted
