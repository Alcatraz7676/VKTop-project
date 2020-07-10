package com.ovchinnikovm.android.vktop.groups;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ovchinnikovm.android.vktop.groups.events.GroupsEvent;
import com.ovchinnikovm.android.vktop.groups.ui.GroupsView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class GroupsPresenterImpl implements GroupsPresenter {
    private EventBus eventBus;
    private GroupsView view;
    private GroupsInteractor interactor;
    private Context context;

    public GroupsPresenterImpl(EventBus eventBus, GroupsView view, GroupsInteractor interactor,
                               Context context) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
        this.context = context;
    }

    @Override
    public void onStart() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        view = null;
        context = null;
    }

    @Override
    public void getGroups() {
        if (isOnline()) {
            view.showLoadingIndicator();
            interactor.getGroups();
        } else {
            view.showDisconnectedView();
        }
    }

    @Override
    public void getGlobalGroups(String query) {
        interactor.getGlobalGroups(query);
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    @Override
    @Subscribe
    public void onEventMainThread(GroupsEvent event) {
        String errorMsg = event.getError();
        if (view != null) {
            if(errorMsg != null) {
                view.onError(errorMsg);
            } else if (!event.isGlobal()){
                view.setGroups(event.getGroups());
                view.showGroups();
            } else {
                view.addGlobalGroups(event.getGroups());
            }
        }
    }
}
