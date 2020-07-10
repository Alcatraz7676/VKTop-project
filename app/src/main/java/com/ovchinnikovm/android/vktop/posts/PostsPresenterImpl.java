package com.ovchinnikovm.android.vktop.posts;

import com.ovchinnikovm.android.vktop.posts.events.DialogEvent;
import com.ovchinnikovm.android.vktop.posts.events.ExtendedPostsEvent;
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
    public void downloadPostsIds(Integer groupId, Integer postsCount) {
        interactor.execute(groupId, postsCount);
    }

    @Override
    public void getPosts(int page) {
        interactor.execute(page);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ExtendedPostsEvent event) {
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
