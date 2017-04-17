package com.ovchinnikovm.android.vktop.group.ui;

import com.ovchinnikovm.android.vktop.group.GroupPresenter;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GroupActivity_MembersInjector implements MembersInjector<GroupActivity> {
  private final Provider<GroupPresenter> presenterProvider;

  public GroupActivity_MembersInjector(Provider<GroupPresenter> presenterProvider) {
    this.presenterProvider = presenterProvider;
  }

  public static MembersInjector<GroupActivity> create(Provider<GroupPresenter> presenterProvider) {
    return new GroupActivity_MembersInjector(presenterProvider);
  }

  @Override
  public void injectMembers(GroupActivity instance) {
    injectPresenter(instance, presenterProvider.get());
  }

  public static void injectPresenter(GroupActivity instance, GroupPresenter presenter) {
    instance.presenter = presenter;
  }
}
