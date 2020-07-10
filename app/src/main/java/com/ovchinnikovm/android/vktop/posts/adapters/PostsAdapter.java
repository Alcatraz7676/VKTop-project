package com.ovchinnikovm.android.vktop.posts.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.entities.Attachment;
import com.ovchinnikovm.android.vktop.entities.Group;
import com.ovchinnikovm.android.vktop.entities.NestedPost;
import com.ovchinnikovm.android.vktop.entities.PostItem;
import com.ovchinnikovm.android.vktop.entities.Posts;
import com.ovchinnikovm.android.vktop.entities.Profile;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Posts posts;
    private ImageLoader imageLoader;
    private OnItemClickListener clickListener;

    public PostsAdapter(Posts posts, ImageLoader imageLoader, OnItemClickListener clickListener) {
        this.posts = posts;
        this.imageLoader = imageLoader;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_post, parent, false), parent.getContext());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PostItem item = posts.items.get(position);
        Set<Profile> profiles = posts.profiles;
        Set<Group> groups = posts.groups;
        // Set clicklistener
        holder.setOnClickListener(item, clickListener);
        // Position in the top
        holder.position.setText(position);
        // Date of the post
        Date now = new Date();
        long time = item.getDate() + TimeZone.getDefault().getOffset(now.getTime());
        holder.date.setText(getPostDate(time));
        // Text of the post
        if (item.getText().equals("")) {
            holder.text.setText(item.getText());
            holder.text.setVisibility(View.VISIBLE);
        }
        // Likes of the post
        holder.likes.setText(item.getLikes());
        // Reposts of the post
        holder.reposts.setText(item.getReposts());
        // Comments of the post
        holder.comments.setText(item.getComments());
        // Author's full name of the post
        if (profiles != null && item.getAuthorId() != null) {
            for(Profile profile : profiles) {
                if (profile.getId() == item.getAuthorId()) {
                    holder.postAuthorName.setText(profile.getFullName());
                    holder.postAuthorIcon.setVisibility(View.VISIBLE);
                    holder.postAuthorName.setVisibility(View.VISIBLE);
                }
            }
        }
        // Single photo
        if (isOnlyOnePhoto(item.attachments)) {
            holder.singlePhoto.setVisibility(View.VISIBLE);
            for(Attachment attachment : item.attachments) {
                if (attachment.getType() == "photo") {
                    imageLoader.loadImage(holder.singlePhoto, attachment.getPhoto());
                    break;
                } else if (attachment.getType() == "posted_photo") {
                    imageLoader.loadImage(holder.singlePhoto, attachment.getPostedPhoto());
                    break;
                }
            }
            // Multiple photos
        } else if (isAnyPhoto(item.attachments)) {
            holder.photosRecyclerview.setVisibility(View.VISIBLE);
            List<String> photos = new ArrayList<>();
            for (Attachment attachment : item.attachments) {
                if (attachment.getType() == "photo") {
                    photos.add(attachment.getPhoto());
                } else if (attachment.getType() == "posted_photo") {
                    photos.add(attachment.getPostedPhoto());
                }
            }
            holder.setPhotos(photos);
        }

        // Media attachments
        List<Attachment> mediaAttachments = new ArrayList<>();
        for (Attachment attachment : item.attachments) {
            if (attachment.getType() == "audio" || attachment.getType() == "video" || attachment.getType() == "link") {
                mediaAttachments.add(attachment);
            }
        }
        if (!mediaAttachments.isEmpty()) {
            holder.mediaRecyclerview.setVisibility(View.VISIBLE);
            holder.setMediaAttachments(mediaAttachments);
        }

        // Nested post
        if (item.nestedPost != null) {
            NestedPost nestedItem = item.nestedPost.get(0);
            // Whole nested post
            holder.attachment.setVisibility(View.VISIBLE);
            // Group icon
            imageLoader.loadIcon(holder.groupIcon, getGroupIconURL(nestedItem.getGroupId(), groups));
            // Group name
            holder.attGroupName.setText(getGroupName(nestedItem.getGroupId(), groups));
            // Date of nested post
            long nestedTime = nestedItem.getDate() + TimeZone.getDefault().getOffset(now.getTime());
            holder.attPostTime.setText(getPostDate(nestedTime));
            // Text of nested post
            if (nestedItem.getText() != null) {
                holder.attText.setText(nestedItem.getText());
                holder.attText.setVisibility(View.VISIBLE);
            }

            // Single photo
            if (isOnlyOnePhoto(nestedItem.attachments)) {
                holder.attSinglePhoto.setVisibility(View.VISIBLE);
                for(Attachment attachment : nestedItem.attachments) {
                    if (attachment.getType() == "photo") {
                        imageLoader.loadImage(holder.attSinglePhoto, attachment.getPhoto());
                        break;
                    } else if (attachment.getType() == "posted_photo") {
                        imageLoader.loadImage(holder.attSinglePhoto, attachment.getPostedPhoto());
                        break;
                    }
                }
                // Multiple photos
            } else if (isAnyPhoto(nestedItem.attachments)) {
                holder.photosRecyclerview.setVisibility(View.VISIBLE);
                List<String> attPhotos = new ArrayList<>();
                for (Attachment attachment : nestedItem.attachments) {
                    if (attachment.getType() == "photo") {
                        attPhotos.add(attachment.getPhoto());
                    } else if (attachment.getType() == "posted_photo") {
                        attPhotos.add(attachment.getPostedPhoto());
                    }
                }
                holder.setAttPhotos(attPhotos);
            }

            // Media attachments
            List<Attachment> attMediaAttachments = new ArrayList<>();
            for (Attachment attachment : nestedItem.attachments) {
                if (attachment.getType() == "audio" || attachment.getType() == "video" || attachment.getType() == "link") {
                    attMediaAttachments.add(attachment);
                }
            }
            if (!attMediaAttachments.isEmpty()) {
                holder.mediaRecyclerview.setVisibility(View.VISIBLE);
                holder.setAttMediaAttachments(attMediaAttachments);
            }

        }

    }

    public void setItems(Posts newPosts) {

        Log.i("mytag", "NewPosts items number in the setItems method: " + Integer.toString(newPosts.items.size()));
        posts.items.addAll(newPosts.items);
        Log.i("mytag", "Posts items number in the setItems method: " + Integer.toString(posts.items.size()));
        posts.profiles.addAll(newPosts.profiles);
        posts.groups.addAll(newPosts.groups);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        Log.i("mytag", "Posts items number in the getitemcount method: " + Integer.toString(posts.items.size()));
        return posts.items.size();
    }

    private String getPostDate(long time) {
        Calendar postDate = Calendar.getInstance();
        postDate.setTimeInMillis(time);
        int minute = postDate.get(Calendar.MINUTE);
        int hour = postDate.get(Calendar.HOUR_OF_DAY);
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

    private boolean isOnlyOnePhoto(List<Attachment> attachments) {
        int quantity = 0;
        for (Attachment attachment : attachments) {
            if (attachment.getType() == "photo" || attachment.getType() == "posted_photo") {
                quantity++;
                if (quantity == 2)
                    return false;
            }
        }
        return true;
    }

    private boolean isAnyPhoto(List<Attachment> attachments) {
        for (Attachment attachment : attachments) {
            if (attachment.getType() == "photo" || attachment.getType() == "posted_photo")
                return true;
        }
        return false;
    }

    @Nullable
    private String getGroupIconURL(Integer groupId, Set<Group> groups) {
        for (Group group : groups) {
            if (group.getId() == groupId)
                return group.getPhotoURL();
        }
        return null;
    }

    @Nullable
    private String getGroupName(Integer groupId, Set<Group> groups) {
        for (Group group : groups) {
            if (group.getId() == groupId)
                return group.getName();
        }
        return null;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.position)
        TextView position;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.text)
        TextView text;
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
        TextView attText;
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

        private View view;

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
            photosAdapter = new PhotosAdapter(photosURL, imageLoader);
            photosRecyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            photosRecyclerview.setAdapter(photosAdapter);

            mediaAttachments = new ArrayList<>();
            mediaAdapter = new MediaAdapter(mediaAttachments);
            mediaRecyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            mediaRecyclerview.setAdapter(mediaAdapter);


            attPhotosURL = new ArrayList<>();
            attPhotosAdapter = new PhotosAdapter(attPhotosURL, imageLoader);
            attPhotosRecyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            attPhotosRecyclerview.setAdapter(attPhotosAdapter);

            attMediaAttachments = new ArrayList<>();
            attMediaAdapter = new MediaAdapter(attMediaAttachments);
            attMediaRecyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            attMediaRecyclerview.setAdapter(attMediaAdapter);
        }

        public void setPhotos(List<String> newURLs) {
            photosURL.clear();
            photosURL.addAll(newURLs);
            photosAdapter.notifyDataSetChanged();
        }

        public void setMediaAttachments(List<Attachment> newAttachments) {
            mediaAttachments.clear();
            mediaAttachments.addAll(newAttachments);
            mediaAdapter.notifyDataSetChanged();
        }

        public void setAttPhotos(List<String> newURLs) {
            attPhotosURL.clear();
            attPhotosURL.addAll(newURLs);
            attPhotosAdapter.notifyDataSetChanged();
        }

        public void setAttMediaAttachments(List<Attachment> newAttachments) {
            attMediaAttachments.clear();
            attMediaAttachments.addAll(newAttachments);
            attMediaAdapter.notifyDataSetChanged();
        }

        public void setOnClickListener(final PostItem postItem, final OnItemClickListener listener) {
            view.setOnClickListener( view -> listener.onItemClick(postItem) );
        }
    }
}
