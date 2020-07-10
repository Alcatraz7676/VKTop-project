package com.ovchinnikovm.android.vktop.groups.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.entities.Group;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {
    private List<Group> groupsDynamic;
    private List<Group> groupsStatic;
    private ImageLoader imageLoader;
    private OnItemClickListener clickListener;
    private Context context;
    private Integer headerPosition;

    public GroupsAdapter(List<Group> groups, ImageLoader imageLoader, OnItemClickListener clickListener,
                         Context context) {
        groupsDynamic = groups;
        groupsStatic = groups;
        this.imageLoader = imageLoader;
        this.clickListener = clickListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_group, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Group group = groupsDynamic.get(position);
        if (headerPosition != null && position == headerPosition)
            holder.globalSearchLabel.setVisibility(View.VISIBLE);
        else
            holder.globalSearchLabel.setVisibility(View.GONE);
        holder.setOnClickListener(group, clickListener);
        holder.groupTitle.setText(group.getName());
        holder.groupDescription.setText(group.getSubName());
        imageLoader.loadIcon(holder.groupIcon, group.getPhotoURL());
        if (position == 0 && (headerPosition == null || (headerPosition != null && headerPosition != 0))) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, (int) context.getResources().getDimension(R.dimen.recyclerview_first_elemennt_margin_top),
                    0, 0);
            holder.groupContainer.setLayoutParams(lp);
        } else {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, 0);
            holder.groupContainer.setLayoutParams(lp);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<Group> getItems() {
        return (ArrayList<Group>) groupsStatic;
    }

    public void filter(String text) {
        groupsDynamic.clear();
        if (!text.trim().isEmpty()) {
            text = text.toLowerCase().trim();
            for (Group item : groupsStatic) {
                if (item.getName().toLowerCase().contains(text)){
                    groupsDynamic.add(item);
                }
            }
        } else {
            groupsDynamic.addAll(groupsStatic);
            headerPosition = null;
        }
        notifyDataSetChanged();
    }

    public void setItems(List<Group> newGroups) {
        groupsDynamic = new ArrayList<>(newGroups);
        groupsStatic = new ArrayList<>(groupsDynamic);
        notifyDataSetChanged();
    }

    public void addGlobalGroups(List<Group> newGroups) {
        headerPosition = groupsDynamic.size();
        groupsDynamic.addAll(newGroups);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return groupsDynamic.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.global_search)
        TextView globalSearchLabel;
        @BindView(R.id.group_container)
        LinearLayout groupContainer;
        @BindView(R.id.group_icon)
        ImageView groupIcon;
        @BindView(R.id.group_title)
        TextView groupTitle;
        @BindView(R.id.group_description)
        TextView groupDescription;

        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }

        public void setOnClickListener(final Group group, final OnItemClickListener listener) {
            view.setOnClickListener( view -> listener.onItemClick(group) );
        }
    }
}
