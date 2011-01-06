package com.foogaro.nosql.mopa4j;

import com.foogaro.nosql.mopa4j.annotation.DBReferenced;
import org.bson.types.ObjectId;

/**
 * @author Luigi Fugaro
 * @version 1.0
 * @since 1.1
 */
public class Nation  {

    private ObjectId _id;
    private String code;
    private String name;
    @DBReferenced
    private City city;

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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Nation{" +
                "_id=" + _id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", city=" + city +
                '}';
    }
}
