package com.ovchinnikovm.android.vktop.posts;

import com.ovchinnikovm.android.vktop.posts.events.DialogEvent;
import com.ovchinnikovm.android.vktop.posts.events.ExtendedPostsEvent;

public interface PostsPresenter {
    void onResume();
    void onPause();
    void onDestroy();
    void downloadPostsIds(Integer groupId, Integer postsCount, Integer sortIntervalType);
    void onEventMainThread(ExtendedPostsEvent event);
    void onEventMainThread(DialogEvent event);
    void getPosts(int page);
    void setSortType(String type);
}
