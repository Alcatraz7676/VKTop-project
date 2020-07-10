package com.ovchinnikovm.android.vktop.group.di;

import com.ovchinnikovm.android.vktop.group.GroupInteractor;
import com.ovchinnikovm.android.vktop.group.GroupInteractorImpl;
import com.ovchinnikovm.android.vktop.group.GroupPresenter;
import com.ovchinnikovm.android.vktop.group.GroupPresenterImpl;
import com.ovchinnikovm.android.vktop.group.GroupRepository;
import com.ovchinnikovm.android.vktop.group.GroupRepositoryImpl;
import com.ovchinnikovm.android.vktop.group.ui.GroupView;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GroupModule {
    private GroupView view;

    public GroupModule(GroupView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    GroupPresenter provideGroupPresenter(EventBus eventBus, GroupView view,
                                         GroupInteractor interactor, ImageLoader imageLoader) {
        return new GroupPresenterImpl(eventBus, view, interactor, imageLoader);
    }

    @Provides
    @Singleton
    GroupView providesGroupView() {
        return this.view;
    }

    @Provides
    @Singleton
    GroupInteractor providesGroupInteractor(GroupRepository repository) {
        return new GroupInteractorImpl(repository);
    }

    @Provides
    @Singleton
    GroupRepository providesGroupRepository(EventBus eventBus) {
        return new GroupRepositoryImpl(eventBus);
    }
}
