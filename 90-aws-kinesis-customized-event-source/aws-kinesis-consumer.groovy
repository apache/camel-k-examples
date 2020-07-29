// camel-k: dependency=camel-aws-kinesis dependency=camel-base64
//
// Apache Camel Kinesis Consumer
//

// Typical data when using cbor looks like this:
// {"sequenceNumber":"1222","approximateArrivalTimestamp":1123,"data":"SGVsbG8gQ2FtZWwgSw==","partitionKey":"p-01123"...}
//
// So we unmarshal it, extract the data element which is in base64 format and decode it
from('knative:channel/aws-kinesis')
    .unmarshal()
        .json()
    .setBody { it.in.body.data }
    .unmarshal()
        .base64()
    .log('Received: ${body}')
