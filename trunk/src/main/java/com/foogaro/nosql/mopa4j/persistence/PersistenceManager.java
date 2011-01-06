package com.foogaro.nosql.mopa4j.persistence;

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
 * @version 1.0.1
 * @since 1.0
 */
public abstract class PersistenceManager<T extends ADocumentObject> extends ABaseManager<T> implements IPersistenceManager<T> {

    private static final Logger log = LoggerFactory.getLogger(PersistenceManager.class);

    public PersistenceManager() {
        super();
        log.debug("PersistenceManager created");
    }

    public T create(T aDocumentObject) {
        log.debug("Creating document " + aDocumentObject);
        DBObject dbObject = mapper.toDBObject(aDocumentObject);
        dbCollection.insert(dbObject);
        mapper.toADocumentObject(dbObject, aDocumentObject);
        return aDocumentObject;
    }

    public T read(T aDocumentObject) {
        log.debug("Reading document " + aDocumentObject);
        BasicDBObject query = new BasicDBObject();
        query.put("_id", aDocumentObject.get("_id"));
        DBCursor dbCursor = dbCollection.find(query);

        Object result = null;
        while (dbCursor.hasNext()) {
            result = dbCursor.next();
            result = mapper.toADocumentObject((DBObject)result, classType);
            return (T)result;
        }

        return null;
    }

    public T update(T aDocumentObject) {
        log.debug("Updating document " + aDocumentObject);
        dbCollection.save(aDocumentObject);
        return aDocumentObject;
    }

    public void delete(T aDocumentObject) {
        log.debug("Deleting document " + aDocumentObject);
        dbCollection.remove(aDocumentObject);
    }

}
