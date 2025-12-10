Here is the **clean, properly formatted SINGLE `README.md`** version of your document, ready to paste directly into GitHub 👇\
(No content changes, only structure, consistency, and polish.)

* * * * *

Currency Converter API
======================

A **Spring Boot REST API** that converts currencies using real-time exchange rates from an external service and stores conversion history.

✅ Safe to share publicly\
✅ No secrets committed\
✅ Uses placeholders for sensitive data

* * * * *

Table of Contents
-----------------

1.  [Prerequisites](https://chatgpt.com/c/69397866-c258-8321-836f-ee344e059cdb#1-prerequisites)

2.  [Project Structure](https://chatgpt.com/c/69397866-c258-8321-836f-ee344e059cdb#2-project-structure-high-level)

3.  [Exchange Rate API Key](https://chatgpt.com/c/69397866-c258-8321-836f-ee344e059cdb#3-exchange-rate-api-key-configuration)

4.  [Database Configuration](https://chatgpt.com/c/69397866-c258-8321-836f-ee344e059cdb#4-database-configuration-h2)

5.  [Running the Application](https://chatgpt.com/c/69397866-c258-8321-836f-ee344e059cdb#5-running-the-application)

6.  [Verify Application Startup](https://chatgpt.com/c/69397866-c258-8321-836f-ee344e059cdb#6-verify-application-startup)

7.  [Authentication](https://chatgpt.com/c/69397866-c258-8321-836f-ee344e059cdb#7-authentication)

8.  [API Usage Examples](https://chatgpt.com/c/69397866-c258-8321-836f-ee344e059cdb#8-api-usage-examples)

9.  [Inspect Database (H2 Console)](https://chatgpt.com/c/69397866-c258-8321-836f-ee344e059cdb#9-inspect-database-h2-console)

10. [Common Issues & Fixes](https://chatgpt.com/c/69397866-c258-8321-836f-ee344e059cdb#10-common-issues--fixes)

11. [Summary](https://chatgpt.com/c/69397866-c258-8321-836f-ee344e059cdb#11-summary)

* * * * *

1\. Prerequisites
-----------------

Before running the project, make sure you have the following installed.

### 1.1 Java

-   Java **17 or later**

-   Verify installation:

```
java -version

```

* * * * *

### 1.2 Maven Wrapper

-   Maven Wrapper (`mvnw`) is included

-   No separate Maven installation required

* * * * *

### 1.3 curl (for API testing)

-   Pre-installed on macOS and Linux

-   Verify:

```
curl --version

```

* * * * *

### 1.4 Internet Connection

-   Required for fetching real-time exchange rates from the external API

* * * * *

2\. Project Structure (High Level)
----------------------------------

Key files and directories:

```
src/main/java/...
├── CurrencyConverterApplication.java   # Application entry point
├── controller/
│   └── CurrencyController.java          # REST endpoints
├── service/
│   └── CurrencyService.java             # Business logic & API calls
├── model/
│   └── ConversionRecord.java            # JPA entity
├── repository/
│   └── ConversionRecordRepository.java  # Data access
├── config/
│   └── SecurityConfig.java              # HTTP Basic Auth
└── resources/
    ├── application.properties

```

* * * * *

3\. Exchange Rate API Key Configuration
---------------------------------------

This project uses a third-party exchange rate service\
(example: **ExchangeRate-API**).

* * * * *

### 3.1 Get an API Key

1.  Visit:

    ```
    https://www.exchangerate-api.com/

    ```

2.  Create a **free account**

3.  Generate an API key from the dashboard

* * * * *

### 3.2 Configure the API Key (Local Only)

Edit the file:

```
src/main/resources/application.properties

```

Add or ensure the following properties exist:

```
app.exchange-rate-api.base-url=https://v6.exchangerate-api.com/v6
app.exchange-rate-api.key=YOUR_EXCHANGERATE_API_KEY

```

🔹 Replace `YOUR_EXCHANGERATE_API_KEY` with your real key **locally**\
🔹 Do **not** use quotes\
🔹 Restart the application after changes

* * * * *

### 🔐 Security Note (Important)

-   ❌ Never commit your real API key

-   ✅ Repository uses placeholders intentionally

-   ✅ For production:

    -   Use environment variables

    -   Or an ignored configuration file

* * * * *

4\. Database Configuration (H2)
-------------------------------

The application uses an **in-memory H2 database**.

Default configuration:

```
spring.datasource.url=jdbc:h2:mem:currencydb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

```

✅ No setup required

* * * * *

5\. Running the Application
---------------------------

### 5.1 Run Using Terminal (Recommended)

From the project root:

```
./mvnw spring-boot:run

```

If permission is denied (macOS/Linux):

```
chmod +x mvnw
./mvnw spring-boot:run

```

* * * * *

### 5.2 Run Using VS Code

1.  Open project folder in **VS Code**

2.  Install extensions:

    -   Java Extension Pack

    -   Spring Boot Extension Pack

3.  Open:

    ```
    CurrencyConverterApplication.java

    ```

4.  Click **Run ▶**

* * * * *

6\. Verify Application Startup
------------------------------

Successful startup logs include:

```
Tomcat started on port 8080
Started CurrencyConverterApplication

```

Base URL:

```
http://localhost:8080

```

⚠️ Visiting `/` may show a Whitelabel 404 page --- this is normal for API-only apps.

* * * * *

7\. Authentication
------------------

The API uses **HTTP Basic Authentication**.

Default in-memory users:

| Username | Password |
| --- | --- |
| user1 | password1 |
| user2 | password2 |

Examples below use:

```
user1 / password1

```

* * * * *

8\. API Usage Examples
----------------------

### 8.1 Get Latest Exchange Rates

**Endpoint**

```
GET /api/rates/{base}

```

**Example**

```
curl -X GET "http://localhost:8080/api/rates/USD"\
  -u user1:password1

```

**Description**

-   Fetches real-time rates from external API

-   Returns JSON response

* * * * *

### 8.2 Convert Currency

**Endpoint**

```
POST /api/convert

```

**Request Body**

```
{
  "fromCurrency": "USD",
  "toCurrency": "INR",
  "amount": 100
}

```

**Example**

```
curl -X POST "http://localhost:8080/api/convert"\
  -u user1:password1\
  -H "Content-Type: application/json"\
  -d '{
    "fromCurrency": "USD",
    "toCurrency": "INR",
    "amount": 100
  }'

```

**What Happens**

1.  Retrieves exchange rates

2.  Converts currency

3.  Saves record in H2 database

4.  Returns conversion response

* * * * *

### 8.3 View Conversion History

**Endpoint**

```
GET /api/history

```

**Example**

```
curl -X GET "http://localhost:8080/api/history"\
  -u user1:password1

```

Returns user-specific conversion history.

* * * * *

9\. Inspect Database (H2 Console)
---------------------------------

1.  Open:

    ```
    http://localhost:8080/h2-console

    ```

2.  Login details:

    ```
    JDBC URL: jdbc:h2:mem:currencydb
    Username: sa
    Password: (empty)

    ```

3.  Query data:

```
SELECT * FROM CONVERSION_RECORDS;

```

* * * * *

10\. Common Issues & Fixes
--------------------------

### 10.1 Whitelabel Error (404)

**Cause**

-   No homepage defined

**Fix**

-   Use API endpoints directly

-   Or add `index.html` in:

```
src/main/resources/static/

```

* * * * *

### 10.2 401 Unauthorized

**Cause**

-   Missing or wrong credentials

**Fix**

```
-u user1:password1

```

* * * * *

### 10.3 External API Error

**Cause**

-   Invalid or missing API key

-   Free API quota exceeded

**Fix**

1.  Verify key in `application.properties`

2.  Test manually:

    ```
    https://v6.exchangerate-api.com/v6/YOUR_API_KEY/latest/USD

    ```

3.  Restart the application

* * * * *

### 10.4 Port 8080 Already in Use

**Fix**

```
server.port=8081

```

Access:

```
http://localhost:8081

```

* * * * *

11\. Summary
------------

This project demonstrates:

-   Spring Boot REST API development

-   Secure endpoints (Spring Security)

-   External API integration

-   JSON handling with Jackson

-   Persistence using Spring Data JPA & H2

-   Clean Controller--Service--Repository architecture

* * * * *

✅ **GitHub-ready**\
✅ **Professional documentation**\
✅ **Perfect for portfolios & LinkedIn**

If you want, I can also:

-   Add badges (Java, Spring Boot, Maven)

-   Create a minimal `CONTRIBUTING.md`

-   Optimize it for recruiters

-   Align it with your LinkedIn project description
