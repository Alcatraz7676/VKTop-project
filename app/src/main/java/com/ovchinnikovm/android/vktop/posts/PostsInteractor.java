package com.ovchinnikovm.android.vktop.posts;

import com.ovchinnikovm.android.vktop.entities.RealmSortedItem;
import com.ovchinnikovm.android.vktop.entities.SortType;

public interface PostsInteractor {
    void sortPosts(Integer sortIntervalType, Long sortStart, Long sortEnd,
                 RealmSortedItem realmSortedItem, Integer UserId);
    void setSortedItem(Integer itemId);
    void getPosts(int page);
    void stopVkRequest();
    void stopRequest();
    void setSortType(SortType type);
}
