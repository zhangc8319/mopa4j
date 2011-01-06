package com.foogaro.nosql.mopa4j;

import com.foogaro.nosql.mopa4j.persistence.DBReferenceManager;
import com.foogaro.nosql.mopa4j.persistence.IPersistenceManager;
import com.foogaro.nosql.mopa4j.query.IQueryManager;
import com.foogaro.nosql.mopa4j.query.QueryObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
