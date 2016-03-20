# Web Template

This project is a web application used to maintain a simple customer entity.
It is designed to demonstrate some of the principals that should be used
when constructing a web application.

It provides a web front end for managing customers as well as RESTful endpoints
to perform the same operations. Swagger is used to provide executable documentation
for all of the restful endpoints, and they are fulled tested using cucumber.

## Testing the application

In order to test the application once you have cloned the repo you should
running the following:

```
gradlew cucumberSetup cucumber
```

## Running the application

In order to test the application once you have cloned the repo you should
running the following:

```
gradlew startWebApp
```

Upon running this task the script will do the following (aside from the
usual compilation etc.)

* Start up an embedded MySql instance on your local machine
* Use flyway to configure the embedded MySql instance
* Start up an embedded Tomcat instance on your local machine
* Deploy the war file into the Tomcat instance and run your application

Once this is complete you will be able to view the application [here](http://localhost:8080/web-template). 
This will show you the web interface that you can use to create, update 
and delete customers. The RESTful endpoints for performing the same operations
can be found [here](http://localhost:8080/web-template/ws/v1/customers).
If you want to use the swagger documentation to explore these endpoints
(recommended) you can do [here](http://localhost:8080/web-template/swagger-ui.html).

You should also be aware that because the application is using an embedded
MySql instance, the data that you create will not be persisted after the
application is stopped. This is because it is intended to be a quick and easy
demo that can be start up and run with minimal effort. If you wish to persist
data after the application is stopped then you will need to amend the jdbc
properties found in src/main/resources/local.properties to point at your own
mysql instance. Once you have amended the properties you will need to run

```
gradlew clean build flywayMigrate startTomcat
```

This will configure the database in your own mysql instance and then start
the application. Flyway does not create the actual database itself so you will
need to create an empty database called template in order for flyway to work
correctly.