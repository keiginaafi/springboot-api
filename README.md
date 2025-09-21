# RESTful API Application with Spring Boot

A simple RESTful API application built with Spring Boot that provides CRUD operations for User management.

## Features

- Complete CRUD operations for User entities
- Input validation
- H2 in-memory database for development
- RESTful API design principles
- Error handling and appropriate HTTP status codes

## Technologies Used

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database
- Maven
- Bean Validation

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. Clone or download the project
2. Navigate to the project directory
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. The application will start on `http://localhost:8080`

## API Endpoints

### User Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| POST | `/api/users` | Create a new user |
| PUT | `/api/users/{id}` | Update user by ID |
| DELETE | `/api/users/{id}` | Delete user by ID |
| DELETE | `/api/users` | Delete all users |
| GET | `/api/users/search?email={email}` | Find user by email |

### Sample API Usage

#### 1. Create a User (POST /api/users)
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "address": "123 Main St, City, Country"
  }'
```

#### 2. Get All Users (GET /api/users)
```bash
curl -X GET http://localhost:8080/api/users
```

#### 3. Get User by ID (GET /api/users/{id})
```bash
curl -X GET http://localhost:8080/api/users/1
```

#### 4. Update User (PUT /api/users/{id})
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Smith",
    "email": "john.smith@example.com",
    "address": "456 Oak Ave, City, Country"
  }'
```

#### 5. Delete User (DELETE /api/users/{id})
```bash
curl -X DELETE http://localhost:8080/api/users/1
```

#### 6. Search User by Email (GET /api/users/search?email={email})
```bash
curl -X GET "http://localhost:8080/api/users/search?email=john.doe@example.com"
```

## User Model

```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "address": "123 Main St, City, Country"
}
```

### Validation Rules

- **name**: Required, 2-50 characters
- **email**: Required, valid email format, unique
- **address**: Optional, max 100 characters

## HTTP Status Codes

- `200 OK` - Successful GET, PUT requests
- `201 Created` - Successful POST request
- `204 No Content` - Successful DELETE request or empty result
- `400 Bad Request` - Invalid input data
- `404 Not Found` - Resource not found
- `409 Conflict` - Email already exists
- `500 Internal Server Error` - Server error

## Database

The application uses H2 in-memory database for development. You can access the H2 console at:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/restfulapi/
│   │       ├── RestfulApiApplication.java
│   │       ├── controller/
│   │       │   └── UserController.java
│   │       ├── model/
│   │       │   └── User.java
│   │       └── repository/
│   │           └── UserRepository.java
│   └── resources/
│       └── application.properties
└── test/
```

## Testing the API

You can test the API using:
- cURL commands (as shown above)
- Postman
- Any REST client
- H2 Console for database inspection