package com.foogaro.nosql.mopa4j.persistence;

import com.foogaro.nosql.mopa4j.ABaseManager;
import com.foogaro.nosql.mopa4j.ADocumentObject;
import com.foogaro.nosql.mopa4j.exception.PersistenceManagerException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Abstract class used for CRUD operations.
 * @author Luigi Fugaro
 * @version 1.0.1
 * @since 1.0
 */
public abstract class PersistenceManager<T extends ADocumentObject> extends ABaseManager implements IPersistenceManager<T> {

    private static final Logger log = LoggerFactory.getLogger(PersistenceManager.class);
    private Class<T> classType;

    public PersistenceManager() {
        super();

        try {
            Type gs = getClass().getGenericSuperclass();
            ParameterizedType pt;
            if (gs instanceof ParameterizedType) {
                pt = (ParameterizedType) gs;
                Type[] ata = pt.getActualTypeArguments();
                if (ata != null && ata.length > 0) {
                    try {
                        this.classType = (Class<T>) ata[0];
                    } catch (Throwable t) {
                        log.error("Error while retrieving Generic Class type", t);
                    }
                }

                if (ata != null && ata.length > 1) {
                    try {
                        this.classType = (Class<T>) ata[1];
                    } catch (Throwable t) {
                        log.error("Error while retrieving next Generic Class type", t);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error while configuring BaseManager", e);
            throw new PersistenceManagerException(e);
        }

        log.debug("PersistenceManager created");
    }

    public T create(T aDocumentObject) {
        log.debug("Creating document " + aDocumentObject);
        DBObject dbObject = mappingHelper.toDBObject(aDocumentObject);
        getDbCollection(classType).insert(dbObject);
        mappingHelper.toADocumentObject(dbObject, aDocumentObject);
        return aDocumentObject;
    }

    public T read(T aDocumentObject) {
        log.debug("Reading document " + aDocumentObject);
        BasicDBObject query = new BasicDBObject();
        query.put("_id", aDocumentObject.get("_id"));
        DBCursor dbCursor = getDbCollection(classType).find(query);

        Object result = null;
        while (dbCursor.hasNext()) {
            result = dbCursor.next();
            result = mappingHelper.toADocumentObject((DBObject)result, classType);
            return (T)result;
        }

        return null;
    }

    public T update(T aDocumentObject) {
        log.debug("Updating document " + aDocumentObject);
        getDbCollection(aDocumentObject.getClass()).save(aDocumentObject);
        return aDocumentObject;
    }

    public void delete(T aDocumentObject) {
        log.debug("Deleting document " + aDocumentObject);
        getDbCollection(aDocumentObject.getClass()).remove(aDocumentObject);
    }

}
