package com.ovchinnikovm.android.vktop.entities;


import java.io.Serializable;

import io.realm.RealmObject;

// This class is used for likes/replies/comments
public class SocialValue extends RealmObject implements Serializable {
    private Integer count;

    public SocialValue() {
    }

    public SocialValue(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "SocialValue{" +
                "count=" + count +
                '}';
    }
}
