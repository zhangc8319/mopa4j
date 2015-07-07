# Introduction #

This class provides the basic for creating, reading, updating and deleting a document to/from your MongoDB instance.

# Details #

You just need to write your own persistence manager extending PersistenceManager giving it your data type you want to operate with.

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

then write down your typed persistence manager:

```
public class UserPersistenceManager extends PersistenceManager < User > {

}
```

Now you can CRUD your User object/document.

Remember that the
```
public T read(T aDocumentObject);
```
method of the PersistenceManager looks for the 