package com.foogaro.nosql.mopa.persistence;

import com.foogaro.nosql.mopa.ABaseManager;
import com.foogaro.nosql.mopa.ADocumentObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class used for CRUD operations.
 * @author Luigi Fugaro
 * @version 1.0
 * @since 1.0
 */
public abstract class PersistenceManager<T extends ADocumentObject> extends ABaseManager<T> implements IPersistenceManager<T> {

    private static final Logger log = LoggerFactory.getLogger(PersistenceManager.class);

    public PersistenceManager() {
        super();
        log.debug("PersistenceManager created");
    }

    public T create(T aDBObject) {
        log.debug("Creating document " + aDBObject);
        dbCollection.insert(mapper.toDBObject(aDBObject));
        return aDBObject;
    }

    public T read(T aDBObject) {
        log.debug("Reading document " + aDBObject);
        BasicDBObject query = new BasicDBObject();
        query.put("_id", aDBObject.get("_id"));
        DBCursor dbCursor = dbCollection.find(query);

        Object result = null;
        while (dbCursor.hasNext()) {
            result = dbCursor.next();
            result = mapper.toADocumentObject((DBObject)result, classType);
            return (T)result;
        }

        return null;
    }

    public T update(T aDBObject) {
        log.debug("Updating document " + aDBObject);
        dbCollection.save(aDBObject);
        return aDBObject;
    }

    public void delete(T aDBObject) {
        log.debug("Deleting document " + aDBObject);
        dbCollection.remove(aDBObject);
    }

}
