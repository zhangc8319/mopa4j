package com.foogaro.nosql.mopa4j;

import org.bson.types.ObjectId;

/**
 * User: luigi
 * Date: Jan 6, 2011
 * Time: 3:21:14 PM
 */
public class DBReferencedObject extends ADocumentObject {

    private String ref;
    private ObjectId _id;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }
}
