package com.ovchinnikovm.android.vktop.posts.di;

import com.ovchinnikovm.android.vktop.posts.PostsInteractor;
import com.ovchinnikovm.android.vktop.posts.PostsPresenter;
import com.ovchinnikovm.android.vktop.posts.ui.PostsView;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.greenrobot.eventbus.EventBus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PostsModule_ProvidePostsPresenterFactory implements Factory<PostsPresenter> {
  private final PostsModule module;

  private final Provider<EventBus> eventBusProvider;

  private final Provider<PostsView> viewProvider;

  private final Provider<PostsInteractor> interactorProvider;

  public PostsModule_ProvidePostsPresenterFactory(
      PostsModule module,
      Provider<EventBus> eventBusProvider,
      Provider<PostsView> viewProvider,
      Provider<PostsInteractor> interactorProvider) {
    this.module = module;
    this.eventBusProvider = eventBusProvider;
    this.viewProvider = viewProvider;
    this.interactorProvider = interactorProvider;
  }

  @Override
  public PostsPresenter get() {
    return provideInstance(module, eventBusProvider, viewProvider, interactorProvider);
  }

  public static PostsPresenter provideInstance(
      PostsModule module,
      Provider<EventBus> eventBusProvider,
      Provider<PostsView> viewProvider,
      Provider<PostsInteractor> interactorProvider) {
    return proxyProvidePostsPresenter(
        module, eventBusProvider.get(), viewProvider.get(), interactorProvider.get());
  }

  public static PostsModule_ProvidePostsPresenterFactory create(
      PostsModule module,
      Provider<EventBus> eventBusProvider,
      Provider<PostsView> viewProvider,
      Provider<PostsInteractor> interactorProvider) {
    return new PostsModule_ProvidePostsPresenterFactory(
        module, eventBusProvider, viewProvider, interactorProvider);
  }

  public static PostsPresenter proxyProvidePostsPresenter(
      PostsModule instance, EventBus eventBus, PostsView view, PostsInteractor interactor) {
    return Preconditions.checkNotNull(
        instance.providePostsPresenter(eventBus, view, interactor),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
