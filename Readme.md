# Service Poller

![Services Dashboard](UI_screenshot.png?raw=true "Services Dashboard")

## Table of Contents
- [Intro](#intro)
- [Features](#features)
- [API](#api)
- [Installation](#installation)
- [License](#license)

## Intro
Service Poller is an application that monitors your services on regular intervals and reports their availability. You can visualize the availability of your services as well as add, edit and delete them.
This github repository contains the frontend application built with React and the backend application (API)
built with Spring Boot and Mysql.

## Features
- __CRUD operations:__ Full create/update/delete functionality for services
- __Maintain State:__ Added services are kept when the server is restarted
- __Save Timestamps:__ Present whenever a service was added and when the last change was
made
- __Realtime Updates:__ The results from the poller are automaticaly shown to the user without a page reload
- __Animated Notifications:__ Informative notifications with nice looking animations on add/remove services
- __Poller Protection:__ The poller is protected from misbehaving services (for example services answering really slowly)
- __Url Validation:__ Validate Urls ("sdgf" is not a valid service url)

## API
The API is documented with the OpenAPI 3 specification and can be viewed via swagger ui at the following url
provided you are running the backend api application localy on port 8080

http://localhost:8080/swagger-ui/index.html?url=http://localhost:8080/actuator/openapi

![Api Documentation](api_docs_screenshot.png?raw=true "API Documentation")

## Installation

### Prerequisites
- Docker
- Node lts
- npm
- Java 11
- Maven 3.6.3
- Ports: 3000, 8080, 3306

### Clone the repository with 
```bash
git clone https://github.com/aristotelisch/service-poller.git
git checkout main
```

### Frontend
Change to the frontend project directory
```bash
cd frontend
npm install
npm start
```

### Backend
Navigate to the backend project directory from the repository root

```bash
cd backend
# Start middleware services with docker (Mysql database)
docker compose up -d
# Start the spring boot application with the dev profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## License
### MIT

Copyright © 2021 Aristotelis Christou

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

<div align="center">
  <sub>Service Status Monitoring application. Built with ❤︎ by
  <a href="https://twitter.com/aristotelis_ch">Aristotelis Christou
</div>
