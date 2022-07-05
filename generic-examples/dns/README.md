# DNS queries in Camel K examples

Contains examples on how to perform DNS queries in a Camel K integration.

You can find more information about Apache Camel and Apache Camel K on the [official Camel website](https://camel.apache.org).

## Before you begin

Read the general instructions in the [root README.md file](/README.md) for setting up your environment and the Kubernetes cluster before looking at this example.

Make sure you've read the [installation instructions](https://camel.apache.org/camel-k/latest/installation/installation.html) for your specific
cluster before starting the example.

## Understanding the Examples
- [`ip.js`](./ip.js): defines a route that every second, uses a message header to query DNS for an IP address and logs the result.
- [`lookup.js`](./lookup.js): defines a route that every 10 seconds, uses message headers to lookup the `MX` records associated with a domain name, processes the response and logs it.

To learn about more options for configuring DNS queries, [see here](https://camel.apache.org/components/3.17.x/dns-component.html)

## Running the Examples
Run the `ip.js` integration:
```
kamel run ip.js --dev
```
You should see the `IP address` logged to the terminal every second:
```terminal
...
[1] 2022-07-05 09:10:29,983 INFO  [dns] (Camel (camel-1) thread #1 - timer://dns) Exchange[ExchangePattern: InOnly, BodyType: java.net.Inet4Address, Body: 142.251.36.36]
[1] 2022-07-05 09:10:30,984 INFO  [dns] (Camel (camel-1) thread #1 - timer://dns) Exchange[ExchangePattern: InOnly, BodyType: java.net.Inet4Address, Body: 142.251.36.36]
```

Run the `lookup.js` integration:
```
kamel run lookup.js --dev
```
Every 10 seconds, You should see the MX records associated with the specified domain being logged to the terminal. Each MX record should have a `Target` value, a `Priority` value for the target, and a `TTL` value:
```terminal
...
[1] 2022-07-05 09:14:27,803 INFO  [mxrecord] (Camel (camel-1) thread #1 - timer://dns) Target: gmail-smtp-in.l.google.com., Priority: 5, TTL: 30
[1] 2022-07-05 09:14:27,821 INFO  [mxrecord] (Camel (camel-1) thread #1 - timer://dns) Target: alt2.gmail-smtp-in.l.google.com., Priority: 20, TTL: 30
[1] 2022-07-05 09:14:27,829 INFO  [mxrecord] (Camel (camel-1) thread #1 - timer://dns) Target: alt4.gmail-smtp-in.l.google.com., Priority: 40, TTL: 30
[1] 2022-07-05 09:14:27,833 INFO  [mxrecord] (Camel (camel-1) thread #1 - timer://dns) Target: alt3.gmail-smtp-in.l.google.com., Priority: 30, TTL: 30
[1] 2022-07-05 09:14:27,858 INFO  [mxrecord] (Camel (camel-1) thread #1 - timer://dns) Target: alt1.gmail-smtp-in.l.google.com., Priority: 10, TTL: 30
```