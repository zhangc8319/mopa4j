package com.foogaro.nosql.mopa4j;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.BSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class is an implementation of com.mongodb.DBObject.
 * It is intend to be used as parent class of any POJOs you would like to manage in your MongoDB instance. 
 * @author Luigi Fugaro
 * @version 1.0
 * @since 1.0
 */
public abstract class ADocumentObject implements DBObject {

    protected Map<String, Object> wrapper = new HashMap<String, Object>();
    protected boolean partialObject = false;
    @Autowired
    protected MappingHelper mappingHelper;

    public ADocumentObject() {
        initWrapper();
    }

    private void initWrapper() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            wrapper.put(field.getName(), null);
        }
    }

    public void markAsPartialObject() {
        this.partialObject = true;
    }

    public boolean isPartialObject() {
        return partialObject;
    }

    public Object put(String key, Object v) {
        if (v != null) {
            if (v instanceof BasicDBObject) {
                if (((BasicDBObject)v).size() > 0) {
                    mappingHelper.setValue(this, key, v);
                }
            } else {
                mappingHelper.setValue(this, key, v);
            }
            wrapper.put(key, v);
        }
        return wrapper.get(key);
    }

    public void putAll(BSONObject o) {
        wrapper.putAll(o.toMap());
    }

    public void putAll(Map m) {
        wrapper.putAll(m);
    }

    public Object get(String key) {
        if (key.equals("_id") || !key.startsWith("_"))
            return mappingHelper.getValue(this, key);
        return null;
    }

    public Map toMap() {
        return mappingHelper.toMap(this);
    }

    public Map toMQL() {
        return mappingHelper.toMQL(this);
    }

    public Object removeField(String key) {
        return wrapper.remove(key);
    }

    public boolean containsKey(String s) {
        return wrapper.keySet().contains(s);
    }

    public boolean containsField(String s) {
        return wrapper.keySet().contains(s);
    }

    public Set<String> keySet() {
        return wrapper.keySet();
    }
}
