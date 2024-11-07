# Open API Camel K examples

Find useful examples about how to expose an Open API specification in a Camel K integration.

## Greetings example

Deploy the examples running

```
kubectl create configmap my-openapi --from-file=greetings-api.json
kamel run greetings.yaml --resource configmap:my-openapi --dev
```

If on minikube, you can get the service endpoint address like this ...

```
$ minikube service greetings
Starting tunnel for service greetings.
|-----------|-----------|-------------|------------------------|
| NAMESPACE |   NAME    | TARGET PORT |          URL           |
|-----------|-----------|-------------|------------------------|
| default   | greetings |             | http://127.0.0.1:58512 |
|-----------|-----------|-------------|------------------------|
```

You can then test that endpoint like this ...

```
$ curl -i http://127.0.0.1:58512/camel/greetings/hello
HTTP/1.1 200 OK
Accept: */*
name: hello
User-Agent: curl/8.6.0
transfer-encoding: chunked

Hello from hello
```
