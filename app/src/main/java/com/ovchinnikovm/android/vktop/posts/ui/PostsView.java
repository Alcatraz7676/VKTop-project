package com.ovchinnikovm.android.vktop.posts.ui;

import com.ovchinnikovm.android.vktop.entities.ExtendedPosts;
import com.ovchinnikovm.android.vktop.posts.events.DialogEvent;

public interface PostsView {
    void onError(String error);
    void setPosts(ExtendedPosts extendedPosts);
    void incrementDialogNumber(DialogEvent event);
}
