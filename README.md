Mongo Persistence Service
==========================
This is a Generic Persistence Service to use for MongoDB easily. Created some generic method which is help full for ORM operation of MongoDB


MongoDB Property
==========================
Open the db.properties file from the conf folder.
Do the following changes in property file

```java
mongo.host=localhost
mongo.dbname=test
mongo.username=""
mongo.password =""
mongo.connectionsPerHost=20
```

Usage
==========================
```java
// Create the Mongo Persistence Service object
MongoPersistenceService persistenceService = new MongoPersistenceService();

//add Single Object
persistenceService.addSingleObject(user);

//update single object with id exist
persistenceService.addOrUpdateObject(user, User.class);

//add multiple objects
List<Object> list = new ArrayList<Object>();
list.add(user);
list.add(employee);
persistenceService.addObjects(list);

//fetch all documents
List<User> user = persistenceService.getRecordsList(User.class);
		
//fetch single document
User user = persistenceService.findOne("first_name", "test", User.class);

//fetch singel document by condition <,>,<=,>= etc.
Employee employee = persistenceService.findOneByCondition("empId", 1,OPERATOR.LESS_THAN_EQUAL, null, null,Employee.class);


//fetch the documents
List<User> user =  persistenceService.find("first_name", "test","_id",OrderBy.DESCENDING, User.class);
	
//find By Order
List<User> orderUser =  persistenceService.findByOrder("first_name", "suresh", "_id",OrderBy.DESCENDING, User.class);

//find by Condition operator <>,>=
List<Employee> employee = persistenceService.findByCondition("empId", 0, OPERATOR.GREATER_THAN, null, null, Employee.class);

//fetch all documents by pagination and query
Query query = new Query();
List<User> user =  persistenceService.getListByPaging(query, 1, 2, null, null, User.class);
		
// fetch the document by using native query of MongoDB and It also fetch data by pagination(lazy loading)
BasicDBObject query = new BasicDBObject();
query.append("first_name", "test");
List<User> user =  persistenceService.getListPagingByNativeQuery(query, 1, 2, "_id" , OrderBy.ASCENDING, User.class);

//fetch the document using projection 
BasicDBObject query = new BasicDBObject();
query.append("first_name", "test");

BasicDBObject projection = new BasicDBObject("first_name",1);
List<User> user1 =  persistenceService.getListByProjection(query, projection,null,null,User.class);

//create ascending order index
persistenceService.createIndex(User.class, null, "first_name");
		
//create descending order index
persistenceService.createIndex(User.class, OrderBy.DESCENDING, "last_name");
		
//create multiple column's ascending order index
persistenceService.createIndex(User.class, OrderBy.ASCENDING, "first_name", "last_name");
		
//create multiple column's ascending order index
persistenceService.createCompoundIndex(User.class, "first_name", "last_name");

//Drop indexes for collection
persistenceService.dropIndexes(User.class);

//delete documents by native query
BasicDBObject query = new BasicDBObject("first_name", "karan1");
persistenceService.deleteRecordByNativeQuery(query, User.class);


```
