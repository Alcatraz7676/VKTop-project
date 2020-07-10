package com.ovchinnikovm.android.vktop.posts.adapters;

import android.content.Context;
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
    private Context context;

    public MediaAdapter(List<Attachment> attachments, OnItemClickListener clickListener, Context context) {
        this.attachments = attachments;
        this.clickListener = clickListener;
        this.context = context;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_media, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.time.setVisibility(View.GONE);
        Attachment attachment = attachments.get(position);
        int minutes;
        int seconds;
        switch (attachment.getType()) {
            case "audio":
                holder.icon.setImageResource(R.drawable.ic_song);
                holder.title.setText(attachment.getAudioArtist());
                holder.subtitle.setText(attachment.getAudioTitle());
                setOnClickListener(holder.view);
                break;
            case "video":
                holder.icon.setImageResource(R.drawable.ic_video);
                holder.title.setText(attachment.getVideoTitle());
                holder.subtitle.setText(attachment.getVideoDescription());
                minutes = attachment.getVideoDuration() / 60;
                seconds = attachment.getVideoDuration() % 60;
                if (seconds >= 0 && seconds < 10) {
                    String secStr = "0" + seconds;
                    holder.time.setText(minutes + ":" + secStr);
                } else {
                    holder.time.setText(minutes + ":" + seconds);
                }
                holder.time.setVisibility(View.VISIBLE);
                setOnClickListener(holder.view);
                break;
            case "link":
                holder.icon.setImageResource(R.drawable.ic_link);
                if (attachment.getLinkTitle().equals(""))
                    holder.title.setText(R.string.link);
                else
                    holder.title.setText(attachment.getLinkTitle());
                holder.subtitle.setText(attachment.getLinkUrl());
                setOnClickListener(holder.view);
                break;
            case "poll":
                holder.icon.setImageResource(R.drawable.ic_poll);
                holder.title.setText(attachment.getQuestion());
                holder.subtitle.setText(attachment.getVotes().toString() + " " + context.getString(R.string.poll_size));
                setOnClickListener(holder.view);
                break;
            case "doc":
                if (attachment.getDocType().equals("gif"))
                    holder.icon.setImageResource(R.drawable.ic_gif);
                else
                    holder.icon.setImageResource(R.drawable.ic_document);
                holder.title.setText(attachment.getDocTitle());
                double docSize = attachment.getDocSize() * 1.0 / (1024 * 1024);
                holder.subtitle.setText(String.format("%.2f", docSize) + " " + context.getString(R.string.doc_size));
                setOnClickListener(holder.view);
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

    private void setOnClickListener(View view) {
        view.setOnClickListener( l -> clickListener.onItemClick(url) );
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
