package com.foogaro.nosql.mopa4j;

import com.foogaro.nosql.mopa4j.annotation.DBReferenced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Luigi Fugaro
 * @version 1.0.1
 * @since 1.0
 */
public class CacheObject {

    private static final Logger log = LoggerFactory.getLogger(CacheObject.class);

    private Map<String, FieldCacheObject> cache = new HashMap<String, FieldCacheObject>();
    private boolean someDBReferenced = false;

    public String[] getFields() {
        return (String[])cache.keySet().toArray(new String[cache.size()]);
    }

    public Object getFieldValue(ADocumentObject aDocumentObject, String fieldName) {
        FieldCacheObject fieldCacheObject = cache.get(fieldName);

        Object value = null;
        try {
            value = fieldCacheObject.getGetter().invoke(aDocumentObject);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
        }

        return value;
    }

    public Class getFieldClass(String fieldName) {
        return (Class)cache.get(fieldName).getFieldType();
    }

    public String getFieldClassName(String fieldName) {
        return cache.get(fieldName).getClassName();
    }

    public Method getGetter(String fieldName) {
        return cache.get(fieldName).getGetter();
    }

    public Method getSetter(String fieldName) {
        return cache.get(fieldName).getSetter();
    }

    public Type getCollectionType(String fieldName) {
        return cache.get(fieldName).getCollectionType();
    }

    public Type getCollectionArgumentType(String fieldName) {
        return cache.get(fieldName).getCollectionArgumentType();
    }

    public boolean isCustom(String fieldName) {
        return cache.get(fieldName).isCustom();
    }

    public boolean isCollection(String fieldName) {
        return cache.get(fieldName).isCollection();
    }

    public boolean isDbReferenced(String fieldName) {
        return cache.get(fieldName).isDbReferenced();
    }

    protected FieldCacheObject newFieldCacheObject(ADocumentObject aDocumentObject, Field field) {
        FieldCacheObject fieldCacheObject = new FieldCacheObject();
        fieldCacheObject.setName(field.getName());
        fieldCacheObject.setFieldType(field.getType());
        fieldCacheObject.setClassName(((Class)fieldCacheObject.getFieldType()).getName());
        fieldCacheObject.setGetter(getGetterMethod(aDocumentObject, fieldCacheObject));
        fieldCacheObject.setSetter(getSetterMethod(aDocumentObject, fieldCacheObject));
        fieldCacheObject.setDbReferenced(field.isAnnotationPresent(DBReferenced.class));

        cache.put(fieldCacheObject.getName(), fieldCacheObject);
        this.someDBReferenced = someDBReferenced || fieldCacheObject.isDbReferenced();

        return fieldCacheObject;
    }

    private Method getGetterMethod(ADocumentObject aDocumentObject, FieldCacheObject fieldCacheObject) {
        Method method = null;
        try {
            method = aDocumentObject.getClass().getMethod(getGetterMethodName(fieldCacheObject));
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }
        return method;
    }

    private Method getSetterMethod(ADocumentObject aDocumentObject, FieldCacheObject fieldCacheObject) {
        Method method = null;
        try {
            if (fieldCacheObject.isCollection()){
                method = aDocumentObject.getClass().getMethod(getSetterMethodName(fieldCacheObject), (Class)fieldCacheObject.getCollectionType());
            } else {
                method = aDocumentObject.getClass().getMethod(getSetterMethodName(fieldCacheObject), (Class)fieldCacheObject.getFieldType());
            }
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        }
        return method;
    }

    private String getGetterMethodName(FieldCacheObject fieldCacheObject) {
        boolean isBoolean = false;
        if (fieldCacheObject.getClassName().equals(Boolean.class.getName())) {
            isBoolean = true;
        }
        if (isBoolean) {
            return "is" + fieldCacheObject.getName().substring(0,1).toUpperCase() + fieldCacheObject.getName().substring(1);
        } else {
            return "get" + fieldCacheObject.getName().substring(0,1).toUpperCase() + fieldCacheObject.getName().substring(1);
        }
    }

    private String getSetterMethodName(FieldCacheObject fieldCacheObject) {
        boolean isBoolean = false;
        if (fieldCacheObject.getClassName().equals(Boolean.class.getName())) {
            isBoolean = true;
        }
        if (isBoolean) {
            return "set" + fieldCacheObject.getName().substring(0,1).toUpperCase() + fieldCacheObject.getName().substring(1);
        } else {
            return "set" + fieldCacheObject.getName().substring(0,1).toUpperCase() + fieldCacheObject.getName().substring(1);
        }
    }

    public boolean isSomeDBReferenced() {
        return someDBReferenced;
    }

}
