# Blogify

This project is built by Java Spring. The idea was to build a basic blogging platform. Spring Boot Blog is a web application using web technologies including:

| #1 | #2 | #3 | #4 |
| --- | --- |--- |--- |
| *Spring Boot* | *Spring Data JPA* | *Spring MVC*   | *Spring Security* |
| *Bootstrap 4* | *Gradle*          | *Thymeleaf*    | *Webjars*         |

## Requirement
- Java 8 or above
- MySQL

## Installation
- Clone project
- Copy all file in: /install/properties to a location then edit ApplicationSettings.java
```
    @PropertySource("file:/home/user/config/settings.properties")
```
- Change connection info in: /src/resources/application.properties
```
    spring.datasource.url=jdbc:mysql://localhost:8889/blogify
    spring.datasource.username=root
    spring.datasource.password=root
```
- Import database and sample data in /install/sql/schema.sql and /install/sql/data.sql
- Then just start Spring Boot in your IDE or using gradle (gradle bootRun).

:boom: **2019-apr**

- Admin account management
- Admin tag management
- Admin category management
- Ajax tag cloud
- Update database
- Change project name, package name, change logo

:boom: **2019-mar**

- Create database
- Pagination
- Admin post management
- Public articles page
- Sign in/sign out

![alt 2019-mar](https://3.bp.blogspot.com/-Mrg0DOmSmQI/XKIgRMa8H6I/AAAAAAAACnE/hS2rYjwFvkAtGT8loVi6TS5dmN0PD0DbACLcBGAs/s1600/2019-mar.png)

## Authors

**[Ngo Quoc Anh](https://github.com/ngoquocanh)**
