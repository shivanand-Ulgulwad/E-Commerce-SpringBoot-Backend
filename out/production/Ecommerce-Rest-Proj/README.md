# 🛒 E-Commerce Backend API

A scalable and secure E-Commerce backend system developed using Spring Boot.  
This project is designed to manage products, categories, carts, orders, authentication, and search functionality with transactional business logic and JWT-based security.

---

# 📖 Overview

The E-Commerce Backend provides REST APIs for handling core shopping operations such as:

- Product Management
- Category Management
- Cart Management
- Order Processing
- Product Search
- User Authentication

The application follows layered architecture and industry-standard backend development practices using Spring Boot and Hibernate.

---

# 🚀 Features

## 🔐 Authentication & Authorization
- JWT Authentication
- Secure Login & Registration APIs
- Spring Security Integration
- Password Encryption using BCrypt
- Stateless Authentication

---

## 📦 Product Management
- Create and manage products
- Bulk product creation support
- Product categorization
- Product pagination & sorting
- Product activation/deactivation ready structure

---

## 🗂️ Category Management
- Create categories
- Bulk category creation
- Category retrieval APIs
- Category deletion support

---

## 🛒 Cart Management
- Add products to cart
- Remove cart items
- Increase/decrease quantity
- Fetch user cart
- Dynamic cart total updates
- Cart count support

---

## 🧾 Order Management
- Create orders from cart
- Multiple order items support
- Dynamic subtotal and total calculations
- Cancel full order
- Cancel single product from order
- Latest orders retrieval
- Transactional order processing

---

## 🔍 Product Search
- Search by product name
- Search by category name
- Pagination with filtering
- Debounced frontend search support

---

# 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming Language |
| Spring Boot | Backend Framework |
| Spring Security | Authentication & Authorization |
| Spring Data JPA | Database Access |
| Hibernate | ORM Framework |
| MySQL | Relational Database |
| Maven | Dependency Management |
| Lombok | Boilerplate Reduction |
| JWT | Token-based Security |

---

# 🧱 Architecture

The project follows layered architecture:

```text
Controller Layer
        ↓
Service Layer
        ↓
Repository Layer
        ↓
Database
```

---

# 📂 Project Structure

```text
src/main/java/com/app
│
├── controller        # REST Controllers
├── dto               # Request & Response DTOs
├── entity            # JPA Entities
├── repository        # Database Repositories
├── service           # Service Interfaces
├── service/impl      # Business Logic Implementations
├── config            # Security & JWT Configuration
├── exception         # Custom Exceptions
├── mapper            # Entity ↔ DTO Mapping
└── security          # JWT Authentication Logic
```

---

# ⚙️ Implemented APIs

## 🔑 Authentication APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register new user |
| POST | `/auth/login` | Authenticate user |

---

## 📦 Product APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | `/products` | Get all products |
| POST | `/products` | Create product |
| POST | `/products/saveAll` | Bulk create products |
| GET | `/products/{id}` | Get product details |
| DELETE | `/products/{id}` | Delete product |
| DELETE | `/products` | Delete all products |
| GET | `/products/search` | Search products |
| GET | `/products/page` | Paginated products |

---

## 🗂️ Category APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/categories` | Create category |
| POST | `/categories/saveAll` | Bulk create categories |
| GET | `/categories/all` | Get all categories |
| DELETE | `/categories/{id}` | Delete category |

---

## 🛒 Cart APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/cart/save` | Add to cart |
| GET | `/cart/{userId}` | Get user cart |
| PUT | `/cart/decrease/{id}` | Decrease cart quantity |
| DELETE | `/cart/{id}` | Remove cart item |

---

## 🧾 Order APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/orders` | Place order |
| GET | `/orders/user/{userId}` | Get user orders |
| PUT | `/orders/cancel/{id}` | Cancel full order |
| DELETE | `/orders/items/{orderItemId}` | Cancel single product from order |

---

# 🧾 Sample Product Response

```json
{
  "id": 1,
  "name": "iPhone 15 Pro",
  "price": 129999,
  "categoryName": "Electronics"
}
```

---

# 🧾 Sample Order Request

```json
{
  "userId": 1
}
```

---

# ✅ Sample Order Response

```json
{
  "id": 3,
  "orderDate": "2026-04-20T12:30:00",
  "totalAmount": 150000.00,
  "items": [
    {
      "id": 10,
      "productName": "Laptop",
      "quantity": 2,
      "price": 75000.00,
      "subtotal": 150000.00
    }
  ]
}
```

---

# 🔥 Core Business Logic

The project implements several real-world backend concepts:

- Cart-to-order conversion
- Automatic cart clearing after order placement
- Dynamic order total calculation
- Single product cancellation from orders
- Order total recalculation after cancellation
- Pagination and sorting
- Product search filtering
- DTO-based API communication
- Transaction management using `@Transactional`
- JWT-secured APIs
- Exception handling with custom exceptions

---

# ❌ Custom Exceptions

- `ProductNotFoundException`
- `CategoryNotFoundException`
- `CartItemNotFoundException`
- `OrderNotFoundException`
- `UserNotFoundException`
- `InvalidQuantityException`

---

# 🗄️ Database

The project uses MySQL as the primary relational database.

Main entities include:

- User
- Product
- Category
- Cart
- Order
- OrderItem

---

# ▶️ Getting Started

## 1️⃣ Clone Repository

```bash
git clone https://github.com/your-username/your-repository-name.git
```

---

## 2️⃣ Configure Database

Update your `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 3️⃣ Run Application

```bash
mvn spring-boot:run
```

Application runs on:

```text
http://localhost:5454/Ecommerce
```

---

# 🔐 JWT Authentication Flow

## Register User

```text
POST /auth/register
```

---

## Login User

```text
POST /auth/login
```

Returns:

```json
{
  "message": "Login Successful",
  "data": "JWT_TOKEN"
}
```

---

## Use Token in Protected APIs

```text
Authorization: Bearer <JWT_TOKEN>
```

---

# 📌 Future Enhancements

- Role-based Authorization (ADMIN / USER)
- Product Images Upload
- Wishlist Feature
- Payment Gateway Integration
- Order Status Tracking
- Refresh Tokens
- Swagger/OpenAPI Documentation
- Redis Caching
- Docker Support
- Unit & Integration Testing

---

# 🧠 Learning Outcomes

This project helped in understanding:

- Spring Boot application architecture
- REST API development
- JWT Authentication
- Spring Security
- Hibernate entity relationships
- DTO & Mapper patterns
- Pagination & filtering
- Product search implementation
- Cart and order business logic
- Exception handling best practices

---

# 👨‍💻 Author

Developed as a backend portfolio project to practice enterprise-level Spring Boot application development and real-world E-Commerce API design.