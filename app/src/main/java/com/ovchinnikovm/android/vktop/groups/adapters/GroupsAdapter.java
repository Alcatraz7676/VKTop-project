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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {

    private List<Group> groups;
    private List<Group> groupsCopy;
    private ImageLoader imageLoader;
    private OnItemClickListener clickListener;
    private Context context;

    public GroupsAdapter(List<Group> groups, ImageLoader imageLoader, OnItemClickListener clickListener,
                         Context context) {
        this.groups = groups;
        groupsCopy = groups;
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
        Group group = groups.get(position);
        holder.setOnClickListener(group, clickListener);
        holder.groupTitle.setText(group.getName());
        holder.groupDescription.setText(group.getSubName());
        imageLoader.loadIcon(holder.groupIcon, group.getPhotoURL());
        if (position == 0) {
            RelativeLayout.LayoutParams lp= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) context.getResources().getDimension(R.dimen.group_item_height));
            lp.setMargins(0, (int) context.getResources().getDimension(R.dimen.recyclerview_first_elemennt_margin_top),
                    0, 0);
            holder.groupContainer.setLayoutParams(lp);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<Group> getItems() {
        return (ArrayList<Group>) groupsCopy;
    }

    public void filter(String text) {
        groups.clear();
        if (!text.isEmpty()) {
            text = text.toLowerCase().trim();
            for (Group item : groupsCopy){
                if (item.getName().toLowerCase().contains(text)){
                    groups.add(item);
                }
            }
        } else {
            groups.addAll(groupsCopy);
        }
        notifyDataSetChanged();
    }

    public void setItems(List<Group> newGroups) {
        groups = new ArrayList<>(newGroups);
        groupsCopy = new ArrayList<>(groups);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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
