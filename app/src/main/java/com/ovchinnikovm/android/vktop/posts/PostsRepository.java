package com.ovchinnikovm.android.vktop.posts;

public interface PostsRepository {
    void getIds(Integer groupId, Integer postsCount);
    void getPosts(int page);
    void clearRequest();
}
