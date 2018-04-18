# auction-service
Server-side app for example auction service using REST API

Purpose of this repository is creating a web application using following technologies:
- Java 8
- Spring boot
- Hibernate
- Mockito 
- JUnit

Packages in application are divided by feature not by layer, so there are auction, bid, user packages. 
It is more readable and easier to keep proper access modifiers for classes and methods. To create 
persistence layer, was picked CrudRepository from Spring technologies. It was chosen because it delivers 
all necessary feature. If it would be an application of bigger size, it would be reasonable to pick 
PagingAndSortingRepository. Below are described packages from application:

auction - main package of application. Controller is joining both services bid and auction, because according 
to REST approach bid is a sub-resource of auction. Except creating an auction, user is allowed to use PATCH request
to partially update an entity. Each method from service layer is validated by a lot of conditions, e.g. user is not 
allowed update auction if someone else bid already. Repository and service layer is fully covered with unit tests.

user - more a placeholder than a real functional package. User is only a simple POJO with  one string field for name. 
Just simple service with basic CRUD functionality without any validators.

bid - only repository and service layer. There is no dedicated controller for it.

exception - all custom exceptions are kept here. Each of them are throwing proper Http status.

model - hibernate's entities. AuctionDescriptionOnly and AuctionStartingPriceOnly were created only for proper 
implementation of PATCH http request.

filter - special package for presentation purpose. Controller's methods are filling database. 


In application.properties are properties for H2 database. This application is not a real product it is just 
a training field for upskilling.

Example API:

Creating an Auction:
- POST /auction
```javascript
{
    "startDate":"2018-04-06T23:46:03.288",
    "finishDate":"2018-04-13T23:46:03.288",
    "auctionType":"BOOKS",
    "description":"Super fancy book",
    "creator":{
        "userId":1
        },
    "startingPrice":10,
    "currentPrice":null
}
```

Update a description of auction:
- PATCH /auction/{id}/description
```javascript
{
    "description":"new description"
}
```

TODO:
- add validators to bid services,
- upgrade user entity
- remove filler package



