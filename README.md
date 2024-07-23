# Batch Insert Performance

This project demonstrates the performance of different approaches to inserting data into a PostgreSQL database using various technologies: JDBC, Hibernate, and Spring JPA. It includes examples for comparing the performance of batch inserts using different database interaction methods.

## Table of Contents

- [Technologies](#technologies)
- [Setup and Run](#setup-and-run)
- [Application Configuration](#application-configuration)
- [Running Performance Tests](#running-performance-tests)
- [Performance Notes](#performance-notes)
- [Contributors](#contributors)

## Technologies

The project uses the following technologies:
- **JDBC**: The standard approach for interacting with the database via Java.
- **Hibernate**: An ORM framework for database interactions.
- **Spring JPA**: A wrapper for JPA that simplifies working with Hibernate and other JPA providers.

## Setup and Run

1. **Clone the repository:**

    ```bash
    git clone https://github.com/avecoss/batch-insert-performance.git
    cd batch-insert
    ```
2. **Starting a PostgreSQL database**

   Running a PostgreSQL container
   ```bash
   docker run -d \
    --name postgres-db \
    -e POSTGRES_DB="your_db_name" \
    -e POSTGRES_USER="your_username" \
    -e POSTGRES_PASSWORD="your_password" \
    -p "5433:5432" \
    --rm \
    postgres:latest
      ```
   Another way is build and run the service using Docker Compose: `compose.yml`
      ```bash
      docker-compose up --build
      ```
    
3. **Set up dependencies:**

    To run performance tests for different data insertion methods:
    1. Run the application with the `jdbc` profile:

      ```bash
      mvn spring-boot:run -Dspring-boot.run.profiles=jdbc
      ```
   2. Run the application with `jpa` profile:

     ```bash
     mvn spring-boot:run -Dspring-boot.run.profiles=jpa
     ```
   3. Run the application with `hibernate` profile:

     ```bash
     mvn spring-boot:run -Dspring-boot.run.profiles=hibernate
     ```
   Another way to set the desired profile in `application.yml`
    ```yml
   spring:
        profiles:
            active: jdbc # specify the active profile (jdbc, jpa, hibernate)
   ```

## Application Configuration

Configure database connection parameters in `application.yml`:

```yaml
spring:
    application:
        name: BatchInsert

    profiles:
        active: jdbc # specify the active profile (jdbc, jpa, hibernate)

    datasource:
        url: jdbc:postgresql://localhost:5433/your_db_name
        username: your_username
        password: your_password
        driver-class-name: org.postgresql.Driver

    flyway:
        locations: db/migration
        schemas:
            - public
        enabled: true

bath:
    amount: 100 # enter the required number
    csv-file-path: "src/main/resources/csv/file.csv"
```
## Performance Notes
**JDBC:** Often demonstrates the best performance for batch inserts as it operates closer to the database level.

**Spring JPA:** Provides advantages through transaction management and simplification of entity handling.

**Hibernate:** May incur additional overhead due to caching and session management.

Testing these approaches will allow you to compare the performance of different data insertion methods and choose the most suitable one for your needs.

## Contributors
- [avexcoss](https://github.com/avecoss)