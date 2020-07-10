package com.ovchinnikovm.android.vktop.posts;

import com.ovchinnikovm.android.vktop.entities.Posts;
import com.ovchinnikovm.android.vktop.entities.PostsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestInterface {

    @GET("wall.get?")
    Observable<PostsResponse<Posts>> getPosts(@Query("owner_id") String owner,
                                              @Query("offset") Long offset,
                                              @Query("count") Integer count,
                                              @Query("filter") String filter,
                                              @Query("access_token") String accessToken);
}
