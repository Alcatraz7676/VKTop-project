package com.ovchinnikovm.android.vktop.groups.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.entities.Group;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder>{

    private List<Group> groups;
    private ImageLoader imageLoader;
    private OnItemClickListener clickListener;

    public GroupsAdapter(List<Group> groups, ImageLoader imageLoader, OnItemClickListener clickListener) {
        this.groups = groups;
        this.imageLoader = imageLoader;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_group, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Group group = groups.get(position);
        holder.setOnClickListener(group, clickListener);
        holder.groupTitle.setText(group.getName());
        holder.groupDescription.setText(group.getSubName());
        imageLoader.loadIcon(holder.groupIcon, group.getPhotoURL());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItems(List<Group> newGroups) {
        groups.clear();
        groups.addAll(newGroups);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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
