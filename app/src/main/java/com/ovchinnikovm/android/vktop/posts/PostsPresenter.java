package com.ovchinnikovm.android.vktop.posts;

import com.ovchinnikovm.android.vktop.entities.RealmSortedItem;
import com.ovchinnikovm.android.vktop.posts.events.DialogEvent;
import com.ovchinnikovm.android.vktop.posts.events.PostsEvent;

public interface PostsPresenter {
    void onCreate();
    void onDestroy();
    void downloadPostsIds(Integer sortIntervalType, Long sortStart, Long sortEnd,
                          RealmSortedItem realmSortedItem);
    void setSortedItem(Integer itemId);
    void onEventMainThread(PostsEvent event);
    void onEventMainThread(DialogEvent event);
    void getPosts(int page);
    void setSortType(String type);
}
