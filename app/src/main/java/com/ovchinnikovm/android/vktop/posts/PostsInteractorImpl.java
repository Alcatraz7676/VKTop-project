package com.ovchinnikovm.android.vktop.posts;

public class PostsInteractorImpl implements PostsInteractor{
    private PostsRepository repository;

    public PostsInteractorImpl(PostsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Integer groupId, Integer postsCount) {
        repository.getPosts(groupId, postsCount);
    }

    @Override
    public void stopRequest() {
        repository.stopRequest();
    }
}
