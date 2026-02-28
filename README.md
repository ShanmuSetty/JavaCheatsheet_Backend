# ☕ Java DSA Cheatsheet — Backend

> REST API powering authentication, favorites, and custom snippets for the Java DSA Cheatsheet.

---

## 🛠️ Built With

- **Java + Spring Boot** — REST API
- **Spring Security + JWT** — stateless authentication
- **PostgreSQL** — persistent storage (hosted on Render)
- **JPA / Hibernate** — ORM
- **Render** — cloud hosting

---

## 🔐 Authentication

JWT-based stateless auth. No sessions, no cookies.

**Flow:**
1. User registers or logs in via `/api/auth/register` or `/api/auth/login`
2. Backend validates credentials, returns a signed JWT
3. Client stores the token and attaches it to all protected requests as:
   ```
   Authorization: Bearer <token>
   ```
4. Spring Security filter validates the token on every protected request

---

## 📡 API Reference

### Auth

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/api/auth/register` | ✗ | Create a new account |
| POST | `/api/auth/login` | ✗ | Login, returns JWT |

**Request body (both endpoints):**
```json
{
  "email": "you@example.com",
  "password": "yourpassword"
}
```

**Success response:**
```json
{
  "token": "eyJhbGci...",
  "email": "you@example.com"
}
```

---

### Favorites

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| GET | `/api/favorites` | ✓ | Get all favorited snippet IDs |
| POST | `/api/favorites/:id` | ✓ | Toggle favorite (add if missing, remove if present) |

**GET response:**
```json
["s1", "a3", "q4"]
```

**POST response:** Updated favorites array (same shape as GET)

---

### Custom Snippets

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| GET | `/api/snippets` | ✓ | Get all custom snippets for the user |
| POST | `/api/snippets` | ✓ | Save a new custom snippet |
| DELETE | `/api/snippets/:id` | ✓ | Delete a custom snippet |

**POST request body:**
```json
{
  "title": "My snippet",
  "tag": "custom",
  "code": "int x = 5;",
  "desc": "A simple variable declaration."
}
```

**GET / POST response:** Array of snippet objects
```json
[
  {
    "id": "abc123",
    "title": "My snippet",
    "tag": "custom",
    "code": "int x = 5;",
    "desc": "A simple variable declaration."
  }
]
```

---

## 🗂️ Project Structure

```
src/
└── main/
    └── java/
        └── com/yourpackage/
            ├── auth/           # JWT filter, token util, security config
            ├── user/           # User entity, repository, service
            ├── favorites/      # Favorites controller, service, repository
            ├── snippets/       # Custom snippets controller, service, repository
            └── config/         # Spring Security, CORS config
```

---

## ⚙️ Local Setup

### Prerequisites
- Java 17+
- Maven
- PostgreSQL running locally

### Steps

```bash
# Clone the repo
git clone https://github.com/yourusername/java-dsa-backend.git
cd java-dsa-backend

# Configure environment variables
# Create application.properties or set env vars:
# DB_URL, DB_USERNAME, DB_PASSWORD, JWT_SECRET

# Run
./mvnw spring-boot:run
```

**`application.properties` (local):**
```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
jwt.secret=${JWT_SECRET}
jwt.expiration=86400000
```

---

## 🚢 Deployment

Hosted on **Render** (free tier).

**Environment variables to set in Render dashboard:**
```
DB_URL        → your Render PostgreSQL internal URL
DB_USERNAME   → db username
DB_PASSWORD   → db password
JWT_SECRET    → any long random string
```

The frontend connects to:
```
https://javacheatsheet-backend.onrender.com/api
```

> **Note:** Render free tier spins down after inactivity. First request after idle may take 30–60 seconds to wake up.

---

## 🌐 Frontend

See the [frontend repo](https://github.com/ShanmuSetty/JavaCheatsheet_Frontend) for the HTML/CSS/JS client that consumes this API.

---

## 🤝 Contributing

Built this for myself, but if it helps you too — feel free to fork, extend, and make it yours.

---

*Stop context-switching. Stay in flow. Ship the solution.* ✨
