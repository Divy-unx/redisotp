# Redis OTP Service

A lightweight Spring Boot REST API for generating and verifying one-time passwords (OTPs) backed by Redis. OTPs are stored with a short time-to-live, making Redis a natural fit for temporary authentication codes and expiry-based validation.

## Features

- Generate a 4-digit OTP for a phone number
- Store OTPs in Redis with a 60-second expiry
- Verify submitted OTPs against Redis
- Delete OTPs after successful verification
- Simple REST endpoints for integration and testing

## Tech Stack

- Java 17
- Spring Boot 3.5.14
- Spring Web
- Spring Data Redis
- Maven
- Redis

## Project Structure

```text
redisotp/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── src
    ├── main
    │   ├── java/com/divyanshu/redisotp
    │   │   ├── RedisOtpApplication.java
    │   │   ├── controller/OTPController.java
    │   │   └── service/OTPService.java
    │   └── resources/application.properties
    └── test/java/com/divyanshu/redisotp
        └── RedisotpApplicationTests.java
```

## Prerequisites

Make sure the following are installed:

- Java 17 or later
- Redis server
- Maven, or use the included Maven wrapper

## Configuration

The application configuration is defined in `src/main/resources/application.properties`:

```properties
server.port=8081
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

By default, the API runs on port `8081` and connects to Redis at `localhost:6379`.

## Running Redis

If Redis is installed locally, start it with:

```bash
redis-server
```

You can verify Redis is running with:

```bash
redis-cli ping
```

Expected response:

```text
PONG
```

## Running the Application

Use the Maven wrapper:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The application will be available at:

```text
http://localhost:8081
```

## API Endpoints

### Generate OTP

Generates a 4-digit OTP for the given phone number and stores it in Redis for 60 seconds.

```http
POST /otp/generate?phone={phone}
```

Example:

```bash
curl -X POST "http://localhost:8081/otp/generate?phone=9876543210"
```

Example response:--

```text
1234
```

### Verify OTP

Verifies the OTP submitted for the given phone number.

```http
POST /otp/verify?phone={phone}&otp={otp}
```

Example:

```bash
curl -X POST "http://localhost:8081/otp/verify?phone=9876543210&otp=1234"
```

Possible responses:

```text
OTP Verified Successfully
```

```text
Invalid OTP
```

```text
OTP Expired
```

## Running Tests

```bash
./mvnw test
```

On Windows:

```bash
mvnw.cmd test
```

## How It Works

1. A client calls `/otp/generate` with a phone number.
2. The service generates a random 4-digit OTP.
3. The OTP is stored in Redis using the phone number as the key.
4. Redis automatically expires the OTP after 60 seconds.
5. A client calls `/otp/verify` with the phone number and OTP.
6. If the OTP matches, it is deleted from Redis and verification succeeds.

## Notes

- This project currently returns the generated OTP directly in the API response. That is useful for development and testing, but production systems should send OTPs through an SMS, email, or messaging provider instead.
- The current implementation uses the phone number directly as the Redis key. For larger applications, consider adding a namespace such as `otp:{phone}`.
- For production use, add rate limiting, request validation, secure Redis configuration, structured API responses, and logging.

## License

No license has been specified yet.
