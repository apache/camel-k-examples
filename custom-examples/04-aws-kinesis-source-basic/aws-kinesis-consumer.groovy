// camel-k: dependency=camel-aws-kinesis dependency=camel-base64
//
// Apache Camel Kinesis Consumer
//

// So we unmarshal it, extract the data element which is in base64 format and decode it
from('knative:channel/aws-kinesis')
    .unmarshal()
        .json()
    .setBody { it.in.body.data }
    .unmarshal()
        .base64()
    .log('Received: ${body}')
