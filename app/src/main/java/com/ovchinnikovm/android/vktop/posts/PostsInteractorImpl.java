package com.ovchinnikovm.android.vktop.posts;

import com.ovchinnikovm.android.vktop.entities.RealmSortedItem;

public class PostsInteractorImpl implements PostsInteractor{
    private PostsRepository repository;

    public PostsInteractorImpl(PostsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void sortPosts(Integer sortIntervalType, Long sortStart, Long sortEnd,
                        RealmSortedItem realmSortedItem) {
        repository.getIds(sortIntervalType, sortStart, sortEnd, realmSortedItem);
    }

    @Override
    public void setSortedItem(Integer itemId) {
        repository.setSortedItem(itemId);
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
    public void setSortType(String type) {
        repository.setSortType(type);
    }
}
