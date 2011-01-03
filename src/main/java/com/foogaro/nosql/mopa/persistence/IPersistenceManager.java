package com.foogaro.nosql.mopa.persistence;

import com.foogaro.nosql.mopa.ADocumentObject;


/**
 * Interface for CRUD operations.
 * @see com.foogaro.nosql.mopa.persistence.PersistenceManager
 * @author Luigi Fugaro
 * @version 1.0
 * @since 1.0
 */
public interface IPersistenceManager<T extends ADocumentObject> {

    public T create(T aDBObject);
    public T read(T aDBObject);
    public T update(T aDBObject);
    public void delete(T aDBObject);
    
}
