# Transaction Statistics API

A REST API developed in **Java + Spring Boot** to register financial transactions and generate aggregated statistics based on a specified period.

## Technologies Used

-   **Java 17+**
-   **Spring Boot (Web, Validation)**
-   **Lombok**
-   **Maven**

## Endpoints

### POST /transactions

Register a new transaction.

#### Request Body

``` json
{
  "value": 123.45,
  "transactionDateTime": "2025-12-06T20:40:32.938Z"
}
```

### DELETE /transactions

Remove all previously recorded transactions.

### GET /statistics/{lastPeriod}

Returns statistics on transactions performed in the last few minutes.

``` json
{
  "count": 10,
  "sum": 1234.56,
  "avg": 123.456,
  "min": 12.34,
  "max": 123.56
}
```

## Project Structure

    src/
     └── main/java/com/diohen/translytics/
          ├── controller/
          ├── service/
          |   └──impl
          ├── model/
          |   └──dto
          ├── handler/
          └── exception/

## How to execute the project

### Maven

    mvn spring-boot:run

### Docker

    docker build -t transactions-api .
    docker run -p 8080:8080 transactions-api
