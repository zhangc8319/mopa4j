package com.foogaro.nosql.mopa4j.query;

import com.foogaro.nosql.mopa4j.ADocumentObject;
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

    private LinkedList<DBObject> queryParameters = new LinkedList<DBObject>();

    protected QueryObject() {
    }

    public static QueryObject newInstance() {
        return new QueryObject();
    }

    public QueryObject or(String key, Object... values) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            DBObject operator = new BasicDBObject();
            operator.put("$or", values);
            dbObject.put(key, operator);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject nor(String key, Object... values) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            DBObject operator = new BasicDBObject();
            operator.put("$nor", values);
            dbObject.put(key, operator);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject eq(String key, Object value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, value);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject ne(String key, Object value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            DBObject operator = new BasicDBObject();
            operator.put(QueryOperators.NE, value);
            dbObject.put(key, operator);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject as(ADocumentObject aDocumentObject) {
        if (aDocumentObject != null) {
            DBObject dbObject = new BasicDBObject();
            dbObject.putAll(aDocumentObject.toMQL());
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject starts(String key, String value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, java.util.regex.Pattern.compile("^" + value));
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject like(String key, String value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, java.util.regex.Pattern.compile(value));
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject ends(String key, String value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            dbObject.put(key, java.util.regex.Pattern.compile(value + "$"));
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject in(String key, List<Object> values) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            DBObject operator = new BasicDBObject();
            operator.put(QueryOperators.IN, values);
            dbObject.put(key, operator);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject nin(String key, List<Object> values) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            DBObject operator = new BasicDBObject();
            operator.put(QueryOperators.NIN, values);
            dbObject.put(key, operator);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject gt(ADocumentObject aDocumentObject) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public QueryObject lt(ADocumentObject aDocumentObject) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public QueryObject gte(ADocumentObject aDocumentObject) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public QueryObject lte(ADocumentObject aDocumentObject) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public QueryObject gt(String key, Object value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            DBObject operator = new BasicDBObject();
            operator.put(QueryOperators.GT, value);
            dbObject.put(key, operator);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject lt(String key, Object value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            DBObject operator = new BasicDBObject();
            operator.put(QueryOperators.LT, value);
            dbObject.put(key, operator);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject gte(String key, Object value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            DBObject operator = new BasicDBObject();
            operator.put(QueryOperators.GTE, value);
            dbObject.put(key, operator);
            queryParameters.add(dbObject);
        }
        return this;
    }

    public QueryObject lte(String key, Object value) {
        if (isValidKey(key)) {
            DBObject dbObject = new BasicDBObject();
            DBObject operator = new BasicDBObject();
            operator.put(QueryOperators.LTE, value);
            dbObject.put(key, operator);
            queryParameters.add(dbObject);
        }
        return this;
    }

    private boolean isValidKey(String key) {
        return (key != null && key.trim().length() > 0);
    }

    public LinkedList<DBObject> getQueryParameters() {
        return queryParameters;
    }
}
