<p align="center">
  <h1 align="center">💰 Mizaniyati — Personal Finance REST API</h1>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21"/>
  <img src="https://img.shields.io/badge/Spring_Boot-3.4.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot 3.4.1"/>
  <img src="https://img.shields.io/badge/MySQL-8.0-005C84?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"/>
  <img src="https://img.shields.io/badge/JWT-Auth-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white" alt="JWT"/>
  <img src="https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge" alt="MIT License"/>
</p>

<p align="center">
  <b>A robust RESTful API for personal budget management, built with Spring Boot and secured with JWT authentication.</b>
</p>

---

## 📌 Description

**Mizaniyati** (ميزانيتي — "My Budget" in Arabic) is a comprehensive personal finance API that helps users take control of their money. It provides a complete backend for:

- 📊 **Expense tracking** with categories and frequency management
- 💵 **Income management** with recurring income support
- 🎯 **Budget planning** per category with automatic spent-amount calculation
- 📈 **Budget strategies** (e.g., the 50/30/20 rule) with activation control
- 🔐 **Secure authentication** via JWT with BCrypt password hashing

The API is designed to power frontend applications (React, Angular, Mobile, etc.) and exposes a complete Swagger/OpenAPI documentation.

---

## ⚙️ Tech Stack

| Technology | Version | Purpose |
|:-----------|:--------|:--------|
| **Java** | 21 | Core language |
| **Spring Boot** | 3.4.1 | Application framework |
| **Spring Security** | — | Authentication & authorization |
| **Spring Data JPA** | — | Database access (ORM) |
| **Spring Validation** | — | Input validation (Bean Validation) |
| **JWT (jjwt)** | 0.12.6 | Token-based stateless authentication |
| **MySQL** | 8.x | Relational database |
| **MapStruct** | 1.5.5 | DTO ↔ Entity mapping |
| **Lombok** | 1.18.32 | Boilerplate reduction |
| **SpringDoc OpenAPI** | 2.7.0 | Swagger UI & API docs |
| **Maven** | 3.9+ | Build & dependency management |

---

## 🏗️ Architecture

The project follows a **layered N-Tier architecture** with clear separation of concerns:

```
com.mizaniyati/
├── config/          # Spring Security & CORS configuration
├── controller/      # REST API endpoints (6 controllers)
├── dto/             # Data Transfer Objects (request/response)
├── entity/          # JPA entities mapped to database tables
├── enums/           # Application enumerations (Currency, Frequency, etc.)
├── exception/       # Custom exception classes
├── mapper/          # MapStruct mappers (Entity ↔ DTO conversion)
├── model/           # Additional model classes
├── repository/      # Spring Data JPA repositories
├── security/        # JWT filter & custom UserDetailsService
├── service/
│   ├── interfaces/  # Service contracts (interfaces)
│   └── impl/        # Service implementations (business logic)
└── util/            # Utility classes (JwtUtil)
```

### Request Flow

```
Client → Controller → Service (interface) → ServiceImpl → Repository → Database
                         ↕                       ↕
                     DTO/Mapper              Entity/JPA
```

---

## 🚀 Prerequisites

Before running this project, ensure you have installed:

