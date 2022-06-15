// camel-k: dependency=camel-aws-kinesis
//
// Apache Camel Kinesis Consumer
//
// This is just a sample producer for AWS that creates 100 messages every 3 seconds
from('timer:java?period=3000&repeatCount=100')
    .setHeader("CamelAwsKinesisPartitionKey")
        .constant("p-01123")
    .setHeader("CamelAwsKinesisSequenceNumber")
        .constant(1)
    .setBody()
        .simple('Hello Camel K')
    .to('aws-kinesis:stream?accessKey={{aws.kinesis.accessKey}}&secretKey={{aws.kinesis.secretKey}}&region={{aws.kinesis.region}}')
