package com.foogaro.nosql.mopa4j;

import com.foogaro.nosql.mopa4j.exception.PersistenceManagerException;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * This class is used as base class for thoseones which need direct access to the MongoDB. 
 * @author Luigi Fugaro
 * @version 1.0
 * @since 1.0
 */
public class ABaseManager {

    protected static final Logger log = LoggerFactory.getLogger(ABaseManager.class);

    @Autowired
    private DBManager dbManager;

    @Autowired
    protected MappingHelper mappingHelper;

    public DBManager getDbManager() {
        return dbManager;
    }

    public DBCollection getDbCollection(Class type) {
        return dbManager.getDBCollection(type.getName());
    }

    public CommandResult getLastError() {
        return dbManager.getDB().getLastError();
    }

    public CommandResult getStats() {
        return dbManager.getDB().getStats();
    }
}
