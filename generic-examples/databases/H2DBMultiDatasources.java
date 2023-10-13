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
// camel-k: dependency=mvn:io.quarkus:quarkus-jdbc-h2
// camel-k: build-property=quarkus.datasource.default.db-kind=h2
// camel-k: property=quarkus.datasource.default.username=username-default
// camel-k: property=quarkus.datasource.default.jdbc.url=jdbc:h2:mem:default
// camel-k: build-property=quarkus.datasource.testing.db-kind=h2
// camel-k: property=quarkus.datasource.testing.username=username-testing
// camel-k: property=quarkus.datasource.testing.jdbc.url=jdbc:h2:mem:testing
// camel-k: trait=camel.runtime-version=3.2.0

import org.apache.camel.builder.RouteBuilder;

public class H2DBMultiDatasources extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        from("timer://initCamel?repeatCount=1")
                .routeId("Init")
                .log("Create table defaultcamel")
                .to("sql:CREATE TABLE defaultcamel (id SERIAL PRIMARY KEY, timestamp VARCHAR(255))?dataSource=#default")
                .to("log:DEBUG?showBody=true&showHeaders=true")
                .log("SUCCESS Create table defaultcamel")
                .log("Create table testingcamel")
                .to("sql:CREATE TABLE testingcamel (id SERIAL PRIMARY KEY, timestamp VARCHAR(255))?dataSource=#testing")
                .to("log:DEBUG?showBody=true&showHeaders=true")
                .log("SUCCESS Create table testingcamel");


        from("timer://insertCamel?period=1000&delay=5000")
            .routeId("Insert")
                .log("Inserting defaultcamel ${messageTimestamp}")
                .setBody().simple("INSERT INTO defaultcamel (timestamp) VALUES (${messageTimestamp})")
                .to("jdbc:default")
                .log("Inserted defaultcamel ${messageTimestamp}")
                .log("Inserting testingcamel ${messageTimestamp}")
                .setBody().simple("INSERT INTO testingcamel (timestamp) VALUES (${messageTimestamp})")
                .to("jdbc:testing")
                .log("Inserted testingcamel ${messageTimestamp}");

        from("timer://selectCamel?period=1000&delay=5000")
                .routeId("Test")
                .to("sql:SELECT * FROM defaultcamel?dataSource=#default")
                .to("log:DEBUG?showBody=true&showHeaders=true")
                .to("sql:SELECT * FROM testingcamel?dataSource=#testing")
                .to("log:DEBUG?showBody=true&showHeaders=true")
                .setBody().simple("SUCCESS");
    }
}