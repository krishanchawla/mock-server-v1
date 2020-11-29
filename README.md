<h1 align="center">
  Mock<b>Server</b> v1.0
</h1>

<h3 align="center">
Mocking Server Designed to stub API's quickly & easily without coding.
</h3>

<p align="center">
<a href="https://app.codacy.com/project/badge/Grade/6c26825fe51d45b88d540621fa1fb3ce"><img
alt="Code Quality Status"
src="https://app.codacy.com/project/badge/Grade/6c26825fe51d45b88d540621fa1fb3ce"></a>
<a href="https://badge.buildkite.com/89fd29118b2b24957feacf08660308feede6f203301ec0eefa.svg"><img
alt="Build Status"
src="https://badge.buildkite.com/89fd29118b2b24957feacf08660308feede6f203301ec0eefa.svg"></a>
<a href="https://img.shields.io/github/last-commit/krishanchawla/mock-server-v1"><img
alt="Last Commit"
src="https://img.shields.io/github/last-commit/krishanchawla/mock-server-v1"></a>
<a href="https://img.shields.io/badge/License-GPLv3-blue.svg"><img
alt="License"
src="https://img.shields.io/badge/License-GPLv3-blue.svg"></a>
</p>

<p align="center">
<img src="https://user-images.githubusercontent.com/28475979/100535959-672afc00-3243-11eb-8e43-e733dde54b48.JPG" width="550">
</p>

## Overview

- **Simplistic UI.** Simplistic UI Design to help quickly create Stubs for API's.
- **Codeless Stubbing.** There is no coding required to create a mock API. Simply click on Add New Mock & get started.
- **Addtional Controls on Stubs** Want to test negative scenerios? Each designed Stubs contains switches to test scenerios like failure or delayed responses.
- **Save Requests Internally** No need to pass on files for Stubbed Requests Internally. All the created Stubs are stored in database locally.

## Pre-requisites

- **Java Development Kit** ([JDK8](https://www.oracle.com/in/java/technologies/javase/javase-jdk8-downloads.html))
- **Database** The Mock Server requires a database to store the mock Requests. The server can run with Mysql Database or H2 Database. If you are looking to run using Mysql database, mysql server is required.

### Installation

The Mock Server comes in two different packages, JAR & WAR. While the Tomcat is already embeded in the project, one can use their own tomcat server to deploy the project.

- [x] Download the Latest Release ([Latest Releases](https://github.com/krishanchawla/mock-server-v1/releases/))
- [x] Place the download JAR / WAR to the directory from where you want to run the server.
- [x] Create & Configure the application.properties within the same directory.

```
## Server Configuration ##
server.port=9000
server.url=http://localhost:9000/

## DataSource Configuration - H2 ##
#spring.datasource.url=jdbc:h2:file:{path/to/create/db/file}
#spring.datasource.driverClassName=org.h2.Driver
#spring.datasource.username={username}
#spring.datasource.password={password}
#spring.jpa.database-platform=org.hibernate.dialect.H2Dialect


## DataSource Configuration - MYSQL ##

spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://{host}:{port}/{dbname}
spring.datasource.username={username}
spring.datasource.password={password}
spring.datasource.initialization-mode=always

# Hibernate Configuration #
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

```

- [x] Execute the following command:

- Linux
```sh
nohup java -jar MockServer-1.0.0.jar &
```

- Windows
```sh
java -jar MockServer-1.0.0.jar
```

The Mock Server will be started on the port provided in application.properties file.

⚠️ While it is the most recent codebase, there may be issues! In such cases, please raise an issue. It is also not recommended to run this as root.
