# Kafka to SQL Server

- Use the quickstart for https://strimzi.io/quickstarts/ and follow the minikube guide.

- If camel-k has been installed in a specific namespace different from the default one, you'll need to add a parameter to all the commands (`-n <namespace_name>`)

- Run the following command

       kubectl run mssql-1 --image=mcr.microsoft.com/mssql/server:2017-latest --port=1433 --env 'ACCEPT_EULA=Y' --env 'SA_PASSWORD=Password!' -n kafka

- Once the pod is up and running we'll need to create the table and populate it with same starting data

      kubectl -n kafka exec -it mssql-1 -- bash
      root@mssql-1:/# /opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P "Password!"
      1> CREATE TABLE accounts (user_id INT PRIMARY KEY, username VARCHAR ( 50 ) UNIQUE NOT NULL, city VARCHAR ( 50 ) NOT NULL );
      2> GO
      1> INSERT into accounts (user_id,username,city) VALUES (1, 'andrea', 'Roma');
      2> GO
      1> INSERT into accounts (user_id,username,city) VALUES (2, 'John', 'New York');
      2> GO

- So we now have two rows in the database.

- Open a different terminal

- Add the correct credentials and container address in the `flow-binding.yaml` for the MSSQL Server database.

- Run the following commands

      kubectl apply -f flow-binding.yaml -n kafka

- Open a different terminal and run the following command

      kubectl -n kafka run kafka-producer -ti --image=quay.io/strimzi/kafka:0.24.0-kafka-2.8.0 --rm=true --restart=Never -- bin/kafka-console-producer.sh --broker-list my-cluster-kafka-bootstrap:9092 --topic test-topic-1

- Send some messages to the kafka topic like for example


      { "user_id":"3", "username":"Vittorio", "city":"Roma" }
      { "user_id":"4", "username":"Hugo", "city":"Paris" }


- Now we can check the database


        kubectl exec -it mssql-1 -- bash
        root@mssql-1:/# /opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P "Password!"
        1> SELECT * from accounts;
        2> GO
        user_id     username                                           city
        ----------- -------------------------------------------------- --------------------------------------------------
                    1 andrea                                             Roma
                    2 John                                               New York
                    3 Vittorio                                           Roma
                    4 Hugo                                               Paris

        (4 rows affected)

- Check logs to see the integration running

      kamel logs kafka-to-sqlserver
