package com.foogaro.nosql.mopa;

import com.mongodb.DBObject;

import java.io.Serializable;
import java.util.*;

/**
 * Not sure about this class, it is here but it is never used.
 * The idea was to create a stack for the conditions used in the query... but I think it would have been too much RDBMS oriented thought.
 * Is here just to remind me what I don't have to do. 
 * @author Luigi Fugaro
 * @version 1.0
 * @since 1.0
 */
public class QueryWrapper implements Serializable {

    private LinkedList<DBObject> queryParameters = new LinkedList<DBObject>();

    protected QueryWrapper() {
        
    }

    public static QueryWrapper newInstance() {
        return new QueryWrapper();
    }

    public void add(DBObject dbObject) {
        Map<String, Object> map = dbObject.toMap();
        Map<String, Object> _map = null;
        Set<String> _keys = null;
        Object _value = null;
        List<Object> in = new ArrayList<Object>();
        for (DBObject _dbObject : queryParameters) {
            _map = _dbObject.toMap();
            _keys = _map.keySet();
            for (String _key : _keys) {
                _value = map.get(_key);
                if (_value == null) {
                    map.put(_key, _map.get(_key));
                } else {
                    map.put(_key, (new ArrayList<Object>()).add(map.get(_key)));
                    in.add(_value);
//                    in.addAll()
                }
            }

        }
//        queryParameters.add()
    }

    public LinkedList<DBObject> getQueryParameters() {
        return queryParameters;
    }
}
