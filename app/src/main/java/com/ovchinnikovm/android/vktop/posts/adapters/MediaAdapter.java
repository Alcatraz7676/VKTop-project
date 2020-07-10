package com.ovchinnikovm.android.vktop.posts.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.entities.Attachment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

    private List<Attachment> attachments;
    private OnItemClickListener clickListener;
    private String url;

    public MediaAdapter(List<Attachment> attachments, OnItemClickListener clickListener) {
        this.attachments = attachments;
        this.clickListener = clickListener;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_media, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Attachment attachment = attachments.get(position);
        int minutes;
        int seconds;
        switch (attachment.getType()) {
            case "audio":
                holder.icon.setImageResource(R.drawable.ic_song);
                holder.title.setText(attachment.getAudioArtist());
                holder.subtitle.setText(attachment.getAudioTitle());
                minutes = attachment.getAudioDuration() / 60;
                seconds = attachment.getAudioDuration() % 60;
                holder.time.setText(minutes + ":" + seconds);
                holder.time.setVisibility(View.VISIBLE);
                setOnClickListener(holder.view, null);
                break;
            case "video":
                holder.icon.setImageResource(R.drawable.ic_video);
                holder.title.setText(attachment.getVideoTitle());
                holder.subtitle.setText(attachment.getVideoDescription());
                minutes = attachment.getVideoDuration() / 60;
                seconds = attachment.getVideoDuration() % 60;
                holder.time.setText(minutes + ":" + seconds);
                holder.time.setVisibility(View.VISIBLE);
                setOnClickListener(holder.view, null);
                break;
            case "link":
                holder.icon.setImageResource(R.drawable.ic_link);
                if (attachment.getLinkTitle().equals(""))
                    holder.title.setText("Ссылка");
                else
                    holder.title.setText(attachment.getLinkTitle());
                holder.subtitle.setText(attachment.getLinkUrl());
                setOnClickListener(holder.view, attachment.getLinkUrl());
                break;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return attachments.size();
    }

    private void setOnClickListener(View view, String newUrl) {
        if (newUrl == null)
            view.setOnClickListener( l -> clickListener.onItemClick(url) );
        else
            view.setOnClickListener( l -> clickListener.onItemClick(newUrl));
    }

    public void setUrl(String url) {
        this.url = url;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.subtitle)
        TextView subtitle;
        @BindView(R.id.time)
        TextView time;

        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.view = itemView;
        }
    }
}
