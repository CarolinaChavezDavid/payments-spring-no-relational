# payments-spring-no-relational

Este proyecto consiste en una API desarrollada con **Spring Boot, Java, lombok y MongoDB**, diseñada para gestionar un sistema de registro de pagos asociados a tarjetas bancarias. La base de datos utilizada es MongoDB, una base de datos no relacional que permite almacenar y consultar los modelos de manera eficiente.
El proyecto incluye múltiples endpoints para realizar operaciones CRUD sobre las entidades relacionadas. Estos endpoints han sido diseñados y documentados para facilitar el testing y la integración con aplicaciones cliente.

## Set-up
  * 🗄️ **Base de datos** Para la base de datos *Payments_db* se utilizó el plan gratuito de MogoDB Atlas para aprendizaje, que se encuentra desplegada en un Cluster en AWS. Actualmente la base de datos cuenta con las colecciones ('banks', 'purchases', 'cards', 'promotions') necesarias para realizar pruebas a traves de los enpoindts de la API de Spring.

![image](https://github.com/user-attachments/assets/7c01866d-dcae-4286-98f9-c33eb6ee3396)

* **Aplicación de Spring** Para ejecutar la aplicación se deberá clonar o descargar este proyecto, se debe contar con Java 17 para ejecutar la aplicacion.
* **Prueba de endpoints** Para probar los endpoints que muestran los mapeos se compartira la collecion *Payments* utilizdas en postman.

<img width="1318" alt="image" src="https://github.com/user-attachments/assets/b5097fbb-e562-4bd8-96e9-83f41d13b41f" />

## Modelos
### 🏦 Bank
La colección 'Banks' almacena la información sobre bancons incluyendo su nombre, identificación  (CUIT), direccion y telefono, manteniendo las siguientes relaciones:
  * **Customers (members):** Representando los miembos asociados al banco. Un banco puede tener multiples clientes, representado por @DBref en la coleccion de members. Esto ayuda a trackear los clientes asociados con el banco.
  * **Promotions:** Representando las promociones ofertradas por el banco. Un banco puede ofertar multiples promociones, la lista de promociones esta linkeado por @DBref

  > http://localhost:8080/banking/67799820bc9ed1ecde0ce4b9

![image](https://github.com/user-attachments/assets/ec66b040-d940-430c-b9ef-339390425a98)


### 🙆‍♀️ Customer

### 💳 Card

### 💸 Promotion



## Test
1. Agregar promoción a banco
   * `POST` **Endpoint:** `http://localhost:8080/promotions/financing?bankId={bank_id}`

![1NoSQL](https://github.com/user-attachments/assets/03a6dbb6-4537-45cd-bdff-2b871bb046ee)

2. Extender el tiempo de validez de una promoción
   *  `PUT` **Endpoint:** `http://localhost:8080/promotions/{promotion_id}/validity?extendDate=2025-12-31`

![2NoSql](https://github.com/user-attachments/assets/194382dc-9d22-4b82-aec8-f91e27dcd1c8)

3.  Generar el total de pago de un mes dado, informando las compras correspondientes.
   *  `GET` **Endpoint:** `http://localhost:8080/payment-summaries?cardId={card_id}&month=2&year=2025`
     
![03nosql](https://github.com/user-attachments/assets/5d214cc4-1179-436b-823d-a1c58a138a7c)
