package com.ovchinnikovm.android.vktop.group.di;

import com.ovchinnikovm.android.vktop.group.GroupRepository;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.greenrobot.eventbus.EventBus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GroupModule_ProvidesGroupRepositoryFactory implements Factory<GroupRepository> {
  private final GroupModule module;

  private final Provider<EventBus> eventBusProvider;

  public GroupModule_ProvidesGroupRepositoryFactory(
      GroupModule module, Provider<EventBus> eventBusProvider) {
    this.module = module;
    this.eventBusProvider = eventBusProvider;
  }

  @Override
  public GroupRepository get() {
    return provideInstance(module, eventBusProvider);
  }

  public static GroupRepository provideInstance(
      GroupModule module, Provider<EventBus> eventBusProvider) {
    return proxyProvidesGroupRepository(module, eventBusProvider.get());
  }

  public static GroupModule_ProvidesGroupRepositoryFactory create(
      GroupModule module, Provider<EventBus> eventBusProvider) {
    return new GroupModule_ProvidesGroupRepositoryFactory(module, eventBusProvider);
  }

  public static GroupRepository proxyProvidesGroupRepository(
      GroupModule instance, EventBus eventBus) {
    return Preconditions.checkNotNull(
        instance.providesGroupRepository(eventBus),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
