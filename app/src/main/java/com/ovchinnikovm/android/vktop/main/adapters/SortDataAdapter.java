package com.ovchinnikovm.android.vktop.main.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.lib.RecyclerViewEmptySupport;
import com.ovchinnikovm.android.vktop.entities.RealmSortedItem;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class SortDataAdapter extends RecyclerViewEmptySupport.Adapter<SortDataAdapter.ViewHolder> {

    private RealmResults<RealmSortedItem> data;
    private ImageLoader imageLoader;
    private OnItemClickListener clickListener;

    public SortDataAdapter(RealmResults<RealmSortedItem> data, ImageLoader imageLoader,
                           OnItemClickListener clickListener) {
        this.data = data;
        this.imageLoader = imageLoader;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sort_info, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RealmSortedItem item = data.get(position);
        imageLoader.loadIcon(holder.groupIcon, item.getGroupIconUrl());
        holder.groupTitle.setText(item.getGroupName());
        holder.sortRange.setText(item.getSortRange());
        holder.setOnClickListener(position, clickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void removeItem(int position) {
        Realm realmInstance = Realm.getDefaultInstance();
        realmInstance.executeTransaction(realm -> {
            // remove a single object
            RealmSortedItem item = data.get(position);
            item.deleteFromRealm();
        });
        notifyItemRemoved(position);
    }

    public RealmSortedItem getItem(int position) {
        return data.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.group_icon)
        ImageView groupIcon;
        @BindView(R.id.group_title)
        TextView groupTitle;
        @BindView(R.id.sort_range)
        TextView sortRange;

        private View view;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
        }

        public void setOnClickListener(final int position, final OnItemClickListener listener) {
            view.setOnClickListener( view -> listener.onItemClick(position));
        }
    }

}
