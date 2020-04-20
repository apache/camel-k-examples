// camel-k: language=java

import org.apache.camel.builder.RouteBuilder;

public class Basic extends RouteBuilder {
  @Override
  public void configure() throws Exception {

      from("timer:java?period=1000")
        .setHeader("example")
          .constant("Java")
        .setBody()
          .simple("Hello World! Camel K route written in ${header.example}.")
        .to("log:info");
      
  }
}
