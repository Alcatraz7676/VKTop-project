package com.ovchinnikovm.android.vktop.posts.di;

import android.content.Context;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule_ProvidesActivityFactory;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule_ProvidesEventBusFactory;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule_ProvidesImageLoaderFactory;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule_ProvidesLibraryImageLoaderFactory;
import com.ovchinnikovm.android.vktop.posts.PostsInteractor;
import com.ovchinnikovm.android.vktop.posts.PostsPresenter;
import com.ovchinnikovm.android.vktop.posts.PostsRepository;
import com.ovchinnikovm.android.vktop.posts.adapters.OnItemClickListener;
import com.ovchinnikovm.android.vktop.posts.ui.PostsActivity;
import com.ovchinnikovm.android.vktop.posts.ui.PostsActivity_MembersInjector;
import com.ovchinnikovm.android.vktop.posts.ui.PostsView;
import com.squareup.picasso.Picasso;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.greenrobot.eventbus.EventBus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerPostsComponent implements PostsComponent {
  private Provider<Picasso> providesLibraryImageLoaderProvider;

  private Provider<Context> providesActivityProvider;

  private Provider<ImageLoader> providesImageLoaderProvider;

  private Provider<OnItemClickListener> providesOnItemClickListenerProvider;

  private Provider<EventBus> providesEventBusProvider;

  private Provider<PostsView> providesPostsViewProvider;

  private Provider<PostsRepository> providesPostsRepositoryProvider;

  private Provider<PostsInteractor> providesPostsInteractorProvider;

  private Provider<PostsPresenter> providePostsPresenterProvider;

  private DaggerPostsComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.providesLibraryImageLoaderProvider =
        DoubleCheck.provider(
            LibsModule_ProvidesLibraryImageLoaderFactory.create(builder.libsModule));
    this.providesActivityProvider =
        DoubleCheck.provider(LibsModule_ProvidesActivityFactory.create(builder.libsModule));
    this.providesImageLoaderProvider =
        DoubleCheck.provider(
            LibsModule_ProvidesImageLoaderFactory.create(
                builder.libsModule, providesLibraryImageLoaderProvider, providesActivityProvider));
    this.providesOnItemClickListenerProvider =
        DoubleCheck.provider(
            PostsModule_ProvidesOnItemClickListenerFactory.create(builder.postsModule));
    this.providesEventBusProvider =
        DoubleCheck.provider(LibsModule_ProvidesEventBusFactory.create(builder.libsModule));
    this.providesPostsViewProvider =
        DoubleCheck.provider(PostsModule_ProvidesPostsViewFactory.create(builder.postsModule));
    this.providesPostsRepositoryProvider =
        DoubleCheck.provider(
            PostsModule_ProvidesPostsRepositoryFactory.create(
                builder.postsModule, providesEventBusProvider));
    this.providesPostsInteractorProvider =
        DoubleCheck.provider(
            PostsModule_ProvidesPostsInteractorFactory.create(
                builder.postsModule, providesPostsRepositoryProvider));
    this.providePostsPresenterProvider =
        DoubleCheck.provider(
            PostsModule_ProvidePostsPresenterFactory.create(
                builder.postsModule,
                providesEventBusProvider,
                providesPostsViewProvider,
                providesPostsInteractorProvider));
  }

  @Override
  public void inject(PostsActivity activity) {
    injectPostsActivity(activity);
  }

  private PostsActivity injectPostsActivity(PostsActivity instance) {
    PostsActivity_MembersInjector.injectImageLoader(instance, providesImageLoaderProvider.get());
    PostsActivity_MembersInjector.injectOnItemClickListener(
        instance, providesOnItemClickListenerProvider.get());
    PostsActivity_MembersInjector.injectPresenter(instance, providePostsPresenterProvider.get());
    return instance;
  }

  public static final class Builder {
    private LibsModule libsModule;

    private PostsModule postsModule;

    private Builder() {}

    public PostsComponent build() {
      if (libsModule == null) {
        throw new IllegalStateException(LibsModule.class.getCanonicalName() + " must be set");
      }
      if (postsModule == null) {
        throw new IllegalStateException(PostsModule.class.getCanonicalName() + " must be set");
      }
      return new DaggerPostsComponent(this);
    }

    public Builder libsModule(LibsModule libsModule) {
      this.libsModule = Preconditions.checkNotNull(libsModule);
      return this;
    }

    public Builder postsModule(PostsModule postsModule) {
      this.postsModule = Preconditions.checkNotNull(postsModule);
      return this;
    }
  }
}
