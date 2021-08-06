# Kafka to LOG 

- Use the quickstart for https://strimzi.io/quickstarts/ and follow the minikube guide.

- Install camel-k on the kafka namespace

- Add the correct credentials in the flow binding file for AWS S3 service. Don't forget to create the kamelets-demo bucket in the region you select.

- Run the following commands

      kubectl apply -f flow-binding.yaml -n kafka

- Check logs

      kamel logs kafka-to-s3-streaming-upload -n kafka

      [1] 2021-07-30 05:45:28,277 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main) Routes startup summary (total:3 started:3)
      [1] 2021-07-30 05:45:28,277 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main)     Started route1 (kamelet://kafka-not-secured-source/source)
      [1] 2021-07-30 05:45:28,277 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main)     Started source (kafka://test-topic)
      [1] 2021-07-30 05:45:28,277 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main)     Started sink (kamelet://source)
      [1] 2021-07-30 05:45:28,278 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main) Apache Camel 3.11.0 (camel-1) started in 1s751ms (build:0ms init:75ms start:1s676ms)
      [1] 2021-07-30 05:45:28,281 INFO  [io.quarkus] (main) camel-k-integration 1.5.0 on JVM (powered by Quarkus 2.0.0.Final) started in 7.710s.
      [1] 2021-07-30 05:45:28,281 INFO  [io.quarkus] (main) Profile prod activated.
      [1] 2021-07-30 05:45:28,282 WARN  [org.apa.kaf.cli.con.ConsumerConfig] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) The configuration 'sasl.kerberos.ticket.renew.window.factor' was supplied but isn't a known config.
      [1] 2021-07-30 05:45:28,284 WARN  [org.apa.kaf.cli.con.ConsumerConfig] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) The configuration 'sasl.kerberos.kinit.cmd' was supplied but isn't a known config.
      [1] 2021-07-30 05:45:28,285 WARN  [org.apa.kaf.cli.con.ConsumerConfig] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) The configuration 'specific.avro.reader' was supplied but isn't a known config.
      [1] 2021-07-30 05:45:28,284 INFO  [io.quarkus] (main) Installed features: [camel-aws2-commons, camel-aws2-s3, camel-bean, camel-core, camel-k-core, camel-k-runtime, camel-kafka, camel-kamelet, camel-support-common, camel-support-commons-logging, camel-support-httpclient, camel-yaml-dsl, cdi, kafka-client]
      [1] 2021-07-30 05:45:28,287 WARN  [org.apa.kaf.cli.con.ConsumerConfig] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) The configuration 'sasl.kerberos.ticket.renew.jitter' was supplied but isn't a known config.
      [1] 2021-07-30 05:45:28,288 WARN  [org.apa.kaf.cli.con.ConsumerConfig] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) The configuration 'ssl.trustmanager.algorithm' was supplied but isn't a known config.
      [1] 2021-07-30 05:45:28,288 WARN  [org.apa.kaf.cli.con.ConsumerConfig] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) The configuration 'ssl.keystore.type' was supplied but isn't a known config.
      [1] 2021-07-30 05:45:28,289 WARN  [org.apa.kaf.cli.con.ConsumerConfig] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) The configuration 'sasl.kerberos.min.time.before.relogin' was supplied but isn't a known config.
      [1] 2021-07-30 05:45:28,289 WARN  [org.apa.kaf.cli.con.ConsumerConfig] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) The configuration 'ssl.endpoint.identification.algorithm' was supplied but isn't a known config.
      [1] 2021-07-30 05:45:28,289 WARN  [org.apa.kaf.cli.con.ConsumerConfig] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) The configuration 'ssl.protocol' was supplied but isn't a known config.
      [1] 2021-07-30 05:45:28,290 WARN  [org.apa.kaf.cli.con.ConsumerConfig] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) The configuration 'ssl.enabled.protocols' was supplied but isn't a known config.
      [1] 2021-07-30 05:45:28,291 WARN  [org.apa.kaf.cli.con.ConsumerConfig] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) The configuration 'ssl.truststore.type' was supplied but isn't a known config.
      [1] 2021-07-30 05:45:28,291 WARN  [org.apa.kaf.cli.con.ConsumerConfig] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) The configuration 'ssl.keymanager.algorithm' was supplied but isn't a known config.
      [1] 2021-07-30 05:45:28,292 INFO  [org.apa.kaf.com.uti.AppInfoParser] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) Kafka version: 2.8.0
      [1] 2021-07-30 05:45:28,292 INFO  [org.apa.kaf.com.uti.AppInfoParser] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) Kafka commitId: ebb1d6e21cc92130
      [1] 2021-07-30 05:45:28,292 INFO  [org.apa.kaf.com.uti.AppInfoParser] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) Kafka startTimeMs: 1627623928292
      [1] 2021-07-30 05:45:28,292 INFO  [org.apa.cam.com.kaf.KafkaConsumer] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) Subscribing test-topic-Thread 0 to topic test-topic
      [1] 2021-07-30 05:45:28,293 INFO  [org.apa.kaf.cli.con.KafkaConsumer] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) [Consumer clientId=consumer-camel-k-integration-2, groupId=camel-k-integration] Subscribed to topic(s): test-topic
      [1] 2021-07-30 05:45:28,582 WARN  [org.apa.kaf.cli.NetworkClient] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) [Consumer clientId=consumer-camel-k-integration-2, groupId=camel-k-integration] Error while fetching metadata with correlation id 2 : {test-topic=LEADER_NOT_AVAILABLE}
      [1] 2021-07-30 05:45:28,583 INFO  [org.apa.kaf.cli.Metadata] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) [Consumer clientId=consumer-camel-k-integration-2, groupId=camel-k-integration] Cluster ID: r6q2BGnHT7awUAK0diO1hA
      [1] 2021-07-30 05:45:28,585 INFO  [org.apa.kaf.cli.con.int.AbstractCoordinator] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) [Consumer clientId=consumer-camel-k-integration-2, groupId=camel-k-integration] Discovered group coordinator my-cluster-kafka-0.my-cluster-kafka-brokers.kafka.svc:9092 (id: 2147483647 rack: null)
      [1] 2021-07-30 05:45:28,635 INFO  [org.apa.kaf.cli.con.int.AbstractCoordinator] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) [Consumer clientId=consumer-camel-k-integration-2, groupId=camel-k-integration] (Re-)joining group
      [1] 2021-07-30 05:45:28,755 INFO  [org.apa.kaf.cli.con.int.AbstractCoordinator] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) [Consumer clientId=consumer-camel-k-integration-2, groupId=camel-k-integration] (Re-)joining group
      [1] 2021-07-30 05:45:28,759 WARN  [org.apa.kaf.cli.NetworkClient] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) [Consumer clientId=consumer-camel-k-integration-2, groupId=camel-k-integration] Error while fetching metadata with correlation id 6 : {test-topic=LEADER_NOT_AVAILABLE}
      [1] 2021-07-30 05:45:28,868 WARN  [org.apa.kaf.cli.NetworkClient] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) [Consumer clientId=consumer-camel-k-integration-2, groupId=camel-k-integration] Error while fetching metadata with correlation id 8 : {test-topic=LEADER_NOT_AVAILABLE}
      [1] 2021-07-30 05:45:31,766 INFO  [org.apa.kaf.cli.con.int.AbstractCoordinator] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) [Consumer clientId=consumer-camel-k-integration-2, groupId=camel-k-integration] Successfully joined group with generation Generation{generationId=1, memberId='consumer-camel-k-integration-2-a03ed9eb-5a45-4170-b519-97f9a32e019d', protocol='range'}
      [1] 2021-07-30 05:45:31,772 INFO  [org.apa.kaf.cli.con.int.ConsumerCoordinator] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) [Consumer clientId=consumer-camel-k-integration-2, groupId=camel-k-integration] Finished assignment for group at generation 1: {consumer-camel-k-integration-2-a03ed9eb-5a45-4170-b519-97f9a32e019d=Assignment(partitions=[test-topic-0])}
      [1] 2021-07-30 05:45:31,790 INFO  [org.apa.kaf.cli.con.int.AbstractCoordinator] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) [Consumer clientId=consumer-camel-k-integration-2, groupId=camel-k-integration] Successfully synced group in generation Generation{generationId=1, memberId='consumer-camel-k-integration-2-a03ed9eb-5a45-4170-b519-97f9a32e019d', protocol='range'}
      [1] 2021-07-30 05:45:31,791 INFO  [org.apa.kaf.cli.con.int.ConsumerCoordinator] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) [Consumer clientId=consumer-camel-k-integration-2, groupId=camel-k-integration] Notifying assignor about the new Assignment(partitions=[test-topic-0])
      [1] 2021-07-30 05:45:31,797 INFO  [org.apa.kaf.cli.con.int.ConsumerCoordinator] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) [Consumer clientId=consumer-camel-k-integration-2, groupId=camel-k-integration] Adding newly assigned partitions: test-topic-0
      [1] 2021-07-30 05:45:31,808 INFO  [org.apa.kaf.cli.con.int.ConsumerCoordinator] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) [Consumer clientId=consumer-camel-k-integration-2, groupId=camel-k-integration] Found no committed offset for partition test-topic-0
      [1] 2021-07-30 05:45:31,818 INFO  [org.apa.kaf.cli.con.int.SubscriptionState] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) [Consumer clientId=consumer-camel-k-integration-2, groupId=camel-k-integration] Resetting offset for partition test-topic-0 to position FetchPosition{offset=0, offsetEpoch=Optional.empty, currentLeader=LeaderAndEpoch{leader=Optional[my-cluster-kafka-0.my-cluster-kafka-brokers.kafka.svc:9092 (id: 0 rack: null)], epoch=0}}.

- Send some data to Kafka topic. Run the following command

      kubectl -n kafka run kafka-producer -ti --image=quay.io/strimzi/kafka:0.24.0-kafka-2.8.0 --rm=true --restart=Never -- bin/kafka-console-producer.sh --broker-list my-cluster-kafka-bootstrap:9092 --topic test-topic
      If you don't see a command prompt, try pressing enter.
      >l1
      >l2
      >l3
      >l4
      >l5

- In the integration logs you should now read 

      [1] 2021-07-30 05:46:12,156 INFO  [org.apa.cam.com.aws.s3.str.AWS2S3StreamUploadProducer] (Camel (camel-1) thread #0 - KafkaConsumer[test-topic]) Completed upload for the part 1 with etag "03204a1ad503637fc7b6cef6ac290fd4-1" at index 5

- If you go into the kamelets-demo bucket of your account, you should see only one file `KafkaTestFile.txt`, containing the file messages concatenated.
