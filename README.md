# Протоколы обмена информацией

## Общие модули

1. [API](/api) – общий для DTO-объектов.
2. [SOAP протокол](/soap-protocol) – программное описание протокола.
3. [gRPC протокол](/grpc-protocol) –
   описание [протокола взаимодействия](/grpc-protocol/src/main/proto/TestService.proto) и сгенерированные DTO.

## Модули

### SOAP

1. [SOAP сервер](/soap-server) – SOAP сервер, базируется на описании из модуля [soap протокол](/soap-protocol) и
   генерирует сервер.
2. [SOAP клиент](/soap-client) – SOAP клиент, генерирует DTO-объекты [wsdl](/soap-client/src/main/resources/wsdl).
   Запросы и ответы логгирует в stdout.

### GraphQL

[GraphQL](/graphql) – GraphQL сервер, [описание схемы](/graphql/src/main/resources/graphql/authors.graphqls).

GraphQL Explorer [GraphiQL](http://localhost:8080/graphiql). Subscription url `/graphql`, т.к. graphiql не умеет
задавать другой url.

### gRPC

1. [gRPC сервер](/grpc-server) – gRPC сервер, запускается вместо HTTP. Работает в режиме запрос-ответ.
2. [gRPC клиент](/grpc-client) – gRPC клиент.

### REST

[RESTful сервер](/restful) – RESTful сервер, методы:

По аннотациям в коде генерируется OpenAPI спецификация, для просмотра в браузере
используется [Swagger UI](http://localhost:8080/swagger-ui.html).

Так же используется генерация ASCIIdoc документации по тестам, `./gradlew :rest-server:asciidoctor`. Результат запуска
будет располагаться в
папке [/rest-server/build/generated-html/html5/index.html](/restful/build/generated-html/html5/index.html).

### Contract Driven Development

1. [Producer](/producer) – содержит в себе [описание контракта](/producer/src/test/resources/contracts), по ним
   на [общих тестовых классов](/producer/src/test/java/ru/romanow/protocols/producer/web) генерируются тесты.<br />После
   запуска `./gradlew :producer:test :producer:publishToMavenLocal` в `build/generated-test-soruces` создаются и
   выполняются тесты на основе описанных контрактов, а результаты тестов собираются в
   директории `/producer/build/stubs/META-INF/ru.romanow.protocols/producer/1.0.0/mappings/operation`, запаковываются в
   jar и публикуются в _локальный_ `~/.m2`.

2. [Consumer](/consumer) – задача потребителя проверить, что его система корректно функционирует на ответах от сервера.
   В тестах выполняются запросы к stub-серверу, но основное отличие в том, что овтеты, которые получает клиент,
   предоставляет ему сервер (producer), они корректны, т.к. они сгенерированы самим сервером на основе тестов.

## Сборка и запуск

```shell
./gradlew clean build
./gradlew bootRun -p <module-name>
```