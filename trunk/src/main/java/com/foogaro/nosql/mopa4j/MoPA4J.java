package com.foogaro.nosql.mopa4j;

import com.foogaro.nosql.mopa4j.persistence.DBReferenceManager;
import com.foogaro.nosql.mopa4j.persistence.IPersistenceManager;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