- **JDK 21** or later — [Download](https://adoptium.net/)
- **Maven 3.9+** (or use the included Maven Wrapper `./mvnw`)
- **MySQL Server 8.x** — via [XAMPP](https://www.apachefriends.org/), [Docker](https://hub.docker.com/_/mysql), or standalone

---

## 🛠️ Installation & Configuration

### 1. Clone the repository

```bash
git clone https://github.com/Yassine-Atiki/Mizaniyati.git
cd Mizaniyati
```

### 2. Create your local configuration

Copy the example configuration file and adjust the values:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Then edit `application.properties` with your local database credentials and a secure JWT secret.

### 3. Create the MySQL database

```sql
CREATE DATABASE IF NOT EXISTS mizaniyati;
```

> **Note:** If you keep `createDatabaseIfNotExist=true` in the JDBC URL, Hibernate will create the database automatically.

### 4. Generate a secure JWT secret

```bash
# Generate a Base64-encoded 512-bit key:
openssl rand -base64 64
```

Copy the output and set it as your `JWT_SECRET` environment variable or paste it into `application.properties`.

---

## ▶️ Running the Application

```bash
# Using the Maven Wrapper (recommended):
./mvnw clean install
./mvnw spring-boot:run

# Or using your local Maven:
mvn clean install
mvn spring-boot:run
```

The API will start on **`http://localhost:8080`**.

### Swagger UI

Once running, access the interactive API documentation at:

```
http://localhost:8080/swagger-ui.html
```

---

## 📡 API Endpoints

All endpoints except authentication require a valid JWT token in the `Authorization: Bearer <token>` header.

### 🔐 Authentication (`/api/auth`)

| Method | Endpoint | Description | Auth Required |
|:-------|:---------|:------------|:--------------|
| `POST` | `/api/auth/register` | Create a new user account | ❌ |
| `POST` | `/api/auth/login` | Login & receive JWT token | ❌ |
| `GET` | `/api/auth/me` | Get current user profile | ✅ |

### 💵 Income (`/api/income`)

| Method | Endpoint | Description | Auth Required |
|:-------|:---------|:------------|:--------------|
| `GET` | `/api/income` | List all incomes for the authenticated user | ✅ |
| `POST` | `/api/income` | Create a new income entry | ✅ |
| `PUT` | `/api/income/{id}` | Update an existing income | ✅ |
| `DELETE` | `/api/income/{id}` | Delete an income | ✅ |

### 💸 Expenses (`/api/expenses`)

| Method | Endpoint | Description | Auth Required |
|:-------|:---------|:------------|:--------------|
| `GET` | `/api/expenses` | List all expenses for the authenticated user | ✅ |
| `POST` | `/api/expenses` | Create a new expense | ✅ |
| `PUT` | `/api/expenses/{id}` | Update an existing expense | ✅ |
| `DELETE` | `/api/expenses/{id}` | Delete an expense | ✅ |

### 🏷️ Categories (`/api/categories`)

| Method | Endpoint | Description | Auth Required |
|:-------|:---------|:------------|:--------------|
| `GET` | `/api/categories` | List all categories for the authenticated user | ✅ |
| `POST` | `/api/categories` | Create a new category | ✅ |
| `PUT` | `/api/categories/{id}` | Update a category | ✅ |
| `DELETE` | `/api/categories/{id}` | Delete a category (fails if linked to expenses) | ✅ |

### 🏦 Budgets (`/api/budgets`)

| Method | Endpoint | Description | Auth Required |
|:-------|:---------|:------------|:--------------|
| `GET` | `/api/budgets` | List all budgets with auto-calculated `spentAmount` | ✅ |
| `POST` | `/api/budgets` | Create a new budget (per category + date range) | ✅ |
| `PUT` | `/api/budgets/{id}` | Update a budget | ✅ |
| `DELETE` | `/api/budgets/{id}` | Delete a budget | ✅ |

### 🎯 Budget Strategies (`/api/budget-strategies`)

| Method | Endpoint | Description | Auth Required |
|:-------|:---------|:------------|:--------------|
| `GET` | `/api/budget-strategies` | List all strategies | ✅ |
| `POST` | `/api/budget-strategies` | Create a new strategy (needs+wants+savings = 100%) | ✅ |
| `PUT` | `/api/budget-strategies/{id}` | Update a strategy | ✅ |
| `PATCH` | `/api/budget-strategies/{id}/activate` | Activate a strategy (deactivates others) | ✅ |
| `PATCH` | `/api/budget-strategies/{id}/deactivate` | Deactivate a strategy | ✅ |
| `DELETE` | `/api/budget-strategies/{id}` | Delete a strategy (must be inactive) | ✅ |

---

## 🔑 Environment Variables

| Variable | Description | Required | Default | Example |
|:---------|:-----------|:---------|:--------|:--------|
| `DB_URL` | MySQL JDBC connection URL | No | `jdbc:mysql://localhost:3306/mizaniyati...` | `jdbc:mysql://prod-db:3306/mizaniyati` |
| `DB_USERNAME` | Database username | No | `root` | `mizaniyati_user` |
| `DB_PASSWORD` | Database password | No | *(empty)* | `s3cur3P@ssw0rd` |
| `JWT_SECRET` | Base64-encoded 512-bit key for JWT signing | **Yes (in prod)** | Dev placeholder | `openssl rand -base64 64` |
| `JWT_EXPIRATION` | JWT token validity in milliseconds | No | `86400000` (24h) | `3600000` (1h) |

---

## 📁 Project Structure

```
Mizaniyati/
├── .gitignore
├── .mvn/                           # Maven Wrapper config
├── mvnw / mvnw.cmd                 # Maven Wrapper scripts
├── pom.xml                         # Maven build file
├── README.md
└── src/
    ├── main/
    │   ├── java/com/mizaniyati/
    │   │   ├── MizaniyatiApplication.java     # Entry point
    │   │   ├── config/
    │   │   │   └── SecurityConfig.java        # Spring Security + CORS
    │   │   ├── controller/
    │   │   │   ├── AuthController.java        # Register, Login, Me
    │   │   │   ├── BudgetController.java
    │   │   │   ├── BudgetStrategyController.java
    │   │   │   ├── CategoryController.java
    │   │   │   ├── ExpenseController.java
    │   │   │   └── IncomeController.java
    │   │   ├── dto/                           # 13 DTOs (Request + Response)
    │   │   ├── entity/                        # 6 JPA Entities
    │   │   ├── enums/                         # Currency, ExpenseType, Frequency, IncomeType
    │   │   ├── exception/                     # ResourceNotFoundException
    │   │   ├── mapper/                        # 6 MapStruct Mappers
    │   │   ├── repository/                    # 6 Spring Data Repositories
    │   │   ├── security/
    │   │   │   ├── CustomUserDetailsService.java
    │   │   │   └── JwtFilter.java
    │   │   ├── service/
    │   │   │   ├── AuthService.java
    │   │   │   ├── interfaces/                # 6 Service interfaces
    │   │   │   └── impl/                      # 6 Service implementations
    │   │   └── util/
    │   │       └── JwtUtil.java               # JWT generation & validation
    │   └── resources/
    │       └── application.properties.example # Configuration template
    └── test/
        └── java/com/mizaniyati/
            └── MizaniyatiApplicationTests.java
```

---

## 🧪 Tests

Run the test suite with:

```bash
# Using Maven Wrapper:
./mvnw test

# Using local Maven:
mvn test
```

---

## 🤝 Contributing

Contributions are welcome! Here's how:

1. **Fork** the repository
2. **Create** a feature branch: `git checkout -b feature/amazing-feature`
3. **Commit** your changes: `git commit -m 'Add amazing feature'`
4. **Push** to the branch: `git push origin feature/amazing-feature`
5. **Open** a Pull Request

Please ensure your code follows the existing architecture patterns and includes appropriate tests.

---

## 📄 License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

---

<p align="center">
  Made with ❤️ by <a href="https://github.com/Yassine-Atiki">Yassine Atiki</a>
</p>
