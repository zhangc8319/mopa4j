package com.foogaro.nosql.mopa4j.persistence;

import com.foogaro.nosql.mopa4j.ABaseManager;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Abstract class used for CRUD operations.
 * @author Luigi Fugaro
 * @version 1.0.1
 * @since 1.0
 */
@Repository
public class PersistenceManager extends ABaseManager implements IPersistenceManager {

    private static final Logger log = LoggerFactory.getLogger(PersistenceManager.class);

//    public PersistenceManager() {
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
//        log.debug("PersistenceManager created");
//    }

    public DBObject create(DBObject dbObject, Class classType) {
        log.debug("Creating document " + dbObject);
        getDBCollection(classType).insert(dbObject);
        return dbObject;
    }

    public DBObject read(DBObject dbObject, Class classType) {
        log.debug("Reading document " + dbObject);
        BasicDBObject query = new BasicDBObject();
        query.put("_id", dbObject.get("_id"));
        DBObject result = getDBCollection(classType).findOne(query);
        return result;
    }

    public DBObject update(DBObject dbObject, Class classType) {
        log.debug("Updating document " + dbObject);
        getDBCollection(classType).save(dbObject);
        return dbObject;
    }

    public void delete(DBObject dbObject, Class classType) {
        log.debug("Deleting document " + dbObject);
        getDBCollection(classType).remove(dbObject);
    }

}
