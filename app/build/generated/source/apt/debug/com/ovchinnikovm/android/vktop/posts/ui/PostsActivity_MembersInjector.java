package com.ovchinnikovm.android.vktop.posts.ui;

import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import com.ovchinnikovm.android.vktop.posts.PostsPresenter;
import com.ovchinnikovm.android.vktop.posts.adapters.OnItemClickListener;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PostsActivity_MembersInjector implements MembersInjector<PostsActivity> {
  private final Provider<ImageLoader> imageLoaderProvider;

  private final Provider<OnItemClickListener> onItemClickListenerProvider;

  private final Provider<PostsPresenter> presenterProvider;

  public PostsActivity_MembersInjector(
      Provider<ImageLoader> imageLoaderProvider,
      Provider<OnItemClickListener> onItemClickListenerProvider,
      Provider<PostsPresenter> presenterProvider) {
    this.imageLoaderProvider = imageLoaderProvider;
    this.onItemClickListenerProvider = onItemClickListenerProvider;
    this.presenterProvider = presenterProvider;
  }

  public static MembersInjector<PostsActivity> create(
      Provider<ImageLoader> imageLoaderProvider,
      Provider<OnItemClickListener> onItemClickListenerProvider,
      Provider<PostsPresenter> presenterProvider) {
    return new PostsActivity_MembersInjector(
        imageLoaderProvider, onItemClickListenerProvider, presenterProvider);
  }

  @Override
  public void injectMembers(PostsActivity instance) {
    injectImageLoader(instance, imageLoaderProvider.get());
    injectOnItemClickListener(instance, onItemClickListenerProvider.get());
    injectPresenter(instance, presenterProvider.get());
  }

  public static void injectImageLoader(PostsActivity instance, ImageLoader imageLoader) {
    instance.imageLoader = imageLoader;
  }

  public static void injectOnItemClickListener(
      PostsActivity instance, OnItemClickListener onItemClickListener) {
    instance.onItemClickListener = onItemClickListener;
  }

  public static void injectPresenter(PostsActivity instance, PostsPresenter presenter) {
    instance.presenter = presenter;
  }
}
