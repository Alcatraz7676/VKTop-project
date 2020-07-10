package com.ovchinnikovm.android.vktop.posts.ui;

import com.ovchinnikovm.android.vktop.entities.Posts;
import com.ovchinnikovm.android.vktop.posts.events.DialogEvent;

public interface PostsView {
    void onError(String error);
    void setPosts(Posts posts);
    void incrementDialogNumber(DialogEvent event);
}
