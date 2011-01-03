package com.foogaro.nosql.mopa;

import org.bson.types.ObjectId;

import java.util.Date;

/**
 * @author Luigi Fugaro
 * @version 1.0
 * @since 1.0
 */
public class User extends ADocumentObject {

    private ObjectId _id;
    private String name;
    private Date birthdate;
    private City city;

    public User() {
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", city=" + city +
                '}';
    }
}
