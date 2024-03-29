# Blogify

This project is built by Java Spring. The idea was to build a basic blogging platform. Spring Boot Blog is a web application using web technologies including:

| #1 | #2 | #3 | #4 |
| --- | --- |--- |--- |
| *Spring Boot* | *Spring Data JPA* | *Spring MVC*   | *Spring Security* |
| *Bootstrap 4* | *Gradle*          | *Thymeleaf*    | *Lombok*         |

## Requirement
- Java 8 or above
- MySQL

## Installation
- Clone project
- Copy all file in: /install/properties to a location then edit ApplicationSettings.java
```
    @PropertySource("file:/home/user/config/settings.properties")
```
- Change connection info and something else in: /src/resources/application.properties
```
    spring.datasource.url=jdbc:mysql://localhost:8889/blogify
    spring.datasource.username=
    spring.datasource.password=
    
    spring.mail.username=
    spring.mail.password=
```
- Change logback folder in: /src/resources/logback.xml
```
     <file>{folder}/quopri.log</file>
     <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <file>{folder}/quopri.log</file>
            ...
     </appender>
```
- Import database and sample data in /install/sql/schema.sql and /install/sql/data.sql
- Then just start Spring Boot in your IDE or using gradle (gradle bootRun).

## Changes
:boom: **2019-june**

- Contact us
- Forgot password
- Reset password
- Validator
- Add logback
- Input tags

![alt 2019-apr](https://3.bp.blogspot.com/-rLl9C3fOURE/XPk-LMkXW0I/AAAAAAAACrw/bCu4VGEc2PYxe-g-p9UC61I9aGp-qOZ5wCPcBGAYYCw/s1600/2019-june.jpg)

:boom: **2019-may**

- Update database
- Create and update article thumbnail
- Update ajax tag cloud
- Update post type and status
- Add page title
- Cross Site Request Forgery (CSRF)
- Sign up

![alt 2019-apr](https://3.bp.blogspot.com/-f9yp4nx4nHI/XPk-LNwU4FI/AAAAAAAACrs/8omlxHNHXTUuhBG7pWifiARD4hXltOmKQCPcBGAYYCw/s1600/2019-may.jpg)

:boom: **2019-apr**

- Admin account management
- Admin tag management
- Admin category management
- Ajax tag cloud
- Update database
- Change project name, package name, change logo

![alt 2019-apr](https://4.bp.blogspot.com/-jAUelk9ZC1M/XL8RvaZnULI/AAAAAAAACo8/xo2gKP0F-z0pWw5vlK4HhcyRhL_itjPcwCPcBGAYYCw/s1600/2019-apr.png)

:boom: **2019-mar**

- Create database
- Pagination
- Admin post management
- Public articles page
- Sign in/sign out

![alt 2019-mar](https://3.bp.blogspot.com/-Mrg0DOmSmQI/XKIgRMa8H6I/AAAAAAAACnE/hS2rYjwFvkAtGT8loVi6TS5dmN0PD0DbACLcBGAs/s1600/2019-mar.png)

## Authors

**[Ngo Quoc Anh](https://github.com/ngoquocanh)**
