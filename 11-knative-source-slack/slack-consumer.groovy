// camel-k: dependency=camel-jackson
//
// Apache Camel Slack Consumer
//
from('knative:channel/slack')
    .unmarshal()
        .json()
    .setBody { it.in.body.text }
    .log('Received: ${body}')
