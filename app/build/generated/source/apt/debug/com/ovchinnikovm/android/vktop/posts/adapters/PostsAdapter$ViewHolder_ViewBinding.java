// Generated code from Butter Knife. Do not modify!
package com.ovchinnikovm.android.vktop.posts.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.lib.ExpandableTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PostsAdapter$ViewHolder_ViewBinding implements Unbinder {
  private PostsAdapter.ViewHolder target;

  @UiThread
  public PostsAdapter$ViewHolder_ViewBinding(PostsAdapter.ViewHolder target, View source) {
    this.target = target;

    target.position = Utils.findRequiredViewAsType(source, R.id.position, "field 'position'", TextView.class);
    target.date = Utils.findRequiredViewAsType(source, R.id.date, "field 'date'", TextView.class);
    target.text = Utils.findRequiredViewAsType(source, R.id.text, "field 'text'", ExpandableTextView.class);
    target.expandButton = Utils.findRequiredViewAsType(source, R.id.expand_collapse, "field 'expandButton'", ImageButton.class);
    target.singlePhoto = Utils.findRequiredViewAsType(source, R.id.single_photo, "field 'singlePhoto'", ImageView.class);
    target.photosRecyclerview = Utils.findRequiredViewAsType(source, R.id.photos_recyclerview, "field 'photosRecyclerview'", RecyclerView.class);
    target.mediaRecyclerview = Utils.findRequiredViewAsType(source, R.id.media_recyclerview, "field 'mediaRecyclerview'", RecyclerView.class);
    target.groupIcon = Utils.findRequiredViewAsType(source, R.id.group_icon, "field 'groupIcon'", ImageView.class);
    target.attachment = Utils.findRequiredViewAsType(source, R.id.attachment, "field 'attachment'", RelativeLayout.class);
    target.attGroupName = Utils.findRequiredViewAsType(source, R.id.att_group_name, "field 'attGroupName'", TextView.class);
    target.attPostTime = Utils.findRequiredViewAsType(source, R.id.att_post_time, "field 'attPostTime'", TextView.class);
    target.attText = Utils.findRequiredViewAsType(source, R.id.att_text, "field 'attText'", ExpandableTextView.class);
    target.attSinglePhoto = Utils.findRequiredViewAsType(source, R.id.att_single_photo, "field 'attSinglePhoto'", ImageView.class);
    target.attPhotosRecyclerview = Utils.findRequiredViewAsType(source, R.id.att_photos_recyclerview, "field 'attPhotosRecyclerview'", RecyclerView.class);
    target.attMediaRecyclerview = Utils.findRequiredViewAsType(source, R.id.att_media_recyclerview, "field 'attMediaRecyclerview'", RecyclerView.class);
    target.postAuthorIcon = Utils.findRequiredViewAsType(source, R.id.post_author_icon, "field 'postAuthorIcon'", ImageView.class);
    target.postAuthorName = Utils.findRequiredViewAsType(source, R.id.post_author_name, "field 'postAuthorName'", TextView.class);
    target.sortTypeImg = Utils.findRequiredViewAsType(source, R.id.sort_type_img, "field 'sortTypeImg'", ImageView.class);
    target.sortTypeNum = Utils.findRequiredViewAsType(source, R.id.sort_type_num, "field 'sortTypeNum'", TextView.class);
    target.separator = Utils.findRequiredView(source, R.id.view, "field 'separator'");
  }

  @Override
  @CallSuper
  public void unbind() {
    PostsAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.position = null;
    target.date = null;
    target.text = null;
    target.expandButton = null;
    target.singlePhoto = null;
    target.photosRecyclerview = null;
    target.mediaRecyclerview = null;
    target.groupIcon = null;
    target.attachment = null;
    target.attGroupName = null;
    target.attPostTime = null;
    target.attText = null;
    target.attSinglePhoto = null;
    target.attPhotosRecyclerview = null;
    target.attMediaRecyclerview = null;
    target.postAuthorIcon = null;
    target.postAuthorName = null;
    target.sortTypeImg = null;
    target.sortTypeNum = null;
    target.separator = null;
  }
}
