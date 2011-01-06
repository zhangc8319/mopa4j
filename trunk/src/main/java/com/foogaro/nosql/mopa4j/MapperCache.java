package com.foogaro.nosql.mopa4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Luigi Fugaro
 * @version 1.0.1
 * @since 1.0
 */
public class MapperCache implements IMapperCache {

    private Map<String, CacheObject> cache = new HashMap<String, CacheObject>();

    public String[] getFields(ADocumentObject aDocumentObject) {
        CacheObject cacheObject = get(aDocumentObject);
        return cache.get(unique(aDocumentObject)).getFields();
    }

    public Object getFieldValue(ADocumentObject aDocumentObject, String fieldName) {
        CacheObject cacheObject = get(aDocumentObject);
        return cacheObject.getFieldValue(aDocumentObject, fieldName);
    }

    public Class getFieldClass(ADocumentObject aDocumentObject, String fieldName) {
        CacheObject cacheObject = get(aDocumentObject);
        return cacheObject.getFieldClass(fieldName);
    }

    public String getFieldClassName(ADocumentObject aDocumentObject, String fieldName) {
        CacheObject cacheObject = get(aDocumentObject);
        return cacheObject.getFieldClassName(fieldName);
    }

    public Method getGetter(ADocumentObject aDocumentObject, String fieldName) {
        CacheObject cacheObject = get(aDocumentObject);
        return cacheObject.getGetter(fieldName);
    }

    public Method getSetter(ADocumentObject aDocumentObject, String fieldName) {
        CacheObject cacheObject = get(aDocumentObject);
        return cacheObject.getSetter(fieldName);
    }

    public Type getCollectionType(ADocumentObject aDocumentObject, String fieldName) {
        CacheObject cacheObject = get(aDocumentObject);
        return cacheObject.getCollectionType(fieldName);
    }

    public Type getCollectionArgumentType(ADocumentObject aDocumentObject, String fieldName) {
        CacheObject cacheObject = get(aDocumentObject);
        return cacheObject.getCollectionArgumentType(fieldName);
    }

    public boolean isCustom(ADocumentObject aDocumentObject, String fieldName) {
        CacheObject cacheObject = get(aDocumentObject);
        return cacheObject.isCustom(fieldName);
    }

    public boolean isCollection(ADocumentObject aDocumentObject, String fieldName) {
        CacheObject cacheObject = get(aDocumentObject);
        return cacheObject.isCollection(fieldName);
    }

    private CacheObject get(ADocumentObject aDocumentObject) {
        CacheObject cacheObject = cache.get(unique(aDocumentObject));
        if (cacheObject == null) {
            cacheObject = newCacheObject(aDocumentObject);
        }

        return cacheObject;
    }

    private String unique(ADocumentObject aDocumentObject) {
        return aDocumentObject.getClass().getName();
    }

    protected CacheObject newCacheObject(ADocumentObject aDocumentObject) {
        CacheObject cacheObject = new CacheObject();
        Field[] fields = aDocumentObject.getClass().getDeclaredFields();
        for (Field field : fields) {
            cacheObject.newFieldCacheObject(aDocumentObject, field);
        }
        cache.put(unique(aDocumentObject), cacheObject);

        return cacheObject;
    }
}
