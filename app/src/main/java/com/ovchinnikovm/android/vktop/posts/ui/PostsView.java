package com.ovchinnikovm.android.vktop.posts.ui;

import com.ovchinnikovm.android.vktop.entities.Posts;

public interface PostsView {
    void onError(String error);
    void setPosts(Posts posts);
    void incrementDialogNumber();
}
