# gRPC vs RESTful: Битва протоколов — как выбрать идеальный API для вашей архитектуры

## Аннотация

В мире современных API сейчас два основных подхода: gRPC и RESTful. В докладе мы поговорим про каждый из них, рассмотрим
преимущества и ограничения каждого подхода, а также их применимость в различных сценариях. Мы разберем 10 ключевых
критериев: от формата протокола и удобства разработки до observability и безопасности, чтобы вы могли принимать
архитектурные решения на основе конкретных требований вашего проекта.

## План

1. Что такое RESTful, основные принципы RESTful API.
2. Протокол gRPC: HTTP/2, RPC и Protocol Buffers.
3. Сравнение RESTful от gRPC:
    * Протокол и транспортный уровень.
    * Стиль коммуникации и архитектурная парадигма.
    * Формат данных и генерация моделей.
    * Стриминг данных.
    * Кеширование.
    * Работа с браузерами и из консоли.
    * Архитектурные паттерны: Load Balancing, Retry, Timeouts, Circuit Breaker.
    * Производительность (тут нужны benchmarks).
    * Безопасность (сертификаты (в том числе mTLS)), Token-based авторизация.
    * Observability: трассировка, логгирование, мониторинг запросов.
4. Вместо выводов: когда и что выбирать для вашего проекта.

## Доклад

### RESTful

REST — это аббревиатура от Representational State Transfer (Передача Состояния Представления). Это согласованный набор
архитектурных принципов, использующий стандартные возможности протокола HTTP. Его ключевая идея — представление данных в
виде ресурсов, доступ к которым осуществляется через уникальные URI.

Сервер содержит ресурсы и определяет операции над ними. Клиент работает с представлениями ресурсов, для изменения
состояния сервера он передает желаемое представление ресурса на сервер. В основе лежит 3 главных принципа:

* **Определение ресурсов**: в терминологии RESTful что угодно может быть ресурсом — HTML-документ, изображение,
  информация о конкретном пользователе и т.д. Каждый ресурс должен быть уникально обозначен постоянным идентификатором.
  Постоянный означает, что идентификатор не изменится за время обмена данными, и даже когда изменится состояние ресурса.
  Если ресурсу присваивается другой идентификатор, сервер должен сообщить клиенту, что запрос был неудачным и дать
  ссылку на новый адрес.
* **Управление ресурсами через представления**: клиент управляет ресурсами, направляя серверу представления, обычно в
  виде JSON, содержащего представление, который он хотел бы добавить, удалить или изменить. В RESTful у сервера полный
  контроль над ресурсами, и он отвечает за любые изменения. Когда клиент хочет внести изменения в ресурсы, он посылает
  серверу представление того, каким он видит итоговый ресурс. Сервер принимает запрос как предложение, но за ним всё так
  же остаётся полный контроль.
* **Самодостаточные сообщения**: каждое сообщение содержит достаточно информации для описания того, как его выполнить.

Действия с ресурсами выполняются с помощью HTTP-методов:

* `GET` для получения данных.
* `POST` для создания.
* `PUT` или `PATCH` для обновления.
* `DELETE` для удаления.

```yaml
openapi: 3.0.1
info:
  title: Person Service
  version: "1.0"
servers:
  - url: http://localhost:8080
paths:
  /api/v1/persons:
    get:
      tags:
        - REST API
      summary: Информация по всем людям
      operationId: all
      responses:
        '200':
          description: Все люди
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/PersonResponse'
    post:
      tags:
        - REST API
      summary: Создание новой записи о человеке
      operationId: create
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/PersonRequest'
        required: true
      responses:
        '201':
          description: Новая запись успешно создана
          headers:
            Location:
              description: Path to new Person
              style: simple
              schema:
                type: string
        '400':
          description: Ошибка входных данных
          content:
            '*/*':
              schema:
                $ref: '#/components/schemas/ValidationErrorResponse'
  /api/v1/persons/{id}:
    get:
      tags:
        - REST API
      summary: Информация о человеке
      operationId: getById
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: integer
            format: int32
      responses:
        '200':
          description: Данные о человеке
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/PersonResponse'
        '404':
          description: Человек с таким ID не найден
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
    delete:
      tags:
        - REST API
      summary: Удаление записи о человеке
      operationId: delete
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: integer
            format: int32
      responses:
        '204':
          description: Человек с таким ID успешно удален
    patch:
      tags:
        - REST API
      summary: Обновление существующей записи о человеке
      operationId: update
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: integer
            format: int32
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/PersonRequest'
        required: true
      responses:
        '200':
          description: Человек с таким ID успешно обновлен
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/PersonResponse'
        '400':
          description: Ошибка входных данных
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ValidationErrorResponse'
        '404':
          description: Человек с таким ID не найден
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
components:
  schemas:
    ValidationErrorResponse:
      type: object
      properties:
        message:
          type: string
        errors:
          type: object
          additionalProperties:
            type: string
    PersonRequest:
      type: object
      properties:
        name:
          type: string
        age:
          type: integer
          format: int32
        address:
          type: string
        work:
          type: string
      required:
        - name
    PersonResponse:
      type: object
      properties:
        id:
          type: integer
          format: int32
        name:
          type: string
        age:
          type: integer
          format: int32
        address:
          type: string
        work:
          type: string
      required:
        - id
        - name
    ErrorResponse:
      type: object
      properties:
        message:
          type: string
```

