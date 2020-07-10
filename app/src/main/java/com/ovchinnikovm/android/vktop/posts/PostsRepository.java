package com.ovchinnikovm.android.vktop.posts;

public interface PostsRepository {
    void getIds(Integer groupId, Integer postsCount, Integer sortIntervalType);
    void getPosts(int page);
    void clearRequest();
    void setSortType(String type);
}
