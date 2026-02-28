# Spring Boot Inoice App

## Overview
This project is a implementation of a Inoice system similar. It includes core functionalities such as invoice creation, invoice management. The system is built using an in-memory H2 database but can be easily connected to MySQL or any other relational database.

## Features Implemented

1. **Invoice Creation**
    - Create Invoice

2. **invoice management**
    - Delete Invoice
	- Update Invoice
	- Show specific Invoice
	- Show all invoices
 

## Prerequisites
- Java (JDK 17 or later)
- Spring Boot
- H2 Database (default) / MySQL (optional)
- Maven 

## Setup & Running the Application

1. **Clone the Repository:**
   ```sh
   git clone https://github.com/sibashish726/InvoiceService.git
   ```

2. **Open in any IDE which supports Spring Boot Applications (preferably Intellij IDEA /STS)**

3. **Run the Application**



## API Endpoints

- **Create Invoice:**
  ```http
  POST /v1/invoice/saveInvoice
  ```
  **Request Body:**
  ```json
  {
    "productId": 105,
    "productDetails": "High-performance Iphone",
    "quantity": 2,
    "amount": 40000.50
  }
  ```
- **Delete Invoice:**
  ```http
  DELETE /v1/deleteInvoiceById/{invoiceId}
  ```
- **Show specific invoice:**
  ```http
  GET /v1/invoice/getInvoiceById/{invoiceId}
  ```
- **Show all invoices:**
  ```http
  GET /v1/invoice/getAllInvoices
  ```
- **update Invoice:**
  ```http
  POST /v1/invoice/updateInvoice/{invoiceId}
  ```
  **Request Body:**
  ```json
  {
    "productId": 105,
    "productDetails": "iPhone 15 Pro - Titanium (Updated)",
    "quantity": 1,
    "amount": 45000.00
 }
  ```







## Database Schema
The system consists of the following database entities:
- **Invoice**
- **InvoiceResponse**
- **InvoiceRequest**
- **ErrorResponse**


## Design Patterns

1. **N-Tier (Layered) Architecture** – Controller -> Service -> Repository.
2. **Inversion of Control (IoC)** – use of @RequiredArgsConstructor.
3. **Data Transfer Object (DTO) Pattern** – Decouple the internal database entity (Invoice) from the API interface.).
4. **Builder Pattern** – It makes your code much more readable..
5. **Strategy Pattern (via Spring Data JPA)** – Use of Spring Data JPA provides the "how" (the SQL implementation) at runtime).
6. **Singleton Pattern** – By default, all Spring Beans (classes marked with @Service, @RestController, or @Repository) are Singletons.

## Future Enhancements
- Integrated Microservices Communication 
- Implement payment gateway integration
- Introduce role-based access control (RBAC)
- Implement caching for performance improvement
- Implement pagination and Sorting
- Implement Monitoringusing Spring Boot Actuator + Prometheus


