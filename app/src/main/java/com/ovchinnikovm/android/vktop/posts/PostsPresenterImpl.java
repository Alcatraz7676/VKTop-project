package com.ovchinnikovm.android.vktop.posts;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ovchinnikovm.android.vktop.entities.RealmSortedItem;
import com.ovchinnikovm.android.vktop.entities.SortType;
import com.ovchinnikovm.android.vktop.posts.events.DialogEvent;
import com.ovchinnikovm.android.vktop.posts.events.PostsEvent;
import com.ovchinnikovm.android.vktop.posts.ui.PostsView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class PostsPresenterImpl implements PostsPresenter {
    private EventBus eventBus;
    private PostsView view;
    private PostsInteractor interactor;

    public PostsPresenterImpl(EventBus eventBus, PostsView view, PostsInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void stopVkRequest() {
        interactor.stopVkRequest();
    }

    @Override
    public void onDestroy() {
        view = null;
        interactor.stopRequest();
        eventBus.unregister(this);
    }

    @Override
    public void downloadPostsIds(Integer sortIntervalType, Long sortStart, Long sortEnd,
                                 RealmSortedItem realmSortedItem, Integer userId) {
        interactor.sortPosts(sortIntervalType, sortStart, sortEnd, realmSortedItem, userId);
    }

    @Override
    public void getPosts(int page) {
        interactor.getPosts(page);
    }

    @Override
    public void setSortedItem(Integer itemId) {
        interactor.setSortedItem(itemId);
    }

    @Override
    public void setSortType(SortType type) {
        interactor.setSortType(type);
    }

    @Override
    @Subscribe
    public void onEventMainThread(PostsEvent event) {
        String errorMsg = event.getError();
        if (view != null) {
            if(errorMsg != null) {
                view.onError(errorMsg);
            } else {
                if (event.getSortedPostsCount() != null)
                    view.setFirstPage(event.getPosts(), event.getSortedPostsCount());
                else
                    view.addPosts(event.getPosts());
            }
        }
    }

    @Override
    @Subscribe
    public void onEventMainThread(DialogEvent event) {
        view.incrementDialogNumber(event);
    }
}
