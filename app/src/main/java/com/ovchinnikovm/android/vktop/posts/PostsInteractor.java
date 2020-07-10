package com.ovchinnikovm.android.vktop.posts;

public interface PostsInteractor {
    void execute(Integer groupId, Integer postsCount, Integer sortIntervalType,
                 Long sortStart, Long sortEnd);
    void execute(int page);
    void stopRequest();
    void setSortType(String type);
}
