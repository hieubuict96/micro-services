# Microservices-example
Example of a microservices using Spring Cloud, Spring Boot, Docker, Mysql, MongoDB, RabbitMQ, Keycloak, Spring WebFlux,  Resilience4J

# About the project
<ul style="list-style-type:disc">
  <li>User can create, customer, create order and get all orders by customer  through <strong>API Gateway</strong>. <strong>MySQL</strong> is used for storage customers and orders. The service uses Asynchronous communication between notification service, using <strong>RabbitMQ</strong>. And <strong>WebClient</strong> is used as Synchronous communication betwen inventoery service</li>
  <li>User can create product, get list of all products and check for product availability through <strong>API Gateway</strong>. <strong>MongoDB</strong> is used for storage all prodcuts.</li>
  <li>You can't create order if there isn't your product in warehouse.</li>
  <li>Notification service is used for getting email after create a new order.</li>
  <li>User can login through <strong>Keycloak</strong></li>
  <li>All projects configurations: https://github.com/MaximRom00/Microservices-example/tree/master/config-repo</strong></li>
</ul>

6 services whose name are shown below have been devised in this project:

- <strong>API Gateway</strong> - API Gateway which is responsible to route the request to specific microservice. The service does user authentication via OAuth 2.0. You can access the API Gateway at http://localhost:8180.
- <strong>Config Server</strong> - Centralized configuration service.
- <strong>Discovery Server</strong> - Service registry that allows other services to find and communicate with each other. You can access this service at http://localhost:8761.
- <strong>Customer Service</strong> - Responsible for managing orders and customers products.
- <strong>Invtentory Service</strong> - Responsible for managing catalog products.
- <strong>Notification Service</strong> - Responsible for getting and sending email notification.

<img src=https://user-images.githubusercontent.com/95149324/235769117-878d2368-db31-4404-b6b3-1415cf43da18.png  width="1100" height="800">

# Explore Rest APIs

| HTTP Verb | API            | Description          | Request Body         | Response Body |
| --------- | :------------- | :------------------- | :------------------- | :------------ |
| POST      | /api/customer  | Create a new customer| CustemerDto   | Customer             |
| POST      | /api/order     | Create a new order   | OrderDto      |      
| GET       | /api/getOrders | Get all orders by customer| CustomerDto| List of orders|         
| POST      | /api/product   | Add a new product to warehouse | ProductRequest | ProductResponse|
| GET       | /api/product   | Get all products from warehouse | None | ProductResponse|
| GET       | /api/product/inventory| Check your product in warehouse | String | InventoryResponse|
| POST      | /api/notification/email| Send email   | NotificationDto| None|

# Used Dependencies
* Core
  * Spring
    * Spring Boot
    * Spring Security
      * OAuth 2.0 Resource Server
    * Spring Web
    * Spring Data
      * Spring Data JPA
    * Spring WebFlux
    * Spring Cloud
      * Spring Cloud Gateway
      * Spring Cloud Bootstrap
      * Spring Cloud Config Server
      * Spring Cloud Config Client
     * Springdoc-openapi
  * Netflix
    * Eureka Server
    * Eureka Client
* Database
  * Mysql
  * MongoDB 
* Message Broker
  * RabbitMQ
* Circuit Breaker
  * Resilience4j
* Lombok
* Security
  * Keycloak

# Run the App

<b>Maven:</b>

<b>1 )</b> Start Keycloak and Rabbit through Docker

<b>2 )</b> Implement their settings

<b>3 )</b> Download your project from this link: `https://github.com/MaximRom00/Microservices-example`

<b>4 )</b> Go to the project's home directory

<b>5 )</b> Create a jar file though this command: `mvn clean install`

<b>6 )</b> Run the project though this command: `mvn spring-boot:run`

<b>Docker</b>

<b>1 )</b> Open <b>Docker Desktop</b> or <b>Docker Desktop</b> (if you use Windows 7/8).

<b>2 )</b> Open terminal and start all containers
```
    docker-compose up
```
<b>3 )</b> Keycloak
```
    1 ) Open Keycloak on the Browser through localhost:8080 (or http://192.168.99.100:8080 - if you use docker tolbox)
    2 ) Enter username and password (admin : admin)
    3 ) After you login, you need to setup a Realm and Client (https://www.keycloak.org/docs/latest/server_admin/index.html#admin-console)
```

<b>4 )</b> RabbitMQ
```
    1 ) Open Rabbitmq on the Browser through http://localhost:15672 (or http://192.168.99.100:15672 - if you use docker tolbox)
    2 ) Enter username and password (user : password)
```
<b>5 )</b> Stops containers
```
    docker-compose down
```
