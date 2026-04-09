🛒 E-Commerce Backend (Spring Boot)

A robust and scalable E-Commerce Backend Application built using Spring Boot, following a clean layered architecture and RESTful API design principles.

🚀 Tech Stack
Java 17+
Spring Boot
Spring Data JPA
REST APIs
Maven / Maven Wrapper
🏗️ Architecture

The project follows a well-structured layered architecture:

Controller Layer → Handles HTTP requests
Service Layer
Interface
Implementation
Repository Layer → Database interaction
DTO Layer → Request & Response models
Mapper Layer → Entity ↔ DTO conversion

📦 Core Features
🛒 Cart Management
Add products to cart
Remove products from cart
Update product quantity
Track cart item count

📦 Order Management
Place orders from cart
Store order details with:
Order
Order Items
Retrieve orders
Orders sorted by latest date

❌ Cancellation Features
✅ Cancel Full Order
PUT /orders/cancel/{id}
✅ Cancel Single Product (Advanced Feature)
DELETE /items/{orderItemId}

🔧 Backend Logic
Removes only selected OrderItem
Recalculates total order amount
Deletes order if no items remain (optional)
🔍 Product Search

Search products dynamically by:

Product name
Category name
GET /products/search?query=iphone

✔ Uses DTOs to avoid exposing internal entities

🧱 Project Structure
src/
 ├── controller/
 ├── service/
 │    ├── interface/
 │    └── implementation/
 ├── repository/
 ├── dto/
 │    ├── request/
 │    └── response/
 ├── mapper/
 └── entity/
 
⚙️ Setup & Run Locally
🔹 Prerequisites
Java 17+
Maven (or Maven Wrapper)
🔹 Clone Repository
git clone https://github.com/shivanand-Ulgulwad/E-Commerce-SpringBoot-Backend.git
cd E-Commerce-SpringBoot-Backend
🔹 Run Application

Using Maven Wrapper (recommended):

./mvnw spring-boot:run

On Windows:

mvnw.cmd spring-boot:run
🌐 API Base URL
http://localhost:5454/Ecommerce
📌 Key Highlights
Clean and maintainable architecture
Proper use of DTO pattern
RESTful API design
Separation of concerns
Scalable backend structure

🚀 Future Improvements
Authentication & Authorization (JWT)
Payment Gateway Integration
Pagination & Filtering
Logging & Monitoring
👨‍💻 Author

Shivanand Ulgulwad

⭐ Support

If you found this project useful, consider giving it a ⭐ on GitHub!
