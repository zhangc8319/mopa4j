package com.foogaro.nosql.mopa4j;

import com.foogaro.nosql.mopa4j.persistence.DBReferenceManager;
import com.foogaro.nosql.mopa4j.persistence.IPersistenceManager;
import com.foogaro.nosql.mopa4j.query.IQueryManager;
import com.foogaro.nosql.mopa4j.query.QueryObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.QueryOperators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * User: luigi
 * Date: Jan 6, 2011
 * Time: 5:54:36 PM
 */
@Service
public class MoPA4J {

    @Autowired
    private IPersistenceManager persistenceManager;

    @Autowired
    private IQueryManager queryManager;

    @Autowired
    private MappingHelper mappingHelper;

    public Object create(Object object) {
        DBObject dbObject = persistenceManager.create(toDBObject(object), object.getClass());
        return toObject(dbObject, object);
    }

    public Object read(Object object) {
        return null;
    }

    public Object update(Object object) {
        return null;
    }

    public void delete(Object object) {
        return;
    }

    public List find(Class classType) {
        return queryManager.find(classType);
    }

    public List find(QueryObject queryObject, Class classType) {
        return queryManager.find(queryObject, classType);
    }

    public Object findOne(QueryObject queryObject, Class classType) {
        return queryManager.findOne(queryObject, classType);
    }


    private DBObject toDBObject(Object object) {
        return mappingHelper.toDBObject(object);
    }

    private Object toObject(DBObject dbObject, Class clazz) {
        return mappingHelper.toObject(dbObject, clazz);
    }

    private Object toObject(DBObject dbObject, Object instance) {
        return mappingHelper.toObject(dbObject, instance);
    }

