package com.ovchinnikovm.android.vktop.posts;

import com.ovchinnikovm.android.vktop.entities.RealmSortedItem;
import com.ovchinnikovm.android.vktop.entities.SortType;

public interface PostsRepository {
    void getIds(Integer sortIntervalType, Long sortStart, Long sortEnd,
                RealmSortedItem realmSortedItem, Integer userId);
    void setSortedItem(Integer itemId);
    void getPosts(int page);
    void cancelVkRequest();
    void clearRequest();
    void setSortType(SortType type);
}
