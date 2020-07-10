package com.ovchinnikovm.android.vktop.groups.di;

import android.content.Context;

import com.ovchinnikovm.android.vktop.entities.Group;
import com.ovchinnikovm.android.vktop.groups.GroupsInteractor;
import com.ovchinnikovm.android.vktop.groups.GroupsInteractorImpl;
import com.ovchinnikovm.android.vktop.groups.GroupsPresenter;
import com.ovchinnikovm.android.vktop.groups.GroupsPresenterImpl;
import com.ovchinnikovm.android.vktop.groups.GroupsRepository;
import com.ovchinnikovm.android.vktop.groups.GroupsRepositoryImpl;
import com.ovchinnikovm.android.vktop.groups.adapters.GroupsAdapter;
import com.ovchinnikovm.android.vktop.groups.adapters.OnItemClickListener;
import com.ovchinnikovm.android.vktop.groups.ui.GroupsView;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GroupsModule {
    private GroupsView view;
    private OnItemClickListener clickListener;

    public GroupsModule(GroupsView view, OnItemClickListener clickListener) {
        this.view = view;
        this.clickListener = clickListener;
    }

    @Provides
    @Singleton
    GroupsAdapter providesAdapter(List<Group> groups, ImageLoader imageLoader, OnItemClickListener clickListener,
                                  Context context) {
        return new GroupsAdapter(groups, imageLoader, clickListener, context);
    }

    @Provides
    @Singleton
    OnItemClickListener providesOnItemClickListener() {
        return this.clickListener;
    }

    @Provides
    @Singleton
    List<Group> providesItemsList() {
        return new ArrayList<>();
    }

    @Provides
    @Singleton
    GroupsPresenter provideGroupsPresenter(EventBus eventBus, GroupsView view,
                                           GroupsInteractor interactor, Context context) {
        return new GroupsPresenterImpl(eventBus, view, interactor, context);
    }

    @Provides
    @Singleton
    GroupsView providesGroupsView() {
        return this.view;
    }

    @Provides
    @Singleton
    GroupsInteractor providesGroupsInteractor(GroupsRepository repository) {
        return new GroupsInteractorImpl(repository);
    }

    @Provides
    @Singleton
    GroupsRepository providesGroupsRepository(EventBus eventBus) {
        return new GroupsRepositoryImpl(eventBus);
    }
}
