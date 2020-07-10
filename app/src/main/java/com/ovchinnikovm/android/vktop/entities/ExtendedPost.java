package com.ovchinnikovm.android.vktop.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


// This class is used in ExtendedPosts class
public class ExtendedPost implements Parcelable {
    private static final String BASE_POST_URL = "https://vk.com/wall";
    public List<Attachment> attachments;
    @SerializedName("copy_history")
    public List<NestedPost> nestedPost;
    public transient List<Photo> photos;
    public transient List<Attachment> other;
    private Integer id;
    private Integer date;
    @SerializedName("signer_id")
    private Integer authorId;
    private String text;
    @SerializedName("owner_id")
    private Integer groupId;
    private SocialValue likes;
    private SocialValue reposts;
    private SocialValue comments;
    private transient String authorFullname;

    public String getAuthorFullname() {
        return authorFullname;
    }

    public void setAuthorFullname(String authorFullname) {
        this.authorFullname = authorFullname;
    }

    public String getPostUrl () {
        return BASE_POST_URL + groupId + "_" + id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getLikes() {
        return likes.getCount();
    }

    public Integer getReposts() {
        return reposts.getCount();
    }

    public Integer getComments() {
        return comments.getCount();
    }

    protected ExtendedPost(Parcel in) {
        if (in.readByte() == 0x01) {
            nestedPost = new ArrayList<>();
            in.readList(nestedPost, NestedPost.class.getClassLoader());
        } else {
            nestedPost = null;
        }
        if (in.readByte() == 0x01) {
            photos = new ArrayList<>();
            in.readList(photos, Photo.class.getClassLoader());
        } else {
            photos = null;
        }
        if (in.readByte() == 0x01) {
            other = new ArrayList<>();
            in.readList(other, Attachment.class.getClassLoader());
        } else {
            other = null;
        }
        id = in.readByte() == 0x00 ? null : in.readInt();
        date = in.readByte() == 0x00 ? null : in.readInt();
        authorId = in.readByte() == 0x00 ? null : in.readInt();
        text = in.readString();
        groupId = in.readByte() == 0x00 ? null : in.readInt();
        likes = (SocialValue) in.readValue(SocialValue.class.getClassLoader());
        reposts = (SocialValue) in.readValue(SocialValue.class.getClassLoader());
        comments = (SocialValue) in.readValue(SocialValue.class.getClassLoader());
        authorFullname = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (nestedPost == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(nestedPost);
        }
        if (photos == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(photos);
        }
        if (other == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(other);
        }
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        if (date == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(date);
        }
        if (authorId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(authorId);
        }
        dest.writeString(text);
        if (groupId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(groupId);
        }
        dest.writeValue(likes);
        dest.writeValue(reposts);
        dest.writeValue(comments);
        dest.writeString(authorFullname);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ExtendedPost> CREATOR = new Parcelable.Creator<ExtendedPost>() {
        @Override
        public ExtendedPost createFromParcel(Parcel in) {
            return new ExtendedPost(in);
        }

        @Override
        public ExtendedPost[] newArray(int size) {
            return new ExtendedPost[size];
        }
    };
}