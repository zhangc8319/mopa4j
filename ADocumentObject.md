# Introduction #

This class is an implementation of **com.mongodb.DBObject**.
It is intend to be used as parent class of any POJOs you would like to manage in your MongoDB instance.


# Details #

To use your _POJOs_ class within **MOPA4J** you need to extend them from ADocumentObject class.
Just do it this way:

```
public class User extends ADocumentObject {
}
```