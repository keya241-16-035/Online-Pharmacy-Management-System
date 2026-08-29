# Pharmacy Management System (Spring Boot + MySQL + Thymeleaf)

A full-featured CRUD web application built with **Spring Boot 3.x**, **Java 21**, **Spring Data JPA**, **Hibernate**, **Thymeleaf**, **MySQL**, **Maven**, and **Bootstrap 5**.

---

## Project Overview

The **Pharmacy Management System** is designed to streamline pharmacy operations, inventory management, supplier tracking, customer records, and sales processing with automatic stock deduction and invoice generation.

### Key Features
- **Dashboard & Inventory Analytics**: Real-time summary cards for total medicines, low-stock warnings (< 10 units), expiring medicines alerts (< 30 days), daily sales revenue, and recent transactions.
- **Medicine Management**: Full CRUD operations for medicines, categorized classification, unit price, stock quantity tracking, supplier assignment, and expiration date validation.
- **Supplier & Customer Modules**: Contact management, phone validation, email, address records, and quick search.
- **Category Management**: Organized classification of medicines (e.g., Antibiotics, Painkillers, Vitamins).
- **Sales & Automatic Stock Deduction**: Multi-item sales processing with real-time stock validation, automated inventory deduction, transaction safety (`@Transactional`), and printable invoice receipt generation.
- **Sample Data Seeding**: Automatic database initialization via `CommandLineRunner` for immediate demonstration upon application startup.

---

## Project Structure

```text
pharmacy-management-system/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── example/
        │           └── pharmacy/
        │               ├── PharmacyApplication.java
        │               ├── config/
        │               │   └── DataInitializer.java
        │               ├── controller/
        │               │   ├── HomeController.java
        │               │   ├── MedicineController.java
        │               │   ├── SupplierController.java
        │               │   ├── CustomerController.java
        │               │   ├── CategoryController.java
        │               │   └── SalesController.java
        │               ├── service/
        │               │   ├── MedicineService.java
        │               │   ├── SupplierService.java
        │               │   ├── CustomerService.java
        │               │   ├── CategoryService.java
        │               │   ├── SalesService.java
        │               │   └── impl/
        │               │       ├── MedicineServiceImpl.java
        │               │       ├── SupplierServiceImpl.java
        │               │       ├── CustomerServiceImpl.java
        │               │       ├── CategoryServiceImpl.java
        │               │       └── SalesServiceImpl.java
        │               ├── repository/
        │               │   ├── MedicineRepository.java
        │               │   ├── SupplierRepository.java
        │               │   ├── CustomerRepository.java
        │               │   ├── CategoryRepository.java
        │               │   ├── SaleRepository.java
        │               │   └── UserRepository.java
        │               ├── model/
        │               │   ├── Medicine.java
        │               │   ├── Supplier.java
        │               │   ├── Customer.java
        │               │   ├── Category.java
        │               │   ├── Sale.java
        │               │   ├── SaleItem.java
        │               │   └── User.java
        │               └── exception/
        │                   ├── ResourceNotFoundException.java
        │                   ├── InsufficientStockException.java
        │                   └── GlobalExceptionHandler.java
        └── resources/
            ├── application.properties
            └── templates/
                ├── layout.html
                ├── index.html
                ├── category/
                │   ├── list.html
                │   └── form.html
                ├── supplier/
                │   ├── list.html
                │   └── form.html
                ├── customer/
                │   ├── list.html
                │   └── form.html
                ├── medicine/
                │   ├── list.html
                │   ├── form.html
                │   └── view.html
                ├── sales/
                │   ├── list.html
                │   ├── form.html
                │   └── view.html
                └── error.html
```

---

## Database Setup & Configuration

### 1. MySQL Setup
1. Open your MySQL client (MySQL Workbench, phpMyAdmin, or MySQL CLI).
2. Create the database:
   ```sql
   CREATE DATABASE pharmacy_db;
   ```
3. Update your credentials in `src/main/resources/application.properties` if different from defaults:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/pharmacy_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true
   spring.datasource.username=root
   spring.datasource.password=root
   ```

### 2. Optional H2 Database Setup (For Quick Zero-Setup Testing)
If you wish to test without running a MySQL server, you can temporarily switch `application.properties` to:
```properties
spring.datasource.url=jdbc:h2:mem:pharmacy_db
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

---

## How to Build and Run

### Command Line
Navigate to the project root directory and execute:

```bash
# Using Maven Wrapper (if present) or installed Maven
mvn clean spring-boot:run
```

Or package the project JAR and run:

```bash
mvn clean package
java -jar target/pharmacy-management-system-0.0.1-SNAPSHOT.jar
```

Once started, open your web browser and navigate to:
**`http://localhost:8080`**

---

## User Endpoints Summary

| Module | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| **Dashboard** | `GET` | `/` | System overview, low stock alerts, revenue metrics |
| **Medicine** | `GET` | `/medicine` | List all medicines / search |
| | `GET` | `/medicine/new` | Show form to add medicine |
| | `POST` | `/medicine/save` | Save new or updated medicine |
| | `GET` | `/medicine/edit/{id}` | Edit medicine form |
| | `GET` | `/medicine/view/{id}` | View medicine details |
| | `GET` | `/medicine/delete/{id}`| Delete medicine record |
| **Category** | `GET` | `/category` | List categories |
| | `GET` | `/category/new` | Add category form |
| | `POST` | `/category/save` | Save category |
| | `GET` | `/category/delete/{id}`| Delete category |
| **Supplier** | `GET` | `/supplier` | List suppliers |
| | `GET` | `/supplier/new` | Add supplier form |
| | `POST` | `/supplier/save` | Save supplier |
| | `GET` | `/supplier/edit/{id}` | Edit supplier form |
| | `GET` | `/supplier/delete/{id}`| Delete supplier |
| **Customer** | `GET` | `/customer` | List customers |
| | `GET` | `/customer/new` | Add customer form |
| | `POST` | `/customer/save` | Save customer |
| | `GET` | `/customer/edit/{id}` | Edit customer form |
| **Sales** | `GET` | `/sales` | Sales transaction history |
| | `GET` | `/sales/new` | Create new sale transaction |
| | `POST` | `/sales/save` | Process sale & deduct stock |
| | `GET` | `/sales/view/{id}` | View & print sale invoice |
