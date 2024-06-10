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

### Run the NettyServer

```
kamel run --dev -t service.type=NodePort NettyServer.java
```

Get the service location. If you're running on minikube, run `minikube service netty-server --url=true`. 

You should see "Hello World" displayed on `http://<service-location>/hello`.
Alternatively, you could run: `curl http://<service-location>/hello`.

### Run the NettySecureServer

This integration requires a Keystore and a Truststore. Open [NettySecureServer.java](./NettySecureServer.java) to find instructions on how to generate a required `keystore.jks` and `truststore.jks` file. 
For this example, keystore and truststore password is `changeit`

Generate keystore.jks and truststore.jks (for this example, keystore and truststore password = changeit):
```shell
keytool -genkeypair -alias EntryName -keyalg RSA -keysize 2048 -keystore keystore.jks
keytool -exportcert -alias EntryName -keystore keystore.jks -rfc -file public.cert
keytool -import -alias EntryName -file public.cert -storetype JKS -keystore truststore.jks
```

Create the secrets associated with the stores:
```shell
kubectl create secret generic http-keystore --from-file keystore.jks
kubectl create secret generic http-truststore --from-file truststore.jks
```

Run the integration:
```shell
kamel run --dev \
  -t mount.resources=secret:http-keystore/keystore.jks@/etc/ssl/keystore.jks \
  -t mount.resources=secret:http-truststore/truststore.jks@/etc/ssl/truststore.jks \
  -t container.port=8443 -t service.type=NodePort \
  NettySecureServer.java
```

Get the service location. If you're running on minikube, run `minikube service netty-secure-server --url=true --https=true`.

You should see "Hello Secure World" displayed on `https://<service-location>/hello`.
Alternatively, you could run: `curl -vk https://<service-location>/hello`.

### Run the PlatformHttpServer

```
kamel run --dev PlatformHttpServer.java
```

Get the service location. If you're running on minikube, run `minikube service platform-http-server --url=true`.

You can now run `curl -H name:World http://<service-location>/hello`.

### Run the PlatformHttpsServer

This integration requires a server key and certificate. 
Open [PlatformHttpsServer.java](./PlatformHttpsServer.java) to find instructions on how to generate those. 

Run the integration:

```
kubectl create secret generic my-self-signed-ssl --from-file=server.key --from-file=server.crt

kamel run --dev PlatformHttpsServer.java
```

Get the service location. If you're running on minikube, run `minikube service platform-https-server --url=true --https=true`.

You can now run `curl -vk -H name:World https://<service-location>/hello`.
