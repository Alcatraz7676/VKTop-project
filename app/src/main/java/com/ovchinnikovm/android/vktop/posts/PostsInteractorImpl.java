package com.ovchinnikovm.android.vktop.posts;

public class PostsInteractorImpl implements PostsInteractor{
    private PostsRepository repository;

    public PostsInteractorImpl(PostsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Integer groupId, Integer postsCount) {
        repository.getIds(groupId, postsCount);
    }

    @Override
    public void execute(int page) {
        repository.getPosts(page);
    }

    @Override
    public void stopRequest() {
        repository.clearRequest();
    }
}
