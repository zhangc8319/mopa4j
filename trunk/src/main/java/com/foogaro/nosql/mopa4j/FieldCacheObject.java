package com.foogaro.nosql.mopa4j;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Luigi Fugaro
 * @version 1.0.1
 * @since 1.0.1
 */
public class FieldCacheObject {

    private String name;
    private Method getter;
    private Method setter;
    private String className;

    //Used for collections
    private Type fieldType;
    private Type collectionType;
    private Type collectionArgumentType;
    private boolean custom = false;
    private boolean collection = false;
    private boolean dbReferenced = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Method getGetter() {
        return getter;
    }

    public void setGetter(Method getter) {
        this.getter = getter;
    }

    public Method getSetter() {
        return setter;
    }

    public void setSetter(Method setter) {
        this.setter = setter;
    }

    public Type getFieldType() {
        return fieldType;
    }

    public void setFieldType(Type fieldType) {
        this.fieldType = fieldType;
        if (this.fieldType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) this.fieldType;
            Type[] types = parameterizedType.getActualTypeArguments();
            if (types.length == 1) {
                this.collection = true;
                this.collectionType = parameterizedType.getRawType();
                this.collectionArgumentType = types[0];
            }
        }
        if (((Class)this.fieldType).getPackage().getName().indexOf("java.") == 0) {
            this.custom = false;
        } else {
            this.custom = true;
        }
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Type getCollectionType() {
        return collectionType;
    }

    public Type getCollectionArgumentType() {
        return collectionArgumentType;
    }

    public boolean isCustom() {
        return custom;
    }

    public boolean isCollection() {
        return collection;
    }

    public boolean isDbReferenced() {
        return dbReferenced;
    }

    public void setDbReferenced(boolean dbReferenced) {
        this.dbReferenced = dbReferenced;
    }
}
