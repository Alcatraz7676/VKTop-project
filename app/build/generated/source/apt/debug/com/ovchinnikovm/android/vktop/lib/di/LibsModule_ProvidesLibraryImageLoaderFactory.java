package com.ovchinnikovm.android.vktop.lib.di;

import com.squareup.picasso.Picasso;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LibsModule_ProvidesLibraryImageLoaderFactory implements Factory<Picasso> {
  private final LibsModule module;

  public LibsModule_ProvidesLibraryImageLoaderFactory(LibsModule module) {
    this.module = module;
  }

  @Override
  public Picasso get() {
    return provideInstance(module);
  }

  public static Picasso provideInstance(LibsModule module) {
    return proxyProvidesLibraryImageLoader(module);
  }

  public static LibsModule_ProvidesLibraryImageLoaderFactory create(LibsModule module) {
    return new LibsModule_ProvidesLibraryImageLoaderFactory(module);
  }

  public static Picasso proxyProvidesLibraryImageLoader(LibsModule instance) {
    return Preconditions.checkNotNull(
        instance.providesLibraryImageLoader(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
