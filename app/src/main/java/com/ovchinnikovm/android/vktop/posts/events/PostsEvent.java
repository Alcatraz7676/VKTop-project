package com.ovchinnikovm.android.vktop.posts.events;

import com.ovchinnikovm.android.vktop.entities.ExtendedPosts;

public class PostsEvent {
    private String error;
    private ExtendedPosts extendedPosts;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ExtendedPosts getExtendedPosts() {
        return extendedPosts;
    }

    public void setExtendedPosts(ExtendedPosts extendedPosts) {
        this.extendedPosts = extendedPosts;
    }
}
