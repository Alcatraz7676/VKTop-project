package com.ovchinnikovm.android.vktop.posts.ui;

import com.ovchinnikovm.android.vktop.entities.ExtendedPost;
import com.ovchinnikovm.android.vktop.entities.ExtendedPosts;
import com.ovchinnikovm.android.vktop.posts.events.DialogEvent;

import java.util.ArrayList;

public interface PostsView {
    void onError(String error);
    void setFirstPage(ArrayList<ExtendedPost> items, Integer numberOfSortedPosts);
    void addPosts(ArrayList<ExtendedPost> items);
    void incrementDialogNumber(DialogEvent event);
}
