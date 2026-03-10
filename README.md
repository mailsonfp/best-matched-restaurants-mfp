### **Mailson Fernando Pereira - technical assessment**

### **Run the project with IntelliJ**  
Get the .zip file attached to the e-mail, import into an IDE(I used IntelliJ) and run the class
MailsonPereiraTechAssessmentApplication  
The class is in module application, package: mailson.pereira.tech.assessment  
The project will create the server on the pattern port and address: http://localhost:8082  
I also created a github repository in my personal github personal account:  
account: https://github.com/mailsonfp  
project repository: https://github.com/mailsonfp/best-matched-restaurants-mfp  
clone url: https://github.com/mailsonfp/best-matched-restaurants-mfp.git  

#### **Attention:**
#### **Ports configured for each service used in docker-compose**
#### **Postgres: port 5432**
#### **Redis: port 6379**
#### **Rabbit-mq: port 5672/15672**
#### **Application: port 8082**
#### **If you are going to build and run the image for the application or via IDE, be attention to pass the variables:**
#### **For Postgres: SPRING_DATASOURCE_URL, SPRING_DATASOURCE_USERNAME and SPRING_DATASOURCE_PASSWORD**
#### **For Redis: SPRING_RABBITMQ_HOST, SPRING_RABBITMQ_PORT, SPRING_RABBITMQ_USERNAME, SPRING_RABBITMQ_PASSWORD**
#### **For Rabbit-mq: SPRING_RABBITMQ_HOST, SPRING_RABBITMQ_PORT, SPRING_RABBITMQ_USERNAME, SPRING_RABBITMQ_PASSWORD**
#### **In the variables use the values for your environment**

### **Run the project for docker users**
The project is prepared to run with docker to mount the image or run with docker compose.  
Dockerfile:
will use gradle to build the jar with dependencies  
docker-compose.yml
will create a postgres server, a redis server, a rabbit-mq service and run the application
In the project root, run command: **docker-compose up --build** to build and run the serves and the application properly

Consume the APIs with Postman using the tech-assessment.postman_collection.json collection

### **Run the project via IDE or only use the image built by Dockerfile, be attention to pass the variables**
**For Postgres**: SPRING_DATASOURCE_URL, SPRING_DATASOURCE_USERNAME and SPRING_DATASOURCE_PASSWORD  
**For Redis**: SPRING_RABBITMQ_HOST, SPRING_RABBITMQ_PORT, SPRING_RABBITMQ_USERNAME, SPRING_RABBITMQ_PASSWORD  
**For Rabbit-mq**: SPRING_RABBITMQ_HOST, SPRING_RABBITMQ_PORT, SPRING_RABBITMQ_USERNAME, SPRING_RABBITMQ_PASSWORD  
In the variables use the values for your environment  

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

An authentication using redis was added, in order to consume the endpoints you must authenticate using the curl or the postman request "authenticate":
curl --location 'http://localhost:8082/v1/authentication/login' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=BE996B0B352CED4ACB6BF949BD0246C5' \
--data '{
"userName": "{username}",
"authorities": [
"RESTAURANT_MAINTENANCE",
"CUISINE_MAINTENANCE",
"RESTAURANT_SEARCH",
"METRIC_REPORT"
]
}'
The authorities:  
- "RESTAURANT_MAINTENANCE" for restaurant crud
- "CUISINE_MAINTENANCE" for cuisine crud
- "RESTAURANT_SEARCH" for search and get the best matched restaurants
- "METRIC_REPORT" for metric reports
 
To test the solution, I used to ways:  
swagger: http://localhost:8082/swagger-ui/index.html#/  
&emsp; all the explanations about the endpoints are described in swagger and is possible to use swagger ui for test  

postman: collection with all endpoints created  
&emsp;location: ..files/postman-collection/  
&emsp;file: tech-assessment.postman_collection.json - 2.1 Postman Collection  
API to find the best matched restaurants:  
http://localhost:8082/v1/restaurants/search - check on swagger for search parameters information.

I also created test classes for the services classes  
location: module service src/test/kotlin, package com.mailson.pereira.tech.assessment.service

### **About the solution**
I used kotlin and spring, which are I used daily in my actual job  
About security, I did not add any security development to facilitate tests but in real application must have security enabled  
In the location  ..files/flows I used draw.io to show a possibility development considering security issues.  
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