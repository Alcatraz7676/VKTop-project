package com.ovchinnikovm.android.vktop.posts;

public interface PostsInteractor {
    void execute(Integer groupId, Integer postsCount, Integer sortIntervalType);
    void execute(int page);
    void stopRequest();
    void setSortType(String type);
}
