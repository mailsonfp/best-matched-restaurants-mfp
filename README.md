### **Mailson Fernando Pereira - technical assessment**

### **Run the project with IntelliJ**  
Get the .zip file attached to the e-mail, import into an IDE(I used IntelliJ) and run the class
MailsonPereiraTechAssessmentApplication  
The class is in module application, package: mailson.pereira.tech.assessment  
The project will create the server on the pattern port and address: http://localhost:8080  
I also created a github repository in my personal github personal account:  
account: https://github.com/mailsonfp  
project repository: https://github.com/mailsonfp/best-matched-restaurants-mfp  
clone url: https://github.com/mailsonfp/best-matched-restaurants-mfp.git  

### **Run the project for docker users**
The project is prepared to run with docker to mount the image or run with docker compose.  
Dockerfile:
will use gradle to build the jar with dependencies  
docker-compose.yml
will create a postgres server
start the application with variables SPRING_DATASOURCE_URL, SPRING_DATASOURCE_USERNAME and SPRING_DATASOURCE_PASSWORD  

In the project root, run: docker compose up  
Consume the APIs with Postman using the tech-assessment.postman_collection.json collection

#### **Attention:**
#### **Both Postgres and the project are set to the default port(5432,8080) be attention with ports already in use**
#### **If you are going to build and run the image, be attention to pass the variables SPRING_DATASOURCE_URL, SPRING_DATASOURCE_USERNAME and SPRING_DATASOURCE_PASSWORD to connect with database properly.**

### **Database**
The application is ready to connect with postgres using the variables below:  
SPRING_DATASOURCE_URL, SPRING_DATASOURCE_USERNAME and SPRING_DATASOURCE_PASSWORD  

About the database I also used Flyway as migration tool, because facilitates insertion of data,
so, when run the project for the first time, tables and data will be provided automatically.  

flyway directory: classpath:db/migration  
V1__create_tables.sql - ddl create for tables  
V2__insert_data.sql - dml inserts for the table
V3__alter_table_set_identity_and_value.sql - ddl to set identity and update the value for table cuisine
&emsp;I just get the data from the .csv files, turned them into insert script and create the migration file.  

### **Testing the solution**
Once the project is running, like the previous explanation, the database must have all the restaurants and cuisines  
To test the solution, I used to ways:  
swagger: http://localhost:8080/swagger-ui/index.html#/  
&emsp; all the explanations about the endpoints are described in swagger and is possible to use swagger ui for test  
postman: collection with all endpoints created  
&emsp;location: ..files/postman-collection/  
&emsp;file: tech-assessment.postman_collection.json - 2.1 Postman Collection  
API to find the best matched restaurants:  
http://localhost:8080/v1/restaurants/search - with the request parameters as description in the brief

I also created test classes for the services classes  
location: module service src/test/kotlin, package com.mailson.pereira.tech.assessment.service

### **About the solution**
I used kotlin and spring, which are I used daily in my actual job  
About security, I did not add any security development to facilitate tests but in real application must have security enabled  
In the location  ..files/flows i used draw.io to show a possibility development considering security issues.  
I also make a visual diagram from the basic search flow.  
I created a simple CRUD for cuisine and restaurants to help understand and think in the search flow.  
I didn't create any update path because didn't make sense at moment or add any advantage for the search flow.  
About the search, I used Specification from JPA with criteria because is clear to understand the search params
and how they work in the SQL sentence that will be run in the database rather than hard coded SQL sentences and criteria use the builder pattern.  
I created an ExceptionHandler to centralize the exception treatment and create a pattern return in case of any error
occurs in the request or process.
I used Clean Architecture principles, is a structure that I use daily and in my personal opinion, facilitates the
comprehension of the project.  

About the search function I assumed that at least only one parameter needs to be sent in the request  
I also assumed that restaurant name and cuisine name only will be considered if any value is present, so, no validation
needed.  
The number parameters will be validated if non-null value is present  
In cuisineName and restaurant name ignore case was added to the search