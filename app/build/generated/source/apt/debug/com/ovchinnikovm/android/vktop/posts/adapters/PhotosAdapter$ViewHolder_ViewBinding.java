// Generated code from Butter Knife. Do not modify!
package com.ovchinnikovm.android.vktop.posts.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.ovchinnikovm.android.vktop.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PhotosAdapter$ViewHolder_ViewBinding implements Unbinder {
  private PhotosAdapter.ViewHolder target;

  @UiThread
  public PhotosAdapter$ViewHolder_ViewBinding(PhotosAdapter.ViewHolder target, View source) {
    this.target = target;

    target.image = Utils.findRequiredViewAsType(source, R.id.photo, "field 'image'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PhotosAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.image = null;
  }
}
