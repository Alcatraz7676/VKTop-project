package com.ovchinnikovm.android.vktop.posts.events;

import com.ovchinnikovm.android.vktop.entities.ExtendedPost;

import java.util.ArrayList;

public class PostsEvent {
    private String error;
    private ArrayList<ExtendedPost> items;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<ExtendedPost> getPosts() {
        return items;
    }

    public void setPosts(ArrayList<ExtendedPost> items) {
        this.items = items;
    }
}
