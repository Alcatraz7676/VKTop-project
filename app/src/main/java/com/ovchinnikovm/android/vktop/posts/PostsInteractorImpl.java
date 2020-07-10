package com.ovchinnikovm.android.vktop.posts;

public class PostsInteractorImpl implements PostsInteractor{
    private PostsRepository repository;

    public PostsInteractorImpl(PostsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Integer groupId, Integer postsCount, Integer sortIntervalType,
                        Long sortStart, Long sortEnd) {
        repository.getIds(groupId, postsCount, sortIntervalType, sortStart, sortEnd);
    }

    @Override
    public void execute(int page) {
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
