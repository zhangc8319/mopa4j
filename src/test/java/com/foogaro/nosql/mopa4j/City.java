package com.foogaro.nosql.mopa4j;

import org.bson.types.ObjectId;

/**
 * @author Luigi Fugaro
 * @version 1.0
 * @since 1.0
 */
public class City {

    private ObjectId _id;
    private String code;
    private String name;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "_id=" + _id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
