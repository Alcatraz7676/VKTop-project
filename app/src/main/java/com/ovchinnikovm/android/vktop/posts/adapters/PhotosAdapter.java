package com.ovchinnikovm.android.vktop.posts.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder>{
    private List<String> URLs;
    private ImageLoader imageLoader;
    private Context context;

    public PhotosAdapter(List<String> URLs, ImageLoader imageLoader, Context context) {
        this.URLs = URLs;
        this.imageLoader = imageLoader;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        imageLoader.loadImage(holder.image, URLs.get(position));
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        //Glide.with(holder.view).clear(holder.image);
    }

    @Override
    public int getItemCount() {
        return URLs.size();
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
