// camel-k: dependency=camel-aws2-s3 dependency=camel-base64
//
// Apache Camel S3 Consumer
//

// So we unmarshal it, extract the data element which is in base64 format and decode it
from('knative:channel/aws-s3-kinesis-firehose')
    .log('Received: ${body}')
