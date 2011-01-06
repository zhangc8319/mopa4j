package com.foogaro.nosql.mopa4j.query;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

import java.util.LinkedList;
import java.util.List;


/**
* When you need to query on your MongoDB, belong to your QueryManager implementation, create your query conditions with it.
* @author Luigi Fugaro
* @version 1.0
* @since 1.0
*/
public class QueryObject implements IQueryObject {

    public final static String OR = "$or";
    public final static String NOR = "$nor";
    public final static String EQ = "$eq";
    public final static String NE = "$ne";
    public final static String AS = "$as";
    public final static String STARTS = "$starts";
    public final static String LIKE = "$like";
    public final static String ENDS = "$ends";
    public final static String IN = "$in";
    public final static String NIN = "$nin";
    public final static String GT = "$gt";
    public final static String LT = "$lt";
    public final static String GTE = "$gte";
    public final static String LTE = "$lte";

    private LinkedList<String> queryOperators = new LinkedList<String>();
    private LinkedList<Object> queryParameters = new LinkedList<Object>();

    protected QueryObject() {
    }

    public static QueryObject newInstance() {
        return new QueryObject();
    }

    public QueryObject or(String key, Object... values) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, values);
            queryOperators.add(OR);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject nor(String key, Object... values) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, values);
            queryOperators.add(NOR);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject eq(String key, Object value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, value);
            queryOperators.add(EQ);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject ne(String key, Object value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, value);
            queryOperators.add(NE);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject as(Object object) {
        if (object != null) {
            queryOperators.add(AS);
            queryParameters.add(object);
//            dbObject.putAll(object.toMQL());
        }
        return this;
    }

    public QueryObject starts(String key, String value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, java.util.regex.Pattern.compile("^" + value));
            queryOperators.add(STARTS);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject like(String key, String value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, java.util.regex.Pattern.compile(value));
            queryOperators.add(LIKE);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject ends(String key, String value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, java.util.regex.Pattern.compile(value + "$"));
            queryOperators.add(ENDS);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject in(String key, List<Object> values) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, values);
            queryOperators.add(IN);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject nin(String key, List<Object> values) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, values);
            queryOperators.add(NIN);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject gt(Object object) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public QueryObject lt(Object object) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public QueryObject gte(Object object) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public QueryObject lte(Object object) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public QueryObject gt(String key, Object value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, value);
            queryOperators.add(GT);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject lt(String key, Object value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, value);
            queryOperators.add(LT);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject gte(String key, Object value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, value);
            queryOperators.add(GTE);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject lte(String key, Object value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, value);
            queryOperators.add(LTE);
            queryParameters.add(dbObject);
        }
        return this;
    }

    private boolean isValidKey(String key) {
        return (key != null && key.trim().length() > 0);
    }

    public LinkedList<Object> getQueryParameters() {
        return queryParameters;
    }

    public LinkedList<String> getQueryOperators() {
        return queryOperators;
    }
}
