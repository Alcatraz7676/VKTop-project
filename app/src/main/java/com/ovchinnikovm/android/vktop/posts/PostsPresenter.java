package com.ovchinnikovm.android.vktop.posts;

import com.ovchinnikovm.android.vktop.posts.events.DialogEvent;
import com.ovchinnikovm.android.vktop.posts.events.PostsEvent;

public interface PostsPresenter {
    void onCreate();
    void onDestroy();
    void downloadPostsIds(Integer groupId, Integer postsCount, Integer sortIntervalType,
                          Long sortStart, Long sortEnd);
    void onEventMainThread(PostsEvent event);
    void onEventMainThread(DialogEvent event);
    void getPosts(int page);
    void setSortType(String type);
}
