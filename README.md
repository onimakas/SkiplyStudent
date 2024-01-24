## Welcome to Skiply Student
Skiply Student is a microservice created with latest version of Springboot which helps to manage student data.
With the help of this we can do all the CRUD operations for Student Management.

This project uses an H2 in-memory database. 
The database schema is automatically created based on the entity classes and data is automatically initialized as per the data.sql under resources folder. 
You can find the database configuration in the application.properties file.

To run this project, make sure you have the following prerequisites installed on your machine:

1.Java Development Kit (JDK) 21.0.2
2.Install IntelliJ IDEA(latest version)
3.Install Postman.
4.git clone https://github.com/onimakas/SkiplyStudent.git or download it into zip and unzip it to your local.
5.Open project in IntelliJ IDEA and run locally.
6.The application should now be up and running on http://localhost:8080/api/v1/students

The Open API documentation links are as follows:
Swagger links - 
http://localhost:8080/swagger-ui/index.html#/

Once the application is running, you can access the following endpoints:

GET[/students] (http://localhost:8080/api/v1/students) - Retrieves a list of all students.
GET[/students/{id}] (http://localhost:8080/api/v1/students/{id}) - Retrieves a specific student by ID.
POST[/students] (http://localhost:8080/api/v1/students) - Creates a new student.
PATCH[/students/{id}] (http://localhost:8080/api/v1/students/{id}) - Updates an existing student.
DELETE[/students/{id}] (http://localhost:8080/api/v1/students/{id}) - Deletes a student by ID.



