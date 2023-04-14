# Протоколы обмена информацией

[![Build project](https://github.com/Romanow/web-protocols/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/Romanow/web-protocols/actions/workflows/build.yml)

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

### Common modules

* [API models](/api)
* [Server Common](/common-server)
* [Client Common](/common-client)

### RESTful

* [RESTful Server](/restful)
* [Rest Client](/rest-client)

##### Запуск сервера

```shell
$ ./gradlew restful:bootRun
```

Для просмотра API доступен [Swagger UI](http://localhost:8080/swagger-ui/index.html)

##### Запуск клиента

```shell
$ java -jar rest-client/build/libs/rest-client.jar
```

### GraphQL

* [GraphQL Server](/graphql)

##### Запуск сервера

```shell
$ ./gradlew graphql:bootRun
```

Для навигации по API можно использовать [GraphiQL][http://localhost:8080/graphiql].

### gRPC

* [gRPC Protocol](/grpc-protocol)
* [gRPC Server](/grpc-server)
* [gRPC Client](/grpc-client)

##### Запуск сервера

```shell
$ ./gradlew grpc-server:bootRun
```

##### Запуск клиента

```shell
$ java -jar grpc-client/build/libs/grpc-client.jar
```

### SOAP

* [SOAP Server](/soap-server)
* [SOAP Client](/soap-client)

##### Запуск сервера

```shell
$ ./gradlew soap-server:bootRun
```

##### Запуск клиента

```shell
$ java -jar soap-client/build/libs/soap-client.jar
```