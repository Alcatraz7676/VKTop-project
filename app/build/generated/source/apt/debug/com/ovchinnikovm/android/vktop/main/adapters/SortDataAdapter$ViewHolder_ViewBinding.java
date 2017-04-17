// Generated code from Butter Knife. Do not modify!
package com.ovchinnikovm.android.vktop.main.adapters;

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

public class SortDataAdapter$ViewHolder_ViewBinding implements Unbinder {
  private SortDataAdapter.ViewHolder target;

  @UiThread
  public SortDataAdapter$ViewHolder_ViewBinding(SortDataAdapter.ViewHolder target, View source) {
    this.target = target;

    target.groupIcon = Utils.findRequiredViewAsType(source, R.id.group_icon, "field 'groupIcon'", ImageView.class);
    target.groupTitle = Utils.findRequiredViewAsType(source, R.id.group_title, "field 'groupTitle'", TextView.class);
    target.sortRange = Utils.findRequiredViewAsType(source, R.id.sort_range, "field 'sortRange'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SortDataAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.groupIcon = null;
    target.groupTitle = null;
    target.sortRange = null;
  }
}
