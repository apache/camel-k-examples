// camel-k: dependency=camel-jackson
// camel-k: language=java

import org.apache.camel.builder.RouteBuilder;

public class SalesforceConsumer extends RouteBuilder {
    public void configure() {
        from("knative:channel/salesforce")
            .unmarshal().json()
            .log("New Salesforce contact was created: [ID:${body[Id]}, Name:${body[Name]}, Email:${body[Email]}, Phone: ${body[Phone]}]")
            .marshal().json(); // To avoid a WARN - https://github.com/apache/camel-k-runtime/issues/390
    }
}
