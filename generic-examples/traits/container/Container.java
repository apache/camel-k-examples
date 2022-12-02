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

// Run the integration
/*
kamel run \
  --name container \
  Container.java \
  --trait container.image-pull-policy=Always \
  --trait container.request-cpu=0.005 \
  --trait container.limit-cpu=0.2 \
  --trait container.request-memory=100Mi \
  --trait container.limit-memory=500Mi
*/

import org.apache.camel.builder.RouteBuilder;

public class Container extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer:tick")
            .setBody()
            .constant("Hello Camel K!")
            .to("log:info");
    }
}
