# Rest API + Web Client

## What is this?

This is an example showing how we could write an API with Java/Spring and a web client with React.

## Technologies

* Rest API: Spring MVC+Data, Mongodb/JPA-postgresql, Cucumber + Jayway Restassured
* Web Client: React, Bootstrap, JQuery, Selenium + Cucumber

## How to use it?

Prerequisites: 

1. MongoDB instance in your machine on default port without authentication.
2. Java 8
3. Firefox (only to run the acceptance tests)

### 1. Run Acceptance/Integration Tests
This command will run API and Web Client tests: `./gradlew acceptanceTest`

### 2. Run server
It is a Spring Boot project, so you could run: `./gradlew bootRun`

If you want to use Postgresql, you can run it with: `./gradlew bootRun -Dspring.profiles.active=jpa`

Keep in mind you need to have running a default Postgresql instance in localhost. 
Run `src/deploy/schema.sql` on `companies` database to prepare the tables.

### 3. Open Web Client

You can open [localhost:8080](http://localhost:8080/)

### 4. Call the API

You can use this cURL commands for it:

#### Create a company (you get an ID for the new resource in Location header in the response).

Keep in mind these fields are required: `name`, `address`, `city`, `country` and `owners`.

```
curl -X POST -H "Content-Type: application/json" -d '{
    "name": "My Company 1",
    "address": "Company St, 1",
    "city": "New York",
    "country": "USA",
    "email": "myemail@mail.com",
    "phone": "55509876",
    "owners": [
      "Mr Owner 1"
    ]
}' "http://localhost:8080/companies"
```

#### Get companies

`curl -X GET -H "Accept: application/json" http://localhost:8080/companies`

#### Get company, you need to use a resourceId from the previous steps, i.e: 12345abcd

`curl -X GET -H "Accept: application/json" http://localhost:8080/companies/12345abcd`

#### Update company, it is an overwrite update, so if you don't send a field, that one will be deleted.

You need to use a resourceId from the previous steps, i.e: 12345abcd

```
curl -X PUT -H "Content-Type: application/json" -d '{
    "name": "My Company 2",
    "address": "Company St, 2",
    "city": "London",
    "country": "UK",
    "email": "myemail@mail.com",
    "phone": "55509876",
    "owners": [
      "Mr Owner 1",
      "Mr Owner 2"
    ]
}' "http://localhost:8080/companies/12345abcd"
```

#### Add owner(s) to a company

You need to use a resourceId from the previous steps, i.e: 12345abcd

```
curl -X POST -H "Content-Type: application/json" -d '{
    "owners": [
      "Mr Owner 6"
    ]
}' "http://localhost:8080/companies/12345abcd/owners"
```

## Technical decisions

### General

A better approach could be to have a multimodule project with: API, acceptanceTest for API, Web Client, 
acceptanceTest for Web Client. The reason for the current composition is to allow me to deploy in Heroku 
at once in one machine.

### API

* There is already a [Spring Boot Data Rest](http://docs.spring.io/spring-data/rest/docs/current/reference/html) 
  component where you only need to develop the Entity, but I wanted to develop it on my own.

* It contains two database implementations (MongoDB and Postgres/JPA), it is not needed, and by default 
  the server uses MongoDB, this has been done just for an exercise to decouple Controller from DB layer and 
  show how this can be done.

* Java 8, any new project should use this version (last one at the moment when I am writing this)

* The API only contains acceptance tests developed with Cucumber-jvm. 
    * With this is enough for now, the code decisions are on rest layer or db layer, so I decided to cover 
      that with integration/accpentace tests
    * They don't use an embedded MongoDB, this could be an improvement. (It uses database name `companies-test`)
    * They only have a db client for MongoDB, we could add a Postgresql one and pass them for both databases.

* Validation. 
    * It uses hibernate validators directly, @NotEmpty one is very handy.
    * Validation has been made on external layer (Rest) leaving free other layers, even db or db access 
      layers have less as possible. The reason is to avoid adding recurrent complexity.
    * Max size of fields are not validated, but they should be to avoid bad-intention problems
    
* List functionality
    * It does not support pagination, just to keep simple this example, but it should have, 
      and it should have default max page size, for performance and security. 
    * It returns all the information for each company, depending on performance and needs, it could leave owners out 
      and create an endpoint `/companies/{id}/owners` to get them 
    * It returns an object with only one field `companies`, it returns an object instead of an array directly 
      because that leaves open the API to return more information in the future, i.e. pagination    

* Custom error handling
    * It leaves this handling to Spring Boot default handlers, just for simplicity in the example, 
      we should add ours to specify the format we want our errors to have in our API.
    * We shouldn't return implementation details from server (such as Exception class, etc), due to 
      security concerns.
    
* IDs
    * It uses the IDs from database, we should use our own resource IDs, so that we decouple the API model 
      from DB model.
    
### Web Client    

* UI Bundles
    * To keep simple the example there is no such technique on it, if we want to be production ready we should 
      compress and create bundles for our JS/CSS/HTML files.
      
* User feedback
    * Although in this example I don't provide complete feedback for the user we should add:
        * Loading items for server interactions
        * Complete set of general error messages
    
* Acceptance tests with Selenium and Cucumber was not easy
    * **You need to have Firefox installed in your machine to run the acceptance tests**, I found problems with 
      HtmlUnitDriver :-(
    * I had to write a workaround to be able to clear content of a form
    * I had to disable the animations to be able to test some scenarios
    * Next time I will try to use other tools for it.
    * API and Web client tests are together in the same gradle configuration, although it shouldn't be 
      hard to extract if needed.

* FLUX Architecture
    * I have not implemented this architecture because the app is quite small, it is true that I don't like to call 
      the API from the view, and I could develop that in the action though.
    

## Useful Links & Notes

* [React Tools](https://github.com/facebook/react/wiki/Complementary-Tools)
* [React Components for Bootstrap](http://react-bootstrap.github.io/)
* Flux implementations
    * http://redux.js.org/
    * http://alt.js.org/
    * https://github.com/kjda/ReactFlux
    * https://github.com/reflux/refluxjs
    * https://github.com/BinaryMuse/fluxxor
    * https://github.com/optimizely/nuclear-js
    * [From forums](https://discuss.reactjs.org/t/how-should-we-handle-ajax-errors-with-flux/99), 
      for Flux + Ajax, consider having a LogStore/StatusStore to trace the status/success/error of requests
* Testing with React (I didn't use any)
    * http://stackoverflow.com/questions/31336618/tdd-bdd-with-react-js
    * http://reactkungfu.com/2015/07/approaches-to-testing-react-components-an-overview/
* Embedded Mongo
    * https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo
    * http://stackoverflow.com/questions/31568351/how-do-you-configure-embedded-mongdb-for-integration-testing-in-a-spring-boot-ap
* [Mongo Driver docs](http://mongodb.github.io/mongo-java-driver/3.0/driver/getting-started/quick-tour/)  
* Gradle, [create a separate configuration for acceptance/integration tests]
  (http://www.petrikainulainen.net/programming/gradle/getting-started-with-gradle-integration-testing/) 
* Cucumber, [run/stop server for acceptance tests](http://zsoltfabok.com/blog/2012/09/cucumber-jvm-hooks/)
* [Repository with example of integration for acceptance tests](https://github.com/chbatey/killrauction)   
* [Spring Boot and static resources, versioning, gzip etc](http://kielczewski.eu/2014/11/static-resources-in-spring41/)
    


