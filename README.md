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

### 💰 Purchase
El modelo `Purchase` representa un compra realizada por un cliente. Esta diseñado como una clase base con dos subtipos: `PurchaseSinglePayment` y `PurchaseMonthlyPayments`.
 * **Relaciones**
    - **Purchasae - Card**
      * **Tipo:** Many-to-One (Muchas compras pueden realizarse con una misma tarjeta).
      * El campo `card` es un `@DBRef` a la entidad Card.
      * Se utiliza la anotación @JsonManagedReference `para gestionar la serialización de esta relación y evitar referencias circulares.
    - **Purchase - Promotion**
      * **Tipo** Many-to-One (Muchas compras pueden usar una misma promoción).
      * El campo `validPromotion` es un `@DBRef` a la entidad `Promotion`.
      * Se usa la anotación `@JsonManagedReference` para gestionar la serialización de esta relación.
    - **PurchaseMonthlyPayments - Quota**
      * **Tipo:** One-To-Many (Una compra puede tener muchas cuotas).
      * Para esta relación, las cuotas no se almacenan como referencias (@DBRef) sino como un campo embebido (@Field("quotas")), lo que hace que las cuotas esten directamente dentro de `PurchaseMonthlyPayments` elmininando la necesidad de una relación externa, esta decisión fue tomada ya que simplifica el query y la lógica utilizada para crear el `PaymentsSummary`, mejorando la eficiencia de la consulta y teniendo una menor complejidad en la gestión de los datos
   - **Quota - PurchaseMonthlyPayments**
      * **Tipo:** Many-To_one (Muchas cuotas pueden pertenecer a una misma compra).
      - El campo purchase en Quota es un @DBRef a la entidad Purchase.
      - Se utiliza @JsonBackReference para evitar referencias circulares durante la serialización en JSON.
```
{
  "_id": "652f1a2b3c4d5e6f7a8b9c10",
  "paymentVoucher": "VOUCHER123",
  "store": "Example Store",
  "cuitStore": "30-12345678-9",
  "amount": 100.0,
  "finalAmount": 90.0,
  "purchaseDate": "2023-10-01",
  "card": {
    "$ref": "cards",
    "$id": "652f1a2b3c4d5e6f7a8b9c0d"
  },
  "validPromotion": {
    "$ref": "promotions",
    "$id": "652f1a2b3c4d5e6f7a8b9c1f"
  },
  "storeDiscount": 10.0,
  "type": "PurchaseSinglePayment"
}
```

## Test
1. Agregar promoción a banco
   * `POST` **Endpoint:** `http://localhost:8080/promotions/financing?bankId={bank_id}`
   *  **Parameters:** `bankId`

![1NoSQL](https://github.com/user-attachments/assets/03a6dbb6-4537-45cd-bdff-2b871bb046ee)

2. Extender el tiempo de validez de una promoción
   *  `PUT` **Endpoint:** `http://localhost:8080/promotions/{promotion_id}/validity?extendDate=2025-12-31`
   *  **Parameters:** `extendDate`

![2NoSql](https://github.com/user-attachments/assets/194382dc-9d22-4b82-aec8-f91e27dcd1c8)

3.  Generar el total de pago de un mes dado, informando las compras correspondientes.
   *  `GET` **Endpoint:** `http://localhost:8080/payment-summaries?cardId={card_id}&month={month}&year={year}`
   *  **Parameters:** `cardId`, `month`, `year`
     
![03nosql](https://github.com/user-attachments/assets/5d214cc4-1179-436b-823d-a1c58a138a7c)

4. Obtener un listado de tarjetas que vencen en los siguientes 30 días.
  *  `GET` **Endpoint:** `http://localhost:8080/cards/expiring`

![04Nosql](https://github.com/user-attachments/assets/f0b5179f-02ed-4e36-a719-6aca86e24bab)

5. Obtener la información de una compra, incluyendo el listado de cuotas si esta posee.
  *  `GET` **Endpoint:** `http://localhost:8080/purchases/{purchase_id}`
  *  **Parameters:** `purchase_id`

![05nosql](https://github.com/user-attachments/assets/41596116-e791-4748-a5e9-c1ec45335754)

6. Eliminar una promoción a través de su código.
  *  `DELETE` **Endpoint:** `http://localhost:8080/promotions/{promotion_to_delete_id}`
  *  **Parameters:** `promotion_to_delete_id`

![06Nosql](https://github.com/user-attachments/assets/56ca2f59-636b-4afc-bbcb-90018b2c6859)

7. Obtener el listado de las promociones disponibles de un local entre dos fechas.
  *  `GET` **Endpoint:** `http://localhost:8080/promotions/valid?cuit_store={cuit_store}&start_date={start_date}&end_date={end_date}`
  *  **Parameters:** `cuit_store`, `start_date`, `end_date`

![07nosql](https://github.com/user-attachments/assets/5e86fc76-2a58-4309-b5ee-9a115ea08209)

8. Obtener la información de las 10 tarjetas con más compras.
  *  `GET` **Endpoint:** `http://localhost:8080/cards/top10`
    
![08Nosql](https://github.com/user-attachments/assets/9096e680-348d-49a1-9a9d-6f21fe945735)

9. Obtener la promoción más utilizada en las compras registradas.
  *  `GET` **Endpoint:** `http://localhost:8080/promotions/most-used`

![09Nosql](https://github.com/user-attachments/assets/dfd6d9da-7830-4bd7-b9b2-0f15ea2d6940)

10. Obtener el nombre y CUIT del local, que más facturó en cierto mes.
  *  `GET` **Endpoint:** `http://localhost:8080/purchases/top-income-store?month={month}&year={year}`
  *  **Parameters:** `month`

![10nosql](https://github.com/user-attachments/assets/7463c2c7-d914-4e46-9835-178c143daf0d)

11. Obtener un listado con el número de clientes de cada banco.
  *  `GET` **Endpoint:** `http://localhost:8080/banks/members`

![11nosql](https://github.com/user-attachments/assets/e6908d82-d5c0-4bc5-b669-a6a287ee4a39)

    
