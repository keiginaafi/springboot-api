# RESTful API Application with Spring Boot

A comprehensive RESTful API application built with Spring Boot that provides CRUD operations for User management and demonstrates external API consumption using both RestTemplate and WebClient.

## Features

### User Management
- Complete CRUD operations for User entities
- Input validation and error handling
- H2 in-memory database for development
- RESTful API design principles

### External API Consumption
- Dog CEO API integration using RestTemplate (traditional approach)
- Dog CEO API integration using WebClient (reactive approach)
- Multiple JSON response handling methods
- Comprehensive error handling and timeout management
- Demonstration of both blocking and non-blocking API calls

## Technologies Used

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- Spring WebFlux (for WebClient)
- H2 Database
- Maven
- Bean Validation
- Jackson (JSON processing)
- RestTemplate & WebClient

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

### Dog API (RestTemplate - Traditional Approach)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/dogs/breeds` | Get all dog breeds |
| GET | `/api/dogs/breeds/{breed}/sub-breeds` | Get sub-breeds for a specific breed |
| GET | `/api/dogs/random-image` | Get a random dog image |
| GET | `/api/dogs/breeds/{breed}/images?count=3` | Get images for a specific breed |
| POST | `/api/dogs/favorites` | Add a breed to favorites (mock) |

### Dog API (WebClient - Reactive Approach)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/dogs/breeds/json-string` | Get breeds as raw JSON string |
| GET | `/api/dogs/breeds/json-node` | Get breeds as Jackson JsonNode |
| GET | `/api/dogs/breeds/map` | Get breeds as Map<String, Object> |
| GET | `/api/dogs/breeds/{breed}/images-from-json` | Get breed images with custom JSON processing |

## Sample API Usage

### User Management Examples

#### Create a User
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "address": "123 Main St, City, Country"
  }'
```

#### Get All Users
```bash
curl -X GET http://localhost:8080/api/users
```

### Dog API Examples

#### Get All Dog Breeds (RestTemplate)
```bash
curl -X GET http://localhost:8080/api/dogs/breeds
```

#### Get Sub-breeds for a Specific Breed
```bash
curl -X GET http://localhost:8080/api/dogs/breeds/hound/sub-breeds
```

#### Get Random Dog Image
```bash
curl -X GET http://localhost:8080/api/dogs/random-image
```

#### Get Breed Images with Count
```bash
curl -X GET "http://localhost:8080/api/dogs/breeds/labrador/images?count=5"
```

#### Get Breeds as Raw JSON String (WebClient)
```bash
curl -X GET http://localhost:8080/api/dogs/breeds/json-string
```

#### Get Breeds as JsonNode (WebClient)
```bash
curl -X GET http://localhost:8080/api/dogs/breeds/json-node
```

#### Get Breeds as Map (WebClient)
```bash
curl -X GET http://localhost:8080/api/dogs/breeds/map
```

#### Get Breed Images with Custom JSON Processing
```bash
curl -X GET "http://localhost:8080/api/dogs/breeds/golden/images-from-json?count=3"
```

## Data Models

### User Model
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "address": "123 Main St, City, Country"
}
```

### Dog Breed Model
```json
{
  "breeds": {
    "affenpinscher": [],
    "african": [],
    "hound": ["afghan", "basset", "blood", "english", "ibizan", "plott", "walker"]
  },
  "status": "success"
}
```

### Dog Image Model
```json
{
  "imageUrl": "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg",
  "status": "success"
}
```

## API Consumption Approaches

### RestTemplate (Traditional/Blocking)
- **Use Case**: Simple applications, lower traffic
- **Characteristics**: Blocking, thread-per-request, easier to understand
- **Example**: `DogService.java`

### WebClient (Modern/Reactive)
- **Use Case**: High-concurrency, microservices, performance-critical
- **Characteristics**: Non-blocking, event-driven, resource-efficient
- **Example**: `DogWebClientService.java`

### JSON Response Handling Options

1. **Class Mapping**: Direct mapping to POJOs (e.g., `DogBreed.class`)
2. **Raw JSON String**: `bodyToMono(String.class)` for custom processing
3. **JsonNode**: `bodyToMono(JsonNode.class)` for flexible navigation
4. **Generic Map**: `bodyToMono(Map.class)` for key-value access

## Configuration

### RestTemplate Configuration
```java
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

### WebClient Configuration
```java
@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://dog.ceo/api")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
```

## Error Handling

The application includes comprehensive error handling:

- **Global Exception Handler**: Centralized error handling for all API calls
- **Custom Error Responses**: Structured error messages with timestamps
- **HTTP Status Codes**: Appropriate status codes for different scenarios
- **Timeout Management**: Configurable timeouts for external API calls
- **Retry Logic**: Built-in retry mechanisms for failed requests

## HTTP Status Codes

- `200 OK` - Successful GET, PUT requests
- `201 Created` - Successful POST request
- `204 No Content` - Successful DELETE request or empty result
- `400 Bad Request` - Invalid input data
- `404 Not Found` - Resource not found
- `409 Conflict` - Email already exists
- `500 Internal Server Error` - Server error
- `503 Service Unavailable` - External API unavailable

## Database

The application uses H2 in-memory database for development:
- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (empty)

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/restfulapi/
│   │       ├── RestfulApiApplication.java
│   │       ├── config/
│   │       │   ├── RestTemplateConfig.java
│   │       │   └── WebClientConfig.java
│   │       ├── controller/
│   │       │   ├── UserController.java
│   │       │   └── DogController.java
│   │       ├── exception/
│   │       │   └── GlobalExceptionHandler.java
│   │       ├── model/
│   │       │   ├── User.java
│   │       │   ├── DogBreed.java
│   │       │   └── DogImage.java
│   │       ├── repository/
│   │       │   └── UserRepository.java
│   │       └── service/
│   │           ├── DogService.java
│   │           └── DogWebClientService.java
│   └── resources/
│       └── application.properties
└── test/
```

## Dependencies

Key dependencies in `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

## Testing the API

You can test the API using:
- **cURL commands** (as shown above)
- **Postman** or **Insomnia**
- **Browser** for GET requests
- **H2 Console** for database inspection

## Best Practices Implemented

- ✅ Dependency Injection with `@Autowired`
- ✅ Proper error handling and logging
- ✅ Timeout management for external calls
- ✅ Response mapping and validation
- ✅ HTTP status code handling
- ✅ Configuration separation
- ✅ Service layer abstraction
- ✅ RESTful API design principles
- ✅ Both blocking and non-blocking approaches
- ✅ Multiple JSON response handling methods

## External API Integration

This project demonstrates integration with the **Dog CEO API** (https://dog.ceo/dog-api/):
- Fetching dog breeds and sub-breeds
- Retrieving random dog images
- Getting breed-specific images
- Handling API responses in multiple formats

The implementation showcases both traditional (RestTemplate) and modern (WebClient) approaches to API consumption in Spring Boot applications.