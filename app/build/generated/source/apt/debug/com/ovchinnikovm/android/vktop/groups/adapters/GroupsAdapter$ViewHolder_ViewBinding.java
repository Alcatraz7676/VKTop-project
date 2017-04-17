// Generated code from Butter Knife. Do not modify!
package com.ovchinnikovm.android.vktop.groups.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.ovchinnikovm.android.vktop.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GroupsAdapter$ViewHolder_ViewBinding implements Unbinder {
  private GroupsAdapter.ViewHolder target;

  @UiThread
  public GroupsAdapter$ViewHolder_ViewBinding(GroupsAdapter.ViewHolder target, View source) {
    this.target = target;

    target.globalSearchLabel = Utils.findRequiredViewAsType(source, R.id.global_search, "field 'globalSearchLabel'", TextView.class);
    target.groupContainer = Utils.findRequiredViewAsType(source, R.id.group_container, "field 'groupContainer'", LinearLayout.class);
    target.groupIcon = Utils.findRequiredViewAsType(source, R.id.group_icon, "field 'groupIcon'", ImageView.class);
    target.groupTitle = Utils.findRequiredViewAsType(source, R.id.group_title, "field 'groupTitle'", TextView.class);
    target.groupDescription = Utils.findRequiredViewAsType(source, R.id.group_description, "field 'groupDescription'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GroupsAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.globalSearchLabel = null;
    target.groupContainer = null;
    target.groupIcon = null;
    target.groupTitle = null;
    target.groupDescription = null;
  }
}
