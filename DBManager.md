# Introduction #

This class is used as singleton to wrap the access to the MongoDB.
Within your Spring configuration files you have to provide few more line to get your both typed QueryManager and PersistenceManager working.

# Details #

Within your Spring configuration files you have to provide few more lines:

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

The DBManager instance is injected into an abstract class named [ABaseManager](ABaseManager.md), which is extended by both [PersistenceManager](PersistenceManager.md) and [QueryManager](QueryManager.md).