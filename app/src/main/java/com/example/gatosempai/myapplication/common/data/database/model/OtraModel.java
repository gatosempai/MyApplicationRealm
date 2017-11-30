package com.example.gatosempai.myapplication.common.data.database.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by oruizp on 11/29/17.
 */

public class OtraModel extends RealmObject implements BaseModel {

    @PrimaryKey
    private long id;
    private String name;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
