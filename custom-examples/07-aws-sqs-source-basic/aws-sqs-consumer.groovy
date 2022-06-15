// camel-k: dependency=camel-aws2-sqs dependency=camel-base64
//
// Apache Camel SQS Consumer
//
from('knative:channel/aws-sqs')
    .log('Received: ${body}')
