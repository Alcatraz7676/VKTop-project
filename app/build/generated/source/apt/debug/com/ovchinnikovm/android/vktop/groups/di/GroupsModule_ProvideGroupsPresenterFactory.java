package com.ovchinnikovm.android.vktop.groups.di;

import android.content.Context;
import com.ovchinnikovm.android.vktop.groups.GroupsInteractor;
import com.ovchinnikovm.android.vktop.groups.GroupsPresenter;
import com.ovchinnikovm.android.vktop.groups.ui.GroupsView;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.greenrobot.eventbus.EventBus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GroupsModule_ProvideGroupsPresenterFactory implements Factory<GroupsPresenter> {
  private final GroupsModule module;

  private final Provider<EventBus> eventBusProvider;

  private final Provider<GroupsView> viewProvider;

  private final Provider<GroupsInteractor> interactorProvider;

  private final Provider<Context> contextProvider;

  public GroupsModule_ProvideGroupsPresenterFactory(
      GroupsModule module,
      Provider<EventBus> eventBusProvider,
      Provider<GroupsView> viewProvider,
      Provider<GroupsInteractor> interactorProvider,
      Provider<Context> contextProvider) {
    this.module = module;
    this.eventBusProvider = eventBusProvider;
    this.viewProvider = viewProvider;
    this.interactorProvider = interactorProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public GroupsPresenter get() {
    return provideInstance(
        module, eventBusProvider, viewProvider, interactorProvider, contextProvider);
  }

  public static GroupsPresenter provideInstance(
      GroupsModule module,
      Provider<EventBus> eventBusProvider,
      Provider<GroupsView> viewProvider,
      Provider<GroupsInteractor> interactorProvider,
      Provider<Context> contextProvider) {
    return proxyProvideGroupsPresenter(
        module,
        eventBusProvider.get(),
        viewProvider.get(),
        interactorProvider.get(),
        contextProvider.get());
  }

  public static GroupsModule_ProvideGroupsPresenterFactory create(
      GroupsModule module,
      Provider<EventBus> eventBusProvider,
      Provider<GroupsView> viewProvider,
      Provider<GroupsInteractor> interactorProvider,
      Provider<Context> contextProvider) {
    return new GroupsModule_ProvideGroupsPresenterFactory(
        module, eventBusProvider, viewProvider, interactorProvider, contextProvider);
  }

  public static GroupsPresenter proxyProvideGroupsPresenter(
      GroupsModule instance,
      EventBus eventBus,
      GroupsView view,
      GroupsInteractor interactor,
      Context context) {
    return Preconditions.checkNotNull(
        instance.provideGroupsPresenter(eventBus, view, interactor, context),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
