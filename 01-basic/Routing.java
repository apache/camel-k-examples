/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
