package com.foogaro.nosql.mopa4j;

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
 * @version 1.0.1
 * @since 1.0
 */
public class MappingHelper {

    private static final Logger log = LoggerFactory.getLogger(MappingHelper.class);

    private IMapperCache mapperCache = new MapperCache();

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

    public ADocumentObject toADocumentObject(DBObject dbObject, Class<? extends ADocumentObject> clazz) {
        return toADocumentObject(dbObject, clazz, null);
    }

    public ADocumentObject toADocumentObject(DBObject dbObject, ADocumentObject aDocumentObject) {
        return toADocumentObject(dbObject, null, aDocumentObject);
    }

    private ADocumentObject toADocumentObject(DBObject dbObject, Class<? extends ADocumentObject> clazz, ADocumentObject aDocumentObject) {
        Map<String, Object> map = dbObject.toMap();
        Set<String> keys = map.keySet();

        Object value = null;

        if (aDocumentObject == null)  {
            try {
                aDocumentObject = (ADocumentObject) clazz.newInstance();
            } catch (InstantiationException e) {
                log.error(e.getMessage(), e);
            } catch (IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        }

        Class cls = null;
        for (String key : keys) {
            value = map.get(key);
            if (value instanceof BasicDBObject) {
                ADocumentObject bm = toADocumentObject((DBObject)value, mapperCache.getFieldClass(aDocumentObject, key));
                invokeSetter(aDocumentObject, key, bm);
            } else if (value instanceof BasicDBList) {
                if (Set.class.equals(mapperCache.getCollectionType(aDocumentObject, key))) {
                    Set set = toSet((BasicDBList)value, mapperCache.getCollectionArgumentType(aDocumentObject, key));
                    invokeSetter(aDocumentObject, key, (Set)set);
                } else if (List.class.equals(mapperCache.getCollectionType(aDocumentObject, key))) {
                    List list = toList((BasicDBList)value, mapperCache.getCollectionArgumentType(aDocumentObject, key));
                    invokeSetter(aDocumentObject, key, list);
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
        List list = null;
        BasicDBObject basicDBObject = null;

        Object obj = null;
        try {
            list = (List)(new ArrayList());
            obj = ((Class)type).newInstance();

            if (basicDBList != null && !basicDBList.isEmpty()) {
                for (Object object : basicDBList) {
                    basicDBObject = (BasicDBObject)object;
                    Map<String, Object> map = basicDBObject.toMap();
                    Set<String> keys = map.keySet();
                    for (String key : keys) {
                        setValue(obj, key, map.get(key));
                    }
                    list.add(obj);
                }
            }
        } catch (InstantiationException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }

        return list;
    }

    private void invokeSetter(ADocumentObject aDocumentObject, String fieldName, Object value) {
        try {
            Method method = mapperCache.getSetter(aDocumentObject, fieldName);
            method.invoke(aDocumentObject, value);
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

    public Map<String, Object> toMap(ADocumentObject aDocumentObject) {
        if (aDocumentObject != null) {
            return processFields(aDocumentObject);
        }
        return null;
    }

    public DBObject toDBObject(Map<String, Object> map) {
        BasicDBObjectBuilder basicDBObjectBuilder = BasicDBObjectBuilder.start(map);
        return basicDBObjectBuilder.get();
    }

    private Map<String, Object> processFields(ADocumentObject aDocumentObject) {

        Map<String, Object> documentMap = new HashMap<String, Object>();

        Object fieldValue = null;
        try {
            String [] fields = mapperCache.getFields(aDocumentObject);
            for (String field : fields) {
                if (field != null) {
                    fieldValue = mapperCache.getFieldValue(aDocumentObject, field);
                    if (fieldValue != null) {
                        if (mapperCache.isCustom(aDocumentObject, field)) {
                            log.debug("Looks like we have a custom type: " + mapperCache.getFieldClassName(aDocumentObject, field));
                            documentMap.put(field, toDBObject((ADocumentObject)fieldValue));
                        } else {
                            if (fieldValue.getClass().isArray()) {
                                Object[] objects =  (Object[])fieldValue;
                                BasicDBList basicDBList = new BasicDBList();
                                for (Object obj : objects) {
                                    basicDBList.add(obj);
                                }
                                documentMap.put(field, basicDBList);
                            } else if (List.class.isInstance(fieldValue)) {
                                List list = (List)fieldValue;
                                BasicDBList basicDBList = new BasicDBList();
                                for (Object obj : list) {
                                    basicDBList.add(obj);
                                }
                                documentMap.put(field, basicDBList);
                            } else if (Set.class.isInstance(fieldValue)) {
                                Set set = (Set)fieldValue;
                                BasicDBList basicDBList = new BasicDBList();
                                for (Object obj : set) {
                                    basicDBList.add(obj);
                                }
                                documentMap.put(field, basicDBList);
                            } else {
                                documentMap.put(field, fieldValue);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return documentMap;
    }

    public Object setValue(Object instance, String fieldName, Object value) {
        try {
            Method method = mapperCache.getSetter((ADocumentObject) instance, fieldName);
            return method.invoke(instance, value);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        } catch (Throwable t) {
            log.error(t.getMessage(), t);
        }

        return null;
    }

    public Object getValue(Object instance, String fieldName) {
        try {
            Method method = mapperCache.getGetter((ADocumentObject) instance, fieldName);
            return method.invoke(instance);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

}
