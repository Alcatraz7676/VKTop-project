package com.ovchinnikovm.android.vktop.posts;

public interface PostsInteractor {
    void execute(Integer groupId, Integer postsCount);
    void execute(int page);
    void stopRequest();
}
