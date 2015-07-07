### What is it? ###

Its code name is **MOPA4J** (_MOngo Persistence API for Java_).

It was born just to do mapping _POJO_ to _com.mongodb.DBObject_ and vice-versa with the [Mapper](Mapper.md) class.

Later on I added some feature like a **PersistenceManager** to obtain the ability of the _CRUD_ operation (it takes advantage of MongoDB Java Driver - actually v2.4).

After the **PersistenceManager** I needed something to query my documents, that's why I introduced the **QueryManager** belong with **QueryObject**.

Actually QueryObject was just a _mistake_, I took a look at the MongoDB Java Driver and I did NOT notice the **com.mongodb.QueryBuilder** object, so I decided to have an _interface_ to the Java driver for quering.

Few days later I discovered the QueryBuilder, I used it and I found my QueryObject a little _better_ (IMHO), more intuitive; it simplifies the way you query, it has the _like();_ method which takes a Pattern as parameter and few other things.


### How does it work? ###

First of all you have to create your _POJOs_ classes and make them extend [ADocumentObject](ADocumentObject.md), as shown below:

```
public class User extends ADocumentObject {
  ...
}
```

Doing so, your pojos will _look_ as document. As you might already know a document is the equivalent of a record in traditional RDBMS.

Now that we have our document ready to be stored, let's implement what we need to save it in our MongoDB.

We want to create our [PersistenceManager](PersistenceManager.md) for the User document:
```
public class UserPersistenceManager extends PersistenceManager<User> {
}
```

Yes, it's all you have to do is extend the abstract class [PersistenceManager](PersistenceManager.md) specifing the type which it will operate with, your POJO class, User in this case.

That's it, your Java code is complete.


### What about configuration? ###

At the moment configuration is easy, it uses the Spring framework and its configuration file(s).
All you have to do is provide a file containing this few lines of code:

```
<context:annotation-config />
<context:component-scan base-package="the.package.you.put.your.userpersistencemanager" />

<bean id="mongo" class="com.mongodb.Mongo" />
<bean id="dbManager" class="com.foogaro.nosql.mopa.DBManager">
  <property name="dbName" value="YOUR_DB_NAME"/>
  <property name="mongo" ref="mongo"/>
</bean>
```

This will create in Spring context two singletons:
  1. com.mongodb.MongoDB named mongo;
  1. com.foogaro.nosql.mopa.[DBManager](DBManager.md) named dbManager;

MongoDB represents the database instance. Yeah I know it misses all the options like host, port and authentication. I'll implement this as soon as I can. ;)

[DBManager](DBManager.md) is used to obtain both the _com.mongodb.DB_ and _com.mongodb.DBCollection_ objects using the previous _mongo_ reference.

Now, you really have all you need to start playing with your MongoDB.

If you want, you can checkout the entire project, which comes with a test to try it all out.


### Coming next... ###

Shortly I will release an update about **DBRef**.
What you will probably never find here is something related to indexing, because indexing has to do with DBAs and DB tuning things, stuff!

Stay tuned for upcoming updates!


### TODO list ###
  1. More configuration about the _com.mongodb.Mongo_ instance: custom host, custom port and authentication;
  1. DBRef;
  1. Allow alias within POJO properties;

# Last, but not least #

**Please, feel free to leave any comments! _;)_**