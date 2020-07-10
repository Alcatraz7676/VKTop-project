package com.ovchinnikovm.android.vktop.posts.di;

import com.ovchinnikovm.android.vktop.lib.di.LibsModule;
import com.ovchinnikovm.android.vktop.posts.ui.PostsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {LibsModule.class, PostsModule.class})
public interface PostsComponent {
    void inject(PostsActivity activity);
}
