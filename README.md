# Smart Product Recommender

A microservices-based recommendation engine using **Spring Boot**, **Kafka**, **PostgreSQL**, **FastAPI**, and **Docker**.

## 📦 Architecture

```
User --> Producer-Service --> FastAPI ML Service
                |               |
            Kafka Topic     Recommendation Logic
                |
        Consumer-Service --> PostgreSQL
```

- **Producer Service**: Exposes REST API and sends user events to Kafka.
- **Consumer Service**: Consumes Kafka events and stores them in PostgreSQL.
- **ML Service**: FastAPI app that returns recommendations based on user & product.

---

## 🚀 Getting Started

### Prerequisites

- Docker & Docker Compose
- Java 21
- Maven 3.9+

### Run All Services

```bash
docker-compose up --build
```

---

## 📂 Endpoints

### Recommendation Request

**GET** `http://localhost:8082/recommendation?userId=1&productId=123`

**Response:**
```json
{
  "recommendations": ["prod-153", "prod-542", "prod-875"]
}
```

---

## ⚙️ Project Modules

- `core`: Shared DTOs and config
- `producer-service`: REST API & Kafka producer
- `consumer-service`: Kafka consumer & PostgreSQL persistence
- `ml-service`: FastAPI microservice with simple recommendation logic

---

## 🧪 Testing

```bash
mvn test
```

---

## 📌 Configuration

Use Spring profiles:
- `local` (default): `localhost` URLs
- `docker`: internal Docker networking (e.g., `ml-service:8000`)

---

## 🐳 Docker Notes

Create `.dockerignore` for efficient builds:
