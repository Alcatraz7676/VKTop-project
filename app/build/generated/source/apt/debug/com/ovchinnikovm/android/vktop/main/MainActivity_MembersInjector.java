package com.ovchinnikovm.android.vktop.main;

import com.ovchinnikovm.android.vktop.main.adapters.SortDataAdapter;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<SortDataAdapter> adapterProvider;

  public MainActivity_MembersInjector(Provider<SortDataAdapter> adapterProvider) {
    this.adapterProvider = adapterProvider;
  }

  public static MembersInjector<MainActivity> create(Provider<SortDataAdapter> adapterProvider) {
    return new MainActivity_MembersInjector(adapterProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectAdapter(instance, adapterProvider.get());
  }

  public static void injectAdapter(MainActivity instance, SortDataAdapter adapter) {
    instance.adapter = adapter;
  }
}
