package com.foogaro.nosql.mopa4j.query;

import com.mongodb.DBObject;

import java.util.LinkedList;
import java.util.List;

/**
* @author Luigi Fugaro
* @version 1.0
* @since 1.0
*/
public interface IQueryManager {

    public List<DBObject> find(Class classType);
    public List<DBObject> find(LinkedList<DBObject> dbObjects, Class classType);
    public DBObject findOne(LinkedList<DBObject> dbObjects, Class classType);

}
