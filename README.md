# BankWatch — ReadyAPI Automation Framework

REST API automation framework for Bank of America login services, built with Java + Maven + TestNG + REST-assured.

## Tech Stack

| Tool | Version | Purpose |
|------|---------|----------|
| Java | 11 | Language |
| Maven | 3.8+ | Build & dependency management |
| REST-assured | 5.3.2 | HTTP client & API testing |
| TestNG | 7.8.0 | Test runner & assertions |
| Jackson | 2.15.3 | JSON serialization |
| Log4j2 | 2.21.1 | Logging |
| Allure | 2.24.0 | Test reporting |

## Project Structure

```
BankWatch/
├── pom.xml
├── testng.xml                       # Full suite (smoke + regression + security)
├── testng-smoke.xml                 # Smoke suite only
└── src/
    ├── main/java/com/bankofamerica/
    │   ├── config/ConfigManager.java
    │   ├── constants/ApiConstants.java
    │   ├── models/request/LoginRequest.java
    │   ├── models/response/LoginResponse.java
    │   ├── models/response/ErrorResponse.java
    │   ├── utils/RequestBuilder.java
    │   ├── utils/ResponseValidator.java
    │   └── utils/TestDataManager.java
    └── test/
        ├── java/com/bankofamerica/
        │   ├── base/BaseTest.java
        │   └── tests/
        │       ├── LoginPositiveTests.java
        │       ├── LoginNegativeTests.java
        │       └── LoginSecurityTests.java
        └── resources/
            ├── config/config.properties
            ├── config/config-qa.properties
            └── testdata/login-testdata.json
```

## Running Tests

```bash
# Full suite
mvn clean test

# Smoke tests only
mvn clean test -Dsurefire.suiteXmlFiles=testng-smoke.xml

# QA environment
mvn clean test -Denv=qa

# Specific group
mvn clean test -Dgroups=security

# Allure report
mvn allure:report
```

## Test Suites

| Suite | Groups | Coverage |
|-------|--------|----------|
| Smoke | `smoke` | Valid login, token returned, response time |
| Regression | `regression` | All positive, negative, security tests |
| Security | `security` | Injection, security headers, rate limiting |
| Negative | `negative` | 400/401/403/415 error scenarios |

## Configuration

| Property | Default | Description |
|----------|---------|-------------|
| `base.url` | `https://secure.bankofamerica.com` | API base URL |
| `auth.endpoint` | `/auth/login` | Login endpoint |
| `timeout.seconds` | `30` | HTTP timeout |
| `max.retry.count` | `3` | Retry attempts |

## Security Test Coverage

- SQL injection in username and password fields
- XSS payload injection
- Security response headers (X-Frame-Options, HSTS, CSP, X-XSS-Protection)
- Password not echoed in response body
- HTTPS enforcement
- Rate limiting after repeated failures (429)
