package com.ovchinnikovm.android.vktop.posts;

import com.ovchinnikovm.android.vktop.entities.RealmSortedItem;

public interface PostsRepository {
    void getIds(Integer sortIntervalType, Long sortStart, Long sortEnd,
                RealmSortedItem realmSortedItem);
    void setSortedItem(Integer itemId);
    void getPosts(int page);
    void clearRequest();
    void setSortType(String type);
}
