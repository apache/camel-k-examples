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


import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class MyIntegration extends RouteBuilder {

    @Override
    public void configure() {
        onException()
                .handled(true)
                .maximumRedeliveries(2)
                .logStackTrace(false)
                .logExhausted(false)
                .log(LoggingLevel.ERROR, "Failed processing ${body}")
                .to("log:exception");

        from("timer:foo?includeMetadata=true")
                .routeId("unreliable-service")
                .setBody(header(Exchange.TIMER_COUNTER).prepend("event #"))
                .log("Successfully processed ${body}")
                .to("micrometer:counter:success");
    }
}
