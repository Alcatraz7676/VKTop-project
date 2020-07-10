package com.ovchinnikovm.android.vktop.posts.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.entities.Photo;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder>{
    private List<Photo> URLs;
    private ImageLoader imageLoader;
    private OnItemClickListener clickListener;
    private String url;

    public PhotosAdapter(List<Photo> URLs, ImageLoader imageLoader,
                         OnItemClickListener clickListener) {
        this.URLs = URLs;
        this.imageLoader = imageLoader;
        this.clickListener = clickListener;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_holder, parent, false);
        setOnClickListener(view);
        return new ViewHolder(view);
    }

    // Two methods below used to fix a bug with repetitive items in the post after scroll back
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        imageLoader.loadRecyclerViewImage(holder.image, URLs.get(position).getPhotoUrl());
    }

    @Override
    public int getItemCount() {
        return URLs.size();
    }

    private void setOnClickListener(View view) {
        Log.i("mytag", "url = " + url);
        view.setOnClickListener( l -> clickListener.onItemClick(url) );
    }

    public void setUrl(String url) {
        this.url = url;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        @BindView(R.id.photo)
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            view = itemView;
        }
    }
}
