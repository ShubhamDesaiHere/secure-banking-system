# Secure Banking System

A backend banking application built using Java and Spring Boot that provides secure user registration, authentication, customer account management, and banking transactions.

## Features

- User registration
- Automatic customer creation after registration
- Automatic savings account creation for every customer
- JWT-based authentication
- Role-based authorization
- Admin and Customer roles
- Admin can view users, customers, and accounts
- Deposit money
- Withdraw money
- Transfer money between accounts
- Transaction history
- Account balance management
- Customer account ownership validation
- Password encryption using BCrypt
- MySQL database integration
- RESTful APIs

## Tech Stack

### Backend
- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- Spring Security
- JWT

### Database
- MySQL

### Build Tool
- Maven

### Testing
- Postman

## Project Architecture

```text
Client / Postman
       |
       v
REST Controllers
       |
       v
Service Layer
       |
       v
Repository Layer
       |
       v
MySQL Database