package com.ovchinnikovm.android.vktop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ovchinnikovm.android.vktop.entities.PostItem;

import java.util.List;

import butterknife.ButterKnife;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private List<PostItem> postItems;

    public PostsAdapter(List<PostItem> postItems) {
        this.postItems = postItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PostItem postItem = postItems.get(position);
        holder.postTextView.setText(postItem.getText());
    }

    @Override
    public int getItemCount() {
        return postItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        //@BindView(R.id.postTextView)
        TextView postTextView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
