package com.ovchinnikovm.android.vktop.posts.di;

import android.support.v4.util.ArraySet;

import com.ovchinnikovm.android.vktop.entities.Posts;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import com.ovchinnikovm.android.vktop.posts.PostsInteractor;
import com.ovchinnikovm.android.vktop.posts.PostsInteractorImpl;
import com.ovchinnikovm.android.vktop.posts.PostsPresenter;
import com.ovchinnikovm.android.vktop.posts.PostsPresenterImpl;
import com.ovchinnikovm.android.vktop.posts.PostsRepository;
import com.ovchinnikovm.android.vktop.posts.PostsRepositoryImpl;
import com.ovchinnikovm.android.vktop.posts.adapters.OnItemClickListener;
import com.ovchinnikovm.android.vktop.posts.adapters.PostsAdapter;
import com.ovchinnikovm.android.vktop.posts.ui.PostsView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PostsModule {
    private PostsView view;
    private OnItemClickListener clickListener;

    public PostsModule (PostsView view, OnItemClickListener clickListener) {
        this.view = view;
        this.clickListener = clickListener;
    }

    @Provides
    @Singleton
    PostsAdapter providesAdapter(Posts posts, ImageLoader imageLoader, OnItemClickListener clickListener) {
        return new PostsAdapter(posts, imageLoader, clickListener);
    }

    @Provides
    @Singleton
    OnItemClickListener providesOnItemClickListener() {
        return this.clickListener;
    }

    @Provides
    @Singleton
    Posts providesItemsList() {
        Posts posts = new Posts();
        posts.items = new ArrayList<>();
        posts.groups = new ArraySet<>();
        posts.profiles = new ArraySet<>();
        posts.count = 0;
        return posts;
    }

    @Provides
    @Singleton
    PostsPresenter providePostsPresenter(EventBus eventBus, PostsView view, PostsInteractor interactor) {
        return new PostsPresenterImpl(eventBus, view, interactor);
    }

    @Provides
    @Singleton
    PostsView providesPostsView() {
        return this.view;
    }

    @Provides
    @Singleton
    PostsInteractor providesPostsInteractor(PostsRepository repository) {
        return new PostsInteractorImpl(repository);
    }

    @Provides
    @Singleton
    PostsRepository providesPostsRepository(EventBus eventBus) {
        return new PostsRepositoryImpl(eventBus);
    }
}
