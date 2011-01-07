package com.foogaro.nosql.mopa4j.persistence;

import com.foogaro.nosql.mopa4j.ABaseManager;
import com.mongodb.DBObject;
import com.mongodb.DBRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author Luigi Fugaro
 * @version 1.0.1
 * @since 1.0.1
 */
@Repository
public class DBReferenceManager extends ABaseManager {

    private static final Logger log = LoggerFactory.getLogger(DBReferenceManager.class);

    public DBReferenceManager() {
        log.debug("DBReferenceManager created");
    }

    public DBObject read(DBObject dbObject, Class type) {
        log.debug("Reading referenced document " + type.getName() + ": " + dbObject);
        DBRef dbRef = new DBRef(getDbManager().getDB(), type.getName(), dbObject.get("_id"));
        DBObject result = dbRef.fetch();
        return result;
    }

    public DBObject insert(DBObject dbObject, Class type) {
        log.debug("Creating referenced document " + type.getName() + ": " + dbObject);
        getDBCollection(type).insert(dbObject);
//        dbObject.putAll(toMap(createDBReferencedObject(dbObject, type));
        return dbObject;
    }

//    private DBReferencedObject createDBReferencedObject(DBObject newDbObject, Class type) {
//        DBReferencedObject dbReferencedObject = new DBReferencedObject();
//        dbReferencedObject.setRef(type.getName());
//        dbReferencedObject.set_id((ObjectId) newDbObject.get("_id"));
//        return dbReferencedObject;
//    }


}
