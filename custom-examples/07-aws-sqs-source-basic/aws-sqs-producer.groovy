// camel-k: dependency=camel-aws2-sqs
//
// Apache Camel SQS Producer
//
// This is just a sample producer for AWS that creates 100 messages every 3 seconds
from('timer:java?period=3000&repeatCount=30')
    .setBody()
        .simple('Hello Camel K')
    .to('aws2-sqs:eventqueue?accessKey={{aws.sqs.accessKey}}&secretKey={{aws.sqs.secretKey}}&region={{aws.sqs.region}}')
