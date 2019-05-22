package com.didi.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjectId {

    private String objectIdValue;

//    public static String useObjectId(ResultSet objectId) throws SQLException {
//
//        return objectId.getString(1);
//    }


    @Override
    public String toString() {
        return objectIdValue;
    }

    public ObjectId(String objectIdValue) {
        this.objectIdValue = objectIdValue;
    }

    public String getObjectIdValue() {
        return objectIdValue;
    }

    public void setObjectIdValue(String objectIdValue) {
        this.objectIdValue = objectIdValue;
    }

    public String getObjectId() {
        return String.format("\"(%s)\"",
                this.objectIdValue);
    }

}
