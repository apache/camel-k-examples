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

// Generate keystore.jks and truststore.jks (for this example, keystore and truststore password = changeit):
//
// keytool -genkeypair -alias EntryName -keyalg RSA -keysize 2048 -keystore keystore.jks
// keytool -exportcert -alias EntryName -keystore keystore.jks -rfc -file public.cert
// keytool -import -alias EntryName -file public.cert -storetype JKS -keystore truststore.jks

// Create the secrets associated with the stores:
//
// kubectl create secret generic http-keystore --from-file keystore.jks
// kubectl create secret generic http-truststore --from-file truststore.jks

// Run the integration:
//
// kamel run NettySecureServer.java
//        -t mount.resources=secret:http-keystore/keystore.jks@/etc/ssl/keystore.jks
//        -t mount.resources=secret:http-truststore/truststore.jks@/etc/ssl/truststore.jks
//        -t container.port=8443 -t service.type=NodePort --dev

// Test
//
// recover the service location. If you're running on minikube, "minikube service netty-secure-server --url=true --https=true"
// curl -k https://<service-location>/hello
//

import org.apache.camel.builder.RouteBuilder;

import org.apache.camel.support.jsse.*;

public class NettySecureServer extends RouteBuilder {
   @Override
   public void configure() throws Exception {
      registerSslContextParameter();
      from("netty-http:https://0.0.0.0:8443/hello?sslContextParameters=#sslContextParameters&ssl=true")
            .transform().constant("Hello Secure World");
   }

   private void registerSslContextParameter() throws Exception {
      KeyStoreParameters ksp = new KeyStoreParameters();
      ksp.setResource("/etc/ssl/keystore.jks");
      ksp.setPassword("changeit");
      KeyManagersParameters kmp = new KeyManagersParameters();
      kmp.setKeyPassword("changeit");
      kmp.setKeyStore(ksp);
      KeyStoreParameters tsp = new KeyStoreParameters();
      tsp.setResource("/etc/ssl/truststore.jks");
      tsp.setPassword("changeit");
      TrustManagersParameters tmp = new TrustManagersParameters();
      tmp.setKeyStore(tsp);
      SSLContextParameters sslContextParameters = new SSLContextParameters();
      sslContextParameters.setKeyManagers(kmp);
      sslContextParameters.setTrustManagers(tmp);

      this.getContext().getRegistry().bind("sslContextParameters", sslContextParameters);
   }
}