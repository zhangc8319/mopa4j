package com.foogaro.nosql.mopa4j.query;

import com.foogaro.nosql.mopa4j.ABaseManager;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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
        List<DBObject> results = new ArrayList<DBObject>();
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
            results.add(dbCursor.next());
        }
        return results;
    }

    public DBObject findOne(LinkedList<DBObject> dbObjects, Class classType) {
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
            return dbCursor.next();
        }
        return null;
    }

}
