package com.foogaro.nosql.mopa4j.persistence;

import com.foogaro.nosql.mopa4j.ADocumentObject;


/**
 * Interface for CRUD operations.
 * @see PersistenceManager
 * @author Luigi Fugaro
 * @version 1.0
 * @since 1.0
 */
public interface IPersistenceManager<T extends ADocumentObject> {

    public T create(T aDocumentObject);
    public T read(T aDocumentObject);
    public T update(T aDocumentObject);
    public void delete(T aDocumentObject);
    
}
