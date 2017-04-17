// Generated code from Butter Knife. Do not modify!
package com.ovchinnikovm.android.vktop.posts.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.ovchinnikovm.android.vktop.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MediaAdapter$ViewHolder_ViewBinding implements Unbinder {
  private MediaAdapter.ViewHolder target;

  @UiThread
  public MediaAdapter$ViewHolder_ViewBinding(MediaAdapter.ViewHolder target, View source) {
    this.target = target;

    target.icon = Utils.findRequiredViewAsType(source, R.id.icon, "field 'icon'", ImageView.class);
    target.title = Utils.findRequiredViewAsType(source, R.id.title, "field 'title'", TextView.class);
    target.subtitle = Utils.findRequiredViewAsType(source, R.id.subtitle, "field 'subtitle'", TextView.class);
    target.time = Utils.findRequiredViewAsType(source, R.id.time, "field 'time'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MediaAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.icon = null;
    target.title = null;
    target.subtitle = null;
    target.time = null;
  }
}
