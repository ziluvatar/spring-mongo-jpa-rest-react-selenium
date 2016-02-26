Rest API example: Spring MVC+Data, Mongodb/JPA-postgresql, Cucumber
----------------------------------------------------------

# What is this?

# How to use it?

# Technical decisions

* There is already a [Spring Boot Data Rest](http://docs.spring.io/spring-data/rest/docs/current/reference/html) component where you only need to develop the Entity, but I wanted to develop it on my own.

* Java 8, any new project should use this version (last one at the moment when I am writing this)

* The API only contains acceptance test developed with Cucumber-jvm. 
    * With this is enough for now, the code decisions are on rest layer or db layer, so I preferred to cover that with integration/accpentace tests
    * They don't use an embedded MongoDB, this could be an improvement. (It uses database name `companies-test`)
    * They only have a db client for MongoDB, we could add a Postgresql one and pass them for both databases.

* Validation. 
    * It uses hibernate validators directly, @NotEmpty one is very handy.
    * Validation has been made on external layer (Rest) leaving free other layers, even db or db access layers have less as possible. The reason is to avoid adding recurrent complexity.
    * Max size of fields are not validated, but they should be to avoid bad-intention problems
    
* List functionality
    * It does not support pagination, just to keep simple this example, but it should have, and it should have default max page size, for performance and security.
    * It returns all the information of each company, depending on performance and needs, it could leave owners out, and create an endpoint `/companies/{id}/owners` to get them 
    * It returns an object with only one field `companies`, we return an object instead of an array directly because that leaves open the API to return more information in the future, i.e. pagination    

* Custom error handling
    * It leaves this handling to Spring Boot default handlers, just for simplicity in the example, we should add ours to specify the format we want our errors to have in our API.
    * We shouldn't return implementation details from server (such as Exception class, etc), due to security concerns.
    
* IDs
    * It uses the IDs from database, we should use our own resource IDs, so that we decouple the API model from DB model.


