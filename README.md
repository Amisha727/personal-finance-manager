# Personal Finance Manager

A full‑stack **Spring Boot–based Personal Finance Management System** that allows users to securely track income, expenses, savings goals, and generate financial reports. This project focuses on clean architecture, security, and real-world backend practices.

---

## 🚀 Live Deployment

* **Base URL:** [https://personal-finance-manager-r7jn.onrender.com](https://personal-finance-manager-r7jn.onrender.com)
* **API Base Path:** `/api`

> The application is deployed on **Render** and may take a few seconds to wake up if idle.

---

## 📌 Key Features

### 1️⃣ User Management & Authentication

* User registration with email, password, full name, and phone number
* Secure login using **session-based authentication**
* HTTP session handling with secure cookies
* Logout with proper session invalidation
* **Strict data isolation**: users can only access their own data

---

### 2️⃣ Transaction Management

* Create, read, update, and delete transactions
* Supported attributes:

  * Amount (positive decimal)
  * Date (YYYY-MM-DD, non-future)
  * Category (validated)
  * Description (optional)
* List transactions sorted by newest first
* Filtering by:

  * Date range
  * Category
  * Transaction type (INCOME / EXPENSE)

---

### 3️⃣ Category Management

#### Default Categories (Non-editable)

* **INCOME:** Salary
* **EXPENSE:** Food, Rent, Transportation, Entertainment, Healthcare, Utilities

#### Custom Categories

* User-defined categories (INCOME / EXPENSE)
* Category names must be **unique per user**
* Categories in use by transactions cannot be deleted

---

### 4️⃣ Savings Goals

* Create savings goals with:

  * Goal name
  * Target amount
  * Target date (future date only)
* Automatic progress tracking
* Progress formula:

  ```
  (Total Income – Total Expenses) since goal start date
  ```
* View percentage completion and remaining amount
* Update or delete goals as needed

---

### 5️⃣ Reports & Analytics

#### Monthly Report

* Total income by category
* Total expenses by category
* Net savings

#### Yearly Report

* Aggregated monthly financial overview

---

## 🧱 System Architecture

The application follows a **clean layered architecture**:

```
Controller → Service → Repository
```

* **Controller Layer:** Handles HTTP requests and responses
* **Service Layer:** Business logic and validations
* **Repository Layer:** Data access using Spring Data JPA
* **DTOs:** Separate request/response objects from entities
* **Global Exception Handling:** Implemented using `@ControllerAdvice`

---

## 🛠️ Technology Stack

| Component  | Technology                     |
| ---------- | ------------------------------ |
| Language   | Java 17                        |
| Framework  | Spring Boot 3.x                |
| ORM        | Spring Data JPA / Hibernate    |
| Database   | H2 (In-memory for development) |
| Security   | Spring Security                |
| Build Tool | Maven                          |
| Testing    | JUnit 5, Mockito               |
| Deployment | Render                         |

---

## 🔐 Security Design

* Session-based authentication
* Secure cookie handling
* Protected APIs (authentication required)
* Only `/api/auth/register` and `/api/auth/login` are public

---

## 🧪 Testing

* Unit tests written using **JUnit 5** and **Mockito**
* Service layer logic tested in isolation
* External dependencies mocked
* Test execution via:

  ```bash
  ./mvnw test
  ```

> Due to free-tier deployment constraints, some tests were skipped during final deployment build using:

```bash
./mvnw clean package -DskipTests
```

---

## ▶️ Running Locally

### Prerequisites

* Java 17+
* Maven

### Steps

```bash
git clone https://github.com/Amisha727/personal-finance-manager.git
cd personal-finance-manager
./mvnw spring-boot:run
```

Application will start at:

```
http://localhost:8080
```

---

## 📡 API Overview

### Authentication

* `POST /api/auth/register`
* `POST /api/auth/login`
* `POST /api/auth/logout`

### Transactions

* `POST /api/transactions`
* `GET /api/transactions`
* `PUT /api/transactions/{id}`
* `DELETE /api/transactions/{id}`

### Categories

* `GET /api/categories`
* `POST /api/categories`
* `DELETE /api/categories/{id}`

### Savings Goals

* `POST /api/goals`
* `GET /api/goals`
* `PUT /api/goals/{id}`
* `DELETE /api/goals/{id}`

### Reports

* `GET /api/reports/monthly`
* `GET /api/reports/yearly`

---

## 🧠 Design Decisions

* Chose **session-based authentication** for simplicity and clarity
* Used **DTOs** to decouple API contracts from persistence models
* Enforced strict validation at service layer
* Deployed early to ensure production readiness

---

## 🔮 Future Improvements

* JWT-based authentication
* Pagination support
* Swagger/OpenAPI documentation
* Improved test coverage
* Production database integration

---

## 👤 Author

**Amisha Kumari**
Computer Science & Engineering Student
Passionate about backend systems, clean architecture, and real‑world problem solving

---

✅ This project reflects an end‑to‑end backend implementation, from system design to live deployment.
