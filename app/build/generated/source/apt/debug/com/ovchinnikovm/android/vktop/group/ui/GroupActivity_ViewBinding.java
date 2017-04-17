// Generated code from Butter Knife. Do not modify!
package com.ovchinnikovm.android.vktop.group.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ovchinnikovm.android.vktop.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GroupActivity_ViewBinding implements Unbinder {
  private GroupActivity target;

  private View view2131296589;

  private View view2131296590;

  private View view2131296374;

  @UiThread
  public GroupActivity_ViewBinding(GroupActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GroupActivity_ViewBinding(final GroupActivity target, View source) {
    this.target = target;

    View view;
    target.groupTitleTextView = Utils.findRequiredViewAsType(source, R.id.group_title, "field 'groupTitleTextView'", TextView.class);
    target.groupDescriptionTextView = Utils.findRequiredViewAsType(source, R.id.group_description, "field 'groupDescriptionTextView'", TextView.class);
    target.groupIconImageView = Utils.findRequiredViewAsType(source, R.id.group_icon, "field 'groupIconImageView'", ImageView.class);
    target.memberNumberTextView = Utils.findRequiredViewAsType(source, R.id.member_number, "field 'memberNumberTextView'", TextView.class);
    target.postsNumberTextView = Utils.findRequiredViewAsType(source, R.id.posts_number, "field 'postsNumberTextView'", TextView.class);
    target.timeNumberTextView = Utils.findRequiredViewAsType(source, R.id.time_number, "field 'timeNumberTextView'", TextView.class);
    view = Utils.findRequiredView(source, R.id.sort_button, "field 'sortButton' and method 'onViewClicked'");
    target.sortButton = Utils.castView(view, R.id.sort_button, "field 'sortButton'", Button.class);
    view2131296589 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.averageTimeLabel = Utils.findRequiredViewAsType(source, R.id.average_time_label, "field 'averageTimeLabel'", TextView.class);
    target.group_info = Utils.findRequiredViewAsType(source, R.id.group_info, "field 'group_info'", ConstraintLayout.class);
    target.loadingIndicator = Utils.findRequiredViewAsType(source, R.id.loading_indicator, "field 'loadingIndicator'", ProgressBar.class);
    target.disconnectedView = Utils.findRequiredViewAsType(source, R.id.disconnected_view, "field 'disconnectedView'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.sort_interval_button, "method 'onViewClicked'");
    view2131296590 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
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
    GroupActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.groupTitleTextView = null;
    target.groupDescriptionTextView = null;
    target.groupIconImageView = null;
    target.memberNumberTextView = null;
    target.postsNumberTextView = null;
    target.timeNumberTextView = null;
    target.sortButton = null;
    target.averageTimeLabel = null;
    target.group_info = null;
    target.loadingIndicator = null;
    target.disconnectedView = null;

    view2131296589.setOnClickListener(null);
    view2131296589 = null;
    view2131296590.setOnClickListener(null);
    view2131296590 = null;
    view2131296374.setOnClickListener(null);
    view2131296374 = null;
  }
}
