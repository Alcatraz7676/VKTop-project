package com.ovchinnikovm.android.vktop.groups.di;

import com.ovchinnikovm.android.vktop.groups.ui.GroupsActivity;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {LibsModule.class, GroupsModule.class})
public interface GroupsComponent {
    void inject(GroupsActivity activity);
}
