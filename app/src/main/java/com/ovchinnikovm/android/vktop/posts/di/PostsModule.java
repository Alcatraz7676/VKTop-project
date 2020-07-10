package com.ovchinnikovm.android.vktop.posts.di;

import com.ovchinnikovm.android.vktop.posts.PostsInteractor;
import com.ovchinnikovm.android.vktop.posts.PostsInteractorImpl;
import com.ovchinnikovm.android.vktop.posts.PostsPresenter;
import com.ovchinnikovm.android.vktop.posts.PostsPresenterImpl;
import com.ovchinnikovm.android.vktop.posts.PostsRepository;
import com.ovchinnikovm.android.vktop.posts.PostsRepositoryImpl;
import com.ovchinnikovm.android.vktop.posts.adapters.OnItemClickListener;
import com.ovchinnikovm.android.vktop.posts.ui.PostsView;

import org.greenrobot.eventbus.EventBus;

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
    OnItemClickListener providesOnItemClickListener() {
        return this.clickListener;
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
