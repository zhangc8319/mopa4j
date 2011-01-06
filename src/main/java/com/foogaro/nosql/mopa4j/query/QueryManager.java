package com.foogaro.nosql.mopa4j.query;

import com.foogaro.nosql.mopa4j.ABaseManager;
import com.foogaro.nosql.mopa4j.exception.PersistenceManagerException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public abstract class QueryManager extends ABaseManager implements IQueryManager {

    private static final Logger log = LoggerFactory.getLogger(QueryManager.class);

//    public QueryManager() {
//        super();
//
//        try {
//            Type gs = getClass().getGenericSuperclass();
//            ParameterizedType pt;
//            if (gs instanceof ParameterizedType) {
//                pt = (ParameterizedType) gs;
//                Type[] ata = pt.getActualTypeArguments();
//                if (ata != null && ata.length > 0) {
//                    try {
//                        this.classType = (Class<T>) ata[0];
//                    } catch (Throwable t) {
//                        log.error("Error while retrieving Generic Class type", t);
//                    }
//                }
//
//                if (ata != null && ata.length > 1) {
//                    try {
//                        this.classType = (Class<T>) ata[1];
//                    } catch (Throwable t) {
//                        log.error("Error while retrieving next Generic Class type", t);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.error("Error while configuring BaseManager", e);
//            throw new PersistenceManagerException(e);
//        }
//
//        log.debug("QueryManager created");
//    }


    public List find(Class classType) {
        return find(null, classType);
    }

    public List find(QueryObject queryObject, Class classType) {
        List results = new ArrayList();
        DBObject query = new BasicDBObject();

        if (queryObject != null) {
            LinkedList<DBObject> dbObjects = queryObject.getQueryParameters();
            DBObject dbObject = null;
            while (dbObjects.size() > 0) {
                dbObject = dbObjects.poll();
                if (dbObject != null) {
                    query.putAll(dbObject.toMap());
                }
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

    public Object findOne(QueryObject queryObject, Class classType) {
        List results = new ArrayList();
        DBObject query = new BasicDBObject();
        if (queryObject != null) {
            LinkedList<DBObject> dbObjects = queryObject.getQueryParameters();
            DBObject dbObject = null;
            while (dbObjects.size() > 0) {
                dbObject = dbObjects.poll();
                if (dbObject != null) {
                    query.putAll(dbObject.toMap());
                }
            }
        }
        DBCursor dbCursor = getDBCollection(classType).find(query);
        while (dbCursor.hasNext()) {
//            return (T) mappingHelper.toADocumentObject(dbCursor.next(), classType);
        }
        return null;
    }

}
