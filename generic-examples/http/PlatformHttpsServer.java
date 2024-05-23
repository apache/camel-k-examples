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

// Private key and Self signed certificate generation:
//
// openssl genpkey -algorithm RSA -out server.key
// openssl req -new -key server.key -out server.csr
// openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt

// camel-k: resource=secret:my-self-signed-ssl@/etc/ssl/my-self-signed-ssl
// camel-k: property=quarkus.http.ssl.certificate.files=/etc/ssl/my-self-signed-ssl/server.crt
// camel-k: property=quarkus.http.ssl.certificate.key-files=/etc/ssl/my-self-signed-ssl/server.key
// camel-k: trait=service.type=NodePort
// camel-k: trait=container.port=8443

import org.apache.camel.builder.RouteBuilder;

public class PlatformHttpsServer extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    from("platform-http:/hello?httpMethodRestrict=GET").setBody(simple("Hello ${header.name}"));
  }
}