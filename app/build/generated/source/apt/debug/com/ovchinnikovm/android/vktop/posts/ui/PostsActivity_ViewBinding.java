// Generated code from Butter Knife. Do not modify!
package com.ovchinnikovm.android.vktop.posts.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.gms.ads.AdView;
import com.ovchinnikovm.android.vktop.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PostsActivity_ViewBinding implements Unbinder {
  private PostsActivity target;

  @UiThread
  public PostsActivity_ViewBinding(PostsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PostsActivity_ViewBinding(PostsActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.recyclerview = Utils.findRequiredViewAsType(source, R.id.recyclerview, "field 'recyclerview'", RecyclerView.class);
    target.groupIconImageView = Utils.findRequiredViewAsType(source, R.id.group_icon, "field 'groupIconImageView'", ImageView.class);
    target.spinner = Utils.findRequiredViewAsType(source, R.id.spinner, "field 'spinner'", Spinner.class);
    target.loadingBar = Utils.findRequiredViewAsType(source, R.id.loading_indicator, "field 'loadingBar'", ProgressBar.class);
    target.emptyView = Utils.findRequiredViewAsType(source, R.id.empty_view, "field 'emptyView'", RelativeLayout.class);
    target.groupNameTextView = Utils.findRequiredViewAsType(source, R.id.group_name, "field 'groupNameTextView'", TextView.class);
    target.adView = Utils.findRequiredViewAsType(source, R.id.adView, "field 'adView'", AdView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PostsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.recyclerview = null;
    target.groupIconImageView = null;
    target.spinner = null;
    target.loadingBar = null;
    target.emptyView = null;
    target.groupNameTextView = null;
    target.adView = null;
  }
}
