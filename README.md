# ☕ Java DSA Cheatsheet – Backend

Spring Boot backend powering the Java DSA Cheatsheet application.

Provides JWT-based authentication, user-specific favorites, and custom snippet storage using PostgreSQL.

---

## 🚀 Tech Stack

- Java 17+
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- PostgreSQL
- Maven
- Deployed on Render

---

## 📦 Features

### 🔐 Authentication
- User registration
- User login
- Password hashing (BCrypt)
- JWT token generation & validation
- Stateless authentication

### ⭐ Favorites API
- Add / remove snippet from favorites
- Fetch all user favorites
- Protected routes (JWT required)

### ➕ Custom Snippets API
- Create custom snippet
- Fetch user-specific snippets
- Persistent PostgreSQL storage

---

## 🏗 Architecture Overview

- REST-based API
- Stateless JWT authentication
- User → One-to-Many → Snippets
- User → Many-to-Many → Favorite Snippets
- PostgreSQL relational schema
- Global exception handling
- CORS configured for frontend deployment

---

## 📂 Project Structure
