# Протоколы передачи информации

## Общие модули
1. [API](/api) – общий для DTO-объектов.
1. [SOAP протокол](/soap-protocol) – программное описание протокола.
1. [gRPC протоокл](/grpc-protocol) – описание [протокола взаимодействия](/grpc-protocol/src/main/proto/TestService.proto) и сгенерированные DTO.

## Модули
### SOAP
1. [SOAP сервер](/soap-server) – SOAP сервер, базируется на описании из модуля [soap протокол](/soap-protocol) и генерирует сервер.
1. [SOAP клиент](/soap-client) – SOAP клиент, генерирует DTO-объекты [wsdl](/soap-client/src/main/resources/wsdl). Запросы и ответы логгирует в stdout.

### GraphQL
1. [GrahQL](/graphql) –

### gRPC
1. [gRPC сервер](/grpc-server) –
1. [gRPC клиент](/grpc-client) –

### REST
1. [REST сервер](/rest-server) – REST сервер, методы:
  * /api/ping – простой ping, возвращает OK.
  * /api/cookie – утсанавливает Cookie.
  * /api/op/process – на вход принимает `id`, если `id` < 100, то выкидывается 418 ошибка.
  
По /api-docs генериуется OpenAPI спецификация, для просмотра в браузере развернуть локально Swagger UI:
```shell script
docker run -p 8010:8080 swaggerapi/swagger-ui
``` 
Так же используется генерация ASCIIdoc документации по тестам, `./gradlew :rest-server:asciidoctor`. 

### Contract Driven Development
1. [Consumer](/consumer) –
1. [Producer](/producer) –

### XML-RPC
1. [XML-RPC сервер](/xml-rpc-server) – сервер на базе Apache XML-RPC модуля (проект сдох в 2010 году) под управлением Spring: получает запрос в контроллер '/', всю дальнейшую обработку передает на `XmlRpcServletServer`.
1. [XML-RPC клиент](/xml-rpc-client) – клиент XML-RPC.

## Сборка и запуск
1. Установить Java 11.
1. `chmod a+x gradlew`
1. `./gradlew clean build`
1. `./gradlew :rest-server:bootRun`