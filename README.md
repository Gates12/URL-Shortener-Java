# URL Shortener API

A simple and efficient URL shortener service built using Spring Boot. This service allows users to shorten URLs and retrieve access count statistics for the shortened URLs. The system also includes an expiration mechanism for the generated short URLs.

## Table of Contents

- [Introduction](#introduction)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [API Endpoints](#api-endpoints)
  - [POST /api/shorten](#post-apishorten)
  - [GET /api/r/{shortKey}](#get-apirshortkey)
  - [GET /api/access-count/{shortKey}](#get-apiaccess-countshortkey)
- [Project Setup](#project-setup)
  - [Prerequisites](#prerequisites)
  - [Running the Application](#running-the-application)
  - [Testing the Application](#testing-the-application)

## Introduction

The **URL Shortener API** is designed to convert long URLs into short, user-friendly links. This service provides an easy-to-use interface for shortening URLs and also tracks the access count for each shortened link. The service ensures that URLs are valid and provides an expiration mechanism for short links, which automatically cleans up expired URLs.

## Technologies Used

- **Spring Boot** for building the REST API.
- **Spring Web** for HTTP request handling.
- **Swagger/OpenAPI** for API documentation.
- **Lombok** for reducing boilerplate code.
- **Java** for programming logic.
- **Maven** for dependency management.

## Features

- **URL Shortening:** Convert long URLs into short, shareable links.
- **Access Count Tracking:** Track how many times a shortened URL has been accessed.
- **Expiration Mechanism:** Automatically removes expired URLs after 24 hours.
- **Swagger Documentation:** Automatically generated interactive API documentation.

## API Endpoints
    - **POST /api/shorten**: Shortens long URLs.
    - **GET /api/r/{shortKey}**: Redirects to the original URL.
    - **GET /api/access-count/{shortKey}**: Retrieves the access count of a shortened URL.

## Project Setup

To get the project up and running, follow these simple steps:

### Prerequisites

Before you begin setting up the application, make sure you have the following software installed:

- **Java 17 or later**: The application is built with Java 17, so you need to have it installed on your system. You can download Java from [Oracle's website](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) or use OpenJDK.
- **Maven**: Maven is used for managing dependencies and building the project. You can download Maven from [here](https://maven.apache.org/download.cgi).
- **IDE (Optional)**: If you want to make changes to the code or simply explore the project, an IDE such as [IntelliJ IDEA](https://www.jetbrains.com/idea/) or [Eclipse](https://www.eclipse.org/downloads/) would be helpful.

### Running the Application

Once you have the prerequisites installed, you can run the application by following these steps:

1. **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/url-shortener.git
    ```
2. **Navigate to the project directory**:
    ```bash
    cd url-shortener
    ```
3. **Build and run the application** using Maven:
    ```bash
    mvn spring-boot:run
    ```

4. Once the application is running, you can access it at `http://localhost:8080`.

## Testing the Application

To test the application, you can use tools like **Postman** or **Swagger** to make HTTP requests to the API. Below are the request/response formats for each API endpoint.

### 1. **Shorten a URL**

- **Endpoint:** `POST /api/shorten`
- **Description:** This endpoint takes a long URL and returns a shortened URL.
- **Request Body:**
    ```json
    {
      "longUrl": "https://example.com"
    }
    ```
    - **longUrl:** The original long URL to be shortened.

- **Response:**
    ```json
    {
      "shortUrl": "http://localhost:8080/api/r/{shortKey}",
      "shortKey": "abc123"
    }
    ```
    - **shortUrl:** The shortened URL.
    - **shortKey:** The key that can be used to retrieve the original URL.

- **Example:**
    - Request:  
    `POST http://localhost:8080/api/shorten`
    ```json
    {
      "longUrl": "https://example.com"
    }
    ```
    - Response:
    ```json
    {
      "shortUrl": "http://localhost:8080/api/r/abc123",
      "shortKey": "abc123"
    }
    ```

### 2. **Redirect to the Original URL**

- **Endpoint:** `GET /api/r/{shortKey}`
- **Description:** This endpoint redirects to the original URL associated with the given short key.
- **Request Parameters:**
    - **shortKey:** The key for the shortened URL (e.g., `abc123`).

- **Response:**  
  The user will be redirected to the original URL (e.g., `https://example.com`).

- **Example:**
    - Request:  
    `GET http://localhost:8080/api/r/abc123`
    - Response:  
    The request will redirect to `https://example.com`.

### 3. **Get the Access Count for a Shortened URL**

- **Endpoint:** `GET /api/access-count/{shortKey}`
- **Description:** This endpoint retrieves the number of times the shortened URL has been accessed.
- **Request Parameters:**
    - **shortKey:** The key for the shortened URL.

- **Response:**
    ```json
    {
      "shortKey": "abc123",
      "accessCount": 5
    }
    ```
    - **shortKey:** The key of the shortened URL.
    - **accessCount:** The number of times the shortened URL has been accessed.

- **Example:**
    - Request:  
    `GET http://localhost:8080/api/access-count/abc123`
    - Response:
    ```json
    {
      "shortKey": "abc123",
      "accessCount": 5
    }
    ```

---

