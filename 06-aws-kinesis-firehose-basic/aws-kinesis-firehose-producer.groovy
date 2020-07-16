// camel-k: dependency=camel-aws2-kinesis
//
// Apache Camel Kinesis Consumer
//
// This is just a sample producer for AWS that creates 100 messages every 3 seconds
from('timer:java?period=3000&repeatCount=100')
    .setBody()
        .simple('Hello Camel K')
    .to('aws2-kinesis-firehose:stream?accessKey={{aws.kinesis.accessKey}}&secretKey={{aws.kinesis.secretKey}}&region={{aws.kinesis.region}}')
