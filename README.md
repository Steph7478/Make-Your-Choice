# 🎮 Make Your Choice

📋 **Project Overview**  
Make Your Choice is a Java Spring Boot application designed as a **learning project** to practice and apply **Domain-Driven Design (DDD)**, **Clean Architecture**, and **Test-Driven Development (TDD)**.  
It simulates a short choice-based game, where dialogs and choices are stored and retrieved through a layered architecture that separates **domain**, **application use cases**, **infrastructure**, and **presentation**.  

---

## 🚧 Project Status  
This project is under active development and serves as a **hands-on playground** for improving skills in:  

- Java backend development  
- Software architecture patterns (DDD, Clean Architecture)  
- Unit and integration testing with TDD  
- Repository and controller design with Spring Boot  

---

## ✨ Features  

- 🗂️ **Domain Entities**: `DialogEntity` and `ChoiceEntity` model the core of the game.  
- 🧩 **Use Cases**: Encapsulated application logic such as *Get All Choices*, *Get Choice By Id*, *Get Dialog By Code*, etc.  
- 🏛️ **DDD & Clean Architecture**: Clear separation of responsibilities (domain, application, infrastructure, presentation).  
- 🧪 **TDD Workflow**: Comprehensive unit and integration tests guiding implementation.  
- ⚙️ **Environment-based configuration**: Choose your own application name, database, and JPA strategy through `.env`.  
- 📡 **REST Controllers**: Example endpoints for retrieving choices and dialogs.  

---

## 🛠️ Technologies  

- **Java 17+**  
- **Spring Boot 3.x**  
- **Maven**  
- **JUnit 5**  
- **Dotenv Java**  

---

## ⚙️ Installation and Running  

### Prerequisites  
- Java 17+  
- Maven 3.8+  

### Steps  

Clone the repository:  

```bash
git clone https://github.com/Steph7478/MakeYourChoice.git
cd MakeYourChoice
```

Create your own `.env` file and configure the environment variables.  
You are free to choose the values depending on your needs.  

Example `.env`:  

```env
SPRING_APPLICATION_NAME=  # you can pick any name
SPRING_DATASOURCE_URL=  # or use PostgreSQL/MySQL if you prefer
SPRING_DATASOURCE_USERNAME=  # set your database username
SPRING_DATASOURCE_PASSWORD=  # set your database password
SPRING_JPA_HIBERNATE_DDL_AUTO=  #create-drop or update / validate / none
```

Run the application using Maven Wrapper:  

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

Run the test suite:  

```bash
./mvnw test
```

---

## OBS

This project is developed solely for **learning and practice purposes**.  
