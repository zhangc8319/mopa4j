package com.foogaro.nosql.mopa4j.query;

import java.util.List;

/**
* Little shy on this one. I did not know there was a QueryBuilder object in the MongoDB Java driver.
* Anyway I think mine is better because takes advantage of my Mapper class; everything is converted for you and mapped in the right way. I hope!
* @see QueryObject
* @author Luigi Fugaro
* @version 1.0
* @since 1.0
*/
public interface IQueryObject {

    public QueryObject eq(String key, Object value);
    public QueryObject ne(String key, Object value);

    public QueryObject or(String key, Object... values);
    public QueryObject nor(String key, Object... values);

    public QueryObject as(Object Object);

    public QueryObject starts(String key, String value);
    public QueryObject like(String key, String value);
    public QueryObject ends(String key, String value);

    public QueryObject in(String key, List<Object> values);
    public QueryObject nin(String key, List<Object> values);

    public QueryObject gt(Object object);
    public QueryObject lt(Object object);
    public QueryObject gte(Object object);
    public QueryObject lte(Object object);

    public QueryObject gt(String key, Object value);
    public QueryObject lt(String key, Object value);
    public QueryObject gte(String key, Object value);
    public QueryObject lte(String key, Object value);

}
