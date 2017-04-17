package com.ovchinnikovm.android.vktop.group.di;

import android.content.Context;
import com.ovchinnikovm.android.vktop.group.GroupInteractor;
import com.ovchinnikovm.android.vktop.group.GroupPresenter;
import com.ovchinnikovm.android.vktop.group.ui.GroupView;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.greenrobot.eventbus.EventBus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GroupModule_ProvideGroupPresenterFactory implements Factory<GroupPresenter> {
  private final GroupModule module;

  private final Provider<EventBus> eventBusProvider;

  private final Provider<GroupView> viewProvider;

  private final Provider<GroupInteractor> interactorProvider;

  private final Provider<ImageLoader> imageLoaderProvider;

  private final Provider<Context> contextProvider;

  public GroupModule_ProvideGroupPresenterFactory(
      GroupModule module,
      Provider<EventBus> eventBusProvider,
      Provider<GroupView> viewProvider,
      Provider<GroupInteractor> interactorProvider,
      Provider<ImageLoader> imageLoaderProvider,
      Provider<Context> contextProvider) {
    this.module = module;
    this.eventBusProvider = eventBusProvider;
    this.viewProvider = viewProvider;
    this.interactorProvider = interactorProvider;
    this.imageLoaderProvider = imageLoaderProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public GroupPresenter get() {
    return provideInstance(
        module,
        eventBusProvider,
        viewProvider,
        interactorProvider,
        imageLoaderProvider,
        contextProvider);
  }

  public static GroupPresenter provideInstance(
      GroupModule module,
      Provider<EventBus> eventBusProvider,
      Provider<GroupView> viewProvider,
      Provider<GroupInteractor> interactorProvider,
      Provider<ImageLoader> imageLoaderProvider,
      Provider<Context> contextProvider) {
    return proxyProvideGroupPresenter(
        module,
        eventBusProvider.get(),
        viewProvider.get(),
        interactorProvider.get(),
        imageLoaderProvider.get(),
        contextProvider.get());
  }

  public static GroupModule_ProvideGroupPresenterFactory create(
      GroupModule module,
      Provider<EventBus> eventBusProvider,
      Provider<GroupView> viewProvider,
      Provider<GroupInteractor> interactorProvider,
      Provider<ImageLoader> imageLoaderProvider,
      Provider<Context> contextProvider) {
    return new GroupModule_ProvideGroupPresenterFactory(
        module,
        eventBusProvider,
        viewProvider,
        interactorProvider,
        imageLoaderProvider,
        contextProvider);
  }

  public static GroupPresenter proxyProvideGroupPresenter(
      GroupModule instance,
      EventBus eventBus,
      GroupView view,
      GroupInteractor interactor,
      ImageLoader imageLoader,
      Context context) {
    return Preconditions.checkNotNull(
        instance.provideGroupPresenter(eventBus, view, interactor, imageLoader, context),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
