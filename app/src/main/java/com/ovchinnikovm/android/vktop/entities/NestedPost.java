package com.ovchinnikovm.android.vktop.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


// This class is responsible for containing reply of the post.
public class NestedPost implements Parcelable {
    public List<Attachment> attachments;
    public transient List<Photo> photos;
    public transient List<Attachment> other;
    private Integer id;
    private Integer date;
    @SerializedName("signer_id")
    private Integer authorId;
    private String text;
    @SerializedName("from_id")
    private Integer groupId;
    private transient String iconUrl;
    private transient String replyName;
    private transient String authorFullname;

    public String getAuthorFullname() {
        return authorFullname;
    }

    public void setAuthorFullname(String authorFullname) {
        this.authorFullname = authorFullname;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
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

    protected NestedPost(Parcel in) {
        if (in.readByte() == 0x01) {
            attachments = new ArrayList<>();
            in.readList(attachments, Attachment.class.getClassLoader());
        } else {
            attachments = null;
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
        iconUrl = in.readString();
        replyName = in.readString();
        authorFullname = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (attachments == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(attachments);
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
        dest.writeString(iconUrl);
        dest.writeString(replyName);
        dest.writeString(authorFullname);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<NestedPost> CREATOR = new Parcelable.Creator<NestedPost>() {
        @Override
        public NestedPost createFromParcel(Parcel in) {
            return new NestedPost(in);
        }

        @Override
        public NestedPost[] newArray(int size) {
            return new NestedPost[size];
        }
    };
}