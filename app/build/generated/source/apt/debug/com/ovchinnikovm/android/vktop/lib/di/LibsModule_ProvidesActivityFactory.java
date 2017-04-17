package com.ovchinnikovm.android.vktop.lib.di;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LibsModule_ProvidesActivityFactory implements Factory<Context> {
  private final LibsModule module;

  public LibsModule_ProvidesActivityFactory(LibsModule module) {
    this.module = module;
  }

  @Override
  public Context get() {
    return provideInstance(module);
  }

  public static Context provideInstance(LibsModule module) {
    return proxyProvidesActivity(module);
  }

  public static LibsModule_ProvidesActivityFactory create(LibsModule module) {
    return new LibsModule_ProvidesActivityFactory(module);
  }

  public static Context proxyProvidesActivity(LibsModule instance) {
    return Preconditions.checkNotNull(
        instance.providesActivity(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
