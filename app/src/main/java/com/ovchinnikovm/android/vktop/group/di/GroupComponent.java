package com.ovchinnikovm.android.vktop.group.di;

import com.ovchinnikovm.android.vktop.group.ui.GroupActivity;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {LibsModule.class, GroupModule.class})
public interface GroupComponent {
    void inject(GroupActivity activity);
}
