package com.ovchinnikovm.android.vktop.posts.events;

import com.ovchinnikovm.android.vktop.entities.Posts;

public class PostsEvent {
    private String error;
    private Posts posts;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Posts getPosts() {
        return posts;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
    }
}
