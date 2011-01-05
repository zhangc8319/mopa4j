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

    public T create(T aDocumentObject);
    public T read(T aDocumentObject);
    public T update(T aDocumentObject);
    public void delete(T aDocumentObject);
    
}