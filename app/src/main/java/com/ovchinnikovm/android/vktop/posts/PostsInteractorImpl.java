package com.ovchinnikovm.android.vktop.posts;

import com.ovchinnikovm.android.vktop.entities.RealmSortedItem;
import com.ovchinnikovm.android.vktop.entities.SortType;

public class PostsInteractorImpl implements PostsInteractor{
    private PostsRepository repository;

    public PostsInteractorImpl(PostsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void sortPosts(Integer sortIntervalType, Long sortStart, Long sortEnd,
                        RealmSortedItem realmSortedItem, Integer userId) {
        repository.getIds(sortIntervalType, sortStart, sortEnd, realmSortedItem, userId);
    }

    @Override
    public void setSortedItem(Integer itemId) {
        repository.setSortedItem(itemId);
    }

    @Override
    public void stopVkRequest() {
        repository.cancelVkRequest();
    }

    @Override
    public void getPosts(int page) {
        repository.getPosts(page);
    }

    @Override
    public void stopRequest() {
        repository.clearRequest();
    }

    @Override
    public void setSortType(SortType type) {
        repository.setSortType(type);
    }
}
