package com.ovchinnikovm.android.vktop.posts;

public interface PostsRepository {
    void getPosts(Integer groupId, Integer postsCount);
    void stopRequest();
}
