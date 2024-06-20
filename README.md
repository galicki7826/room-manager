# Room Occupancy Manager

## Overview
The Room Occupancy Manager API is designed to help hotels optimize their room occupancy rates. It allows hotel management to input the number of available Premium and Economy rooms and quickly determine the optimal room allocation to maximize revenue based on guests' payment preferences.

## Technologies Used
- Java 17
- Spring Boot
- Maven

## Getting Started

### Clone the Repository
To begin, clone this repository to your local machine with the following command:

```bash
git clone https://github.com/galicki7826/room-manager.git
```

### Run the Application
After successfully building the application, you can run it using:

```bash
mvn spring-boot:run
```

The API will be accessible at `http://localhost:8080`.

## Running Tests
To execute the test suite, run:

```bash
mvn test
```

## API Documentation
Once the application is running, you can view the API documentation via Swagger UI at `http://localhost:8080/swagger-ui.html`.

## Docker
To build and run the application using Docker, use the following commands:
```bash
docker build -t your-image-name .
docker run -p 8080:8080 your-image-name
```


## TL/DR
The application provides two main endpoints. To use it, first add customers via the endpoint POST /customer,
then use POST /room-occupancy/calculate to perform the occupancy calculations.
There is no advantage in storing room numbers in the database since they are dynamically handled through the API.
For quicker data entry, navigate to `http://localhost:8080/h2-console/`, use the credentials and necessary details found in application.properties,
and run the following query:
```
INSERT INTO Customer (price)
VALUES
(209.00),
(115.00),
(101.00),
(99.99),
(22.00),
(374.00),
(155.00),
(45.00),
(100.00),
(23.00);
```