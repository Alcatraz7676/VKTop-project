package com.ovchinnikovm.android.vktop.posts;

import com.ovchinnikovm.android.vktop.entities.PostSortItem;
import com.ovchinnikovm.android.vktop.entities.PostsApiResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestInterface {

    @GET("wall.get?filter=owner&count=100&v=5.73")
    Observable<PostsApiResponse<PostSortItem>> getPosts(@Query("owner_id") String owner,
                                                        @Query("offset") Long offset,
                                                        @Query("access_token") String accessToken);
}
