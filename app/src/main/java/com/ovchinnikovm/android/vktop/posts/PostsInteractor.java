package com.ovchinnikovm.android.vktop.posts;

import com.ovchinnikovm.android.vktop.entities.RealmSortedItem;

public interface PostsInteractor {
    void sortPosts(Integer sortIntervalType, Long sortStart, Long sortEnd,
                 RealmSortedItem realmSortedItem);
    void setSortedItem(Integer itemId);
    void getPosts(int page);
    void stopRequest();
    void setSortType(String type);
}
