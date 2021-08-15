# Spring Boot with DynamoDB and RabbitMQ as Docker Containers

The purpose of this repository is to learn about DynamoDB. So if some things seems to be overthing then they are overthought so that I could gain knowledge.

## Running Containers

Before running Docker containers both Spring Boot Apps need to be build. The Front App provides model library for the Backend App so it needs to be build first.

```bash
mvn clean install -f ./front/pom.xml
mvn clean install -f ./backend/pom.xml
docker-compose up --build
```

Or as one huge command:
```bash
docker-compose down && mvn clean install -f ./front/pom.xml && mvn clean install -f ./backend/pom.xml && docker-compose up --build
```

This is a very fast command where Maven skips quality checks and uses multicores:
```bash
docker-compose down && mvn clean install -f ./front/pom.xml -Pno-quality -T4 && mvn clean install -f ./backend/pom.xml -Pno-quality -T4 && docker-compose up --build
```

### Fast build

 * Both Spring Boot Apps have a Maven profile `no-quality` defined which skips some quality checks.
 * Maven can run on multiple threads be providing `-T` parameter.

 ```bash
 mvn clean install -T4 -f ./front/pom.xml -Pno-quality && mvn clean install -T4 -f ./backend/pom.xml -Pno-quality
 ```

## DynamoDB

To access DynamoDB tables and documents use [DynamoDB Admin](http://localhost:8080) web tool.

For Backend App it is required to provide the same credencials as defined in `docker-compose` file. Otherwise tables in DynamoDB won't be shown.

## RabbitMQ

On default 15672 port a [RabbitMQ Management](http://localhost:15672) is available. User is `cookieMq` and password is `very_secret_cookie_password`.

In case when either or both Spring Boot Apps cannot connect to RsbbitMQ, check out if there is avaliable space (e.g. egzamin how much space is taken by all images in Docker Desktop). If that doesn't help then execute these two commands to free up some space for RabbitMQ Container:
 ```bash
 docker system prune
 docker volume rm $(docker volume ls -qf dangling=true)
 ```

## Front Spring Boot App

This app produces messages that are pushed into RabbitMQ queue. This app does not have a direct access into DynamoDB. It requires to use Backend App to interact with DynamoDB. To use this app check out the 
[Swagger](http://localhost:8081/api/swagger-ui/) documentation.

It is possible to run this Front App locally (e.g. for debugging purposes) and use other Docker containers. To do so, run this App with `local` Spring profile and comment out respected container in `docker-compose` file.

## Backend Spring Boot App

This app consumes messages from a RabbitMQ Queue and stores data in DynamoDB.
A [Swagger](http://localhost:8082/api/swagger-ui/) documentation is avaliable along with a Postman collection.

Just like with the Front All it is possible to run this Backend App locally (e.g. for debugging purposes) and use other Docker containers. To do so, run this App with `local` Spring profile and comment out respected container in `docker-compose` file.

## Setup Container

This container's purpose is to run a shell sccript and then it's stopped. The shell script contains few Curl commands to insert (send POST request) few movies into DynamoDB table via Front App endpoint.

## All links
 * [Front App Swagger](http://localhost:8081/api/swagger-ui/)
 * [Backend App Swagger](http://localhost:8082/api/swagger-ui/)
 * [DynamoDB Admin](http://localhost:8080)
 * [RabbitMQ Management](http://localhost:15672)
    * user `cookieMq`
    * password `very_secret_cookie_password`

# Amazon AWS CLI Container

This container runs specific commands, so after the DynamoDB container is running you can play with AWS CLI container.

See [AWS Reference documentation](https://awscli.amazonaws.com/v2/documentation/api/latest/reference/dynamodb/index.html) for more details.

To get an AWS CLI container run `docker pull amazon/aws-cli:latest` command.

### Some Commands

 * List all tables in DynamoDB
   ```bash
   docker run --rm -it --network=host -e AWS_SECRET_ACCESS_KEY=very_secret_aws_key -e AWS_ACCESS_KEY_ID=very_secret_aws_access_key -e AWS_DEFAULT_REGION=local amazon/aws-cli dynamodb list-tables --endpoint-url=http://localhost:8000/
   ```
 * List items in a `mcu_movies` table:
   ```bash
   docker run --rm -it --network=host -e AWS_SECRET_ACCESS_KEY=very_secret_aws_key -e AWS_ACCESS_KEY_ID=very_secret_aws_access_key -e AWS_DEFAULT_REGION=local amazon/aws-cli dynamodb scan --table-name mcu_movies --endpoint-url=http://localhost:8000/
   ```
 * Put one item into a `mcu_movies` table:
   ```bash
   docker run --rm -it --network=host -e AWS_SECRET_ACCESS_KEY=very_secret_aws_key -e AWS_ACCESS_KEY_ID=very_secret_aws_access_key -e AWS_DEFAULT_REGION=local amazon/aws-cli dynamodb put-item --table-name mcu_movies --item '{"title": {"S": "Black Widow"}, "release_year": {"N": "2021"}, "created_at": {"S": "by aws cli"}}' --endpoint-url=http://localhost:8000/
   ```
   A result of this command can be easly viewed in a [DynamoDB Admin Web UI](http://localhost:8080/tables/mcu_movies).
