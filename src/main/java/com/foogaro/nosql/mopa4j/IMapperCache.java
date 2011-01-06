package com.foogaro.nosql.mopa4j;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author Luigi Fugaro
 * @version 1.0.1
 * @since 1.0
 */
public interface IMapperCache {

    public String[] getFields(ADocumentObject aDocumentObject);
    public Object getFieldValue(ADocumentObject aDocumentObject, String fieldName);
    public Class getFieldClass(ADocumentObject aDocumentObject, String fieldName);
    public String getFieldClassName(ADocumentObject aDocumentObject, String fieldName);
    public Method getGetter(ADocumentObject aDocumentObject, String fieldName);
    public Method getSetter(ADocumentObject aDocumentObject, String fieldName);
    public Type getCollectionType(ADocumentObject aDocumentObject, String fieldName);
    public Type getCollectionArgumentType(ADocumentObject aDocumentObject, String fieldName);
    public boolean isCustom(ADocumentObject aDocumentObject, String fieldName);
    public boolean isCollection(ADocumentObject aDocumentObject, String fieldName);
    public boolean isSomeDBReferenced(ADocumentObject aDocumentObject);
    public boolean isDbReferenced(ADocumentObject aDocumentObject, String fieldName);

}
