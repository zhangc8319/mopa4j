package com.foogaro.nosql.mopa4j.query;

import java.util.List;

/**
* @author Luigi Fugaro
* @version 1.0
* @since 1.0
*/
public interface IQueryManager {

    public List find(Class classType);
    public List find(QueryObject queryObject, Class classType);
    public Object findOne(QueryObject queryObject, Class classType);

}
