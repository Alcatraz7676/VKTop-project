package com.ovchinnikovm.android.vktop.group;

import android.widget.ImageView;

import com.ovchinnikovm.android.vktop.group.events.GroupEvent;
import com.ovchinnikovm.android.vktop.group.ui.GroupView;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class GroupPresenterImpl implements GroupPresenter {
    private EventBus eventBus;
    private GroupView view;
    private GroupInteractor interactor;
    private ImageLoader imageLoader;

    public GroupPresenterImpl(EventBus eventBus, GroupView view, GroupInteractor interactor,
                              ImageLoader imageLoader) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
        this.imageLoader = imageLoader;
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
    }

    @Override
    public void getPostsCount(Integer groupId) {
        interactor.execute(groupId);
    }

    @Override
    public void loadIcon(ImageView imageView, String URL) {
        imageLoader.load(imageView, URL);
    }

    @Override
    @Subscribe
    public void onEventMainThread(GroupEvent event) {
        String errorMsg = event.getError();
        if (view != null) {
            if(errorMsg != null) {
                view.onError(errorMsg);
            } else if (event.getPostsNumber() != 0) {
                view.setPostsAndTime(event.getPostsNumber(), getTime(event.getPostsNumber()));
            }
        }
    }

    private Integer getTime(Integer postsNumber) {
        return postsNumber / 300;
    }
}
