package com.ovchinnikovm.android.vktop.posts;

import com.ovchinnikovm.android.vktop.entities.RealmSortedItem;
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
    public void onDestroy() {
        view = null;
        interactor.stopRequest();
        eventBus.unregister(this);
    }

    @Override
    public void downloadPostsIds(Integer sortIntervalType, Long sortStart, Long sortEnd,
                                 RealmSortedItem realmSortedItem) {
        interactor.sortPosts(sortIntervalType, sortStart, sortEnd, realmSortedItem);
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
    public void setSortType(String type) {
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
                view.setPosts(event.getExtendedPosts());
            }
        }
    }

    @Override
    @Subscribe
    public void onEventMainThread(DialogEvent event) {
        view.incrementDialogNumber(event);
    }
}
