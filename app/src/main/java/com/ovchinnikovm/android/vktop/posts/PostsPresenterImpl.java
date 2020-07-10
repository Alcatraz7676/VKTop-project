package com.ovchinnikovm.android.vktop.posts;

import android.support.v4.util.ArraySet;
import android.util.Log;

import com.ovchinnikovm.android.vktop.entities.Posts;
import com.ovchinnikovm.android.vktop.posts.events.DialogEvent;
import com.ovchinnikovm.android.vktop.posts.events.PostsEvent;
import com.ovchinnikovm.android.vktop.posts.ui.PostsView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class PostsPresenterImpl implements PostsPresenter {
    private EventBus eventBus;
    private PostsView view;
    private PostsInteractor interactor;
    private Posts posts;

    public PostsPresenterImpl(EventBus eventBus, PostsView view, PostsInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onResume() {
        eventBus.register(this);
    }

    @Override
    public void onPause() {
        eventBus.unregister(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        interactor.stopRequest();
    }

    @Override
    public void downloadPosts(Integer groupId, Integer postsCount) {
        interactor.execute(groupId, postsCount);
    }

    @Override
    public Posts getPosts(int page) {
        Log.i("mytag", String.valueOf(page));
        Log.i("mytag", String.valueOf(posts.items.size()));
        Posts twentyPosts = new Posts();
        twentyPosts.items = new ArrayList<>();
        if (posts.items.size() > page*20) {
            if (posts.items.size() >= (page*20)+20) {
                twentyPosts.items.addAll(posts.items.subList(page*20, (page*20)+20));
            } else {
                twentyPosts.items.addAll(posts.items.subList(page*20, posts.items.size()));
            }
        }
        return twentyPosts;
    }

    @Override
    @Subscribe
    public void onEventMainThread(PostsEvent event) {
        String errorMsg = event.getError();
        if (view != null) {
            if(errorMsg != null) {
                view.onError(errorMsg);
            } else {
                posts = event.getPosts();

                Posts twentyPosts = new Posts();
                twentyPosts.items = new ArrayList<>();
                if (posts.items.size() >= 20) {
                    twentyPosts.items.addAll(posts.items.subList(0, 20));
                } else {
                    twentyPosts.items.addAll(posts.items.subList(0, posts.items.size()));
                }
                twentyPosts.groups = new ArraySet<>();
                twentyPosts.groups.addAll(posts.groups);
                twentyPosts.profiles = new ArraySet<>();
                twentyPosts.profiles.addAll(posts.profiles);

                view.setPosts(twentyPosts);
            }
        }
    }

    @Override
    @Subscribe
    public void onEventMainThread(DialogEvent event) {
        view.incrementDialogNumber(event);
    }
}
