package com.ovchinnikovm.android.vktop.groups.ui;

import com.ovchinnikovm.android.vktop.groups.GroupsPresenter;
import com.ovchinnikovm.android.vktop.groups.adapters.GroupsAdapter;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GroupsActivity_MembersInjector implements MembersInjector<GroupsActivity> {
  private final Provider<GroupsAdapter> adapterProvider;

  private final Provider<GroupsPresenter> presenterProvider;

  public GroupsActivity_MembersInjector(
      Provider<GroupsAdapter> adapterProvider, Provider<GroupsPresenter> presenterProvider) {
    this.adapterProvider = adapterProvider;
    this.presenterProvider = presenterProvider;
  }

  public static MembersInjector<GroupsActivity> create(
      Provider<GroupsAdapter> adapterProvider, Provider<GroupsPresenter> presenterProvider) {
    return new GroupsActivity_MembersInjector(adapterProvider, presenterProvider);
  }

  @Override
  public void injectMembers(GroupsActivity instance) {
    injectAdapter(instance, adapterProvider.get());
    injectPresenter(instance, presenterProvider.get());
  }

  public static void injectAdapter(GroupsActivity instance, GroupsAdapter adapter) {
    instance.adapter = adapter;
  }

  public static void injectPresenter(GroupsActivity instance, GroupsPresenter presenter) {
    instance.presenter = presenter;
  }
}
