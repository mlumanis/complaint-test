# Complaint Management System

A RESTful service for managing product complaints, built with Spring Boot and Kotlin.

## Features

- Create new complaints
- Edit complaint content
- Retrieve complaints
- Automatic country detection based on IP address
- Duplicate handling with counter increment
- PostgreSQL database storage

## Prerequisites

- JDK 21
- PostgreSQL
- Maven

## Setup

1. Create a PostgreSQL database:
```sql
CREATE DATABASE complaint_db;
```

2. Update database credentials in `src/main/resources/application.yml` if needed.

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Create a Complaint
```http
POST /api/complaints
Content-Type: application/json

{
    "productId": "PROD123",
    "content": "Product is defective",
    "reportedBy": "john.doe@example.com"
}
```

### Update a Complaint
```http
PUT /api/complaints/{id}
Content-Type: text/plain

Updated complaint content
```

### Get a Complaint
```http
GET /api/complaints/{id}
```

### Get All Complaints
```http
GET /api/complaints
```

## Response Format

```json
{
    "id": 1,
    "productId": "PROD123",
    "content": "Product is defective",
    "createdAt": "2024-03-15T10:30:00",
    "reportedBy": "john.doe@example.com",
    "country": "United States",
    "counter": 1
}
``` 