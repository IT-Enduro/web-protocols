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
    * Архитектурные паттерны: Retry, Timeouts, Circuit Breaker.
    * Отмена запроса.
    * Производительность (тут нужны benchmarks).
    * Безопасность (сертификаты (в том числе mTLS)), Token-based авторизация.
    * Observability: трассировка и логгирование запросов.
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

[//]: # (@formatter:off)
```yaml
openapi: 3.0.1
info:
  title: Servers API
  version: "1.0"
servers:
  - url: http://localhost:8080
paths:
  /api/v1/servers:
    get:
      tags:
        - Servers API
      summary: Найти сервера в городе
      operationId: findInCity
      parameters:
        - name: city
          in: query
          required: true
          schema:
            type: string
      responses:
        '200':
          description: Список серверов
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ServersResponse'
            application/xml:
              schema:
                $ref: '#/components/schemas/ServersResponse'
    post:
      tags:
        - Servers API
      summary: Создать новый сервер
      operationId: create
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/CreateServerRequest'
          application/xml:
            schema:
              $ref: '#/components/schemas/CreateServerRequest'
        required: true
      responses:
        '201':
          description: Сервер успешно создан
          headers:
            Location:
              description: Ссылка на созданный сервер
              style: simple
              schema:
                type: string
        '400':
          description: Некорректные параметры запроса
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ValidationErrorResponse'
            application/xml:
              schema:
                $ref: '#/components/schemas/ValidationErrorResponse'
  /api/v1/servers/{id}:
    get:
      tags:
        - Servers API
      summary: Найти сервер по Id
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
          description: Информация о сервере
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ServerResponse'
            application/xml:
              schema:
                $ref: '#/components/schemas/ServerResponse'
        '404':
          description: Сервер не найден по Id
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
            application/xml:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
    delete:
      tags:
        - Servers API
      summary: Удалить сервер по Id
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
          description: Сервер успешно удален
    patch:
      tags:
        - Servers API
      summary: Редактировать данные сервера по Id
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
              $ref: '#/components/schemas/UpdateServerRequest'
          application/xml:
            schema:
              $ref: '#/components/schemas/UpdateServerRequest'
        required: true
      responses:
        '200':
          description: Сервер успешно обновлен
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ServerResponse'
            application/xml:
              schema:
                $ref: '#/components/schemas/ServerResponse'
        '400':
          description: Некорректные параметры запроса
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ValidationErrorResponse'
            application/xml:
              schema:
                $ref: '#/components/schemas/ValidationErrorResponse'
        '404':
          description: Сервер не найден по Id
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
            application/xml:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
components:
  schemas:
    ErrorDescription:
      type: object
      properties:
        field:
          type: string
        error:
          type: string
    ValidationErrorResponse:
      required:
        - error
        - message
      type: object
      properties:
        message:
          type: string
        error:
          type: array
          items:
            $ref: '#/components/schemas/ErrorDescription'
    CreateServerRequest:
      required:
        - bandwidth
        - latency
        - purpose
        - state
      type: object
      properties:
        purpose:
          type: string
        latency:
          maximum: 100
          minimum: 0
          type: integer
          format: int32
        bandwidth:
          maximum: 10000000
          minimum: 0
          type: integer
          format: int32
        state:
          $ref: '#/components/schemas/StateInfo'
    StateInfo:
      type: object
      properties:
        id:
          type: integer
          format: int32
        city:
          type: string
        country:
          type: string
    ErrorResponse:
      type: object
      properties:
        message:
          type: string
    UpdateServerRequest:
      type: object
      properties:
        purpose:
          type: string
        latency:
          maximum: 100
          minimum: 0
          type: integer
          format: int32
        bandwidth:
          maximum: 10000000
          minimum: 0
          type: integer
          format: int32
        state:
          $ref: '#/components/schemas/StateInfo'
    ServerResponse:
      type: object
      properties:
        id:
          type: integer
          format: int32
        purpose:
          type: string
          enum:
            - FRONTEND
            - BACKEND
            - DATABASE
        latency:
          type: integer
          format: int32
        bandwidth:
          type: integer
          format: int32
        state:
          $ref: '#/components/schemas/StateInfo'
    ServersResponse:
      required:
        - servers
      type: object
      properties:
        servers:
          type: array
          items:
            $ref: '#/components/schemas/ServerResponse'
```
[//]: # (@formatter:on)

### gRPC

gRPC — это фреймворк для удаленного вызова процедур (RPC), разработанный Google. В его основе лежит Protocol Buffers —
бинарный формат данных, который обеспечивает быструю сериализацию и компактный размер сообщений. В отличие от REST, gRPC
использует HTTP/2, что позволяет мультиплексировать несколько запросов через одно соединение и поддерживать
двунаправленные потоки.

gRPC описывается через `.proto`-файлы (в файле описываются сервисы, методы, входные и выходные сообщения), где задаются
методы сервиса и структуры сообщений. Т.е. в нем используется подход Contract First. Потом с помощью компилятора
`protoc` генерируется код для клиента и сервера.

```protobuf
syntax = "proto3";

package ru.romanow.protocols.grpc;

import "google/protobuf/empty.proto";

option java_outer_classname = "ServerServiceModels";
option java_package = "ru.romanow.protocols.grpc";

service ServerService {
    rpc getById(ID) returns (ServerResponse) {};
    rpc findAll(google.protobuf.Empty) returns (ServersResponse) {};
    rpc findInCity(City) returns (ServersResponse) {};
    rpc create(CreateServerRequest) returns (ServerResponse) {};
    rpc update(UpdateServerRequest) returns (ServerResponse) {};
    rpc delete(ID) returns (google.protobuf.Empty) {};
}

message ID {
    int32 id = 1;
}

message City {
    string city = 1;
}

message CreateServerRequest {
    Purpose purpose = 1;
    int32 latency = 2;
    int32 bandwidth = 3;
    StateInfo state = 4;
}

message UpdateServerRequest {
    int32 id = 1;
    optional Purpose purpose = 2;
    optional int32 latency = 3;
    optional int32 bandwidth = 4;
    optional StateInfo state = 5;
}

message ServerResponse {
    int32 id = 1;
    Purpose purpose = 2;
    int32 latency = 3;
    int32 bandwidth = 4;
    StateInfo state = 5;
}

message ServersResponse {
    repeated ServerResponse server = 1;
}

message StateInfo {
    optional string city = 1;
    optional string country = 2;
}

enum Purpose {
    FRONTEND = 0;
    BACKEND = 1;
    DATABASE = 2;
}
```

### Сравнение RESTful от gRPC

#### Транспортный уровень

HTTP/REST строится на базе протокола HTTP 1.1 и имеет ряд проблем с производительностью:

* В HTTP 1.1 при использовании одного TCP-соединения запросы обрабатываются последовательно. Это означает, что если один
  запрос долго отвечает, все последующие запросы будут ждать его завершения.
* HTTP 1.1 использует новое TCP-соединение для каждого запроса, если нет keep-alive.
* Большой объем заголовков: каждый запрос передаёт одни и те же заголовки (например, `Authorization`, `User-Agent`), они
  могут занимать больше места чем сам payload.

Блокировка потока является самой большой проблемой, влияющая на производительность. В HTTP/2 она решена за счет
использованиям механизма мультиплексирования: множество запросов и ответов могут передаваться параллельно в рамках
одного TCP-соединения, избегая блокировок.

Потоки (streams) в HTTP/2 позволяют вести несколько параллельных взаимодействий внутри одного TCP-соединения. gRPC
использует это следующим образом:

* Channel – это абстракция над _единственным_ TCP-соединением между клиентом и сервером. Это позволяет избежать
  накладных расходов на установку новых TCP-соединений для каждого запроса.
* RPC call – поток внутри канала (за счет мультиплексирования HTTP/2).
* RPC message – несколько HTTP/2 DATA фреймов.

В gRPC каждое сообщение кодируется с помощью Protocol Buffers в бинарный формат, а затем передаётся в формате HTTP/2
DATA-фреймов.

#### Стиль коммуникации и архитектурная парадигма

RESTful строится на основе архитектурного стиля REST (Representational State Transfer), который ориентирован на
работу с ресурсами. Взаимодействие происходит через HTTP-протокол, где стандартные методы (`GET`, `POST`, `PUT`,
`PATCH`, `DELETE`)используются для управления ресурсами. HTTP/REST использует текстовые форматы данных, такие как JSON
или XML, что делает его универсальным и удобным для веб-приложений.

gRPC, напротив, реализует RPC (Remote Procedure Call) – модель удаленного вызова процедур, где клиент обращается к
серверу как к локальному объекту. Взаимодействие происходит через HTTP/2, что обеспечивает высокую производительность
и поддержку стриминга данных.

Основное преимущество RPC заключается в том, что оно абстрагирует сетевое взаимодействие, позволяя разработчикам
вызывать удаленные методы так же, как локальные. Это упрощает программирование и делает код более читаемым и удобным в
использовании.

#### Формат данных и генерация моделей

В отличие от REST, где API часто проектируется _на лету_, gRPC by-design Contract First, что требует проработки
архитектуры и согласования контакта со всеми заинтересованными лицами до написания кода.

Основные элементы протокола Protobuf:

```protobuf
// Указывает версию Protobuf
syntax = "proto3";

// Позволяет импортировать определения из других .proto файлов
import "google/protobuf/empty.proto";
import "google/protobuf/any.proto";

// Описывает gRPC-сервис
service HealthCheck {
    // Определяет удаленный метод в сервисе
    rpc handle(google.protobuf.Empty) returns (HealthCheckResult);
}

message HealthCheckResult {
    // Резервирует номера полей для предотвращения их использования в будущем
    reserved 1 to 10;

    // Примитивные типы
    string name = 11;

    // Ссылки на другие сообщения
    Status status = 12;

    // Поле, которое может отсутствовать
    optional Description description = 13;

    // Помечает поле как устаревшее
    map<string, string> params = 14 [deprecated = true];

    // Поле, содержащее список значений (аналог массива)
    repeated google.protobuf.Any details = 15;
}

message Description {
    // Группа полей, где одновременно может быть задано только одно
    oneof reason {
        string message = 1;
        string stacktrace = 2;
    }
}

// Определяет перечисление
enum Status {
    HEALTHY = 0;
    UNHEALTHY = 1;
}
```

Т.к. RESTful является лишь набором архитектурных соглашений, то даже придерживаясь их разные команды могут видеть
реализацию одного и того же набора API по-разному. Для формализации REST контракта наибольшее распространение получил
формат OpenAPI – это описание методов API в формате YAML или JSON, для которых описываются:

* query-параметры;
* заголовки и авторизация;
* тела входных и выходных сообщений.
* и т.п.

Как упомянуто выше, обычно при работе с REST разработчики сразу бросаются писать код (Code First), а потом уже на базе
кода создается файл OpenAPI. При правильной организации процессов, на этот подход хорошо ложится Test Driven
Development (TDD), что в результате даст хорошее покрытие тестами и проработку уже внутренних особенностей реализации. С
другой стороны требуется время на разработку каркаса, прежде чем будет доступен OpenAPI.

Если же пытаться применять Contract First с OpenAPI и RESTful, то мы сталкиваемся с OpenAPI generator, который нужно
подкрутить, прежде чем он начнет генерировать код, от которого не хочется выйти из окна.

#### Стриминг данных

gRPC использует HTTP/2 для передачи данных, а стриминг работает за счёт мультиплексированных потоков внутри одного
соединения.

* **Server Streaming** – сервер отправляет поток данных на один запрос клиента.
* **Client Streaming** – клиент отправляет поток данных, получая один ответ от сервера.
* **Bidirectional Streaming** – асинхронный обмен сообщениями.

В контексте стриминга важно упомянуть встроенный механизм Flow Control – это механизм, гарантирующий, что получатель
сообщений не будет перегружен быстрым отправителем. Управление потоком предотвращает потерю данных, улучшает
производительность и повышает надежность. gRPC использует механизм плавающего окна (Floating Window): когда данные
считываются на принимающей стороне, отправителю возвращается подтверждение, сообщающее, что у получателя больше емкости.
При необходимости фреймворк gRPC будет ждать перед возвратом из вызова записи. За реализацию Flow Control отвечает сам
gRPC.

HTTP/REST напрямую не поддерживают стриминг данных и для этого используется несколько решений:

* Chunked Transfer Encoding – сервер отправляет данные частями (`Chunks`) без указания заголовка `Content-Length`.
  Каждый chunk начинается с размера в шестнадцатеричном формате. В основном применяется в стриминге файлов, логов и т.п.
* WebSocket – протокол двунаправленной связи (может передавать и принимать одновременно) поверх TCP-соединения,
  предназначенный для обмена сообщениями между браузером и сервером в режиме реального времени.

Важно заметить, что ни в одной реализации стриминга в HTTP нет механизма Flow Control, следовательно, клиент может быть
не готов обработать столько данных (именно обработать, за получение отвечает Flow Control на уровне TCP).

#### Кеширование

В отличие от REST, где кэширование можно легко настроить с помощью HTTP-заголовков (`Cache-Control`, `ETag`), gRPC не
предоставляет встроенной поддержки кэширования. Это связано с тем, что: gRPC построен поверх HTTP/2, запросы идут как
POST.

В остальном кеширование можно реализовать руками как на клиенте так и на сервере:

* На клиенте перед отправкой запроса данные проверяются в кеше (через gRPC Interceptor).
* На сервере перед выполнением метода данные проверяются в кеше (через gRPC Interceptor).

#### Версионирование

В gRPC нельзя переиспользовать номер и название поля (в том числе просто удаляя его, нужно прописать reserved). Т.е. для
удаления поля из новой версии API нужно:

```proto
message User {
  string name = 1;
  int32 user_id = 2;
}
```

Заменить на:

```proto
message User {
  reserved 2;
  reserved user_id;

  string name = 1;
  string user_external_id = 3;
}
```

в gRPC каждое поле кодируется как `<key><value>`, где:
* `key = (field_number << 3) | wire_type`.
* `value = number | length`.

`wire_type` бывают:

| Wire Type | Значение | Применение |
|:---------:|:--------:|:----------:|
|     0     |  VARINT  |   int32    |
|     1     |   I64    |   double   |
|     2     |  LENGTH  |   string   |
|     5     |   I32    |   float    |


Следовательно, если вы меняете тип поля, то можете сломать бинарную совместимость

| Wire Type |     Значение     |            Применение            |
|:---------:|:----------------:|:--------------------------------:|
|     0     |      varint      |     int32, int64, bool, enum     |
|     1     |      64-bit      |         fixed64, double          |
|     2     | length-delimited | string, bytes, embedded messages |
|     5     |      32-bit      |          fixed32, float          |

#### Работа с браузерами и из консоли

Взаимодействие с UI возможно только через Envoy Proxy, т.к. помимо HTTP/2 требуются еще дополнительные
параметры [issue](https://github.com/grpc/grpc-web/issues/347).

Пример: [A basic tutorial introduction to gRPC-web](https://grpc.io/docs/platforms/web/basics/).

##### HTTP

Самый распространенный клиент – curl. Поддержка: HTTPS, HTTP/2, методы, заголовки, куки, аутентификация.

```shell
$ curl -w "Start Transfer:\t%{time_starttransfer}\nTotal Time:\t%{time_total}\n" \
    -o /dev/null -s http://localhost:8080/api/v1/servers
```

[HTTPie](https://httpie.io/) – более удобная альтернатива cURL с подсветкой JSON и простым синтаксисом.

##### gRPC

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

##### Графические клиенты

Postman умеет работать как с REST (например, загрузка через OpenAPI), так и с gRPC.

#### Архитектурные паттерны

##### Retry

Fail Fast – это принцип проектирования программного обеспечения, который заключается в том, чтобы быстро обнаруживать
ошибки и сбои в системе и немедленно прекращать выполнение задачи при их возникновении. Основная идея этого подхода
состоит в том, чтобы минимизировать ущерб от ошибок и избежать дальнейшего выполнения кода, который может привести к еще
большим проблемам.

Retry — это механизм повторного выполнения операции в случае временного сбоя. Это особенно полезно в распределенных
системах, где ошибки могут быть вызваны временной недоступностью сети, перегрузками или краткосрочными отказами.

* Повторная отправка HTTP-запроса при временном сбое сети.
* Повтор вызова API, если он завершился с кодом 5xx или timeout.

Механика retry может включать:

* Фиксированные интервалы: попытка через 100 мс.
* Экспоненциальная задержка (Exponential Backoff): увеличение времени ожидания перед каждой новой попыткой (100мс,
  200мс, 400 мс и т.д.).
* Jitter (случайное добавление задержки) — уменьшает вероятность массовых повторов в кластере, если сбой затронул сразу
  много клиентов.

Retry можно использовать только если это временный сбой (недоступность сети, 5xx ошибки, таймауты), иначе (например,
403 Permission Denied – ошибка прав доступа) это замедлит систему. Так же Retry нужно комбинировать с Circuit Breaker,
чтобы не перегружать сервис. Retry хорошо работает на операциях чтения, а для модификации нужно предусмотреть
идемпотентность операции.

[//]: # (TODO: Retry)

##### Timeouts

Timeout (таймаут) — это предельное время ожидания ответа перед тем, как считать операцию неудачной. Они помогают
ограничить время ожидания между попытками и предотвращают бесконечные циклы повторений. Таймаут обычно устанавливается
для каждой попытки, а также для общего времени работы механизма Retry.

Вычисление величины таймаута для сервиса — это баланс между пользовательским опытом, производительностью и устойчивостью
системы. Таймаут должен быть достаточно большим, чтобы дать системе шанс обработать запрос, но достаточно коротким,
чтобы избежать зависаний и неэффективного использования ресурсов.

В HTTP/REST таймаут задается только на клиенте, сервер не знает об ограничении времени и если сервер начал работать, но
не успел ответить, он всё равно завершит обработку (даже если клиент уже закрыл соединение).

В gRPC используется понятие Deadline: т.е. время, до которого вызов должен завершиться. Это значение передаётся через
метаданные, и _все сервисы по цепочке знают об этом_. Чтобы защититься от проблемы не синхронизированного времени на
серверах, gRPC преобразует крайний срок в тайм-аут, из которого уже вычитается прошедшее время.

Если сервер превысил срок обработки запроса, клиент отменяет запрос и завершит RPC-вызов со статусом
`DEADLINE_EXCEEDED`. Промежуточные сервисы тоже узнают об этом и могут прекратить работу.

##### Circuit Breaker

Паттерн Circuit Breaker (Предохранитель) — это архитектурный шаблон проектирования программного обеспечения, который
используется для защиты системы от каскадных сбоев при взаимодействии между сервисами. Основная идея заключается в том,
чтобы предотвратить дальнейшие вызовы зависимых сервисов, когда они находятся в состоянии сбоя, тем самым снижая
нагрузку на систему и предотвращая её полное падение. В отличие от Retry, паттерн Circuit Breaker рассчитан на менее
ожидаемые ошибки, которые могут длиться намного дольше: обрыв сети, отказ сервиса, оборудования. В таких случаях
повторная отправка запросов с высокой вероятностью приведёт к аналогичным ошибкам.

Паттерн Circuit Breaker предотвращает попытки приложения выполнить операцию, которая скорее всего завершится неудачно,
что позволяет продолжить работу дальше не тратя важные ресурсы, пока известно, что проблема не устранена. Приложение
должно быстро принять сбой операции и обработать его. Он также позволяет приложению определять, была ли устранена
неисправность. Если проблема устранена, приложение может попытаться вызвать операцию снова.

Встроенной реалиазации Circuit Breaker нет ни в HTTP/REST ни в gRPC, но ее можно добавить программно с
помощью [Resilience4j](https://resilience4j.readme.io/) или через Envoy Proxy.

* HTTP/REST: [RestServerClient](../rest-client/src/main/java/ru/romanow/protocols/restful/service/RestServerClient.kt)
* gRPC: [GrpcServerClient](../grpc-client/src/main/java/ru/romanow/protocols/grpc/service/GrpcServerClient.kt)

#### Производительность

Как сказано выше, за счет того, что gRPC является бинарным протоколом и работает поверх HTTP/2, его производительность в
разы лучше HTTP/REST.

Если размер запроса небольшой и включена опция `Keep-Alive: true` (т.е. не тратится время на повторную установку
HTTP соединения), то скорость REST сопоставима с gRPC.

* [Benchmarking performance gRPC vs REST on Node.js](https://medium.com/@TomorrowTechReviews/benchmarking-performance-grpc-vs-rest-on-node-js-bf766127f170)
* [gRPC vs GraphQL with keep-alive](https://piotrzakrzewski.medium.com/graphql-keep-alive-4379fe1a8b58)

#### Безопасность

[//]: # (TODO: Безопасность)

#### Observability

##### Трассировка

Распределённая трассировка — это метод мониторинга и анализа производительности приложений, работающих в микросервисной
архитектуре. Основная цель этого метода заключается в том, чтобы отслеживать выполнение запросов через множество
сервисов и компонентов системы, выявлять узкие места и проблемы, а также улучшать общую производительность и надёжность
приложения.

Как и в HTTP/REST, так и в gRPC для трассировки используются interceptors.

##### Логгирование запросов и ответов

Как и в HTTP/REST, так и в gRPC для логгирования запросов и ответов используются interceptors.

В HTTP тело запроса можно прочитать только один раз (после чтения поток становится _пустым_ для последующих операций),
следовательно, если нужно его записать в лог, то его нужно положить в буфер, что потребляет дополнительную память.

HTTP/REST: [LoggingFilter](../restful/src/main/java/ru/romanow/protocols/restful/filters/LoggingFilter.kt)
gRPC: [LogInterceptor](../grpc-server/src/main/java/ru/romanow/protocols/grpc/server/interceptors/LogInterceptor.kt)

### Отмена запроса

Протокол HTTP не поддерживает отмену запроса и для реализации отмены нужно использовать механизм асинхронного выполнения
и остановки.

```kotlin
@RestController
@RequestMapping("/api/v1/tasks")
class TaskController(private val taskService: TaskService) {

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun accept(@RequestBody request: Map<String, Double>): UUID {
        return taskService.accept { Thread.sleep(10000) }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{operationId}/cancel")
    fun cancelOrder(@PathVariable operationId: UUID) {
        taskService.cancel(operationId)
    }
}

@Service
class TaskService {
    private val executorService = Executors.newCachedThreadPool()
    private val operations = ConcurrentHashMap<UUID, Future<*>>()

    fun accept(task: Runnable): UUID {
        val operationId = UUID.randomUUID()
        val future = executorService.submit {
            try {
                task.run()
            } catch (exception: InterruptedException) {
                println("Task $operationId cancelled")
            }
        }
        operations[operationId] = future
        return operationId
    }

    fun cancel(operationId: UUID) {
        operations.remove(operationId)?.cancel(true)
    }
}
```

В gRPC отмена операций поддерживается на уровне контекста выполнения (`Context`). Когда клиент прерывает запрос, это
событие автоматически распространяется _по всей цепочке вызовов_, позволяя серверам освобождать ресурсы и завершать
выполнение (аналогично происходит при превышении deadline и ошибках I/O).

Для отмены операции создается контекст `Context.current().withCancellation()` и внутри контекста вызывается Runnable
блок. Операция `cancel` вызывается на этом контексте. Хотя клиенты gRPC не предоставляют серверу дополнительных сведений
о причине отмены, вызов `cancel` принимает `Throwable` с описанием причины, что приведет к исключению на стороне клиента
и записи в лог, содержащий указанную причину.

При получении нотификации о прерывании запроса, обработчик на сервере должен сам координировать работу с библиотекой
gRPC (проверка `Context.current().isCancelled()`), чтобы гарантировать прекращение локальной обработки запроса.

### Вместо выводов: когда и что выбирать для вашего проекта

* gRPC Contract First by-design и это очень хорошо: вы сначала думаете, потом пишете код.
* gRPC дает хорошую производительность из коробки.
* Но для работы с UI вам все равно ряд не самых простых приседаний.
* RESTful привычный и понятный, на нем уже все есть и не нужно ничего изобретать.
* К тому же работает в браузере без лишних приседаний.
* Если вы хотите увеличить производительность, то лучше в первую очередь смотреть на оптимизацию кода и структуры БД.
  Сетевая коммуникация обычно занимает менее 1% времени.

И такие выводы я написал, когда только начинал работу над докладом. Они строились на моем обширном опыте работы с
HTTP/REST и всего лишь 3-х летним опыте работы с gRPC несколько лет назад. И для меня это выглядело действительно так:
yet another protocol. Но потом я начал разбирать сложные механизмы gRPC и стало понятно, что как только вы прекращаете
проектировать gRPC как REST API, то у вас появляется очень много крутых инструментов:

* Стриминг данных (а может уже и очередь не так нужна)?
* Flow Control чтобы не перегружать потребителей.
* Отмена запроса и распространение отмены по всей цепочке вызовов (как тебе такое, Илон Маск?).
* Удобные таймауты, которые распространяются на всю цепочку вызовов.
* Действительно лучшая производительность за счет HTTP/2.
* Вообще-то браузер поддерживает только GET, поэтому для всего, что сложнее вам все равно придется использовать
  консольные клиенты.
* Ну а для работы с Front End потребуется развернуть Gateway API, который будет преобразовывать REST в gRPC (не
  обязательно Envoy, есть
  реализация [JsonToGrpc](https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway/gatewayfilter-factories/jsontogrpc-factory.html)
  для Spring Cloud Gateway).

Исходя из вышесказанного, для своего следующего проекта я возьму gRPC, т.к. это зрелый протокол, предоставляющий большое
количество полезных инструментов.

## Ссылки

* [gRPC on HTTP/2 Engineering a Robust, High-performance Protocol](https://grpc.io/blog/grpc-on-http2/)
* [Разбираем HTTP/2 по байтам](https://habr.com/ru/companies/timeweb/articles/751338/)
* [Грабли на пути к keep-alive](https://habr.com/ru/articles/495308/)

## Замечания

1. [x] Объяснить почему GitHub API хороший (screenshot).
2. [x] Картинка на таймаут и Circuit Breaker.
3. [x] Картинка как gRPC ложится на HTTP/2.
4. [x] Рассказать про JSON RPC: выглядит вот так, получается странно.
5. [x] CURL / HTTPie просто screenshot.
6. [x] Видосик про grpcurl / evans.
7. [x] Говорить только Protobuf!
8. [ ] Показать как удалить поле и добавить новое поле в gRPC протокол.
9. [x] Трассировка TraceId/ SpanId в заголовках.
10. [x] Продумать проблематику в начале доклада.
11. [x] Сократит план доклада (сравниваем по основным пунктам).
12. [x] gRPC на 6 слайде маленькими буквами.
13. [x] На REST два метода, в gRPC схлопнули в одно.
14. [x] Объяснить почему зачем мы хотим использовать gRPC из браузера? На мобильных устройствах работает gRPC (мем c
    Тинькофф: работает и ладно).
15. [x] Показать как происходит отмена запроса в gRPC: RST_STREAM в HTTP/2.
16. [x] Объяснить почему вам нужно выбрать gRPC.
17. [x] Добавить минусы gRPC: версионирование, в Service Mesh сложно влезть внутрь запроса и модифицировать.
18. [x] Добавить голосование кто выиграл.
19. [x] Перенести первые выводы до слайда "Архитектурные Паттерны".
