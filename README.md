# Welcome to Skiply Student

## Overview
Skiply Student is a microservice created with latest version of Springboot which helps to manage student data.
With the help of this we can do all the CRUD operations for Student Management.

## DB configuration
This project uses an H2 in-memory database.
The database schema is automatically created based on the entity classes and data is automatically initialized as per the data.sql under resources folder.
You can find the database configuration in the application.properties file.

## Setup instructions
To run this project, make sure you have the following prerequisites installed on your machine:
1. Java Development Kit (JDK) 21.0.2
2. Install IntelliJ IDEA(latest version)
3. Install Postman.
4. `git clone https://github.com/onimakas/SkiplyStudent.git` or download it into zip and unzip it to your local.
5. Open project in IntelliJ IDEA.
6. Enable Lombok Annotation processing in Intellij IDEA while building the application.
7. Run the application.
8. The application should now be up and running on http://localhost:8080/api/v1/students

## API documentation
The Open API documentation links are as follows:  
Swagger link -
http://localhost:8080/swagger-ui/index.html#/

Once the application is running, you can access the following endpoints:

1. GET Request (http://localhost:8080/api/v1/students) - Retrieves a list of all students.
2. GET Request (http://localhost:8080/api/v1/students/{id}) - Retrieves a specific student by ID.
3. POST Request (http://localhost:8080/api/v1/students) - Creates a new student.
4. PATCH Request (http://localhost:8080/api/v1/students/{id}) - Updates an existing student.
5. DELETE Request (http://localhost:8080/api/v1/students/{id}) - Deletes a student by ID.