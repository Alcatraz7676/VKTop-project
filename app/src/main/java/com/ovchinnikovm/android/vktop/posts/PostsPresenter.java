package com.ovchinnikovm.android.vktop.posts;

import com.ovchinnikovm.android.vktop.posts.events.PostsEvent;

public interface PostsPresenter {
    void onResume();
    void onPause();
    void onDestroy();
    void getPosts(Integer groupId, Integer postsCount);
    void onEventMainThread(PostsEvent event);
}
