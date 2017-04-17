package com.ovchinnikovm.android.vktop.lib.di;

import android.content.Context;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import com.squareup.picasso.Picasso;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LibsModule_ProvidesImageLoaderFactory implements Factory<ImageLoader> {
  private final LibsModule module;

  private final Provider<Picasso> picassoProvider;

  private final Provider<Context> contextProvider;

  public LibsModule_ProvidesImageLoaderFactory(
      LibsModule module, Provider<Picasso> picassoProvider, Provider<Context> contextProvider) {
    this.module = module;
    this.picassoProvider = picassoProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public ImageLoader get() {
    return provideInstance(module, picassoProvider, contextProvider);
  }

  public static ImageLoader provideInstance(
      LibsModule module, Provider<Picasso> picassoProvider, Provider<Context> contextProvider) {
    return proxyProvidesImageLoader(module, picassoProvider.get(), contextProvider.get());
  }

  public static LibsModule_ProvidesImageLoaderFactory create(
      LibsModule module, Provider<Picasso> picassoProvider, Provider<Context> contextProvider) {
    return new LibsModule_ProvidesImageLoaderFactory(module, picassoProvider, contextProvider);
  }

  public static ImageLoader proxyProvidesImageLoader(
      LibsModule instance, Picasso picasso, Context context) {
    return Preconditions.checkNotNull(
        instance.providesImageLoader(picasso, context),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
