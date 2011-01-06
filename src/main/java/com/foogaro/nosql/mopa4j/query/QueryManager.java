package com.foogaro.nosql.mopa4j.query;

import com.foogaro.nosql.mopa4j.ABaseManager;
import com.foogaro.nosql.mopa4j.ADocumentObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Luigi Fugaro
 * @version 1.0
 * @since 1.0
 */
public abstract class QueryManager<T extends ADocumentObject> extends ABaseManager<T> implements IQueryManager<T> {

    private static final Logger log = LoggerFactory.getLogger(QueryManager.class);

    public QueryManager() {
        super();
        log.debug("QueryManager created");
    }


    public List<T> find() {
        return find(null);
    }
    
    public List<T> find(QueryObject queryObject) {
        List<T> results = new ArrayList<T>();
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
        DBCursor dbCursor = dbCollection.find(query);
        DBObject dbCursorElement = null;
        while (dbCursor.hasNext()) {
            dbCursorElement = dbCursor.next();
            results.add((T)mapper.toADocumentObject(dbCursorElement, classType));
        }
        return results;
    }

    public T findOne(QueryObject queryObject) {
        List<T> results = new ArrayList<T>();
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
        DBCursor dbCursor = dbCollection.find(query);
        while (dbCursor.hasNext()) {
            return (T)mapper.toADocumentObject(dbCursor.next(), classType);
        }
        return null;
    }

}
