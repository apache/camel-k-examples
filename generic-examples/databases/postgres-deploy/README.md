# How to deploy a simple Postgres DB to a Kubernetes cluster

This is a simple guide on how to deploy and create a Postgres database. **Note**, this is not ready for any production purpose.

## Create a Kubernetes Deployment
**Note:** Openshift environments may first need to change the security context constraints to allow Postgres to perform root operations. To do this, run: 
``` 
oc adm policy add-scc-to-user anyuid -z default 
```

To create a configmap, persist data, deploy Postgres to kubernetes and create a service, run the following commands:
```
kubectl create -f postgres-configmap.yaml
kubectl create -f postgres-storage.yaml
kubectl create -f postgres-deployment.yaml
kubectl create -f postgres-service.yaml
```
## Test the connection

Connection credentials available in the [postgres-configmap.yaml](./postgres-configmap.yaml) descriptor.

```
kubectl get svc postgres
```

To connect to the PostgreSQL database, run the command below, changing the pod name:
```
kubectl exec -it postgres-xxxx -- psql -h postgres -U postgresadmin --password -p 5432 postgresdb
```
You will be prompted for password. Connection credentials are in the [postgres-configmap.yaml](./postgres-configmap.yaml) file. \
After you enter your password, you should get a PostgreSQL shell

## Create a `testdb` database with a `test` table
Run the command below in the PostgreSQL shell to both create a new database and switch to it. Enter the previous password when prompted:
```
CREATE DATABASE testdb;
\c testdb;
```

To create a table and populate it, run:
```
CREATE TABLE test (data TEXT PRIMARY KEY);
INSERT INTO test(data) VALUES ('hello'), ('world');
```
### Read the `test` table from the `testdb` database
```
SELECT * FROM test;
```
You should see 2 rows containing 'hello' and 'world' respectively. Enter `exit` to exit the shell. \
Our `testdb` database works fine and can now be used in a Camel K integration.
