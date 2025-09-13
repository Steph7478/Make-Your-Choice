# 🎮 Make Your Choice

📋 **Project Overview**
Make Your Choice is a **Java Spring Boot** application developed as a **learning project** to practice and apply **Domain-Driven Design (DDD)**, **Clean Architecture**, and **Test-Driven Development (TDD)**.
It simulates a short choice-based game where dialogs and choices are stored and retrieved through a layered architecture that separates **domain**, **application use cases**, **infrastructure**, and **presentation**.

---

## 🚧 Project Status

This project is **actively under development** and serves as a **hands-on playground** to improve skills in:

* Java backend development
* Software architecture patterns (DDD, Clean Architecture)
* Unit and integration testing with TDD
* Repository and controller design with Spring Boot
* Docker usage

---

## ✨ Features

* 🗂️ **Domain Entities**: `DialogEntity` and `ChoiceEntity` model the core of the game.
* 🧩 **Use Cases**: Application logic encapsulated, such as *Get All Choices*, *Get Choice By Id*, *Get Dialog By Code*, etc.
* 🏛️ **DDD & Clean Architecture**: Clear separation of responsibilities (domain, application, infrastructure, presentation).
* 🧪 **TDD Workflow**: Unit and integration tests guiding implementation.
* ⚙️ **Environment-based configuration**: Customize application name, database, and JPA strategy via `.env`.
* 📡 **REST Controllers**: Endpoints for retrieving choices and dialogs.
* 🐳 **Docker Ready**: Build and run the application easily inside a container.
* 🔒 **Spring Security**: Protects endpoints with basic authorization rules, applies security headers, and sets the stage for future authentication mechanisms.

---

## 🛠️ Technologies

* **Java 17+**
* **Spring Boot 3.x**
* **Maven**
* **JUnit 5**
* **Dotenv Java**
* **Docker**
* **Spring Security**

---

## ⚙️ Installation and Running

### Prerequisites

* Java 17+ (if not using Docker)
* Maven 3.8+ (if not using Docker)
* Docker (optional, but recommended for isolated runs)

### Steps

Clone the repository:

```bash
git clone https://github.com/Steph7478/MakeYourChoice.git
cd MakeYourChoice
```

Create your own `.env` file and configure environment variables:

```env
SPRING_APPLICATION_NAME=
SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=
SPRING_JPA_HIBERNATE_DDL_AUTO=
```

### Running locally via Maven:

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

### Running tests:

```bash
./mvnw test
```

### 🐳 Docker

You can run the application entirely inside a Docker container without installing Java or Maven locally.

Build the Docker image:

```bash
docker build -t make-your-choice .
```

Run the application:

```bash
docker run --rm -p 8080:8080 make-your-choice
```

The application will be available at [http://localhost:8080](http://localhost:8080)

Port 8080 is exposed from the container to your machine.

Run tests inside the container (optional):

```bash
docker run --rm make-your-choice mvn test
```

Executes all tests inside the container.
No need to have Maven or Java installed locally.

### 📝 Operation Scripts

The project includes a main script `ops.sh` to simplify running utilities:

```bash
./ops.sh debug       # Runs debug.sh
./ops.sh endpoints   # Runs test-endpoints.sh
```

* `debug.sh`: Starts the application in debug mode.
* `test-endpoints.sh`: Tests application endpoints using curl.

---

## 🛡️ Security Notes

The project integrates **Spring Security** to:

* Protect REST endpoints with basic authorization rules (public vs private endpoints).
* Deny access to unspecified routes by default.
* Apply security headers to improve protection against common attacks (CSP, HSTS, X-Frame-Options, Referrer Policy).
* Allow future integration of login mechanisms such as JWT, OAuth2, or custom authentication.

Currently, the project **does not implement user login or authentication**; private endpoints are placeholders for future secured routes.

---

## 🛡️ Disclaimer

This project is for learning and practice purposes only.
