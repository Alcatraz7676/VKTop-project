package com.ovchinnikovm.android.vktop.group.di;

import com.ovchinnikovm.android.vktop.group.GroupInteractor;
import com.ovchinnikovm.android.vktop.group.GroupRepository;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GroupModule_ProvidesGroupInteractorFactory implements Factory<GroupInteractor> {
  private final GroupModule module;

  private final Provider<GroupRepository> repositoryProvider;

  public GroupModule_ProvidesGroupInteractorFactory(
      GroupModule module, Provider<GroupRepository> repositoryProvider) {
    this.module = module;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GroupInteractor get() {
    return provideInstance(module, repositoryProvider);
  }

  public static GroupInteractor provideInstance(
      GroupModule module, Provider<GroupRepository> repositoryProvider) {
    return proxyProvidesGroupInteractor(module, repositoryProvider.get());
  }

  public static GroupModule_ProvidesGroupInteractorFactory create(
      GroupModule module, Provider<GroupRepository> repositoryProvider) {
    return new GroupModule_ProvidesGroupInteractorFactory(module, repositoryProvider);
  }

  public static GroupInteractor proxyProvidesGroupInteractor(
      GroupModule instance, GroupRepository repository) {
    return Preconditions.checkNotNull(
        instance.providesGroupInteractor(repository),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
