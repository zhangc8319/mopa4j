package com.foogaro.nosql.mopa;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.*;

/**
 * This class gives you the capability to convert any POJO to a DBObject and vice-versa.
 * @author Luigi Fugaro
 * @version 1.0
 * @since 1.0
 */
public class Mapper {

    private static final Logger log = LoggerFactory.getLogger(Mapper.class);

    public Map<String, Object> toMQL(ADocumentObject aDocumentObject) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.putAll(toMap(aDocumentObject));

        Set<String> keys = map.keySet();
        Object value = null;

        if (keys != null && !keys.isEmpty()) {
            for (String key : keys) {
                value = map.get(key);
                if (value != null){
                    if (value instanceof BasicDBObject) {
                        map.remove(key);
                        map.putAll(toMQL((BasicDBObject)value, key));
                    }
                }
            }
        }
        
        return map;
    }

    private Map<String, Object> toMQL(BasicDBObject basicDBObject, String parentKey) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.putAll(basicDBObject.toMap());

        Set<String> keys = map.keySet();
        Object value = null;
        String newKey = null;
        if (keys != null && !keys.isEmpty()) {
            for (String key : keys) {
                value = map.get(key);
                if (value != null){
                    newKey = parentKey+"."+key;
                    if (value instanceof BasicDBObject) {
                        map.remove(key);
                        map.putAll(toMQL((BasicDBObject)value, newKey));
                    } else {
                        result.put(newKey, value);
                    }
                }
            }
        }

        return result;
    }

    public ADocumentObject toADocumentObject(DBObject dbObject, Class clazz) {

        Map<String, Object> map = dbObject.toMap();
        Set<String> keys = map.keySet();

        Object value = null;
        ADocumentObject aDocumentObject = null;
        try {
            aDocumentObject = (ADocumentObject) clazz.newInstance();
        } catch (InstantiationException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }

        Class cls = null;
        for (String key : keys) {
            value = map.get(key);
            if (value instanceof BasicDBObject) {
                try {
                    Field field = aDocumentObject.getClass().getDeclaredField(key);
                    cls = field.getType();
                    ADocumentObject bm = toADocumentObject((DBObject)value, cls);
                    invokeSetter(aDocumentObject, key, bm);
                } catch (NoSuchFieldException e) {
                    log.error(e.getMessage(), e);
                }
            } else if (value instanceof BasicDBList) {
                try {
                    Field field = aDocumentObject.getClass().getDeclaredField(key);
                    Type type = field.getGenericType();
                    if (type instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                        Type[] types = parameterizedType.getActualTypeArguments();
                        if (types.length == 1) {
                            Type typeArgument = types[0];
                            if (Set.class.toString().equals(parameterizedType.getRawType().toString())) {
                                Set set = toSet((BasicDBList)value, typeArgument);
                                invokeSetterWithInterface(aDocumentObject, key, (Set)set);
                            } else if (List.class.toString().equals(parameterizedType.getRawType().toString())) {
                                List list = toList((BasicDBList)value, typeArgument);
                                invokeSetterWithInterface(aDocumentObject, key, list);
                            }
                        }
                    }
                } catch (NoSuchFieldException e) {
                    log.error(e.getMessage(), e);
                }
            } else {
                invokeSetter(aDocumentObject, key, value);
            }
        }
        return aDocumentObject;
    }

    private Set toSet(BasicDBList basicDBList, Type type) {
        Set set = null;
        BasicDBObject basicDBObject = null;

        Object obj = null;
        try {
            set = (Set)(new HashSet());
            obj = ((Class)type).newInstance();

            if (basicDBList != null && !basicDBList.isEmpty()) {
                for (Object object : basicDBList) {
                    basicDBObject = (BasicDBObject)object;
                    Map<String, Object> map = basicDBObject.toMap();
                    Set<String> keys = map.keySet();
                    for (String key : keys) {
                        setValue(obj, key, map.get(key));
                    }
                    set.add(obj);
                }
            }
        } catch (InstantiationException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }

        return set;
    }

    private List toList(BasicDBList basicDBList, Type type) {
        List list = new ArrayList();
        if (basicDBList != null && !basicDBList.isEmpty()) {
            for (Object object : basicDBList) {

            }
        }

        return list;
    }

    private void invokeSetter(ADocumentObject aDocumentObject, String key, Object value) {
        try {
            String setterMethod = fieldSetterMethodName(key);
            log.debug("setterMethod: " + setterMethod);
            log.debug("Parameter: " + value);
            Method method = aDocumentObject.getClass().getMethod(setterMethod, value.getClass());
            log.debug("Method: " + method);
            method.invoke(aDocumentObject, value);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void invokeSetterWithInterface(ADocumentObject aDocumentObject, String key, Object value) {
        try {
            String setterMethod = fieldSetterMethodName(key);
            log.debug("setterMethod: " + setterMethod);
            Type[] types = value.getClass().getGenericInterfaces();
            Class parameterClass = types != null && types.length > 0 ? (Class)((ParameterizedType)types[0]).getRawType() : value.getClass();
            log.debug("parameterClass: " + parameterClass);
            Method method = aDocumentObject.getClass().getMethod(setterMethod, parameterClass);
            log.debug("Method: " + method);
            log.debug("Parameter: " + value);
            method.invoke(aDocumentObject, value);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
    }

    public DBObject toDBObject(ADocumentObject aDocumentObject) {
        BasicDBObjectBuilder basicDBObjectBuilder = BasicDBObjectBuilder.start();
        basicDBObjectBuilder.get().putAll(toMap(aDocumentObject));
        return basicDBObjectBuilder.get();
    }

    public DBObject toDBObject(Map<String, Object> map) {
        BasicDBObjectBuilder basicDBObjectBuilder = BasicDBObjectBuilder.start(map);
        return basicDBObjectBuilder.get();
    }

    public Map<String, Object> toMap(ADocumentObject aDocumentObject) {
        if (aDocumentObject != null) {
            Field[] fields = aDocumentObject.getClass().getDeclaredFields();
            if (fields != null && fields.length > 0) {
                return processFields(aDocumentObject, fields);
            }
        }
        return null;
    }

//    public Map<String, Object> toMap(DBObject dbObject) {
//        return dbObject.toMap();
//    }

    private Map<String, Object> processFields(ADocumentObject aDocumentObject, Field[] fields) {

        Map<String, Object> documentMap = new HashMap<String, Object>();
        Method method = null;
        String methodName = null;
        Type fieldType = null;
        Class fieldClass = null;
        String fieldName = null;
        String className = null;
        Object fieldValue = null;

        try {
            for (Field field : fields) {
                if (field != null) {
                    fieldName = field.getName();
                    methodName = fieldGetterMethodName(field);
                    fieldType = field.getType();
                    className = fieldType.toString();
                    className = className.substring(className.indexOf(" ")+1);
                    try {
                        fieldClass = Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        log.error(e.getMessage(), e);
                    }

                    try {
                        method = aDocumentObject.getClass().getMethod(methodName);
                        fieldValue = method.invoke(aDocumentObject);
                    } catch (NoSuchMethodException e) {
                        log.error(e.getMessage(), e);
                    } catch (InvocationTargetException e) {
                        log.error(e.getMessage(), e);
                    } catch (IllegalAccessException e) {
                        log.error(e.getMessage(), e);
                    }

                    if (fieldValue != null) {
                        if (fieldValue.getClass().isArray()) {
                            Object[] objects =  (Object[])fieldValue;
                            BasicDBList basicDBList = new BasicDBList();
                            for (Object obj : objects) {
                                basicDBList.add(obj);
                            }
                            documentMap.put(fieldName, basicDBList);
                        } else if (List.class.isInstance(fieldValue)) {
                            List list = (List)fieldValue;
                            BasicDBList basicDBList = new BasicDBList();
                            for (Object obj : list) {
                                basicDBList.add(obj);
                            }
                            documentMap.put(fieldName, basicDBList);
                        } else if (Set.class.isInstance(fieldValue)) {
                            Set set = (Set)fieldValue;
                            BasicDBList basicDBList = new BasicDBList();
                            for (Object obj : set) {
                                basicDBList.add(obj);
                            }
                            documentMap.put(fieldName, basicDBList);
                        } else if (java.util.Date.class.getName().equals(className)) {
                            documentMap.put(fieldName, fieldValue);
                        } else if (org.bson.types.ObjectId.class.getName().equals(className)) {
                            documentMap.put(fieldName, fieldValue);
                        } else if (fieldClass.isPrimitive() || isJVM(fieldClass)) {
                            documentMap.put(fieldName, fieldValue);
                        } else {
                            log.debug("Looks like we have a custom type: " + className);
                            documentMap.put(fieldName, toDBObject((ADocumentObject)fieldValue));
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("method: " + method);
            log.error("methodName: " + methodName);
            log.error("fieldType: " + fieldType);
            log.error("fieldClass: " + fieldClass);
            log.error("fieldName: " + fieldName);
            log.error("className: " + className);
            log.error("fieldValue: " + fieldValue);
            log.error(e.getMessage(), e);
        }

        return documentMap;
    }

    private Class getFieldType(Object instance, String fieldName) {
        Class result = Object.class;
        Class clazz = instance.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (fieldName.equals(field.getName())) {
                result = field.getType();
                break;
            }
        }
        return result;
    }

    public Object setValue(Object instance, String fieldName, Object value) {
        try {
            String fieldSetterMethodName = fieldSetterMethodName(fieldName);
            Class parameterClass = getFieldType(instance, fieldName);
            Method method = instance.getClass().getMethod(fieldSetterMethodName, parameterClass);
            return method.invoke(instance, value);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
        }

        return null;
    }

    public Object getValue(Object instance, String propertyName) {
        return getValue(instance, propertyName, false);
    }
    
    private Object getValue(Object instance, String fieldName, boolean isBoolean) {
        try {
            Method method = instance.getClass().getMethod(fieldGetterMethodName(fieldName, isBoolean));
            return method.invoke(instance);
        } catch (NoSuchMethodException e) {
            if (!isBoolean) return getValue(instance, fieldName, true);
            log.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    private String fieldGetterMethodName(Field field) {
        if (field != null) {
            boolean isBoolean = false;
            if (field.getClass().getName().equals(Boolean.class.getName())) {
                isBoolean = true;
            }

            String fieldName = field.getName();
            if (fieldName != null && fieldName.trim().length() > 0) {
                if (isBoolean) {
                    return "is" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                } else {
                    return "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                }
            }
        }
        throw new IllegalArgumentException("Invalid field: " + field);
    }

    private String fieldGetterMethodName(String fieldName, boolean isBoolean) {
        if (fieldName != null && fieldName.trim().length() > 0) {
            if (isBoolean) {
                return "is" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
            } else {
                return "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
            }
        }

        return fieldName;
    }

    private String fieldSetterMethodName(Field field) {
        if (field != null) {
            String fieldName = field.getName();
            if (fieldName != null && fieldName.trim().length() > 0) {
                return "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
            }
        }
        throw new IllegalArgumentException("Invalid field: " + field);
    }

    private String fieldSetterMethodName(String fieldName) {
        if (fieldName != null && fieldName.trim().length() > 0) {
            return "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
        }
        throw new IllegalArgumentException("Invalid field name: " + fieldName);
    }

    private boolean isJVM(Class clazz) {
        if (clazz.getPackage().getName().indexOf("java.lang") == 0) {
            return true;
        }

        return false;
    }

}
