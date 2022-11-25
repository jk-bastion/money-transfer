##  RESTful API for money transfers between accounts
# Technologies
 - Java 17
 - Spring
 - H2 in memory database
 - Hibernate 
# How to run
Clone project, run MainApplication from IDE or run with maven by executing

```
mvn clean install exec:java
```

# Application usage
Create an account
````
POST http://localhost:8080/api/v1/account
{
	"username" : "testusername",
	"email" : "testemail@gop.com",
	"balance" :10,
	"currencyCode" : "usd"
}
````
Response
````
HTTP 201 Created
{
    "accountId": 1,
    "username": "testusername",
    "email": "testemail@gop.com",
    "balance": 10,
    "currencyCode": "usd"
}
````
Get account
````
GET http://localhost:8080/api/v1/account/1
````
Response
````
HTTP 200 OK
{
    "accountId": 1,
    "username": "testusername",
    "email": "testemail@gop.com",
    "balance": 10.00,
    "currencyCode": "usd"
}
````
Get all accounts
````
GET http://localhost:8080/api/v1/accounts/
````
Response
````
HTTP 200 OK
<response body>
````

Update account balance
````
PUT http://localhost:8080/api/v1/accounts
{
    "accountId": 1,
    "username": "testusername",
    "email": "testemail@gop.com",
    "balance": 10,
    "currencyCode": "usd"
}
````
Response
````
HTTP 204 No Content
````

Delete account
````
DELETE http://localhost:8080/api/v1/account/1
````
Response
````
HTTP 204 NO CONTENT
````
Add user transaction - transfer money from one account to another
````
POST  http://localhost:8080/api/v1/account/transaction
{
    "fromAccountId": 1,
    "toAccountId": 2,
    "amount": "2",
    "currencyCode": "usd"
}
````
Response
````
HTTP 201 Created
````
Get all transaction for given account
````
GET  http://localhost:8080/api/v1/account/1/transaction
````
Response
````
HTTP 200 OK
<body response>
````



