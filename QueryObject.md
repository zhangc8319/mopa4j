# Introduction #

When you need to query your documents on MongoDB, belong to your QueryManager implementation, create your query conditions with it.

# Details #

Here is an example of how to use it:

```
QueryObject queryObject = QueryObject.newInstance();
queryObject.like("name", "L");

List<User> users = userQueryManager.find(queryObject);
```

The root document is given by the _typed_ implementation of the QueryManager, in this case we use the _userQueryManager_ which is as follow:

```
public class UserQueryManager extends QueryManager<User> {
}
```


Here are the list of the methods QueryObject provides:

```
public QueryObject eq(String key, Object value);
public QueryObject ne(String key, Object value);

public QueryObject or(String key, Object... values);
public QueryObject nor(String key, Object... values);

public QueryObject as(ADocumentObject aDocumentObject);

public QueryObject starts(String key, String value);
public QueryObject like(String key, String value);
public QueryObject ends(String key, String value);

public QueryObject in(String key, List<Object> values);
public QueryObject nin(String key, List<Object> values);

public QueryObject gt(ADocumentObject aDocumentObject);
public QueryObject lt(ADocumentObject aDocumentObject);
public QueryObject gte(ADocumentObject aDocumentObject);
public QueryObject lte(ADocumentObject aDocumentObject);

public QueryObject gt(String key, Object value);
public QueryObject lt(String key, Object value);
public QueryObject gte(String key, Object value);
public QueryObject lte(String key, Object value);
```