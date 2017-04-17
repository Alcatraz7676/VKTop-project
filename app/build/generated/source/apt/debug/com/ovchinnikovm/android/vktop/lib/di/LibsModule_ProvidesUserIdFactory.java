package com.ovchinnikovm.android.vktop.lib.di;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LibsModule_ProvidesUserIdFactory implements Factory<Integer> {
  private final LibsModule module;

  public LibsModule_ProvidesUserIdFactory(LibsModule module) {
    this.module = module;
  }

  @Override
  public Integer get() {
    return provideInstance(module);
  }

  public static Integer provideInstance(LibsModule module) {
    return proxyProvidesUserId(module);
  }

  public static LibsModule_ProvidesUserIdFactory create(LibsModule module) {
    return new LibsModule_ProvidesUserIdFactory(module);
  }

  public static Integer proxyProvidesUserId(LibsModule instance) {
    return Preconditions.checkNotNull(
        instance.providesUserId(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
