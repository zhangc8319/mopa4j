package com.foogaro.nosql.mopa4j;

import com.foogaro.nosql.mopa4j.exception.PersistenceManagerException;
import com.mongodb.CommandResult;
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
public abstract class ABaseManager<T extends ADocumentObject> {

    protected static final Logger log = LoggerFactory.getLogger(ABaseManager.class);

    @Autowired
    private DBManager dbManager;
    protected DBCollection dbCollection;
    protected Class<T> classType;
    protected Mapper mapper;

    @PostConstruct
    private void init() {
        log.debug("init");
        initClassType();
        log.debug("Generic Class type configured: " + classType.getName());
        mapper = new Mapper();
        log.debug("Mapper configured: " + mapper);
        dbCollection = dbManager.getDBCollection(classType.getName());
        log.debug("DBCollection retrieved: " + dbCollection);
    }

    private void initClassType() {
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
    }

    public DBManager getDbManager() {
        return dbManager;
    }

    public DBCollection getDbCollection() {
        return dbCollection;
    }

    public Mapper getMapper() {
        return mapper;
    }

    public CommandResult getLastError() {
        return dbManager.getDB().getLastError();
    }

    public CommandResult getStats() {
        return dbManager.getDB().getStats();
    }
}
