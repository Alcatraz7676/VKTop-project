package com.ovchinnikovm.android.vktop.posts;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ovchinnikovm.android.vktop.entities.Posts;
import com.ovchinnikovm.android.vktop.entities.PostsResponse;
import com.ovchinnikovm.android.vktop.posts.events.PostsEvent;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiWall;

import org.greenrobot.eventbus.EventBus;

public class PostsRepositoryImpl implements PostsRepository {
    private EventBus eventBus;

    public PostsRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getPosts(Integer groupId, Integer postsCount) {
        VKRequest vkRequest = new VKApiWall()
                .get(VKParameters.from(VKApiConst.OWNER_ID, "-" + groupId,
                        VKApiConst.COUNT, 20));
        vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
                PostsResponse postsResponse = gson
                        .fromJson(response.responseString, PostsResponse.class);
                Log.i("mytag", response.responseString);
                post(postsResponse.response);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                post(error.toString());
            }
        });
    }

    private void post(Posts posts) {
        post(posts, null);
    }

    private void post(String error) {
        post(null, error);
    }

    private void post(Posts posts, String error) {
        PostsEvent event = new PostsEvent();
        event.setError(error);
        event.setPosts(posts);
        eventBus.post(event);
    }
}
