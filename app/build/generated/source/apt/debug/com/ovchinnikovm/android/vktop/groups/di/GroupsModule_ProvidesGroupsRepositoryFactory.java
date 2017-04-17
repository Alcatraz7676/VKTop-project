package com.ovchinnikovm.android.vktop.groups.di;

import com.ovchinnikovm.android.vktop.groups.GroupsRepository;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.greenrobot.eventbus.EventBus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GroupsModule_ProvidesGroupsRepositoryFactory
    implements Factory<GroupsRepository> {
  private final GroupsModule module;

  private final Provider<EventBus> eventBusProvider;

  public GroupsModule_ProvidesGroupsRepositoryFactory(
      GroupsModule module, Provider<EventBus> eventBusProvider) {
    this.module = module;
    this.eventBusProvider = eventBusProvider;
  }

  @Override
  public GroupsRepository get() {
    return provideInstance(module, eventBusProvider);
  }

  public static GroupsRepository provideInstance(
      GroupsModule module, Provider<EventBus> eventBusProvider) {
    return proxyProvidesGroupsRepository(module, eventBusProvider.get());
  }

  public static GroupsModule_ProvidesGroupsRepositoryFactory create(
      GroupsModule module, Provider<EventBus> eventBusProvider) {
    return new GroupsModule_ProvidesGroupsRepositoryFactory(module, eventBusProvider);
  }

  public static GroupsRepository proxyProvidesGroupsRepository(
      GroupsModule instance, EventBus eventBus) {
    return Preconditions.checkNotNull(
        instance.providesGroupsRepository(eventBus),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
