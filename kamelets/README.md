# Camel K Examples - Kamelets

This folder contains a collection of Camel K examples based on Kamelets and KameletBinding CRD.

## Before you begin

Follow the instructions from the Repository Root Folder's README.

## Important Notes

All the Kamelet examples in this folder have been tested on Camel-K 1.5.0.

## Examples List

- [AWS S3 to Log](./aws-s3-to-log): Create a Kamelet binding between an AWS S3 Source Kamelet and a Log Sink Kamelet
- [AWS S3 to Log with Secret](./aws-s3-to-log-with-secret): Create a Kamelet binding between an AWS S3 Source Kamelet and a Log Sink Kamelet and define S3 credentials through Kubernetes secret
- [AWS S3 to Kafka with Timestamp router](./aws-s3-to-kafka-with-timestamp-router): Create a Kamelet binding between an AWS S3 Source Kamelet and a Kafka Sink Kamelet, with the usage of the Timestamp Router Action.
- [Kafka to AWS S3 Streaming Upload](./kafka-to-s3-streaming-upload): Create a Kamelet binding between a Kafka Source Kamelet and a AWS S3 Streaming Upload Sink Kamelet.
- [Kafka to Kafka with Regex router](./kafka-to-kafka-with-regex-router): Create a Kamelet binding between a Kafka Source Kamelet and a Kafka Sink Kamelet, with the usage of the Regex Router Action.
- [Kafka to Kafka with Manual commit](./kafka-to-kafka-with-manual-commit): Create a Kamelet binding between a Kafka Source Kamelet and a Kafka Sink Kamelet, with the usage of the Manual Commit Action.
- [Kafka to Kafka with Timestamp router](./kafka-to-kafka-with-timestamp-router): Create a Kamelet binding between a Kafka Source Kamelet and a Kafka Sink Kamelet, with the usage of the Timestamp Router Action.
- [Kafka to Log with Value to Key](./kafka-to-log-with-value-to-key): Create a Kamelet binding between a Kafka Source Kamelet and a Log Sink Kamelet, with the usage of the Value to Key Action.
- [Kafka to SQL Server](./kafka-to-sqlserver): Create a Kamelet binding between a Kafka Source Kamelet and a SQL Sink Kamelet.
- [PostgreSQL to Log](./postgresql-to-log): Create a Kamelet binding between a PostgreSQL Source Kamelet and a Log Sink Kamelet.
