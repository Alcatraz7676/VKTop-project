package com.ovchinnikovm.android.vktop.groups.di;

import com.ovchinnikovm.android.vktop.groups.GroupsInteractor;
import com.ovchinnikovm.android.vktop.groups.GroupsRepository;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GroupsModule_ProvidesGroupsInteractorFactory
    implements Factory<GroupsInteractor> {
  private final GroupsModule module;

  private final Provider<GroupsRepository> repositoryProvider;

  public GroupsModule_ProvidesGroupsInteractorFactory(
      GroupsModule module, Provider<GroupsRepository> repositoryProvider) {
    this.module = module;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GroupsInteractor get() {
    return provideInstance(module, repositoryProvider);
  }

  public static GroupsInteractor provideInstance(
      GroupsModule module, Provider<GroupsRepository> repositoryProvider) {
    return proxyProvidesGroupsInteractor(module, repositoryProvider.get());
  }

  public static GroupsModule_ProvidesGroupsInteractorFactory create(
      GroupsModule module, Provider<GroupsRepository> repositoryProvider) {
    return new GroupsModule_ProvidesGroupsInteractorFactory(module, repositoryProvider);
  }

  public static GroupsInteractor proxyProvidesGroupsInteractor(
      GroupsModule instance, GroupsRepository repository) {
    return Preconditions.checkNotNull(
        instance.providesGroupsInteractor(repository),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
