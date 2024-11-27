[![Build project](https://github.com/Romanow/web-protocols/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/Romanow/web-protocols/actions/workflows/build.yml)
[![License: CC BY-NC-ND 4.0](https://img.shields.io/badge/License-CC%20BY--NC--ND%204.0-lightgrey.svg)](https://creativecommons.org/licenses/by-nc-nd/4.0/)
[![pre-commit](https://img.shields.io/badge/pre--commit-enabled-brightgreen?logo=pre-commit)](https://github.com/pre-commit/pre-commit)

# Протоколы обмена информацией

## Сборка и запуск

```shell
# сборка
$ ./gradlew clean build
````

Для запуска серверов требуется Postgres, запускаем через docker:

```shell
$ docker compose up -d
```

В клиентских приложениях
используется [Spring Shell](https://docs.spring.io/spring-shell/docs/current/reference/htmlsingle/), поэтому запуск
через `java -jar ...`. В клиенте доступны следующие команды:

```log
shell:>help

Built-In Commands
       help: Display help about available commands
       clear: Clear the shell screen.
       quit, exit: Exit the shell.

Server Command
       find-by-id: Get server by Id
       update: Update server by Id
       create: Create server
       delete: Delete server by Id
       find-in-city: Get servers in city
       find-all: Get all servers
```

### RESTful

Сервер:

```shell
$ ./gradlew restful:bootRun
```

Для просмотра API доступен [Swagger UI](http://localhost:8080/swagger-ui/index.html)

Клиент:

```shell
$ java -jar rest-client/build/libs/rest-client.jar
```

### GraphQL

Протокол [servers.graphqls](graphql/src/main/resources/graphql/servers.graphqls).

Сервер:

```shell
$ ./gradlew graphql:bootRun
```

Для навигации по API можно использовать [GraphiQL][http://localhost:8080/graphiql].

### gRPC

Протокол [ServerService.proto](grpc-protocol/src/main/proto/ServerService.proto).

Сервер:

```shell
$ ./gradlew grpc-server:bootRun
```

Клиент:

```shell
$ java -jar grpc-client/build/libs/grpc-client.jar
```

### SOAP

Сервер:

```shell
$ ./gradlew soap-server:bootRun
```

Клиент:

```shell
$ java -jar soap-client/build/libs/soap-client.jar
```
