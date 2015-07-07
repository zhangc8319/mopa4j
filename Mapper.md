# Introduction #

This class gives you the capability to convert any _POJOs_ to a _com.mongodb.DBObject_ and vice-versa.
In this case for POJO we intend POJO IS [ADocumentObject](ADocumentObject.md).

It was the first class written at all. After few ours I got the basic to convert my pojos in DBObjects and vice-versa.

After a couple of days I needed to add more features like mapping and converting nested pojos.


# Details #

As mentioned above, this class provides convertions and mapping methods for to and from [ADocumentObject](ADocumentObject.md)/DBObject.

Follows the list of methods:
```
public DBObject toDBObject(ADocumentObject aDocumentObject);
public DBObject toDBObject(Map<String, Object> map);
public ADocumentObject toADocumentObject(DBObject dbObject, Class clazz);
public Map<String, Object> toMap(ADocumentObject aDocumentObject);
```

Those methods are quite intuitive.
Here are three more method which needs few, very few words of explanation:
```
public Map<String, Object> toMQL(ADocumentObject aDocumentObject);
public Object setValue(Object instance, String fieldName, Object value);
public Object getValue(Object instance, String propertyName);
```

### toMQL ###

The method _toMQL()_ just translates a [ADocumentObject](ADocumentObject.md), i.e. your pojo, to a key/value map for querying;
Let's say you have a bunch of users in your db and you want all the users who live in Rome; in the _Mongo Shell_ you will query like this:
```
db.com.foo.User.find({"city.name" : "Rome"})
```

in MOPA4J real world we have to do as follow:
```
QueryObject queryObject = QueryObject.newInstance();

User user = new User();
City city = new City();
city.setName("Rome");
user.setCity(city);

queryObject.as(user);

List<User> userList = userQueryManager.find(queryObject);
```

the _as()_ method will convert your user pojo in a map which contains one key "city.name" with one value "Rome".


### setValue and getValue ###

These two methods provide the capabilities to set/get a value for your pojos at runtime.