package com.ovchinnikovm.android.vktop.posts;

import com.ovchinnikovm.android.vktop.entities.Posts;
import com.ovchinnikovm.android.vktop.posts.events.DialogEvent;
import com.ovchinnikovm.android.vktop.posts.events.PostsEvent;

public interface PostsPresenter {
    void onResume();
    void onPause();
    void onDestroy();
    void downloadPosts(Integer groupId, Integer postsCount);
    void onEventMainThread(PostsEvent event);
    void onEventMainThread(DialogEvent event);
    Posts getPosts(int page);
}
