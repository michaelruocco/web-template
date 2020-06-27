# Web Template

This project is a web application used to maintain a simple customer entity.

It provides a web front end for managing customers as well as RESTful endpoints
to perform the same operations. Swagger is used to provide executable documentation
for all of the restful endpoints, and they are tested using cucumber.

## Testing the application

In order to test the application once you have cloned the repo you can
run the following command:

```
gradlew cucumber
```

## Running the application

In order to test the application once you have cloned the repo you can
run the following command:

```
gradlew bootRun
```

Upon running this task the script will do the following:

* Start up an embedded MySql instance on your local machine
* Start up the spring boot application (which internally will run flyway against the mysql database)

Once this is complete you will be able to view the application [here](http://localhost:8080). 
This will show you the web interface that you can use to create, update 
and delete customers. The RESTful endpoints for performing the same operations
can be found [here](http://localhost:8080/ws/v1/customers). The link will take
you to the list of customers currently stored, for a new start up it will be an
empty array. If you want to use the swagger documentation to explore these endpoints
(recommended) you can do [here](http://localhost:8080/swagger-ui.html).