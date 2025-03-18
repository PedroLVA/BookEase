BookEase - SpringBoot Backend for Ticket and Movie Management
Overview
BookEase is a SpringBoot-based backend application designed to manage tickets and movies. This project was developed for learning purposes, focusing on implementing more complex business logic within a monolithic architecture using the layered architecture pattern.

Features
Movie Management: CRUD operations for movies.

Ticket Management: CRUD operations for tickets.

Layered Architecture: The application is structured into layers for better separation of concerns.

Complex Business Logic: Implements more complex business rules and validations.

Technologies Used
Spring Boot: Core framework for building the application.

Spring Data JPA: For data access and persistence.

Hibernate: As the JPA provider.

MySQL: Database for storing movie and ticket information.

Maven: Build tool for dependency management and project build.

Lombok: For reducing boilerplate code.

Project Structure
The project follows a layered architecture, with the following main packages:

controller: Contains the REST controllers to handle HTTP requests.

service: Implements the business logic and acts as a bridge between the controller and repository layers.

repository: Interfaces for data access, extending JpaRepository.

model: Entities representing the data model.

dto: Data Transfer Objects for transferring data between layers.

exception: Custom exceptions and global exception handling.

config: Configuration classes for the application.
