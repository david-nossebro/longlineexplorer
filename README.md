# Long Line Explorer

An extremely useful tool if you want to list the 10 bus lines in the Stockholm area with the most stops, otherwise it is a less useful tool. 

It is a web application and a REST API built with React and Spring Boot. To be able to run the application you will need to have java and node.js installed.

To start the API, go into the `BE` folder and run the following command:

```
./mvnw spring-boot:run 
```

To start the frontend, go into the FE folder an run the following commands:

```
npm ci
npm start
```

Once started the frontend can be found on this url:
[http://localhost:3000](http://localhost:3000)

The API is located on port 8080 and swagger documentation can be explored here:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)