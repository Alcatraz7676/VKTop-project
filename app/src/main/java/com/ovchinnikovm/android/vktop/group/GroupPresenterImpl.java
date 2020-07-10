package com.ovchinnikovm.android.vktop.group;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    private Context context;

    public GroupPresenterImpl(EventBus eventBus, GroupView view, GroupInteractor interactor,
                              ImageLoader imageLoader, Context context) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
        this.imageLoader = imageLoader;
        this.context = context;
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
        if (isOnline()) {
            view.showLoadingIndicator();
            interactor.execute(groupId);
        } else {
            view.showDisconnectedView();
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    @Override
    public void loadIcon(ImageView imageView, String URL) {
        imageLoader.loadIcon(imageView, URL);
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
        return ((postsNumber / 250) + 1);
    }
}
