# Camel K Examples - Kamelets

This folder contains a collection of Camel K examples based on Kamelets and KameletBinding CRD.

## Before you begin

Follow the instructions from the Repository Root Folder's README.

## Important Notes

All the Kamelet examples in this folder have been tested on Camel-K 1.5.0.

## Examples List

- [AWS S3 to Log](./aws-s3-to-log): Create a Kamelet binding between an AWS S3 Source Kamelet and a Log Sink Kamelet
- [AWS S3 to Kafka with Timestamp router](./aws-s3-to-kafka-with-timestamp-router): Create a Kamelet binding between an AWS S3 Source Kamelet and a Kafka Sink Kamelet, with the usage of the Timestamp Router Action.
- [Kafka to Kafka with Regex router](./kafka-to-kafka-with-regex-router): Create a Kamelet binding between a Kafka Source Kamelet and a Kafka Sink Kamelet, with the usage of the Regex Router Action.
