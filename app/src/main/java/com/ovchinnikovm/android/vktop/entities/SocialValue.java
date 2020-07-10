package com.ovchinnikovm.android.vktop.entities;


// This class is used for likes/replies/comments
public class SocialValue {
    private Integer count;

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
