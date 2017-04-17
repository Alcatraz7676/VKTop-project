// Generated code from Butter Knife. Do not modify!
package com.ovchinnikovm.android.vktop.groups.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ovchinnikovm.android.vktop.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GroupsActivity_ViewBinding implements Unbinder {
  private GroupsActivity target;

  private View view2131296374;

  @UiThread
  public GroupsActivity_ViewBinding(GroupsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GroupsActivity_ViewBinding(final GroupsActivity target, View source) {
    this.target = target;

    View view;
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerview, "field 'recyclerView'", RecyclerView.class);
    target.loadingIndicator = Utils.findRequiredViewAsType(source, R.id.loading_indicator, "field 'loadingIndicator'", ProgressBar.class);
    target.disconnectedView = Utils.findRequiredViewAsType(source, R.id.disconnected_view, "field 'disconnectedView'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.disconnected_button, "method 'onViewClicked'");
    view2131296374 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    GroupsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.loadingIndicator = null;
    target.disconnectedView = null;

    view2131296374.setOnClickListener(null);
    view2131296374 = null;
  }
}
