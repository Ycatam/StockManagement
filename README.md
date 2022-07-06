

# Quotation Management Application



This application is a REST based application, which purpose is to store stock quotes from the stock market. The application don't use https protocol, only http.

This application is exposed throught port 8081.

Before test this application, some configuration need to be done. First, you need to have Docker service runing (can be trhough WSL2 or in Linux, how to use docker isn't the point of this readme).

With the docker service running, start the following docker containers:

docker container run -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=bootdb -p 3306:3306 -p 33060:33060 -d mysql:8

docker container run -p 8080:8080 -d lucasvilela/stock-manager

Use docker ps to ensure that the services are up.

The container lucasvilela/stock-manager will be responsible for manage what Stock Id we can store in the mysql conatiner. At start we can store only two Stock Id. To check with Id is stored in the container use the command:

##### localhost:8080/stock

To register a new Stock Id, a Json object is expected like the example:

{ 
    "id": "petr7", 
    "description": "petrobras pn" 
}

Use the URL:

##### localhost:8080/stock

This application can handle the following HTTP requests:

* GET
* POST
* DELETE

### List all Stocks in mysql

##### localhost:8081/stocks

### List a Stocks by Stock Id in mysql

##### localhost:8081/stocks/"id"

Example: localhost:8081/stocks/petr4

### Register a Stock by Stock Id that is registered in the stock-manager container

To POST a new Stock in mysql a tool like Postman is require, to handle the POST request.
A Json object is expect like the example:

{  
    "stockId": "petr4", 
    "quotes": 
    { 
        "2019-01-01": "10", 
        "2019-01-02": "11", 
        "2019-01-03": "14"
    } 
}

Through:

#### localhost:8081/stocks/

A response with status code ok and a Json is expected like the example:

{ 
    "id": "c01cede4-cd45-11eb-b8bc-0242ac130003", 
    "stockId": "petr4", 
    "quotes": { "2019-01-01": "10", 
                "2019-01-02": "11", 
                "2019-01-03": "14" } 
}

The values above is a example. Quotes can contain a single input, or a list, like the example above.

### Delete a Stock in mysql by Stock Id

To handle DELETE request, a tool like Postman is required. To delete a Stock that is stored in mysql container, run the delete request with the Stock Id (the name of the stock) as a URL parameter:

#### localhost:8081/stocks/"id"

Example: localhost:8081/stocks/petr4

### A Swagger documentation about this API can be checked through:

#### http://localhost:8081/swagger-ui/#/

### A Docker Image can be generated through the Dockerfile that is in the source files:

To generete a docker image run the docker command in the example below:

#### docker build -t rafaelrocha/TheNameOfyourFolderHere:1.0 .

Thanks in advance, have fun trying this application. Any sugestions please do a PR!