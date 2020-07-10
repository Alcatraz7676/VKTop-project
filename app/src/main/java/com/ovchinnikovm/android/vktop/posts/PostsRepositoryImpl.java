package com.ovchinnikovm.android.vktop.posts;

import android.support.v4.util.ArraySet;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.ovchinnikovm.android.vktop.entities.Attachment;
import com.ovchinnikovm.android.vktop.entities.ExtendedPost;
import com.ovchinnikovm.android.vktop.entities.ExtendedPosts;
import com.ovchinnikovm.android.vktop.entities.Group;
import com.ovchinnikovm.android.vktop.entities.PostSortItem;
import com.ovchinnikovm.android.vktop.entities.PostsApiResponse;
import com.ovchinnikovm.android.vktop.entities.PostsSdkResponse;
import com.ovchinnikovm.android.vktop.entities.Profile;
import com.ovchinnikovm.android.vktop.entities.SocialValue;
import com.ovchinnikovm.android.vktop.posts.events.DialogEvent;
import com.ovchinnikovm.android.vktop.posts.events.PostsEvent;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiWall;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostsRepositoryImpl implements PostsRepository {
    private static final String BASE_URL = "https://api.vk.com/method/";
    private EventBus eventBus;
    private Gson gson;

    private String groupId;
    private int postsCount;

    private PostsApiResponse<PostSortItem> byLikes;
    private PostsApiResponse<PostSortItem> byShares;
    private PostsApiResponse<PostSortItem> byComments;

    private String sortType = "likes";

    private Disposable disposable;

    // Variables for log time
    private long c;
    private long a;

    public PostsRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getIds(Integer groupId, Integer postsCount, Integer sortIntervalType,
                       Long sortStart, Long sortEnd) {

        this.groupId = "-" + groupId.toString();
        this.postsCount = postsCount;

        PostsApiResponse<PostSortItem> posts = new PostsApiResponse<>();
        posts.response = new ArrayList<>();

        gson = new GsonBuilder().create();

        c = System.currentTimeMillis();

        switch (sortIntervalType) {
            case 0:
                downloadIds(posts);
                break;
            case 1:
                downloadIdsForLastPeriod(posts, 31536000);
                break;
            case 2:
                downloadIdsForLastPeriod(posts, 2592000);
                break;
            case 3:
                downloadIdsForLastPeriod(posts, 604800);
                break;
            case 4:
                downloadIdsInRange(posts, sortStart, sortEnd);
                break;
            default:
                downloadIds(posts);
                break;
        }
    }

    /*
    private void downloadIdsOld(Posts posts) {
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
                                        PostsApiResponse<Posts> postsResponse = gson
                                                .fromJson(response.responseString, new TypeToken<PostsApiResponse<Posts>>(){}.getType());
                                        posts.item.addAll(postsResponse.response.items);
                                        eventBus.post(new DialogEvent(false));
                                        requestNumber++;
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

                        }, () -> sortPosts(posts)
                );
    }
    */

    private void sortPosts(PostsApiResponse<PostSortItem> posts) {
        clearRequest();

        long a = System.currentTimeMillis();

        byLikes = new PostsApiResponse<>();
        byLikes.response = new ArrayList<>();
        byLikes.response.addAll(posts.response);
        Collections.sort(byLikes.response, (post1, post2) ->
                post2.getLikes().compareTo(post1.getLikes()));

        byShares = new PostsApiResponse<>();
        byShares.response = new ArrayList<>();
        byShares.response.addAll(posts.response);
        Collections.sort(byShares.response, (post1, post2) ->
                post2.getReposts().compareTo(post1.getReposts()));

        byComments = new PostsApiResponse<>();
        byComments.response = new ArrayList<>();
        byComments.response.addAll(posts.response);
        Collections.sort(byComments.response, (post1, post2) ->
                post2.getComments().compareTo(post1.getComments()));

        long b = System.currentTimeMillis();

        Log.i("mytag", "Time of download and add response to array: " + ((b - c) / 1000.0)
                + " seconds. And time of sort is " + ((b - a) / 1000.0) + " seconds." );
        Log.i("mytag", "Size of the array: " + posts.response.size());
        eventBus.post(new DialogEvent(true));
    }

    @Override
    public void setSortType(String type) {
        sortType = type;
    }

    @Override
    public void getPosts(int page) {

        PostsApiResponse<PostSortItem> posts;

        switch (sortType) {
            case "likes":
                posts = byLikes;
                break;
            case "shares":
                posts = byShares;
                break;
            case "comments":
                posts = byComments;
                break;
            default:
                posts = byLikes;
        }

        Log.i("mytag", String.valueOf(page));
        Log.i("mytag", String.valueOf(posts.response.size()));

        List<Integer> twentyIds = new ArrayList<>();
        int start = page*20;
        int end = (page*20)+20;
        if (posts.response.size() > start) {
            if (posts.response.size() >= end) {
                for (int i = start; i < end; i++) {
                    twentyIds.add(posts.response.get(i).getId());
                }
            } else {
                for (int i = start; i < posts.response.size(); i++) {
                    twentyIds.add(posts.response.get(i).getId());
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

                PostsSdkResponse<ExtendedPosts> postsResponse = gson
                        .fromJson(response.responseString, new TypeToken<PostsSdkResponse<ExtendedPosts>>(){}.getType());

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
                Log.i("mytag", "JUST GOT ERROR WHILE LOAD POSTS LMAO");
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

    private void downloadIds(PostsApiResponse<PostSortItem> posts) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(PostsApiResponse.class, new PostsApiResponseDeserializer());
        Gson gson = gsonBuilder.create();

        RequestInterface requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(RequestInterface.class);

        a = System.currentTimeMillis();
        disposable = Observable
                .intervalRange(0, (postsCount / 100) + 1, 400, 400, TimeUnit.MILLISECONDS)
                .flatMap(offset -> requestInterface
                        .getPosts(groupId, offset * 100, 100, "owner",
                                VKAccessToken.currentToken().accessToken)
                        .map(postsApiResponse -> postsApiResponse.response)
                        .retryWhen(new RetryWithDelay(3, 400))
                        .subscribeOn(Schedulers.computation()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        items -> {
                            long b = System.currentTimeMillis();
                            Log.i("mytag", "Ids: Get that post in " + ((b - a) / 1000.0)
                                    + " seconds. First id = " + items.get(0).getId());
                            a = System.currentTimeMillis();
                            posts.response.addAll(items);
                            eventBus.post(new DialogEvent(false));
                        },  e -> {
                            Log.i("mytag", "JUST GOT ERROR WHILE LOAD IDS LMAO");
                            post(e.toString());
                }, () -> {
                            Log.i("mytag", "IN THE COMPLETE CALLBACK");
                            sortPosts(posts);
                }
                );
    }

    private void downloadIdsForLastPeriod(PostsApiResponse<PostSortItem> posts, int time) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(PostsApiResponse.class, new PostsApiResponseWithDateDeserializer());
        Gson gson = gsonBuilder.create();

        RequestInterface requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(RequestInterface.class);

        long currentTimeInSeconds = System.currentTimeMillis() / 1000;
        a = System.currentTimeMillis();
        disposable = Observable
                .intervalRange(0, (postsCount / 100) + 1, 400, 400, TimeUnit.MILLISECONDS)
                .flatMap(offset -> requestInterface
                        .getPosts(groupId, offset * 100, 100, "owner",
                                VKAccessToken.currentToken().accessToken)
                        .map(postsApiResponse -> postsApiResponse.response)
                        .retryWhen(new RetryWithDelay(3, 400))
                        .subscribeOn(Schedulers.computation()))
                .takeUntil(items -> currentTimeInSeconds - items.get(items.size() - 1).getDate() > time)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        items -> {
                            long b = System.currentTimeMillis();
                            Log.i("mytag", "IdsWithForLastPeriod: Get that post in " + ((b - a) / 1000.0)
                                    + " seconds. First id = " + items.get(0).getId());
                            a = System.currentTimeMillis();
                            if (currentTimeInSeconds - items.get(items.size() - 1).getDate() > time) {
                                for (PostSortItem item : items) {
                                    if (currentTimeInSeconds - item.getDate() <= time) {
                                        posts.response.add(item);
                                    } else
                                        break;
                                }
                            } else
                                posts.response.addAll(items);
                        },  e -> {
                            Log.i("mytag", "JUST GOT ERROR WHILE LOAD IDS LMAO");
                            post(e.toString());
                        }, () -> {
                            Log.i("mytag", "IN THE COMPLETE LAST PERIOD CALLBACK");
                            sortPosts(posts);
                        }
                );
    }

    private void downloadIdsInRange(PostsApiResponse<PostSortItem> posts, Long sortStart,
                                    Long sortEnd) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(PostsApiResponse.class, new PostsApiResponseWithDateDeserializer());
        Gson gson = gsonBuilder.create();

        RequestInterface requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(RequestInterface.class);

        a = System.currentTimeMillis();
        disposable = Observable
                .intervalRange(0, (postsCount / 100) + 1, 400, 400, TimeUnit.MILLISECONDS)
                .flatMap(offset -> requestInterface
                        .getPosts(groupId, offset * 100, 100, "owner",
                                VKAccessToken.currentToken().accessToken)
                        .map(postsApiResponse -> postsApiResponse.response)
                        .retryWhen(new RetryWithDelay(3, 400))
                        .subscribeOn(Schedulers.computation()))
                .takeUntil(items -> items.get(items.size() - 1).getDate() < sortStart)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        items -> {
                            long b = System.currentTimeMillis();
                            Log.i("mytag", "IdsInRange: Get that post in " + ((b - a) / 1000.0)
                                    + " seconds. First id = " + items.get(0).getId());
                            a = System.currentTimeMillis();
                            if (items.get(items.size() - 1).getDate() < sortStart) {
                                if (items.get(0).getDate() > sortEnd) {
                                    for (PostSortItem item : items) {
                                        if (item.getDate() >= sortStart && item.getDate() <= sortEnd)
                                            posts.response.add(item);
                                    }
                                } else {
                                    for (PostSortItem item : items) {
                                        if (item.getDate() >= sortStart)
                                            posts.response.add(item);
                                        else
                                            break;
                                    }
                                }
                            } else if (items.get(0).getDate() > sortEnd) {
                                for (PostSortItem item : items) {
                                    if (item.getDate() <= sortEnd)
                                        posts.response.add(item);
                                }
                            }
                            else
                                posts.response.addAll(items);
                        },  e -> {
                            Log.i("mytag", "JUST GOT ERROR WHILE LOAD IDS IN RANGE LMAO");
                            post(e.toString());
                        }, () -> {
                            Log.i("mytag", "IN THE COMPLETE IDS RANGE CALLBACK");
                            sortPosts(posts);
                        }
                );
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
        PostsEvent event = new PostsEvent();
        event.setError(error);
        event.setExtendedPosts(posts);
        eventBus.post(event);
    }

    public class RetryWithDelay implements Function<Observable<? extends Throwable>, Observable<?>> {
        private final int maxRetries;
        private final int retryDelayMillis;
        private int retryCount;

        public RetryWithDelay(final int maxRetries, final int retryDelayMillis) {
            this.maxRetries = maxRetries;
            this.retryDelayMillis = retryDelayMillis;
            this.retryCount = 0;
        }

        @Override
        public Observable<?> apply(final Observable<? extends Throwable> attempts) {
            return attempts
                    .flatMap(new Function<Throwable, Observable<?>>() {
                        @Override
                        public Observable<?> apply(final Throwable throwable) {
                            post("SKIP BUNCH OF POSTS num: " + retryCount);
                            Log.i("mytag", "SKIP BUNCH OF POSTS num: " + retryCount);
                            if (++retryCount < maxRetries) {
                                // When this Observable calls onNext, the original
                                // Observable will be retried (i.e. re-subscribed).
                                return Observable.timer(retryDelayMillis,
                                        TimeUnit.MILLISECONDS);
                            }
                            // Max retries hit. Just pass the error along.
                            return Observable.error(throwable);
                        }
                    });
        }
    }

    private class PostsApiResponseDeserializer
            implements JsonDeserializer<PostsApiResponse<PostSortItem>> {

        @Override
        public PostsApiResponse<PostSortItem> deserialize(JsonElement json, Type type,
                                            JsonDeserializationContext context) throws JsonParseException {

            Log.i("mytag", json.toString());

            JsonArray jArray = json.getAsJsonObject().get("response").getAsJsonArray();

            PostsApiResponse<PostSortItem> vkPostItem = new PostsApiResponse<>();
            vkPostItem.response = new ArrayList<>();

            for (int i=1; i < jArray.size(); i++) {
                JsonObject jObject = (JsonObject) jArray.get(i);
                //assuming you have the suitable constructor...
                PostSortItem item = new PostSortItem(jObject.get("id").getAsInt(),
                        new SocialValue(jObject.getAsJsonObject("likes").get("count").getAsInt()),
                        new SocialValue(jObject.getAsJsonObject("reposts").get("count").getAsInt()),
                        new SocialValue(jObject.getAsJsonObject("comments").get("count").getAsInt()));
                vkPostItem.response.add(item);
            }

            return vkPostItem;
        }
    }

    private class PostsApiResponseWithDateDeserializer
            implements JsonDeserializer<PostsApiResponse<PostSortItem>> {

        @Override
        public PostsApiResponse<PostSortItem> deserialize(JsonElement json, Type type,
                                            JsonDeserializationContext context) throws JsonParseException {

            Log.i("mytag", json.toString());

            JsonArray jArray = json.getAsJsonObject().get("response").getAsJsonArray();

            PostsApiResponse<PostSortItem> vkPostItem = new PostsApiResponse<>();
            vkPostItem.response = new ArrayList<>();

            for (int i=1; i < jArray.size(); i++) {
                JsonObject jObject = (JsonObject) jArray.get(i);
                //assuming you have the suitable constructor...
                PostSortItem item = new PostSortItem(jObject.get("id").getAsInt(),
                        new SocialValue(jObject.getAsJsonObject("likes").get("count").getAsInt()),
                        new SocialValue(jObject.getAsJsonObject("reposts").get("count").getAsInt()),
                        new SocialValue(jObject.getAsJsonObject("comments").get("count").getAsInt()),
                        jObject.get("date").getAsInt());
                vkPostItem.response.add(item);
            }

            return vkPostItem;
        }
    }
}
