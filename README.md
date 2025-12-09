
# Currency Converter API

This document explains **how to set up, run, and test** the Currency Converter API locally.

This file is safe to share publicly.  
All credentials, API keys, and sensitive values are placeholders.

---

## 1. Prerequisites

Before running the project, make sure you have:

### 1.1 Java
- Java **17 or later**
- Verify installation:
  ```bash
  java -version
````

### 1.2 Maven Wrapper

* Maven wrapper (`mvnw`) is included in the project
* No separate Maven installation required

### 1.3 curl (for API testing)

* curl is pre-installed on macOS and Linux
* Verify:

  ```bash
  curl --version
  ```

### 1.4 Internet Connection

* Required to fetch real-time exchange rates from the external API

---

## 2. Project Structure (High Level)

Important files and directories:

* `src/main/java/.../CurrencyConverterApplication.java`
  Main Spring Boot application entry point.

* `controller/CurrencyController.java`
  Exposes REST endpoints:

  * `/api/convert`
  * `/api/history`
  * `/api/rates/{base}`

* `service/CurrencyService.java`
  Business logic and external API calls.

* `model/ConversionRecord.java`
  JPA entity storing conversion history.

* `repository/ConversionRecordRepository.java`
  Spring Data JPA repository.

* `config/SecurityConfig.java`
  HTTP Basic Authentication configuration.

* `application.properties`
  Application configuration (database, API URL, API key placeholder).

---

## 3. Exchange Rate API Key Configuration

This project uses a third-party exchange rate service
(example: **ExchangeRate-API**).

### 3.1 Get an API Key

1. Visit:

   ```
   https://www.exchangerate-api.com/
   ```
2. Create a **free account**
3. Generate an API key from the dashboard

---

### 3.2 Configure the API Key (Local Only)

Open:

```
src/main/resources/application.properties
```

Ensure these properties exist:

```properties
app.exchange-rate-api.base-url=https://v6.exchangerate-api.com/v6
app.exchange-rate-api.key=YOUR_EXCHANGERATE_API_KEY
```

Replace `YOUR_EXCHANGERATE_API_KEY` **locally** with your own key.

✅ **Do not use quotes**
✅ **Restart the app after changes**

---

### 🔐 Security Note (Important for GitHub)

* **Never commit your real API key**
* This repository intentionally uses a placeholder
* For production or deployment:

  * Use environment variables
  * Or a separate ignored config file

---

## 4. Database Configuration (H2)

The application uses an **in-memory H2 database**.

Default configuration (already provided):

```properties
spring.datasource.url=jdbc:h2:mem:currencydb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

No setup required.

---

## 5. Running the Application

### 5.1 Run Using Terminal (Recommended)

From the project root:

```bash
./mvnw spring-boot:run
```

If permission is denied (macOS/Linux):

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

---

### 5.2 Run Using VS Code

1. Open the project folder in VS Code
2. Install extensions:

   * Java Extension Pack
   * Spring Boot Extension Pack
3. Open:

   ```
   CurrencyConverterApplication.java
   ```
4. Click **Run ▶**

---

## 6. Verify Application Startup

Successful startup log includes:

```text
Tomcat started on port 8080
Started CurrencyConverterApplication
```

Base URL:

```
http://localhost:8080
```

> Opening `/` in the browser may show a Whitelabel 404 page.
> This is expected for an API-only backend.

---

## 7. Authentication

The API uses **HTTP Basic Authentication**
(in-memory users for demonstration purposes).

Default users:

| Username | Password  |
| -------- | --------- |
| user1    | password1 |
| user2    | password2 |

All examples below use:

```
user1 / password1
```

---

## 8. API Usage Examples

### 8.1 Get Latest Rates

**Endpoint**

```http
GET /api/rates/{base}
```

**Example**

```bash
curl -X GET "http://localhost:8080/api/rates/USD" \
  -u user1:password1
```

**Result**

* Calls external exchange rate service
* Returns JSON with available currency rates

---

### 8.2 Convert Currency

**Endpoint**

```http
POST /api/convert
```

**Request Body**

```json
{
  "fromCurrency": "USD",
  "toCurrency": "INR",
  "amount": 100
}
```

**Example**

```bash
curl -X POST "http://localhost:8080/api/convert" \
  -u user1:password1 \
  -H "Content-Type: application/json" \
  -d '{
    "fromCurrency": "USD",
    "toCurrency": "INR",
    "amount": 100
  }'
```

**What Happens**

1. Fetches exchange rates
2. Converts amount
3. Persists data to H2
4. Returns conversion response

---

### 8.3 View Conversion History

**Endpoint**

```http
GET /api/history
```

**Example**

```bash
curl -X GET "http://localhost:8080/api/history" \
  -u user1:password1
```

Returns user-specific conversion history.

---

## 9. Inspect Database (H2 Console)

1. Open browser:

   ```
   http://localhost:8080/h2-console
   ```

2. Login with:

   ```
   JDBC URL: jdbc:h2:mem:currencydb
   Username: sa
   Password: (empty)
   ```

3. Run query:

   ```sql
   SELECT * FROM CONVERSION_RECORDS;
   ```

---

## 10. Common Issues & Fixes

### 10.1 Whitelabel Error (404)

**Cause**

* No homepage defined

**Fix**

* Use API endpoints directly
* Or add `index.html` under:

  ```
  src/main/resources/static/
  ```

---

### 10.2 401 Unauthorized

**Cause**

* Missing or incorrect credentials

**Fix**

```bash
-u user1:password1
```

---

### 10.3 External API Error

**Cause**

* Missing / invalid API key
* Free plan quota exceeded

**Fix**

1. Verify key in `application.properties`
2. Test directly:

   ```
   https://v6.exchangerate-api.com/v6/YOUR_API_KEY/latest/USD
   ```
3. Restart application

---

### 10.4 Port 8080 Already in Use

**Fix**
Change port:

```properties
server.port=8081
```

Access app at:

```
http://localhost:8081
```

---

## 11. Summary

This project demonstrates:

* Spring Boot REST API development
* Secure endpoints using Spring Security
* External API integration
* JSON handling with Jackson
* Persistence using Spring Data JPA and H2
* Clean controller–service–repository architecture
