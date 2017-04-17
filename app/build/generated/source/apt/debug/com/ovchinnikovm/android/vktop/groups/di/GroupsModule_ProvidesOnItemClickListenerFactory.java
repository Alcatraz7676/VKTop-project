package com.ovchinnikovm.android.vktop.groups.di;

import com.ovchinnikovm.android.vktop.groups.adapters.OnItemClickListener;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GroupsModule_ProvidesOnItemClickListenerFactory
    implements Factory<OnItemClickListener> {
  private final GroupsModule module;

  public GroupsModule_ProvidesOnItemClickListenerFactory(GroupsModule module) {
    this.module = module;
  }

  @Override
  public OnItemClickListener get() {
    return provideInstance(module);
  }

  public static OnItemClickListener provideInstance(GroupsModule module) {
    return proxyProvidesOnItemClickListener(module);
  }

  public static GroupsModule_ProvidesOnItemClickListenerFactory create(GroupsModule module) {
    return new GroupsModule_ProvidesOnItemClickListenerFactory(module);
  }

  public static OnItemClickListener proxyProvidesOnItemClickListener(GroupsModule instance) {
    return Preconditions.checkNotNull(
        instance.providesOnItemClickListener(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
