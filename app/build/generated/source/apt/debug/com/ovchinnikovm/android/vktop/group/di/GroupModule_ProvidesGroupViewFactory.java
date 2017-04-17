package com.ovchinnikovm.android.vktop.group.di;

import com.ovchinnikovm.android.vktop.group.ui.GroupView;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GroupModule_ProvidesGroupViewFactory implements Factory<GroupView> {
  private final GroupModule module;

  public GroupModule_ProvidesGroupViewFactory(GroupModule module) {
    this.module = module;
  }

  @Override
  public GroupView get() {
    return provideInstance(module);
  }

  public static GroupView provideInstance(GroupModule module) {
    return proxyProvidesGroupView(module);
  }

  public static GroupModule_ProvidesGroupViewFactory create(GroupModule module) {
    return new GroupModule_ProvidesGroupViewFactory(module);
  }

  public static GroupView proxyProvidesGroupView(GroupModule instance) {
    return Preconditions.checkNotNull(
        instance.providesGroupView(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
