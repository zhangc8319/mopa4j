package com.foogaro.nosql.mopa4j.query;

import com.foogaro.nosql.mopa4j.ABaseManager;
import com.foogaro.nosql.mopa4j.exception.PersistenceManagerException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
* @author Luigi Fugaro
* @version 1.0
* @since 1.0
*/
@Repository
public class QueryManager extends ABaseManager implements IQueryManager {

    private static final Logger log = LoggerFactory.getLogger(QueryManager.class);

    public List<DBObject> find(Class classType) {
        return find(null, classType);
    }

    public List<DBObject> find(LinkedList<DBObject> dbObjects, Class classType) {
        List results = new ArrayList();
        DBObject query = new BasicDBObject();

        DBObject dbObject = null;
        while (dbObjects.size() > 0) {
            dbObject = dbObjects.poll();
            if (dbObject != null) {
                query.putAll(dbObject.toMap());
            }
        }
        DBCursor dbCursor = getDBCollection(classType).find(query);
        DBObject dbCursorElement = null;
        while (dbCursor.hasNext()) {
            dbCursorElement = dbCursor.next();
//            results.add((T) mappingHelper.toADocumentObject(dbCursorElement, classType));
        }
        return results;
    }

    public DBObject findOne(LinkedList<DBObject> dbObjects, Class classType) {
        List results = new ArrayList();
        DBObject query = new BasicDBObject();
        DBObject dbObject = null;
        while (dbObjects.size() > 0) {
            dbObject = dbObjects.poll();
            if (dbObject != null) {
                query.putAll(dbObject.toMap());
            }
        }
        DBCursor dbCursor = getDBCollection(classType).find(query);
        while (dbCursor.hasNext()) {
//            return (T) mappingHelper.toADocumentObject(dbCursor.next(), classType);
        }
        return null;
    }

}
