
# Spring Boot REST API Project

This is a Spring Boot project that implements a REST API for user login and signup functionality. It utilizes Spring Security for authentication and JWT (JSON Web Tokens) for secure token-based authentication.


## Features

- User signup: Allows users to create new accounts securely and store their information in an H2 database.
- User login: Authenticates user credentials and generates a JWT token upon successful authentication.
- JWT token-based authentication: Requires the use of JWT tokens for accessing protected endpoints.
- H2 database integration: Stores user information securely in an H2 in-memory database.
- Spring Security integration: Provides security and authentication features to protect the API endpoints.


## Prerequisites

To run this project, make sure you have the following prerequisites installed:

- Java Development Kit (JDK) 8 or higher
- Maven build tool
- Postman or any similar API testing tool


## Getting Started

1. Clone the repository:

   ```shell
   git clone https://github.com/ishita2002rai/spring-boot-rest-api.git

2. Navigate to the project directory:

    ```shell
    cd spring-boot-rest-api

3. Build the project using Maven:
    ```shell
    mvn clean install

4. Start the application:
    ```shell
    mvn spring-boot:run

5. Once the application is running, you can test the endpoints using Postman or any API testing tool.



## API Endpoints

The following API endpoints are available:

**POST /api/auth/signup -** User signup: Create a new user account.

**POST /api/auth/login -** User login: Authenticate user credentials and generate a JWT token.

**Note:** All endpoints except /api/auth/signup require JWT token-based authentication.

For detailed information on the request and response formats of each endpoint, refer to the API documentation or Postman collection.


## Configuration

The application can be configured using the following properties:

**jwt.secret -** Secret key used for JWT token generation and verification.

**jwt.expiration -** Expiration time for JWT tokens (in milliseconds).

These properties can be modified in the application.properties file located in the src/main/resources directory.


## Dependencies

This project utilizes the following major dependencies:

- Spring Boot
- Spring Web
- Spring Security
- H2 Database
- JSON Web Token (JWT)

All dependencies and their versions can be found in the pom.xml file.
