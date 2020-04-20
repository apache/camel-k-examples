// camel-k: language=java

import java.util.Random;

import org.apache.camel.PropertyInject;
import org.apache.camel.builder.RouteBuilder;

public class Routing extends RouteBuilder {

  private Random random = new Random();

  @PropertyInject("priority-marker")
  private String priorityMarker;

  @Override
  public void configure() throws Exception {

      from("timer:java?period=3000")
        .id("generator")
        .bean(this, "generateRandomItem({{items}})")
        .choice()
          .when().simple("${body.startsWith('{{priority-marker}}')}")
            .transform().body(String.class, item -> item.substring(priorityMarker.length()))
            .to("direct:priorityQueue")
          .otherwise()
            .to("direct:standardQueue");
      
      from("direct:standardQueue")
        .id("standard")
        .log("Standard item: ${body}");

      from("direct:priorityQueue")
        .id("priority")
        .log("!!Priority item: ${body}");
      
  }

  public String generateRandomItem(String items) {
    if (items == null || items.equals("")) {
      return "[no items configured]";
    }
    String[] list = items.split("\\s");
    return list[random.nextInt(list.length)];
  }

}
