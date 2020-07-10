package com.ovchinnikovm.android.vktop.posts;

import android.support.v4.util.ArraySet;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ovchinnikovm.android.vktop.entities.Attachment;
import com.ovchinnikovm.android.vktop.entities.ExtendedPost;
import com.ovchinnikovm.android.vktop.entities.ExtendedPosts;
import com.ovchinnikovm.android.vktop.entities.Group;
import com.ovchinnikovm.android.vktop.entities.Posts;
import com.ovchinnikovm.android.vktop.entities.PostsResponse;
import com.ovchinnikovm.android.vktop.entities.Profile;
import com.ovchinnikovm.android.vktop.posts.events.DialogEvent;
import com.ovchinnikovm.android.vktop.posts.events.ExtendedPostsEvent;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiWall;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostsRepositoryImpl implements PostsRepository {
    public static final String BASE_URL = "https://api.vk.com/method/";
    private EventBus eventBus;
    private Gson gson;

    private int offset;
    private String groupId;
    private Posts posts;
    private int postsCount;

    private Disposable disposable;

    private int requestNumber;

    private int numberOfRequests;

    // Variables for time
    private long c;
    private long a;

    public PostsRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getIds(Integer groupId, Integer postsCount) {

        this.groupId = "-" + groupId.toString();
        this.postsCount = postsCount;

        posts = new Posts();
        posts.items = new ArrayList<>();

        gson = new GsonBuilder().create();

        c = System.currentTimeMillis();

        downloadIds();
    }

    @Override
    public void getPosts(int page) {
        Log.i("mytag", String.valueOf(page));
        Log.i("mytag", String.valueOf(posts.items.size()));

        List<Integer> twentyIds = new ArrayList<>();
        int start = page*20;
        int end = (page*20)+20;
        if (posts.items.size() > start) {
            if (posts.items.size() >= end) {
                for (int i = start; i < end; i++) {
                    twentyIds.add(posts.items.get(i).getId());
                }
            } else {
                for (int i = start; i < posts.items.size(); i++) {
                    twentyIds.add(posts.items.get(i).getId());
                }
            }
        }

        downloadPosts(twentyIds);
    }

    private void downloadPosts(List<Integer> twentyIds) {
        String postsParameters = "";
        for(Integer id : twentyIds) {
            postsParameters += groupId + "_" + id + ",";
        }
        VKRequest vkRequest = new VKApiWall()
                .getById(VKParameters.from(VKApiConst.POSTS, postsParameters,
                        VKApiConst.EXTENDED, 1));
        vkRequest.attempts = 0;
        vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                ExtendedPosts extendedPosts = new ExtendedPosts();
                extendedPosts.items = new ArrayList<>();
                Set<Group> groups = new ArraySet<>();
                Set<Profile> profiles = new ArraySet<>();

                PostsResponse<ExtendedPosts> postsResponse = gson
                        .fromJson(response.responseString, new TypeToken<PostsResponse<ExtendedPosts>>(){}.getType());

                extendedPosts.items.addAll(postsResponse.response.items);
                groups.addAll(postsResponse.response.groups);
                profiles.addAll(postsResponse.response.profiles);

                for (ExtendedPost post : extendedPosts.items) {
                    for(Profile profile : profiles) {
                        if (profile.getId().equals(post.getAuthorId())) {
                            post.setAuthorFullname(profile.getFullName());
                            break;
                        }
                    }
                    post.photos = new ArrayList<>();
                    if (post.attachments != null) {
                        int numberOfPhotos = getNumberOfPhotos(post.attachments);
                        // Single photo
                        if (numberOfPhotos == 1) {
                            for (Attachment attachment : post.attachments) {
                                if (attachment.getType().equals("photo")) {
                                    post.photos.add(attachment.getPhoto());
                                    break;
                                } else if (attachment.getType().equals("posted_photo")) {
                                    post.photos.add(attachment.getPostedPhoto());
                                    break;
                                }
                            }
                            // Multiple photos
                        } else if (numberOfPhotos > 1) {
                            for (Attachment attachment : post.attachments) {
                                if (attachment.getType().equals("photo")) {
                                    post.photos.add(attachment.getPhoto());
                                } else if (attachment.getType().equals("posted_photo")) {
                                    post.photos.add(attachment.getPostedPhoto());
                                }
                            }
                        }

                        // Media attachments
                        post.other = new ArrayList<>();
                        for (Attachment attachment : post.attachments) {
                            if (attachment.getType().equals("audio") || attachment.getType().equals("video")
                                    || attachment.getType().equals("link")) {
                                post.other.add(attachment);
                            }
                        }
                    }

                    if (post.nestedPost != null && !post.nestedPost.isEmpty()) {
                        for (Group group : groups) {
                            if (group.getId().equals(post.nestedPost.get(0).getGroupId() * (-1))) {
                                post.nestedPost.get(0).setIconUrl(group.getSmallPhotoUrl());
                                post.nestedPost.get(0).setReplyName(group.getName());
                            }
                        }
                        for (Profile profile : profiles) {
                            if (profile.getId().equals(post.nestedPost.get(0).getGroupId())) {
                                post.nestedPost.get(0).setIconUrl(profile.getSmallPhotoUrl());
                                post.nestedPost.get(0).setReplyName(profile.getFullName());
                            }
                        }
                    }

                    if (post.nestedPost != null && post.nestedPost.get(0).attachments != null) {
                        post.nestedPost.get(0).photos = new ArrayList<>();
                        int numberOfPhotos = getNumberOfPhotos(post.nestedPost.get(0).attachments);
                        // Single photo
                        if (numberOfPhotos == 1) {
                            for (Attachment attachment : post.nestedPost.get(0).attachments) {
                                if (attachment.getType().equals("photo")) {
                                    post.nestedPost.get(0).photos.add(attachment.getPhoto());
                                    break;
                                } else if (attachment.getType().equals("posted_photo")) {
                                    post.nestedPost.get(0).photos.add(attachment.getPostedPhoto());
                                    break;
                                }
                            }
                            // Multiple photos
                        } else if (numberOfPhotos > 1) {
                            for (Attachment attachment : post.nestedPost.get(0).attachments) {
                                if (attachment.getType().equals("photo")) {
                                    post.nestedPost.get(0).photos.add(attachment.getPhoto());
                                } else if (attachment.getType().equals("posted_photo")) {
                                    post.nestedPost.get(0).photos.add(attachment.getPostedPhoto());
                                }
                            }
                        }

                        // Media attachments
                        post.nestedPost.get(0).other = new ArrayList<>();
                        for (Attachment attachment : post.nestedPost.get(0).attachments) {
                            if (attachment.getType().equals("audio") || attachment.getType().equals("video")
                                    || attachment.getType().equals("link")) {
                                post.nestedPost.get(0).other.add(attachment);
                            }
                        }
                    }
                }

                post(extendedPosts);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.i("extendedPosts", "JUST GOT ERROR LMAO");
                post(error.toString());
            }
        });
    }

    private int getNumberOfPhotos(List<Attachment> attachments) {
        int quantity = 0;
        for (Attachment attachment : attachments) {
            if (attachment.getType().equals("photo") || attachment.getType().equals("posted_photo")) {
                quantity++;
            }
        }
        return quantity;
    }

    private void downloadIdsNew() {
        RequestInterface requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RequestInterface.class);

        disposable = Observable
                .intervalRange(0, (postsCount / 100) + 1, 0, 334, TimeUnit.MILLISECONDS)
                .concatMap(offset -> requestInterface
                        .getPosts(groupId, offset * 100, 100, "owner", VKAccessToken.currentToken().accessToken)
                        .map(posts -> {
                            Log.i("extendedPosts", "GOT IT");
                            Log.i("extendedPosts", posts.toString());
                            return posts.response.items;
                        }))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        posts -> {
                            this.posts.items.addAll(posts);
                            eventBus.post(new DialogEvent(false));
                        },  e -> {
                            Log.i("extendedPosts", e.toString());
                            post(e.toString());
                }, this::sortPosts
                );


    }

    private void downloadIds() {
        a = System.currentTimeMillis();
        numberOfRequests = (postsCount / 100);
        disposable = Observable
                .intervalRange(0, numberOfRequests + 4, 0, 400, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (Long aLong) -> {
                            if ((offset / 100) <= numberOfRequests) {
                                VKRequest vkRequest = new VKApiWall()
                                        .get(VKParameters.from(VKApiConst.OWNER_ID, groupId,
                                                VKApiConst.OFFSET, offset,
                                                VKApiConst.COUNT, 100,
                                                VKApiConst.FILTERS, "owner"));
                                vkRequest.attempts = 0;
                                Log.i("extendedPosts", "Offset: " + offset  + ". PostsCount: " + postsCount );
                                offset += 100;
                                vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
                                    @Override
                                    public void onComplete(VKResponse response) {
                                        super.onComplete(response);
                                        long b = System.currentTimeMillis();
                                        Log.i("extendedPosts", "Get last post in " + ((b - a) / 1000.0) + " seconds. Number " + requestNumber);
                                        a = System.currentTimeMillis();
                                        if (posts != null) {
                                            PostsResponse<Posts> postsResponse = gson
                                                    .fromJson(response.responseString, new TypeToken<PostsResponse<Posts>>(){}.getType());
                                            posts.items.addAll(postsResponse.response.items);
                                            eventBus.post(new DialogEvent(false));
                                            requestNumber++;
                                        }
                                    }

                                    @Override
                                    public void onError(VKError error) {
                                        super.onError(error);
                                        numberOfRequests++;
                                        Log.i("extendedPosts", "JUST GOT ERROR LMAO, offset = " +
                                                vkRequest.getMethodParameters().get(VKApiConst.OFFSET).toString());
                                        post(error.toString());
                                    }
                                });
                            } else {
                                Log.i("extendedPosts", "Offset: " + offset  + ". PostsCount: " + postsCount );
                                offset += 100;
                            }

                    }, (Throwable e) -> {
                            Log.i("extendedPosts", "JUST GOT ERROR IN THE DISPOSABLE LMAO");
                            post(e.toString());

                    }, this::sortPosts
                    );
    }

    private void sortPosts() {
        clearRequest();

        long a = System.currentTimeMillis();

        Collections.sort(posts.items, (post1, post2) ->
                post2.getLikes().compareTo(post1.getLikes()));

        long b = System.currentTimeMillis();

        Log.i("extendedPosts", "Time of download and add response to array: " + ((b - c) / 1000.0)
                + " seconds. And time of sort is " + ((b - a) / 1000.0) + " seconds." );
        Log.i("extendedPosts", "Size of the array: " + posts.items.size());
        eventBus.post(new DialogEvent(true));
        //post(posts);
    }

    @Override
    public void clearRequest() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private void post(ExtendedPosts posts) {
        post(posts, null);
    }

    private void post(String error) {
        post(null, error);
    }

    private void post(ExtendedPosts posts, String error) {
        ExtendedPostsEvent event = new ExtendedPostsEvent();
        event.setError(error);
        event.setExtendedPosts(posts);
        eventBus.post(event);
    }
}
