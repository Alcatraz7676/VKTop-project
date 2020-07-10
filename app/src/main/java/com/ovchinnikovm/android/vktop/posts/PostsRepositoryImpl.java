package com.ovchinnikovm.android.vktop.posts;

import android.support.v4.util.ArraySet;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ovchinnikovm.android.vktop.entities.Posts;
import com.ovchinnikovm.android.vktop.entities.PostsResponse;
import com.ovchinnikovm.android.vktop.posts.events.PostsEvent;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKBatchRequest;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiWall;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;

public class PostsRepositoryImpl implements PostsRepository {
    private EventBus eventBus;

    private Gson gson;
    private int offset;
    private int groupId;
    private Posts posts;
    private int postsCount;
    private VKBatchRequest batch;

    public PostsRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getPosts(Integer groupId, Integer postsCount) {
        offset = 0;
        posts = new Posts();
        posts.items = new ArrayList<>();
        posts.groups = new ArraySet<>();
        posts.profiles = new ArraySet<>();
        gson = new GsonBuilder().create();
        this.groupId = groupId;
        this.postsCount = postsCount;
        downloadPosts();
    }

    private void downloadPosts() {
        VKRequest vkRequest1 = new VKApiWall()
                .get(VKParameters.from(VKApiConst.OWNER_ID, "-" + groupId,
                        VKApiConst.OFFSET, offset,
                        VKApiConst.COUNT, 100,
                        VKApiConst.FILTERS, "owner",
                        VKApiConst.EXTENDED, 1));
        offset += 100;
        VKRequest vkRequest2 = new VKApiWall()
                .get(VKParameters.from(VKApiConst.OWNER_ID, "-" + groupId,
                        VKApiConst.OFFSET, offset,
                        VKApiConst.COUNT, 100,
                        VKApiConst.FILTERS, "owner",
                        VKApiConst.EXTENDED, 1));
        offset += 100;
        VKRequest vkRequest3 = new VKApiWall()
                .get(VKParameters.from(VKApiConst.OWNER_ID, "-" + groupId,
                        VKApiConst.OFFSET, offset,
                        VKApiConst.COUNT, 100,
                        VKApiConst.FILTERS, "owner",
                        VKApiConst.EXTENDED, 1));
        offset += 100;
        batch = new VKBatchRequest(vkRequest1, vkRequest2, vkRequest3);
        long c = System.currentTimeMillis();
        batch.executeWithListener(new VKBatchRequest.VKBatchRequestListener() {
            @Override
            public void onComplete(VKResponse[] responses) {
                long d = System.currentTimeMillis();
                Log.i("mytag", "Time of download and add response to array: " + (d - c) + " milliseconds");
                super.onComplete(responses);

                for (VKResponse vkResponse : responses) {
                    PostsResponse postsResponse = gson
                            .fromJson(vkResponse.responseString, PostsResponse.class);
                    posts.items.addAll(postsResponse.response.items);
                    posts.groups.addAll(postsResponse.response.groups);
                    posts.profiles.addAll(postsResponse.response.profiles);
                }
                if (posts.items.size() >= postsCount) {
                    setPosts(posts);
                } else {
                    downloadPosts();
                }
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                post(error.toString());
            }


        });
    }

    private void setPosts (Posts posts) {
        Collections.sort(posts.items, (post1, post2) ->
                post2.getLikes().compareTo(post1.getLikes()));
        Posts twentyTopPosts = new Posts();
        if (posts.items.size() >= 20) {
            twentyTopPosts.items = posts.items.subList(0, 20);
        } else {
            twentyTopPosts.items = posts.items.subList(0, posts.items.size());
        }
        twentyTopPosts.groups = posts.groups;
        twentyTopPosts.profiles = posts.profiles;
        post(twentyTopPosts);
    }

    @Override
    public void stopRequest() {
        batch.cancel();
        posts = null;
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
