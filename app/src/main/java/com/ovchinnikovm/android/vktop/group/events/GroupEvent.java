package com.ovchinnikovm.android.vktop.group.events;

public class GroupEvent {
    private String error;
    private Integer postsNumber;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getPostsNumber() {
        return postsNumber;
    }

    public void setPostsNumber(Integer postsNumber) {
        this.postsNumber = postsNumber;
    }
}
