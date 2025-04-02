# Order Management System

A Spring Boot-based REST API for managing orders, built with Java 17 and Spring Boot 3.2.3.

## Features

- Create, read, update, and manage orders
- Track order status (PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED)
- Support for multiple items per order
- Automatic order status updates
- H2 in-memory database for development
- Comprehensive error handling
- RESTful API design
- Unit and integration tests

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Postman (for API testing)

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/order-management-system.git
cd order-management-system
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Orders

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/orders` | Create a new order |
| GET | `/api/orders` | Get all orders |
| GET | `/api/orders/{id}` | Get order by ID |
| GET | `/api/orders?status={status}` | Get orders by status |
| PUT | `/api/orders/{id}/status` | Update order status |
| PUT | `/api/orders/{id}/cancel` | Cancel a pending order |

### Example Request Bodies

#### Create Order
```json
{
    "customerName": "John Doe",
    "customerEmail": "john.doe@example.com",
    "items": [
        {
            "productName": "Laptop",
            "quantity": 1,
            "price": 999.99
        },
        {
            "productName": "Mouse",
            "quantity": 2,
            "price": 29.99
        }
    ]
}
```

#### Update Order Status
```json
"PROCESSING"
```

## Testing

### Running Tests

```bash
mvn test
```

### Test Coverage

The project includes:
- Unit tests for services and models
- Integration tests for repositories
- Controller tests with MockMvc
- Exception handling tests

## Error Handling

The API provides consistent error responses in the following format:

```json
{
    "timestamp": "2024-04-01T12:34:56.789",
    "message": "Error message",
    "status": 404,
    "error": "Not Found"
}
```

Common HTTP Status Codes:
- 200: Success
- 201: Created
- 400: Bad Request
- 404: Not Found
- 500: Internal Server Error

## Development

### Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/ordermanagement/
│   │       ├── controller/
│   │       ├── service/
│   │       ├── repository/
│   │       ├── model/
│   │       └── exception/
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/example/ordermanagement/
```

### Dependencies

- Spring Boot Web
- Spring Data JPA
- H2 Database
- Lombok
- Spring Boot Test

## API Testing with Postman

1. Import the `Order_Management_System.postman_collection.json` file into Postman
2. Create a new environment in Postman
3. Set the `baseUrl` variable to `http://localhost:8080`
4. Start testing the API endpoints

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Spring Boot team for the excellent framework
- H2 Database team for the in-memory database
- All contributors who have helped with this project 