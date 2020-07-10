package com.ovchinnikovm.android.vktop.posts;

import android.support.v4.util.ArraySet;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ovchinnikovm.android.vktop.entities.Posts;
import com.ovchinnikovm.android.vktop.entities.PostsResponse;
import com.ovchinnikovm.android.vktop.posts.events.DialogEvent;
import com.ovchinnikovm.android.vktop.posts.events.PostsEvent;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiWall;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class PostsRepositoryImpl implements PostsRepository {
    private EventBus eventBus;

    private Gson gson;

    private int offset;
    private int groupId;
    private Posts posts;
    private int postsCount;

    private Disposable disposable;

    //private VKRequest vkRequest;

    private long c;
    private long a;

    public PostsRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getPosts(Integer groupId, Integer postsCount) {

        this.groupId = groupId;
        this.postsCount = postsCount;

        posts = new Posts();
        posts.items = Collections.synchronizedList(new ArrayList<>());
        posts.groups = Collections.synchronizedSet(new ArraySet<>());
        posts.profiles = Collections.synchronizedSet(new ArraySet<>());

        gson = new GsonBuilder().create();

        c = System.currentTimeMillis();

        downloadPosts();
    }

    private void downloadPosts() {
        a = System.currentTimeMillis();
        disposable = Observable
                .interval(0, 334, TimeUnit.MILLISECONDS)
                .takeUntil(aLong -> (offset / 100) == ((postsCount / 100) + 9))
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(@NonNull Long aLong) {
                       Log.i("mytag", "Offset: " + offset + ". PostsCount: " + postsCount);
                        if (offset < postsCount) {
                            VKRequest vkRequest = new VKApiWall()
                                    .get(VKParameters.from(VKApiConst.OWNER_ID, "-" + groupId,
                                            VKApiConst.OFFSET, offset,
                                            VKApiConst.COUNT, 100,
                                            VKApiConst.FILTERS, "owner",
                                            VKApiConst.EXTENDED, 1));
                            vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
                                @Override
                                public void onComplete(VKResponse response) {
                                    super.onComplete(response);

                                    if (posts != null) {
                                        PostsResponse postsResponse = gson
                                                .fromJson(response.responseString, PostsResponse.class);
                                        posts.items.addAll(postsResponse.response.items);
                                        posts.groups.addAll(postsResponse.response.groups);
                                        posts.profiles.addAll(postsResponse.response.profiles);
                                        eventBus.post(new DialogEvent());
                                        offset += 100;
                                        long b = System.currentTimeMillis();
                                        Log.i("mytag", "Get last post in " + ((b - a) / 1000.0) + " seconds. Number " + (offset / 100));
                                        a = System.currentTimeMillis();
                                    }
                                }

                                @Override
                                public void onError(VKError error) {
                                    super.onError(error);
                                    post(error.toString());
                                }

                            });
                        } else {
                            offset += 100;
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        post(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        setPosts(posts);
                    }
                });

    }

    /*
    private void downloadPosts() {

        a = System.currentTimeMillis();

        vkRequest = new VKApiWall()
                .get(VKParameters.from(VKApiConst.OWNER_ID, "-" + groupId,
                        VKApiConst.OFFSET, offset,
                        VKApiConst.COUNT, 100,
                        VKApiConst.FILTERS, "owner",
                        VKApiConst.EXTENDED, 1));
        vkRequest.attempts = 0;
        vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                if (posts != null) {
                    PostsResponse postsResponse = gson
                            .fromJson(response.responseString, PostsResponse.class);
                    posts.items.addAll(postsResponse.response.items);
                    posts.groups.addAll(postsResponse.response.groups);
                    posts.profiles.addAll(postsResponse.response.profiles);
                    eventBus.post(new DialogEvent());
                    offset += 100;
                    long b = System.currentTimeMillis();
                    Log.i("mytag", "Get last post in " + ((b - a) / 1000.0) + " seconds. Number " + (offset / 100));
                    if (posts.items.size() >= postsCount) {
                        setPosts(posts);
                    } else {
                        downloadPosts();
                    }
                }
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                post(error.toString());
                downloadPosts();
            }

        });
    }
    */

    private void setPosts (Posts posts) {

        long a = System.currentTimeMillis();

        Collections.sort(posts.items, (post1, post2) ->
                post2.getLikes().compareTo(post1.getLikes()));

        long b = System.currentTimeMillis();

        Posts twentyTopPosts = new Posts();

        if (posts.items.size() >= 20) {
            twentyTopPosts.items = posts.items.subList(0, 20);
        } else {
            twentyTopPosts.items = posts.items.subList(0, posts.items.size());
        }

        twentyTopPosts.groups = posts.groups;
        twentyTopPosts.profiles = posts.profiles;

        long d = System.currentTimeMillis();

        Log.i("mytag", "Time of download and add response to array: " + ((d - c) / 1000.0)
                + " seconds. And time of sort is " + ((b - a) / 1000.0) + " seconds." );
        Log.i("mytag", "Size of the array: " + twentyTopPosts.items.size());
        post(twentyTopPosts);
    }

    @Override
    public void stopRequest() {
        //vkRequest.cancel();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
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
