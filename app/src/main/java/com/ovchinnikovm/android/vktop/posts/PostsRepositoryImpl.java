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
import com.ovchinnikovm.android.vktop.entities.Photo;
import com.ovchinnikovm.android.vktop.entities.PostSortItem;
import com.ovchinnikovm.android.vktop.entities.PostsApiResponse;
import com.ovchinnikovm.android.vktop.entities.PostsSdkResponse;
import com.ovchinnikovm.android.vktop.entities.Profile;
import com.ovchinnikovm.android.vktop.entities.RealmSortedItem;
import com.ovchinnikovm.android.vktop.entities.SocialValue;
import com.ovchinnikovm.android.vktop.entities.SortType;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostsRepositoryImpl implements PostsRepository {
    private static final String BASE_URL = "https://api.vk.com/method/";
    private EventBus eventBus;
    private Gson gson = new GsonBuilder().create();

    private RealmSortedItem realmSortedItem;

    private SortType sortType = SortType.LIKES;

    private Disposable disposable;
    private VKRequest vkRequest;

    // Variables for log time
    private long c;
    private long a;

    public PostsRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getIds(Integer sortIntervalType, Long sortStart, Long sortEnd,
                       RealmSortedItem realmSortedItem) {

        this.realmSortedItem = realmSortedItem;

        PostsApiResponse<PostSortItem> posts = new PostsApiResponse<>();
        posts.response = new ArrayList<>();

        c = System.currentTimeMillis();

        switch (sortIntervalType) {
            case 0:
                downloadIds(posts);
                break;
            case 1:
                setSortRange(System.currentTimeMillis() - 31536000000L, System.currentTimeMillis());
                downloadIdsForLastPeriod(posts, 31536000);
                break;
            case 2:
                setSortRange(System.currentTimeMillis() - 2592000000L, System.currentTimeMillis());
                downloadIdsForLastPeriod(posts, 2592000);
                break;
            case 3:
                setSortRange(System.currentTimeMillis() - 604800000L, System.currentTimeMillis());
                downloadIdsForLastPeriod(posts, 604800);
                break;
            case 4:
                setSortRange(System.currentTimeMillis() - 86400000L, System.currentTimeMillis());
                downloadIdsForLastPeriod(posts, 86400);
                break;
            case 5:
                setSortRange(sortStart, sortEnd);
                downloadIdsInRange(posts, sortStart / 1000, sortEnd / 1000);
                break;
            default:
                downloadIds(posts);
                break;
        }
    }

    @Override
    public void setSortedItem(Integer itemId) {
        Realm realmInstance = Realm.getDefaultInstance();
        realmSortedItem = realmInstance.where(RealmSortedItem.class)
                .equalTo("sortId", itemId).findFirst();
    }

    private void setSortRange(long from, long to) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(from);
        String fromDateString = "" + calendar.get(Calendar.DAY_OF_MONTH) + "."
                + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR);
        calendar.setTimeInMillis(to);
        String toDateString = "" + calendar.get(Calendar.DAY_OF_MONTH) + "."
                + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR);
        realmSortedItem.setSortRange(fromDateString + " - " + toDateString);
    }

    private void sortPosts(PostsApiResponse<PostSortItem> posts) {
        clearRequest();

        if (posts.response.size() != 0) {
            long a = System.currentTimeMillis();

            List<PostSortItem> byLikes = new ArrayList<>();
            byLikes.addAll(posts.response);
            Collections.sort(byLikes, (post1, post2) ->
                    post2.getLikes().compareTo(post1.getLikes()));
            realmSortedItem.addByLikes(byLikes);

            List<PostSortItem> byShares = new ArrayList<>();
            byShares.addAll(posts.response);
            Collections.sort(byShares, (post1, post2) ->
                    post2.getReposts().compareTo(post1.getReposts()));
            realmSortedItem.addByShares(byShares);

            List<PostSortItem> byComments = new ArrayList<>();
            byComments.addAll(posts.response);
            Collections.sort(byComments, (post1, post2) ->
                    post2.getComments().compareTo(post1.getComments()));
            realmSortedItem.addByComments(byComments);

            if (realmSortedItem.getSortRange() == null) {
                setSortRange((posts.response.get(posts.response.size() - 1).getDate() * 1000L), System.currentTimeMillis());
            }

            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm1 -> {
                // increment index
                Number currentIdNum = realm1.where(RealmSortedItem.class).max("sortId");
                int nextId;
                if(currentIdNum == null) {
                    nextId = 1;
                } else {
                    nextId = currentIdNum.intValue() + 1;
                }
                realmSortedItem.setSortId(nextId);
                realm1.copyToRealm(realmSortedItem);

                long b = System.currentTimeMillis();

                Log.i("mytag", "Time of download and add response to array: " + ((b - c) / 1000.0)
                        + " seconds. And time of sort is " + ((b - a) / 1000.0) + " seconds.");
                Log.i("mytag", "Size of the array: " + posts.response.size());

                eventBus.post(new DialogEvent(true, nextId));
            });
        }
        eventBus.post(new DialogEvent(true));
    }

    @Override
    public void setSortType(SortType type) {
        sortType = type;
    }

    @Override
    public void getPosts(int page) {

        List<PostSortItem> posts;

        switch (sortType) {
            case LIKES:
                posts = realmSortedItem.getByLikes();
                break;
            case SHARES:
                posts = realmSortedItem.getByShares();
                break;
            case COMMENTS:
                posts = realmSortedItem.getByComments();
                break;
            default:
                posts = realmSortedItem.getByLikes();
        }

        Log.i("mytag", "Page num: " + String.valueOf(page));
        Log.i("mytag", "Posts size: " + String.valueOf(posts.size()));

        List<Integer> twentyIds = new ArrayList<>();
        int start = page*20;
        int end = (page*20)+20;
        if (posts.size() > start) {
            if (posts.size() >= end) {
                for (int i = start; i < end; i++) {
                    twentyIds.add(posts.get(i).getId());
                }
            } else {
                for (int i = start; i < posts.size(); i++) {
                    twentyIds.add(posts.get(i).getId());
                }
            }
            downloadPosts(twentyIds);
        } else if(posts.size() == 0) {
            post(null, null);
        }
    }

    private void downloadPosts(List<Integer> twentyIds) {
        StringBuilder postsParameters = new StringBuilder();
        for(Integer id : twentyIds) {
            postsParameters.append(realmSortedItem.getGroupId()).append("_").append(id).append(",");
        }
        vkRequest = new VKApiWall()
                .getById(VKParameters.from(VKApiConst.POSTS, postsParameters.toString(),
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
                                    post.photos.add(new Photo(attachment.getPhoto(),
                                            (attachment.getPhotoHeight() * 1.00) / attachment.getPhotoWidth()));
                                    break;
                                } else if (attachment.getType().equals("posted_photo")) {
                                    post.photos.add(new Photo(attachment.getPostedPhoto(),
                                            (attachment.getPhotoHeight() * 1.00) / attachment.getPhotoWidth()));
                                    break;
                                }
                            }
                            // Multiple photos
                        } else if (numberOfPhotos > 1) {
                            for (Attachment attachment : post.attachments) {
                                if (attachment.getType().equals("photo")) {
                                    post.photos.add(new Photo(attachment.getPhoto(), null));
                                } else if (attachment.getType().equals("posted_photo")) {
                                    post.photos.add(new Photo(attachment.getPhoto(), null));
                                }
                            }
                        }

                        // Media attachments
                        post.other = new ArrayList<>();
                        for (Attachment attachment : post.attachments) {
                            if (attachment.getType().equals("audio") || attachment.getType().equals("video")
                                    || attachment.getType().equals("link") || attachment.getType().equals("poll")
                                    || attachment.getType().equals("doc")) {
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
                                Log.i("testing", profile.getFullName());
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
                                    post.nestedPost.get(0).photos.add(new Photo(attachment.getPhoto(),
                                            (attachment.getPhotoHeight() * 1.00) / attachment.getPhotoWidth()));
                                    break;
                                } else if (attachment.getType().equals("posted_photo")) {
                                    post.nestedPost.get(0).photos.add(new Photo(attachment.getPostedPhoto(),
                                            (attachment.getPhotoHeight() * 1.00) / attachment.getPhotoWidth()));
                                    break;
                                }
                            }
                            // Multiple photos
                        } else if (numberOfPhotos > 1) {
                            for (Attachment attachment : post.nestedPost.get(0).attachments) {
                                if (attachment.getType().equals("photo")) {
                                    post.nestedPost.get(0).photos.add(new Photo(attachment.getPhoto(), null));
                                } else if (attachment.getType().equals("posted_photo")) {
                                    post.nestedPost.get(0).photos.add(new Photo(attachment.getPhoto(), null));
                                }
                            }
                        }

                        // Media attachments
                        post.nestedPost.get(0).other = new ArrayList<>();
                        for (Attachment attachment : post.nestedPost.get(0).attachments) {
                            if (attachment.getType().equals("audio") || attachment.getType().equals("video")
                                    || attachment.getType().equals("link") || attachment.getType().equals("poll")
                                    || attachment.getType().equals("doc")) {
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
                .intervalRange(0, (realmSortedItem.getPostsCount() / 100) + 1, 400, 400, TimeUnit.MILLISECONDS)
                .flatMap(offset -> requestInterface
                        .getPosts(realmSortedItem.getGroupId(), offset * 100,
                                VKAccessToken.currentToken().accessToken)
                        .map(postsApiResponse -> postsApiResponse.response)
                        .retryWhen(new RetryWithDelay(400))
                        .subscribeOn(Schedulers.computation()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        items -> {
                            long b = System.currentTimeMillis();
                            Log.i("tmpTag", "Ids: Get that post in " + ((b - a) / 1000.0)
                                    + " seconds. First id = " + items.get(0).getId());
                            a = System.currentTimeMillis();
                            posts.response.addAll(items);
                            eventBus.post(new DialogEvent(false));
                        },  e -> {
                            Log.i("tmpTag", "JUST GOT ERROR WHILE LOAD IDS LMAO");
                            post(e.toString());
                        }, () -> {
                            Log.i("tmpTag", "IN THE COMPLETE CALLBACK");
                            sortPosts(posts);
                        }
                );

    }

    private void downloadIdsForLastPeriod(PostsApiResponse<PostSortItem> posts, int time) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(PostsApiResponse.class, new PostsApiResponseDeserializer());
        Gson gson = gsonBuilder.create();

        RequestInterface requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(RequestInterface.class);

        Log.i("mytag", VKAccessToken.currentToken().accessToken);

        int currentTimeInSeconds = (int) (System.currentTimeMillis() / 1000);
        a = System.currentTimeMillis();
        disposable = Observable
                .intervalRange(0, (realmSortedItem.getPostsCount() / 100) + 1, 400, 400, TimeUnit.MILLISECONDS)
                .flatMap(offset -> requestInterface
                        .getPosts(realmSortedItem.getGroupId(), offset * 100,
                                VKAccessToken.currentToken().accessToken)
                        .map(postsApiResponse -> postsApiResponse.response)
                        .retryWhen(new RetryWithDelay(400))
                        .subscribeOn(Schedulers.computation()))
                .takeUntil(items -> currentTimeInSeconds - items.get(items.size() - 1).getDate() > time)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        items -> {
                            long b = System.currentTimeMillis();
                            Log.i("mytag", "IdsWithForLastPeriod: Get that post in " + ((b - a) / 1000.0)
                                    + " seconds.");
                            a = System.currentTimeMillis();
                            if (currentTimeInSeconds - items.get(items.size() - 1).getDate() > time) {
                                PostSortItem item;
                                // Проверка на случай закрепленного поста
                                if (items.get(0).getDate() > items.get(1).getDate()) {
                                    for (int i = 0; i < items.size(); i++) {
                                        item = items.get(i);

                                        Log.i("mytag", "Id: " + item.getId() + ".Range in time: "
                                                + (currentTimeInSeconds - item.getDate()) + ". Time: " + time);

                                        if (currentTimeInSeconds - item.getDate() <= time) {
                                            posts.response.add(item);
                                        } else
                                            break;
                                    }
                                } else {
                                    for (int i = 1; i < items.size(); i++) {
                                        item = items.get(i);

                                        Log.i("mytag", "Id: " + item.getId() + ".Range in time: "
                                                + (currentTimeInSeconds - item.getDate()) + ". Time: " + time);

                                        if (currentTimeInSeconds - item.getDate() <= time) {
                                            posts.response.add(item);
                                        } else
                                            break;
                                    }
                                    if (currentTimeInSeconds - items.get(0).getDate() <= time) {
                                        posts.response.add(items.get(0));
                                    }
                                }
                            } else if (items.get(0).getDate() > items.get(1).getDate()) {
                                if (currentTimeInSeconds - items.get(0).getDate() > time) {
                                    items.remove(0);
                                }
                                posts.response.addAll(items);
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
                .intervalRange(0, (realmSortedItem.getPostsCount() / 100) + 1, 400, 400, TimeUnit.MILLISECONDS)
                .flatMap(offset -> requestInterface
                        .getPosts(realmSortedItem.getGroupId(), offset * 100,
                                VKAccessToken.currentToken().accessToken)
                        .map(postsApiResponse -> postsApiResponse.response)
                        .retryWhen(new RetryWithDelay(400))
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
                                        Log.i("mytag", "Id: " + item.getId() + ". Sort start: "
                                                + sortStart + ". Sort end: " + sortEnd + ". Post time: " + item.getDate());
                                        if (item.getDate() >= sortStart && item.getDate() <= sortEnd)
                                            posts.response.add(item);
                                    }
                                } else {
                                    PostSortItem item;
                                    // Проверка на случай закрепленного поста
                                    if (items.get(0).getDate() > items.get(1).getDate()) {
                                        for (int i = 0; i < items.size(); i++) {
                                            item = items.get(i);
                                            Log.i("mytag", "Id: " + item.getId() + ". Sort start: "
                                                    + sortStart + ". Sort end: " + sortEnd + ". Post time: " + item.getDate());
                                            if (item.getDate() >= sortStart)
                                                posts.response.add(item);
                                            else
                                                break;
                                        }
                                    } else {
                                        for (int i = 1; i < items.size(); i++) {
                                            item = items.get(i);
                                            Log.i("mytag", "Id: " + item.getId() + ". Sort start: "
                                                    + sortStart + ". Sort end: " + sortEnd + ". Post time: " + item.getDate());
                                            if (item.getDate() >= sortStart)
                                                posts.response.add(item);
                                            else
                                                break;
                                        }
                                        if (items.get(0).getDate() >= sortStart) {
                                            posts.response.add(items.get(0));
                                        }
                                    }
                                }
                            } else if (items.get(0).getDate() > sortEnd) {
                                for (PostSortItem item : items) {
                                    Log.i("mytag", "Id: " + item.getId() + ". Sort start: "
                                            + sortStart + ". Sort end: " + sortEnd + ". Post time: " + item.getDate());
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
    public void cancelVkRequest() {
        if (vkRequest != null) {
            vkRequest.cancel();
            vkRequest = null;
        }
    }

    @Override
    public void clearRequest() {
        cancelVkRequest();
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
        if (error != null)
            event.setError(error);
        else
            event.setPosts(posts.items);
        eventBus.post(event);
    }

    public class RetryWithDelay implements Function<Observable<? extends Throwable>, Observable<?>> {
        private final int retryDelayMillis;
        private int retryCount;

        public RetryWithDelay(final int retryDelayMillis) {
            this.retryDelayMillis = retryDelayMillis;
            this.retryCount = 0;
        }

        @Override
        public Observable<?> apply(final Observable<? extends Throwable> attempts) {
            return attempts
                    .flatMap((Function<Throwable, Observable<?>>) throwable -> {
                        post("SKIP BUNCH OF POSTS num: " + retryCount);
                        Log.i("tmpTag", "SKIP BUNCH OF POSTS num: " + retryCount);
                        ++retryCount;
                        // When this Observable calls onNext, the original
                        // Observable will be retried (i.e. re-subscribed).
                        return Observable.timer(retryDelayMillis,
                                TimeUnit.MILLISECONDS);
                    });
        }
    }

    private class PostsApiResponseDeserializer
            implements JsonDeserializer<PostsApiResponse<PostSortItem>> {

        @Override
        public PostsApiResponse<PostSortItem> deserialize(JsonElement json, Type type,
                                            JsonDeserializationContext context) throws JsonParseException {

            Log.i("mytag", json.toString());

            JsonArray jArray = json.getAsJsonObject().getAsJsonObject("response").getAsJsonArray("items");

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
