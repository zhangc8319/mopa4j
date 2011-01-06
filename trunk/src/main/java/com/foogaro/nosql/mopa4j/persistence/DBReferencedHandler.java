package com.foogaro.nosql.mopa4j.persistence;

import com.foogaro.nosql.mopa4j.ABaseManager;
import com.foogaro.nosql.mopa4j.ADocumentObject;
import com.foogaro.nosql.mopa4j.DBManager;
import com.foogaro.nosql.mopa4j.DBReferencedObject;
import com.mongodb.*;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: luigi
 * Date: Jan 6, 2011
 * Time: 12:13:48 PM
 */
public class DBReferencedHandler {

    private static final Logger log = LoggerFactory.getLogger(DBReferencedHandler.class);

    @Autowired
    private DBManager dbManager;
    
    public DBReferencedHandler() {
        log.debug("DBReferencedHandler created");
    }

    public DBObject getDBObject(DBObject dbObject) {
        return null;
    }

    public DBObject getDBObject(ADocumentObject aDocumentObject) {
        return null;
    }

    public ADocumentObject getADocumentObject(DBObject dbObject) {
        return null;
    }

    public ADocumentObject getADocumentObject(ADocumentObject aDocumentObject) {
        return null;
    }

    public DBRef getDBRef(DBObject dbObject) {
        return null;
    }

    public DBRef getDBRef(ADocumentObject aDocumentObject) {
        return null;
    }

    public DBObject read(DBObject dbObject, Class type) {
        log.debug("Reading referenced document " + dbObject);
        DBRef dbRef = new DBRef(dbManager.getDB(), type.getName(), dbObject.get("_id"));
        DBObject result = dbRef.fetch();
        return result;
    }

    public DBObject insert(DBObject dbObject, Class type) {
        log.debug("Creating referenced document " + dbObject);
        dbManager.getDBCollection(type.getName()).insert(dbObject);
        dbObject = createDBReferencedObject(dbObject, type);
        return dbObject;
    }

    private DBObject createDBReferencedObject(DBObject newDbObject, Class type) {
        DBReferencedObject dbReferencedObject = new DBReferencedObject();
        dbReferencedObject.setRef(type.getName());
        dbReferencedObject.set_id((ObjectId) newDbObject.get("_id"));
        return dbReferencedObject;
    }


}
