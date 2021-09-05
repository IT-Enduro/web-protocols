# Протоколы обмена информацией

## Общие модули

1. [API](/api) – общий для DTO-объектов.
1. [SOAP протокол](/soap-protocol) – программное описание протокола.
1. [gRPC протокол](/grpc-protocol) –
   описание [протокола взаимодействия](/grpc-protocol/src/main/proto/TestService.proto) и сгенерированные DTO.

## Модули

### SOAP

1. [SOAP сервер](/soap-server) – SOAP сервер, базируется на описании из модуля [soap протокол](/soap-protocol) и
   генерирует сервер.
1. [SOAP клиент](/soap-client) – SOAP клиент, генерирует DTO-объекты [wsdl](/soap-client/src/main/resources/wsdl).
   Запросы и ответы логгирует в stdout.

### GraphQL

[GrahQL](/graphql) – GraphQL сервер, [описание схемы](/graphql/src/main/resources/schema/authros.graphqls).

Для запросов можно использовать _встроенный_ [GraphQL ALtair](https://altair.sirmuel.design/) по
адресу `http://localhost:8080/altair`.

Для просмотра всей схемы можно использовать [Voyager](https://apis.guru/graphql-voyager/) по
адресу `http://localhost:8080/voyager`.

Т.к. эти два модуля идут как встроенные модули, они сразу отображают текущую схему.

### gRPC

1. [gRPC сервер](/grpc-server) – gRPC сервер, запускается вместо HTTP. Работает в режиме запрос-ответ.
1. [gRPC клиент](/grpc-client) – gRPC клиент.

### REST

[REST сервер](/rest-server) – REST сервер, методы:

* `GET /api/v1/ping` – простой ping, возвращает OK.
* `GET /api/v1/cookie` – утсанавливает Cookie.
* `GET /api/v1/opertation/process` – на вход принимает `id`, если `id` < 100, то выкидывается 418 ошибка.

По /api-docs генериуется OpenAPI спецификация, для просмотра в браузере используется Swagger
UI `http://localhost:8080/swagger-ui.html`.

Так же используется генерация ASCIIdoc документации по тестам, `./gradlew :rest-server:asciidoctor`. Результат запуска
будет распологаться в
папке [/rest-server/build/generated-html/html5/index.html](/rest-server/build/generated-html/html5/index.html).

### Contract Driven Development

1. [Producer](/producer) – содержит в себе [описание контракта](/producer/src/test/resources/contracts), по ним
   на [общих тестовых классов](/producer/src/test/java/ru/romanow/protocols/producer/web) генерируются тесты.<br />После
   запуска `./gradlew :producer:test :producer:publishToMavenLocal` в `build/generated-test-soruces` создаются и
   выполняются тесты на основе описанных контрактов, а результаты тестов собираются в
   директории `/producer/build/stubs/META-INF/ru.romanow.protocols/producer/1.0.0/mappings/operation`, запаковываются в
   jar и публикуются в _локальный_ m2.

1. [Consumer](/consumer) – задача потребителя проверить, что его система корректно функционирует на ответах от сервера.
   В тестах выполняются запросы к stub-серверу, но основное отличие в том, что овтеты, которые получает клиент,
   предоставляет ему сервер (producer), они корректны, т.к. они сгенерированы самим сервером на основе тестов.

## Сборка и запуск

```shell
./gradlew clean build
./gradlew bootRun -p <module-name>
```