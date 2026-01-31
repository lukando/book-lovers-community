# 📚 Book Lovers Community (BLC)

> A modern web application for book enthusiasts to manage their virtual libraries, track reading progress, and share reviews. Built with Java & Spring Boot.

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-ForestGreen?style=for-the-badge&logo=thymeleaf&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)

## 💡 About The Project

**Book Lovers Community** is a multi-module monolithic application designed to simulate a social platform for readers. It allows users to maintain personal bookshelves, rate books, and import data from external sources.

The project was developed to demonstrate proficiency in the **Spring Ecosystem**, clean architecture principles, and integration with external APIs. It combines a server-side rendered frontend (MVC) with a RESTful API architecture.

### Key Features

* **📖 Virtual Library Management:**
    * Users can categorize books into dynamic shelves: *"Want to Read"*, *"Reading"*, *"Read"*.
    * Smart logic automatically moves books between shelves (e.g., marking as "Read" removes it from "Want to Read").
* **🤖 External Data Integration:**
    * Automated book imports from **OpenLibrary API** using `RestTemplate`.
    * Scheduled tasks (`@Scheduled`) to keep the library database updated in the background.
* **📊 Analytics & Reporting:**
    * Community statistics calculated using **raw JDBC** for performance optimization (bypassing Hibernate overhead for aggregations).
    * Dynamic rating system using Spring Expression Language (SpEL) calculations.
* **👤 User Profile & Security:**
    * Full registration and authentication flow (Spring Security, BCrypt).
    * Profile management with avatar support.
    * **Data Backup:** Users can download their entire library data as a JSON file (implemented with Jackson).
* **💬 Social Interaction:**
    * Review and rating system.
    * Community-wide shelf statistics for each book.

## 🏗 Architecture & Tech Stack

The project follows a **Maven Multi-Module** architecture to enforce separation of concerns:

* **`blc-data`**: Persistence layer (JPA Entities, Repositories).
* **`blc-services`**: Business logic, DTOs, External API clients.
* **`blc-web`**: Controllers (MVC & REST), Exception Handling, Security Config, Thymeleaf views.

### Technologies Used

* **Backend:** Java 17, Spring Boot 3 (Web, Data JPA, Security, Validation)
* **Frontend:** Thymeleaf, HTML5, CSS3
* **Database:** H2 Database (In-Memory for development convenience)
* **Testing:** JUnit 5, Mockito, Spring Boot Test, MockMvc
* **Tools:** Maven, Lombok, Swagger/OpenAPI

## 🚀 Getting Started

To run this project locally, you need JDK 17+ and Maven installed.

### Installation

1.  **Clone the repository**
    ```bash
    git clone [https://github.com/YourUsername/book-lovers-community.git](https://github.com/YourUsername/book-lovers-community.git)
    cd book-lovers-community
    ```

2.  **Build the project**
    ```bash
    ./mvnw clean install
    ```

3.  **Run the application**
    ```bash
    cd blc-web
    ./mvnw spring-boot:run
    ```

4.  **Access the app**
    * Application: [http://localhost:8080](http://localhost:8080)
    * API Documentation (Swagger): [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
    * H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

## 🧪 Testing

The project maintains a high level of code coverage using various testing strategies:
* **Unit Tests:** Business logic verification using **Mockito**.
* **Integration Tests:** End-to-end flow verification using `@SpringBootTest` and `MockMvc`.
* **Repository Tests:** Verification of custom JPQL and JDBC queries.

## 👤 Autor
**Łukasz Święcicki**
   * 🌐 **LinkedIn:** [My Profile](https://www.linkedin.com/in/Lukasz-Swiecicki/)

---
*This project was created for educational purposes.*
