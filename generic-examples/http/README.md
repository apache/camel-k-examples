# Examples showing how to interact with HTTP/HTTPS protocol

Find useful examples about how to develop a Camel K integration leveraging `Http`/`Https` protocol.

You can find more information about Apache Camel and Apache Camel K on the [official Camel website](https://camel.apache.org).

## Before you begin

Read the general instructions in the [root README.md file](/README.md) for setting up your environment and the Kubernetes cluster before looking at this example.

Make sure you've read the [installation instructions](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific
cluster before starting the example.

## Understanding the Examples

- Using HTTP with SSL/TLS layer
    - [NettySecureServer.java](./NettySecureServer.java): Uses HTTPS protocol. The route uses Netty HTTP as a HTTP server.
    - [PlatformHttpsServer.java](./PlatformHttpsServer.java): Uses HTTPS protocol. The route uses the platform-http component which allows the use of the runtime's existing HTTP server.

- Using HTTP
    - [NettyServer.java](./NettyServer.java): Uses HTTP without SSL security. The route uses Netty HTTP as a HTTP server
    - [PlatformHttpServer.java](./PlatformHttpServer.java): uses plain HTTP protocol. The route uses the runtime's existing HTTP server

## Running the Examples

### Run the Integration in `NettySecureServer.java`:
This integration requires a Keystore and a Truststore. Open [NettySecureServer.java](./NettySecureServer.java) to find instructions on how to generate a required `keystore.jks` 
and `truststore.jks` file. For this example, keystore and trustore password is `changeit`

Run the integration:
```
kamel run NettySecureServer.java --resource file:keystore.jks@/etc/ssl/keystore.jks \
    --resource file:truststore.jks@/etc/ssl/truststore.jks -t container.port=8443 --dev
```
Get the service location. If you're running on minikube, run `minikube service netty-secure-server --url=true --https=true` \
Visit `https://<service-location>/hello`. You should see "Hello Secure World" displayed on the web page. \
Alternatively, you could run: `curl -k https://<service-location>/hello`.

### Run the other Integrations:
- Open [NettyServer.java](./NettyServer.java) to find instruction on how to run the integration.
- Open [PlatformHttpsServer.java](./PlatformHttpsServer.java) to find instructions on how to generate a required private key and self-signed certificate, and also how to run the integration.
- Open [PlatformHttpServer.java](./PlatformHttpServer.java) to find instruction on how to run the integration.