### gRPC

gRPC — это фреймворк для удаленного вызова процедур (RPC), разработанный Google. В его основе лежит Protocol Buffers —
бинарный формат данных, который обеспечивает быструю сериализацию и компактный размер сообщений. В отличие от REST, gRPC
использует HTTP/2, что позволяет мультиплексировать несколько запросов через одно соединение и поддерживать
двунаправленные потоки.

gRPC описывается через `.proto`-файлы (в файле описываются сервисы, методы, входные и выходные сообщения.), где задаются
методы сервиса и структуры сообщений. Т.е. в нем используется подход Contract First. Потом с помощью компилятора
`protoc` генерируется код для клиента и сервера.

```protobuf
syntax = "proto3";

package ru.romanow.persons;

import "google/protobuf/empty.proto";

service PersonService {
  rpc getById(IdRequest) returns (PersonResponse);
  rpc persons(google.protobuf.Empty) returns (ListPersonsResponse);
  rpc create(CreatePersonRequest) returns (PersonResponse);
  rpc update(UpdatePersonRequest) returns (PersonResponse);
  rpc delete(IdRequest) returns (google.protobuf.Empty);
}

message IdRequest {
  int32 id = 1;
}

message CreatePersonRequest {
  int32 id = 1;
  string name = 2;
  int32 age = 3;
  string address = 4;
  string work = 5;
}

message UpdatePersonRequest {
  int32 id = 1;
  string name = 2;
  int32 age = 3;
  string address = 4;
  string work = 5;
}

message PersonResponse {
  int32 id = 1;
  string name = 2;
  int32 age = 3;
  string address = 4;
  string work = 5;
}

message ListPersonsResponse {
  repeated PersonResponse persons = 1;
}
```

### Сравнение RESTful от gRPC

В отличие от REST, где API часто проектируется _на лету_, gRPC by-design Contract First, что требует проработки
архитектуры до написания кода.

#### Протокол и транспортный уровень

#### Стиль коммуникации и архитектурная парадигма

RESTful API строится на основе архитектурного стиля REST (Representational State Transfer), который ориентирован на
работу с ресурсами. Взаимодействие происходит через HTTP-протокол, где стандартные методы (`GET`, `POST`, `PUT`,
`PATCH`, `DELETE`)используются для управления ресурсами. REST использует текстовые форматы данных, такие как JSON или
XML, что делает его универсальным и удобным для веб-приложений.

gRPC, напротив, реализует RPC (Remote Procedure Call) – модель удаленного вызова процедур, где клиент обращается к
серверу как к локальному объекту. Взаимодействие происходит через HTTP/2, что обеспечивает высокую производительность
и поддержку стриминга данных.

Основное преимущество RPC заключается в том, что оно абстрагирует сетевое взаимодействие, позволяя разработчикам
вызывать удаленные методы так же, как локальные. Это упрощает программирование и делает код более читаемым и удобным в
использовании.

#### Формат данных и генерация моделей

#### Стриминг данных

#### Кеширование

Важное преимущество REST — встроенная поддержка кэширования через HTTP-заголовки (`ETag`, `Cache-Control` и т.д.), что
снижает нагрузку на сервер.

#### Работа с браузерами и из консоли