    private List<DBObject> scanQuery(QueryObject queryObject) {

        List<DBObject> result = new ArrayList<DBObject>();

        List<String> operators = queryObject.getQueryOperators();
        List<Object> parameters = queryObject.getQueryParameters();

        String operator = null;
        Object parameter = null;
        String key = null;
        Object value = null;
        // probably useless check
        if (operators != null && parameters != null && parameters.size() == operators.size()) {
            for (int index = 0; index < operators.size(); index++) {

                operator = operators.get(index);
                parameter = parameters.get(index);

                if (operator == null || operator.trim().length() == 0) {
                    //that's weird!!!
                } else if (QueryObject.Operators.OR.equalsIgnoreCase(operator)) {
                    if (parameter instanceof DBObject) {
                        key = ((DBObject) parameter).keySet().toArray(new String[1])[0];
                        value = ((DBObject) parameter).get(key);
                        DBObject dbObject = new BasicDBObject();
                        DBObject operatorDbObject = new BasicDBObject();
                        operatorDbObject.put(operator, value);
                        dbObject.put(key, operatorDbObject);
                        result.add(dbObject);
                    }
                } else if (QueryObject.Operators.NOR.equalsIgnoreCase(operator)) {
                    if (parameter instanceof DBObject) {
                        key = ((DBObject) parameter).keySet().toArray(new String[1])[0];
                        value = ((DBObject) parameter).get(key);
                        DBObject dbObject = new BasicDBObject();
                        DBObject operatorDbObject = new BasicDBObject();
                        operatorDbObject.put(operator, value);
                        dbObject.put(key, operatorDbObject);
                        result.add(dbObject);
                    }
                } else if (QueryObject.Operators.EQ.equalsIgnoreCase(operator)) {
                    if (parameter instanceof DBObject) {
                        key = ((DBObject) parameter).keySet().toArray(new String[1])[0];
                        value = ((DBObject) parameter).get(key);
                        DBObject dbObject = new BasicDBObject();
                        DBObject operatorDbObject = new BasicDBObject();
                        operatorDbObject.put(operator, value);
                        dbObject.put(key, operatorDbObject);
                        result.add(dbObject);
                    }
                } else if (QueryObject.Operators.NE.equalsIgnoreCase(operator)) {
                    if (parameter instanceof DBObject) {
                        key = ((DBObject) parameter).keySet().toArray(new String[1])[0];
                        value = ((DBObject) parameter).get(key);
                        DBObject dbObject = new BasicDBObject();
                        DBObject operatorDbObject = new BasicDBObject();
                        operatorDbObject.put(operator, value);
                        dbObject.put(key, operatorDbObject);
                        result.add(dbObject);
                    }
                } else if (QueryObject.Operators.AS.equalsIgnoreCase(operator)) {
                    if (parameter instanceof DBObject) {
                        key = ((DBObject) parameter).keySet().toArray(new String[1])[0];
                        value = ((DBObject) parameter).get(key);
                        DBObject dbObject = new BasicDBObject();
                        dbObject.putAll(mappingHelper.toMQL(value));
                        result.add(dbObject);
                    }
                } else if (QueryObject.Operators.STARTS.equalsIgnoreCase(operator)) {
                    if (parameter instanceof DBObject) {
                        key = ((DBObject) parameter).keySet().toArray(new String[1])[0];
                        value = ((DBObject) parameter).get(key);
                        DBObject dbObject = new BasicDBObject();
                        DBObject operatorDbObject = new BasicDBObject();
                        operatorDbObject.put(operator, value);
                        dbObject.put(key, operatorDbObject);
                        result.add(dbObject);
                    }
                } else if (QueryObject.Operators.LIKE.equalsIgnoreCase(operator)) {
                    if (parameter instanceof DBObject) {
                        key = ((DBObject) parameter).keySet().toArray(new String[1])[0];
                        value = ((DBObject) parameter).get(key);
                        DBObject dbObject = new BasicDBObject();
                        DBObject operatorDbObject = new BasicDBObject();
                        operatorDbObject.put(operator, value);
                        dbObject.put(key, operatorDbObject);
                        result.add(dbObject);
                    }
                } else if (QueryObject.Operators.ENDS.equalsIgnoreCase(operator)) {
                    if (parameter instanceof DBObject) {
                        key = ((DBObject) parameter).keySet().toArray(new String[1])[0];
                        value = ((DBObject) parameter).get(key);
                        DBObject dbObject = new BasicDBObject();
                        DBObject operatorDbObject = new BasicDBObject();
                        operatorDbObject.put(operator, value);
                        dbObject.put(key, operatorDbObject);
                        result.add(dbObject);
                    }
                } else if (QueryObject.Operators.IN.equalsIgnoreCase(operator)) {
                    if (parameter instanceof DBObject) {
                        key = ((DBObject) parameter).keySet().toArray(new String[1])[0];
                        value = ((DBObject) parameter).get(key);
                        DBObject dbObject = new BasicDBObject();
                        DBObject operatorDbObject = new BasicDBObject();
                        operatorDbObject.put(operator, value);
                        dbObject.put(key, operatorDbObject);
                        result.add(dbObject);
                    }
                } else if (QueryObject.Operators.NIN.equalsIgnoreCase(operator)) {
                    if (parameter instanceof DBObject) {
                        key = ((DBObject) parameter).keySet().toArray(new String[1])[0];
                        value = ((DBObject) parameter).get(key);
                        DBObject dbObject = new BasicDBObject();
                        DBObject operatorDbObject = new BasicDBObject();
                        operatorDbObject.put(operator, value);
                        dbObject.put(key, operatorDbObject);
                        result.add(dbObject);
                    }
                } else if (QueryObject.Operators.GT.equalsIgnoreCase(operator)) {
                    if (parameter instanceof DBObject) {
                        key = ((DBObject) parameter).keySet().toArray(new String[1])[0];
                        value = ((DBObject) parameter).get(key);
                        DBObject dbObject = new BasicDBObject();
                        DBObject operatorDbObject = new BasicDBObject();
                        operatorDbObject.put(operator, value);
                        dbObject.put(key, operatorDbObject);
                        result.add(dbObject);
                    }
                } else if (QueryObject.Operators.LT.equalsIgnoreCase(operator)) {
                    if (parameter instanceof DBObject) {
                        key = ((DBObject) parameter).keySet().toArray(new String[1])[0];
                        value = ((DBObject) parameter).get(key);
                        DBObject dbObject = new BasicDBObject();
                        DBObject operatorDbObject = new BasicDBObject();
                        operatorDbObject.put(operator, value);
                        dbObject.put(key, operatorDbObject);
                        result.add(dbObject);
                    }
                } else if (QueryObject.Operators.GTE.equalsIgnoreCase(operator)) {
                    if (parameter instanceof DBObject) {
                        key = ((DBObject) parameter).keySet().toArray(new String[1])[0];
                        value = ((DBObject) parameter).get(key);
                        DBObject dbObject = new BasicDBObject();
                        DBObject operatorDbObject = new BasicDBObject();
                        operatorDbObject.put(operator, value);
                        dbObject.put(key, operatorDbObject);
                        result.add(dbObject);
                    }
                } else if (QueryObject.Operators.LTE.equalsIgnoreCase(operator)) {
                    if (parameter instanceof DBObject) {
                        key = ((DBObject) parameter).keySet().toArray(new String[1])[0];
                        value = ((DBObject) parameter).get(key);
                        DBObject dbObject = new BasicDBObject();
                        DBObject operatorDbObject = new BasicDBObject();
                        operatorDbObject.put(operator, value);
                        dbObject.put(key, operatorDbObject);
                        result.add(dbObject);
                    }
                } else {
                    // dunno!
                }
            }
        }

        return null;
    }

    private boolean isValidKey(String key) {
        return (key != null && key.trim().length() > 0);
    }



}
