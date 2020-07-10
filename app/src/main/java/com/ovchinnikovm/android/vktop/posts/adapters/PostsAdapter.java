package com.ovchinnikovm.android.vktop.posts.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ovchinnikovm.android.vktop.PreCachingLayoutManager;
import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.entities.Attachment;
import com.ovchinnikovm.android.vktop.entities.ExtendedPost;
import com.ovchinnikovm.android.vktop.entities.ExtendedPosts;
import com.ovchinnikovm.android.vktop.entities.NestedPost;
import com.ovchinnikovm.android.vktop.lib.ExpandableTextView;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private ExtendedPosts extendedPosts;
    private ImageLoader imageLoader;
    private OnItemClickListener clickListener;
    private SparseBooleanArray togglePositions;
    private Context context;

    public PostsAdapter(ExtendedPosts extendedPosts, ImageLoader imageLoader,
                        OnItemClickListener clickListener, Context context) {
        this.extendedPosts = extendedPosts;
        this.imageLoader = imageLoader;
        this.clickListener = clickListener;
        this.context = context;
        togglePositions = new SparseBooleanArray();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_post, parent, false), parent.getContext());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ExtendedPost item = extendedPosts.items.get(position);
        // Set clicklistener
        holder.setOnClickListener(item, clickListener);
        holder.setPostUrl(item.getPostUrl());
        // Position in the top
        holder.position.setText(String.valueOf(position + 1));
        // Date of the post
        holder.date.setText(getPostDate(item.getDate()));
        // Text of the post
        if (!item.getText().equals("")) {
            holder.text.setText(item.getText(), togglePositions, position,
                    clickListener, item.getPostUrl());
            holder.text.setVisibility(View.VISIBLE);
        }
        // Likes of the post
        holder.likes.setText(String.valueOf(item.getLikes()));
        // Reposts of the post
        holder.reposts.setText(String.valueOf(item.getReposts()));
        // Comments of the post
        holder.comments.setText(String.valueOf(item.getComments()));
        // Author's full name of the post
        if (item.getAuthorFullname() != null) {
            holder.postAuthorName.setText(item.getAuthorFullname());
            holder.postAuthorIcon.setVisibility(View.VISIBLE);
            holder.postAuthorName.setVisibility(View.VISIBLE);
        }
        if (item.attachments != null) {
            if (item.photos.size() == 1) {
                holder.singlePhoto.setVisibility(View.VISIBLE);
                imageLoader.loadImage(holder.singlePhoto, item.photos.get(0));
                holder.singlePhoto.setBackgroundColor(Color.TRANSPARENT);
            } else if (item.photos.size() > 1) {
                holder.photosRecyclerview.setVisibility(View.VISIBLE);
                holder.setPhotos(item.photos);
            }
            if (item.other != null && !item.other.isEmpty()) {
                holder.mediaRecyclerview.setVisibility(View.VISIBLE);
                holder.setMediaAttachments(item.other);
            }
        }

        // Nested post
        if (item.nestedPost != null) {
            NestedPost nestedItem = item.nestedPost.get(0);
            // Whole nested post
            holder.attachment.setVisibility(View.VISIBLE);
            // Group icon
            imageLoader.loadIcon(holder.groupIcon, nestedItem.getIconUrl());
            // Group name
            holder.attGroupName.setText(nestedItem.getReplyName());
            // Date of nested post
            //long nestedTime = nestedItem.getDate() + TimeZone.getDefault().getOffset(now.getTime());
            holder.attPostTime.setText(getPostDate(nestedItem.getDate()));
            // Text of nested post
            if (!nestedItem.getText().equals("")) {
                holder.attText.setText(nestedItem.getText(), togglePositions, position,
                        clickListener, item.getPostUrl());
                holder.attText.setVisibility(View.VISIBLE);
            }

            if (item.nestedPost.get(0).attachments != null) {
                if (item.nestedPost.get(0).photos.size() == 1) {
                    holder.attSinglePhoto.setVisibility(View.VISIBLE);
                    imageLoader.loadImage(holder.attSinglePhoto, item.nestedPost.get(0).photos.get(0));
                    holder.singlePhoto.setBackgroundColor(Color.TRANSPARENT);
                } else if (item.nestedPost.get(0).photos.size() > 1) {
                    holder.attPhotosRecyclerview.setVisibility(View.VISIBLE);
                    holder.setAttPhotos(item.nestedPost.get(0).photos);
                }
                if (!item.nestedPost.get(0).other.isEmpty()) {
                    holder.attMediaRecyclerview.setVisibility(View.VISIBLE);
                    holder.setAttMediaAttachments(item.nestedPost.get(0).other);
                }
            }

        }

    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.text.setVisibility(View.GONE);
        holder.postAuthorIcon.setVisibility(View.GONE);
        holder.postAuthorName.setVisibility(View.GONE);
        holder.singlePhoto.setVisibility(View.GONE);
        holder.photosRecyclerview.setVisibility(View.GONE);
        holder.mediaRecyclerview.setVisibility(View.GONE);
        holder.attachment.setVisibility(View.GONE);
        holder.attText.setVisibility(View.GONE);
        holder.attSinglePhoto.setVisibility(View.GONE);
        holder.attPhotosRecyclerview.setVisibility(View.GONE);
        holder.attMediaRecyclerview.setVisibility(View.GONE);
        holder.singlePhoto.setBackgroundColor(ContextCompat.getColor(context, R.color.photoBackground));
        holder.attSinglePhoto.setBackgroundColor(ContextCompat.getColor(context, R.color.photoBackground));
    }

    public void setItems(ExtendedPosts newExtendedPosts) {
        extendedPosts.items.addAll(newExtendedPosts.items);
    }

    public void removeItems() {
        extendedPosts.items.clear();
        togglePositions = new SparseBooleanArray();
    }

    // Two methods below used to fix a bug with repetitive item in the post after scroll back
    @Override
    public long getItemId(int position) {
        return extendedPosts.items.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return extendedPosts.items.size();
    }

    private String getPostDate(long time) {
        Calendar postDate = Calendar.getInstance();
        postDate.setTimeInMillis(time * 1000);
        String minute = String.valueOf(postDate.get(Calendar.MINUTE));
        if (minute.length() == 1)
            minute = "0" + minute;
        String hour = String.valueOf(postDate.get(Calendar.HOUR_OF_DAY));
        if (hour.length() == 1)
            hour = "0" + hour;
        int day = postDate.get(Calendar.DAY_OF_MONTH);
        int monthNumber = postDate.get(Calendar.MONTH);
        String month;
        switch (monthNumber) {
            case Calendar.JANUARY:
                month = "января";
                break;
            case Calendar.FEBRUARY:
                month = "февраля";
                break;
            case Calendar.MARCH:
                month = "марта";
                break;
            case Calendar.APRIL:
                month = "апреля";
                break;
            case Calendar.MAY:
                month = "мая";
                break;
            case Calendar.JUNE:
                month = "июня";
                break;
            case Calendar.JULY:
                month = "июля";
                break;
            case Calendar.AUGUST:
                month = "августа";
                break;
            case Calendar.SEPTEMBER:
                month = "сентября";
                break;
            case Calendar.OCTOBER:
                month = "октября";
                break;
            case Calendar.NOVEMBER:
                month = "ноября";
                break;
            case Calendar.DECEMBER:
                month = "декабря";
                break;
            default:
                month = "какого-то месяца";
                break;
        }
        int year = postDate.get(Calendar.YEAR);
        return day + " " + month + " " + year + " года в " + hour + ":" + minute;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        @BindView(R.id.position)
        TextView position;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.text)
        ExpandableTextView text;
        @BindView(R.id.expand_collapse)
        ImageButton expandButton;
        @BindView(R.id.single_photo)
        ImageView singlePhoto;
        @BindView(R.id.photos_recyclerview)
        RecyclerView photosRecyclerview;
        @BindView(R.id.media_recyclerview)
        RecyclerView mediaRecyclerview;
        @BindView(R.id.group_icon)
        ImageView groupIcon;
        @BindView(R.id.attachment)
        RelativeLayout attachment;
        @BindView(R.id.att_group_name)
        TextView attGroupName;
        @BindView(R.id.att_post_time)
        TextView attPostTime;
        @BindView(R.id.att_text)
        ExpandableTextView attText;
        @BindView(R.id.att_single_photo)
        ImageView attSinglePhoto;
        @BindView(R.id.att_photos_recyclerview)
        RecyclerView attPhotosRecyclerview;
        @BindView(R.id.att_media_recyclerview)
        RecyclerView attMediaRecyclerview;
        @BindView(R.id.post_author_icon)
        ImageView postAuthorIcon;
        @BindView(R.id.post_author_name)
        TextView postAuthorName;
        @BindView(R.id.likes)
        TextView likes;
        @BindView(R.id.comments)
        TextView comments;
        @BindView(R.id.replies)
        TextView reposts;

        private PhotosAdapter photosAdapter;
        private List<String> photosURL;
        private MediaAdapter mediaAdapter;
        private List<Attachment> mediaAttachments;

        private PhotosAdapter attPhotosAdapter;
        private List<String> attPhotosURL;
        private MediaAdapter attMediaAdapter;
        private List<Attachment> attMediaAttachments;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;

            photosURL = new ArrayList<>();
            photosAdapter = new PhotosAdapter(photosURL, imageLoader, clickListener);
            photosAdapter.setHasStableIds(true);
            photosRecyclerview.setLayoutManager(new PreCachingLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL, false));
            photosRecyclerview.setAdapter(photosAdapter);

            mediaAttachments = new ArrayList<>();
            mediaAdapter = new MediaAdapter(mediaAttachments, clickListener);
            mediaAdapter.setHasStableIds(true);
            mediaRecyclerview.setLayoutManager(new PreCachingLayoutManager(context,
                    LinearLayoutManager.VERTICAL, false));
            mediaRecyclerview.setAdapter(mediaAdapter);


            attPhotosURL = new ArrayList<>();
            attPhotosAdapter = new PhotosAdapter(attPhotosURL, imageLoader, clickListener);
            attPhotosAdapter.setHasStableIds(true);
            attPhotosRecyclerview.setLayoutManager(new PreCachingLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL, false));
            attPhotosRecyclerview.setAdapter(attPhotosAdapter);

            attMediaAttachments = new ArrayList<>();
            attMediaAdapter = new MediaAdapter(attMediaAttachments, clickListener);
            attMediaAdapter.setHasStableIds(true);
            attMediaRecyclerview.setLayoutManager(new PreCachingLayoutManager(context,
                    LinearLayoutManager.VERTICAL, false));
            attMediaRecyclerview.setAdapter(attMediaAdapter);
        }

        private void setPostUrl(String url) {
            photosAdapter.setUrl(url);
            mediaAdapter.setUrl(url);
            attPhotosAdapter.setUrl(url);
            attMediaAdapter.setUrl(url);
        }

        private void setPhotos(List<String> newURLs) {
            photosURL.clear();
            photosURL.addAll(newURLs);
            photosAdapter.notifyDataSetChanged();
        }

        private void setMediaAttachments(List<Attachment> newAttachments) {
            mediaAttachments.clear();
            mediaAttachments.addAll(newAttachments);
            mediaAdapter.notifyDataSetChanged();
        }

        private void setAttPhotos(List<String> newURLs) {
            attPhotosURL.clear();
            attPhotosURL.addAll(newURLs);
            attPhotosAdapter.notifyDataSetChanged();
        }

        private void setAttMediaAttachments(List<Attachment> newAttachments) {
            attMediaAttachments.clear();
            attMediaAttachments.addAll(newAttachments);
            attMediaAdapter.notifyDataSetChanged();
        }

        private void setOnClickListener(final ExtendedPost extendedPost, final OnItemClickListener listener) {
            view.setOnClickListener( l -> listener.onItemClick(extendedPost.getPostUrl()));
        }
    }
}
