# Inventory Management System (Spring Boot + React + Redis + Prometheus + Grafana)

## Overview
This project is a **high-performance, scalable** Inventory Management System built with **Spring Boot, PostgreSQL, Redis, React, Redux, Prometheus, and Grafana**. It provides efficient **CRUD operations**, caching for improved performance, monitoring, and an interactive UI.

## Features
### Backend (Spring Boot + PostgreSQL + Redis)
 **CRUD Operations**: Create, Read, Update, Delete products
 **Database Persistence**: Uses PostgreSQL for data storage
 **Caching with Redis**: Optimized API response times by caching frequently accessed data
 **Performance Testing**: Stress tests for bulk inserts & API response benchmarking
 **Prometheus & Grafana Integration**: Real-time monitoring & visualization
 **Swagger API Documentation**: Auto-generated API docs for easy reference

### Frontend (React + Redux)
 **Modern UI**: Built with React and Redux Toolkit
 **State Management**: Efficient data flow with Redux slices
 **Optimized API Calls**: Uses async Redux actions to fetch data efficiently
 **Real-time Updates**: UI reflects changes instantly with cache invalidation

## Tech Stack
### Backend
- **Spring Boot** – REST API development
- **PostgreSQL** – Persistent storage
- **Redis** – Caching layer
- **JUnit & Mockito** – Automated testing
- **Swagger** – API documentation
- **Prometheus** – API & system performance monitoring
- **Grafana** – Data visualization

### Frontend
- **React** – UI development
- **Redux Toolkit** – State management
- **Material UI** – UI components

---

## Installation & Setup

### Backend Setup (Spring Boot + PostgreSQL + Redis)
1️⃣ Clone the repository:
```bash
git clone https://github.com/yourusername/inventory-management.git
cd inventory-management/backend
```

2️⃣ Configure PostgreSQL:
```sql
CREATE DATABASE inventory_db;
```

3️⃣ Start Redis (if not installed, install it first):
```bash
brew install redis  # MacOS
sudo apt install redis-server  # Ubuntu
redis-server
```

4️⃣ Update `application.properties` with your PostgreSQL and Redis settings:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/inventory_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.cache.type=redis
spring.redis.host=localhost
spring.redis.port=6379
```

5️⃣ Run the backend:
```bash
./mvnw spring-boot:run
```

### Frontend Setup (React + Redux)
1️⃣ Navigate to the frontend directory:
```bash
cd ../frontend
```

2️⃣ Install dependencies:
```bash
npm install
```

3️⃣ Start the React app:
```bash
npm start
```

### Monitoring Setup (Prometheus + Grafana)
1️⃣ Install Prometheus:
```bash
brew install prometheus  # MacOS
```

2️⃣ Configure `prometheus.yml`:
```yaml
global:
  scrape_interval: 5s
scrape_configs:
  - job_name: "spring-boot-app"
    metrics_path: '/actuator/prometheus'
    static_configs:
    - targets: ["localhost:8080"]
```

3️⃣ Start Prometheus:
```bash
prometheus --config.file=/path/to/prometheus.yml
```

4️⃣ Install and start Grafana:
```bash
brew install grafana
brew services start grafana
```

5️⃣ Access Grafana at `http://localhost:3000/`, add Prometheus as a data source, and create dashboards.

---

## API Endpoints
### Product Management
| Method | Endpoint                 | Description |
|--------|--------------------------|-------------|
| GET    | `/api/products`          | Get all products |
| GET    | `/api/products/{id}`     | Get product by ID |
| POST   | `/api/products`          | Create a new product |
| PUT    | `/api/products/{id}`     | Update a product |
| DELETE | `/api/products/{id}`     | Delete a product |

---

## Performance Improvements
### Before & After Redis Caching
| Scenario               | Without Redis | With Redis |
|------------------------|--------------|-----------|
| Single Product Fetch   | ~300ms       | ~50ms     |
| Bulk Product Fetch     | ~500ms       | ~80ms     |
| Large Data Insertions  | ~10s         | ~2s       |

### Stress Test Results (10,000 Products)
| Action                 | Execution Time |
|------------------------|---------------|
| Insert 10,000 Products | ~8s            |
| Fetch All Products     | ~600ms         |

---

## Challenges & Fixes
### Backend Issues
| Issue | Fix |
|-------|-----|
| PostgreSQL DB connection failure | Created DB manually: `CREATE DATABASE inventory_db;` |
| Swagger UI not loading | Fixed dependency version mismatch |
| Redis cache not invalidating | Used `@CacheEvict` for updates |
| Slow bulk inserts | Used batch processing & increased PostgreSQL `max_connections` |

### Frontend Issues
| Issue | Fix |
|-------|-----|
| React state not updating | Used `useEffect()` to refresh UI after API calls |
| Redux errors on async actions | Used `.unwrap()` on dispatch actions |
| CORS issues | Added `@CrossOrigin(origins = "http://localhost:3000")` in Spring Boot |

### Monitoring Issues
| Issue | Fix |
|-------|-----|
| Spring Boot app not visible in Prometheus | Verified `application.properties`, restarted Prometheus |
| Prometheus crash due to missing log directory | Created `/usr/local/bin/data` and set correct permissions |
| Grafana & React using same port (3000) | Changed Grafana port to `3001` |

---

## Future Enhancements
**Security**
- Implement JWT Authentication
- Role-based API access (Admin/User)

**Performance Optimizations**
- Use **Redis TTL** for auto-expiring cache
- Implement **pagination** for large datasets

**Monitoring & Logging**
- Add **cache hit/miss** logging
- Track API **response times in production**

---

## Conclusion
This **Inventory Management System** has evolved from a basic CRUD app to a **high-performance, cache-efficient, and production-ready** solution. 🚀 We tackled real-world scalability issues, optimized database performance, and integrated monitoring tools for seamless observability.

Let us know if you have feedback or suggestions!

