# Introduction #

This class provides three basic methods to query your documents on the database:
```
    public List<T> find();
    public List<T> find(QueryObject queryObject);
    public T findOne(QueryObject queryObject);
```


# Details #

You just need to write your own query manager extending QueryManager giving it your data type you want to query for.

Let's say you have a class like this:

```
public class User {

  private String name;
  private Date birthdate;

  ...getters/setters...
}
```

First make your User class extend [ADocumentObject](ADocumentObject.md):

```
public class User extends ADocumentObject {
 ...
}
```

then write down your typed query manager:

```
public class UserQueryManager extends QueryManager<User> {

}
```

Now you can query your User object/document by providing a QueryObject.