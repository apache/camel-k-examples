from('knative:channel/jira')
.unmarshal()
.json()
.setBody { "KEY:"+ it.in.body.key+"| Summary"+ it.in.body.summary }
.log('Recieved:  ${body}')
.setBody {}

