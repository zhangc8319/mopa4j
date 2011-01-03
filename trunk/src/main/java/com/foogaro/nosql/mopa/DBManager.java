package com.foogaro.nosql.mopa;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.net.UnknownHostException;

/**
 * This class is used as singleton to wrap the access to the MongoDB. 
 * @author Luigi Fugaro
 * @version 1.0
 * @since 1.0
 */
public class DBManager {

    private static final Logger log = LoggerFactory.getLogger(DBManager.class);

    @Autowired
    private Mongo mongo;
    private DB db;
    private String dbName;

    public DBManager() {
    }

    @PostConstruct
    private void init() {
        log.debug("init");
        if (dbName == null || dbName.trim().length() == 0) {
            throw new IllegalStateException("Check your configuration for database name (DBManager - property dbName)");
        }
        
        db = mongo.getDB(dbName);
        log.debug("DB retrieved: " + db);
    }

    public DB getDB() {
        return db;
    }

    public DBCollection getDBCollection(String collectionName) {
        if (db == null) {
            throw new IllegalStateException("Check your configuration about Mongo DB declaration");
        }

        return db.getCollection(collectionName);
    }

    public Mongo getMongo() {
        return mongo;
    }

    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
