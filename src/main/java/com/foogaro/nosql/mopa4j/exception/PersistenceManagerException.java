package com.foogaro.nosql.mopa4j.exception;

/**
 * @author Luigi Fugaro
 * @version 1.0
 * @since 1.0
 */
public class PersistenceManagerException extends RuntimeException {

    public PersistenceManagerException() {
        super();
    }

    public PersistenceManagerException(String message) {
        super(message);
    }

    public PersistenceManagerException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public PersistenceManagerException(Throwable throwable) {
        super(throwable);
    }

}
