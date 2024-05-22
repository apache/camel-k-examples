# Rest Camel K examples

Find useful examples about how to Produce/Consume RESTful webservices in a Camel K integration.

## REST endpoint with Java DSL

```
kamel run --dev RestDSL.java

kamel run --dev rest-dsl.js
```

If on minikube, you can get the service endpoint address like this ...

```
$ minikube service rest-dsl
Starting tunnel for service rest-dsl.
|-----------|-----------|-------------|------------------------|
| NAMESPACE |   NAME    | TARGET PORT |          URL           |
|-----------|-----------|-------------|------------------------|
| default   | rest-dsl  |             | http://127.0.0.1:58632 |
|-----------|-----------|-------------|------------------------|
```

You can then test that endpoint like this ...

```
$ curl -i http://127.0.0.1:58632/hello
HTTP/1.1 200 OK
Accept: */*
User-Agent: curl/8.6.0
transfer-encoding: chunked
Content-Type: text/plain

Hello World
```