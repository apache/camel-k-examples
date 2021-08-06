# PostgreSQL to Log example

- Ensure you have helm installed so you could use it on Minikube

- Run the following command

      helm install my-release bitnami/postgresql
      NAME: my-release
      LAST DEPLOYED: Thu Jul 29 11:05:23 2021
      NAMESPACE: default
      STATUS: deployed
      REVISION: 1
      TEST SUITE: None
      NOTES:
      ** Please be patient while the chart is being deployed **
    
    PostgreSQL can be accessed via port 5432 on the following DNS name from within your cluster:
    
      my-release-postgresql.default.svc.cluster.local - Read/Write connection
    
    To get the password for "postgres" run:
    
      export POSTGRES_PASSWORD=$(kubectl get secret --namespace default my-release-postgresql -o jsonpath="{.data.postgresql-password}" | base64 --decode)
    
    To connect to your database run the following command:
    
      kubectl run my-release-postgresql-client --rm --tty -i --restart='Never' --namespace default --image docker.io/bitnami/postgresql:11.12.0-debian-10-r38 --env="PGPASSWORD=$POSTGRES_PASSWORD" --command -- psql --host my-release-postgresql -U postgres -d postgres -p 5432
    
    
    
    To connect to your database from outside the cluster execute the following commands:
    
      kubectl port-forward --namespace default svc/my-release-postgresql 5432:5432 &
      PGPASSWORD="$POSTGRES_PASSWORD" psql --host 127.0.0.1 -U postgres -d postgres -p 5432

- Now it's time to create the database and populate it

      kubectl run my-release-postgresql-client --rm --tty -i --restart='Never' --namespace default --image docker.io/bitnami/postgresql:11.12.0-debian-10-r38 --env="PGPASSWORD=$POSTGRES_PASSWORD" --command -- psql --host my-release-postgresql -U postgres -d postgres -p 5432
      If you don't see a command prompt, try pressing enter.
      postgres=# CREATE TABLE accounts (
      postgres(# user_id serial PRIMARY KEY,
      postgres(# username VARCHAR ( 50 ) UNIQUE NOT NULL,
      postgres(# city VARCHAR ( 50 ) NOT NULL
      postgres(# );
      CREATE TABLE
      postgres=# INSERT into accounts (username,city) VALUES ('andrea', 'Roma');
      INSERT 0 1
      postgres=# INSERT into accounts (username,city) VALUES ('John', 'New York');
      INSERT 0 1
      postgres=# select * from accounts;
      user_id | username |   city
      ---------+----------+----------
          1 | andrea   | Roma
          2 | John     | New York
      (2 rows)

- So we now have two rows to consume.

- Add the correct credentials and address in the `flow-binding.yaml` for the PostgreSQL database.

- Run the following commands

      kubectl apply -f log-sink.kamelet.yaml
      kubectl apply -f flow-binding.yaml

- Check logs

      kamel logs sql-to-log

- You should see the following output

      [1] 2021-07-29 09:55:55,058 INFO  [org.apa.cam.k.Runtime] (main) Apache Camel K Runtime 1.8.0
      [1] 2021-07-29 09:55:55,135 INFO  [org.apa.cam.qua.cor.CamelBootstrapRecorder] (main) Bootstrap runtime: org.apache.camel.quarkus.main.CamelMainRuntime
      [1] 2021-07-29 09:55:55,550 INFO  [org.apa.cam.k.lis.SourcesConfigurer] (main) Loading routes from: SourceDefinition{name='camel-k-embedded-flow', language='yaml', type='source', location='file:/etc/camel/sources/camel-k-embedded-flow.yaml', }
      [1] 2021-07-29 09:55:55,902 INFO  [org.apa.cam.k.lis.SourcesConfigurer] (main) Loading routes from: SourceDefinition{name='log-sink', language='yaml', type='source', location='file:/etc/camel/sources/log-sink.yaml', }
      [1] 2021-07-29 09:55:55,906 INFO  [org.apa.cam.k.lis.SourcesConfigurer] (main) Loading routes from: SourceDefinition{name='postgresql-source', language='yaml', type='source', location='file:/etc/camel/sources/postgresql-source.yaml', }
      [1] 2021-07-29 09:55:56,079 INFO  [org.apa.cam.imp.eng.AutowiredLifecycleStrategy] (main) Autowired property: dataSource on component: sql as exactly one instance of type: javax.sql.DataSource (org.apache.commons.dbcp2.BasicDataSource) found in the registry
      [1] 2021-07-29 09:55:56,174 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main) Routes startup summary (total:3 started:3)
      [1] 2021-07-29 09:55:56,174 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main)     Started route1 (kamelet://postgresql-source/source)
      [1] 2021-07-29 09:55:56,174 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main)     Started source (sql://SELECT%20*%20FROM%20accounts)
      [1] 2021-07-29 09:55:56,174 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main)     Started sink (kamelet://source)
      [1] 2021-07-29 09:55:56,174 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main) Apache Camel 3.11.0 (camel-1) started in 180ms (build:0ms init:108ms start:72ms)
      [1] 2021-07-29 09:55:56,177 INFO  [io.quarkus] (main) camel-k-integration 1.5.0 on JVM (powered by Quarkus 2.0.0.Final) started in 2.272s.
      [1] 2021-07-29 09:55:56,177 INFO  [io.quarkus] (main) Profile prod activated.
      [1] 2021-07-29 09:55:56,178 INFO  [io.quarkus] (main) Installed features: [agroal, camel-bean, camel-core, camel-jackson, camel-k-core, camel-k-runtime, camel-kamelet, camel-log, camel-sql, camel-support-common, camel-support-commons-logging, camel-support-spring, camel-yaml-dsl, cdi, narayana-jta, smallrye-context-propagation]
      [1] 2021-07-29 09:55:57,383 INFO  [info] (Camel (camel-1) thread #0 - sql://SELECT%20*%20FROM%20accounts) Exchange[Id: 86D986CD24E5F68-0000000000000000, ExchangePattern: InOnly, Properties: {}, Headers: {Content-Type=application/json}, BodyType: byte[], Body: {"user_id":1,"username":"andrea","city":"Roma"}]
      [1] 2021-07-29 09:55:57,398 INFO  [info] (Camel (camel-1) thread #0 - sql://SELECT%20*%20FROM%20accounts) Exchange[Id: 86D986CD24E5F68-0000000000000001, ExchangePattern: InOnly, Properties: {}, Headers: {Content-Type=application/json}, BodyType: byte[], Body: {"user_id":2,"username":"John","city":"New York"}]

  If you go back to the postgresql console you won't have any row on the database, because of the consumedQuery parameter

- Now it's time to check the database

      kubectl run my-release-postgresql-client --rm --tty -i --restart='Never' --namespace default --image docker.io/bitnami/postgresql:11.12.0-debian-10-r38 --env="PGPASSWORD=$POSTGRES_PASSWORD" --command -- psql --host my-release-postgresql -U postgres -d postgres -p 5432
      If you don't see a command prompt, try pressing enter.
      postgres=# select * from Accounts;
       user_id | username | city
      ---------+----------+------
      (0 rows)
