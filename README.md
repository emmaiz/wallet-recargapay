# Wallet Service - RESTful API

## Overview
The Wallet Service is a RESTful API designed to manage user wallets. It supports operations like depositing, withdrawing, transferring funds, and retrieving wallet balances (current and historical). The implementation ensures high availability and traceability, as it handles critical financial transactions.

---

## Features

1. **Create Wallet**: Initialize wallets for users with a starting balance.
2. **Retrieve Current Balance**: Fetch the current balance of a specific wallet.
3. **Retrieve Historical Balance**: Query the balance at a specific point in time.
4. **Deposit Funds**: Add funds to a user's wallet.
5. **Withdraw Funds**: Deduct funds from a user's wallet (ensuring sufficient balance).
6. **Transfer Funds**: Transfer funds between two user wallets.

---

## Technology Stack

- **Language**: Java
- **Framework**: Spring Boot (RESTful API development)
- **Database**: H2 (in-memory, for simplicity)
- **Testing**: JUnit (unit and integration tests)
- **Dependency Management**: Gradle
- **Build Tool**: Gradle

---

## Prerequisites

1. Java 17 or higher installed.
2. Gradle installed.
3. Git installed.

---

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/emmaiz/wallet-recargapay.git
   ```

2. Build the project:
   ```bash
   ./gradlew clean build
   ```

3. Run the application:
   ```bash
   ./gradlew bootRun
   ```

4. Access the API:
    - API Base URL: [http://localhost:8080/api/wallets](http://localhost:8080/api/wallets)

---

## API Endpoints

### User Management

1. **Create User**

   - Endpoint: `POST /api/users/create`
   - Parameters: `username`, `email`
   - Example:
     ```bash
     curl -X POST "http://localhost:8080/api/users/create?username=johndoe&email=john@example.com"
     ```
   - Response:
     ```json
     {
       "id": 1,
       "username": "johndoe",
       "email": "john@example.com"
     }
     ```

---

### Wallet Management

1. **Create Wallet**
    - Endpoint: `POST /api/wallets/create`
    - Parameters: `userId`, `initialBalance`
    - Example:
      ```bash
      curl -X POST "http://localhost:8080/api/wallets/create?userId=1&initialBalance=100"
      ```

2. **Get Wallet Balance**
    - Endpoint: `GET /api/wallets/{walletId}/balance`
    - Example:
      ```bash
      curl -X GET "http://localhost:8080/api/wallets/1/balance"
      ```

3. **Deposit Funds**
    - Endpoint: `POST /api/wallets/{walletId}/deposit`
    - Parameters: `amount`
    - Example:
      ```bash
      curl -X POST "http://localhost:8080/api/wallets/1/deposit?amount=50"
      ```

4. **Withdraw Funds**
    - Endpoint: `POST /api/wallets/{walletId}/withdraw`
    - Parameters: `amount`
    - Example:
      ```bash
      curl -X POST "http://localhost:8080/api/wallets/1/withdraw?amount=20"
      ```

5. **Transfer Funds**
    - Endpoint: `POST /api/wallets/{sourceWalletId}/transfer/{targetWalletId}`
    - Parameters: `amount`
    - Example:
      ```bash
      curl -X POST "http://localhost:8080/api/wallets/1/transfer/2?amount=30"
      ```

---

## Testing

1. Run Unit and Integration Tests:
   ```bash
   ./gradlew test
   ```

2. View Test Results in the console after execution.

---

## Design Decisions

1. **High Traceability**: Every transaction is logged in the `Transaction` table, providing a full audit trail.
2. **Simplicity**: Chose H2 for in-memory persistence to simplify setup.
3. **Modularity**: Divided functionality into distinct service and controller layers to facilitate scalability.
4. **Error Handling**: Custom exceptions for scenarios like insufficient funds or non-existent wallets.
5. **Security**: Although not implemented here, authentication and authorization can be added using Spring Security.

---

## Limitations

1. **Database**: In-memory database (H2) does not persist data across application restarts.
2. **Concurrency**: High-concurrency scenarios may require locking mechanisms to prevent race conditions.
3. **Historical Balance**: Retrieving historical balance is not yet implemented.

---

## Future Improvements

1. **Persistent Database**: Migrate to a persistent database like PostgreSQL or MySQL.
2. **Historical Queries**: Extend the `Transaction` entity to allow for efficient historical balance queries.
3. **Swagger Integration**: Enhance API documentation for better developer onboarding.

---

## Contact

For further assistance or inquiries, reach out to:

- **Name**: Maiz Emmanuel
- **Email**: m.emmanuel.r@gmail.com