Взаимодействие с UI возможно только через envoy proxy, т.к. помимо HTTP/2 требуются еще дополнительные
параметры [issue](https://github.com/grpc/grpc-web/issues/347).

##### Консольные клиенты

###### HTTP

Самый распространенный клиент – curl. Поддержка: HTTPS, HTTP/2, методы, заголовки, куки, аутентификация.

```shell
$ curl -w "Start Transfer:\t%{time_starttransfer}\nTotal Time:\t%{time_total}\n" \
    -o /dev/null -s http://localhost:8080/api/v1/servers
```

[HTTPie]() – более удобная альтернатива cURL с подсветкой JSON и простым синтаксисом.

###### gRPC

Т.к. gRPC использует схему, то ее нужно передать консольному клиенту. Для этого используется gRPC Reflection — механизм,
позволяющий клиентам динамически узнавать информацию о сервере (доступные сервисы, методы, типы сообщений) без
необходимости заранее предоставлять .proto-файлы. Если Reflection не включён, нужно указывать .proto-файлы вручную через
флаг `-proto`.

Самый распространенный gRPC клиент – [grpcur](https://github.com/fullstorydev/grpcurl), аналог curl для gRPC.

```shell
$ grpcurl -plaintext localhost:8080 list
> grpc.health.v1.Health
> ru.romanow.protocols.grpc.ServerService

$ grpcurl -plaintext localhost:8080 list ru.romanow.protocols.grpc.ServerService
> ru.romanow.protocols.grpc.ServerService.create
> ru.romanow.protocols.grpc.ServerService.delete
> ru.romanow.protocols.grpc.ServerService.findAll
> ru.romanow.protocols.grpc.ServerService.findInCity
> ru.romanow.protocols.grpc.ServerService.getById
> ru.romanow.protocols.grpc.ServerService.update

$ grpcurl -plaintext localhost:8080 describe ru.romanow.protocols.grpc.ServerResponse
> ru.romanow.protocols.grpc.ServerResponse is a message:
> message ServerResponse {
>   int32 id = 1;
>   .ru.romanow.protocols.grpc.Purpose purpose = 2;
>   int32 latency = 3;
>   int32 bandwidth = 4;
>   .ru.romanow.protocols.grpc.StateInfo state = 5;
> }

$ grpcurl -d '{"id": 1}' -plaintext localhost:8080 ru.romanow.protocols.grpc.ServerService.getById
> {
>   "purpose": "BACKEND",
>   "latency": 10,
>   "bandwidth": 10000,
>   "state": {
>     "city": "Moscow",
>     "country": "Russia"
>   }
> }
```

Второй удобный клиент [evans](https://github.com/ktr0731/evans) – он позволяет работать с gRPC в интерактивном режиме.

```shell
$ evans --reflection --host localhost --port 8080
$ package ru.romanow.protocols.grpc
$ show service
+---------------+------------+---------------------+-----------------+
|    SERVICE    |    RPC     |    REQUEST TYPE     |  RESPONSE TYPE  |
+---------------+------------+---------------------+-----------------+
| ServerService | getById    | ID                  | ServerResponse  |
| ServerService | findAll    | Empty               | ServersResponse |
| ServerService | findInCity | City                | ServersResponse |
| ServerService | create     | CreateServerRequest | ServerResponse  |
| ServerService | update     | CreateServerRequest | ServerResponse  |
| ServerService | delete     | ID                  | Empty           |
+---------------+------------+---------------------+-----------------+

$ desc ServerResponse
+-----------+--------------------------+----------+
|   FIELD   |           TYPE           | REPEATED |
+-----------+--------------------------+----------+
| bandwidth | TYPE_INT32               | false    |
| id        | TYPE_INT32               | false    |
| latency   | TYPE_INT32               | false    |
| purpose   | TYPE_ENUM (Purpose)      | false    |
| state     | TYPE_MESSAGE (StateInfo) | false    |
+-----------+--------------------------+----------+

$ service ServerService
$ call getById
> id (TYPE_INT32) => 1
> {
>   "bandwidth": 10000,
>   "latency": 10,
>   "purpose": "BACKEND",
>   "state": {
>     "city": "Moscow",
>     "country": "Russia"
>   }
> } 
```

#### Архитектурные паттерны

Load Balancing, Retry, Timeouts, Circuit Breaker

#### Производительность

#### Безопасность

#### Observability

Трассировка, логгирование, мониторинг запросов

### Вместо выводов: когда и что выбирать для вашего проекта