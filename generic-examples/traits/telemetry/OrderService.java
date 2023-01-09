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


/*

kamel run --name=order-service-api -d camel-swagger-java -d camel-jackson -d camel-undertow  OrderService.java --dev

*/

import java.util.HashMap;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.Exchange;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.processor.aggregate.GroupedBodyAggregationStrategy;

public class OrderService extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
        restConfiguration()
            .enableCORS(true)
            .bindingMode(RestBindingMode.json);

        rest()
            .post("/place")
                .to("direct:placeorder");

        JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
        jacksonDataFormat.setUnmarshalType(Order.class);

        from("direct:placeorder")
            .log("-----IN ${headers}")
            .marshal(jacksonDataFormat)
            .log("inputBody --> ${body}")
            .to("http://inventory/notify/order?bridgeEndpoint=true")
            .removeHeaders("*")
            .log("responseBody from inventory --> ${body}")
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
            .setBody(simple("{\"inventory\":${body}}"))
            .unmarshal().json()
        ;
    }
    
    private static class Order implements java.io.Serializable{
        private static final long serialVersionUID = 1L;
        
        private Integer orderId;
        private Integer itemId;
        private Integer quantity;

        private String orderItemName;
        private Integer price;

        public void setOrderId(Integer orderId){
            this.orderId=orderId;
        }
        public void setItemId(Integer itemId){
            this.itemId=itemId;
        }
        public void setQuantity(Integer quantity){
            this.quantity=quantity;
        }

        public void setOrderItemName(String orderItemName){
            this.orderItemName=orderItemName;
        }
        public void setPrice(Integer price){
            this.price=price;
        }
        
        public Integer getOrderId(){
            return this.orderId;
        }
        public Integer getItemId(){
            return this.itemId;
        }
        public Integer getQuantity(){
            return this.quantity;
        }

        public String getOrderItemName(){
            return this.orderItemName;
        }
        public Integer getPrice(){
            return this.price;
        }

    }
}