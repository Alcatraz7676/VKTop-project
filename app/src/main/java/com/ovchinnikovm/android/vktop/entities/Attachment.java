package com.ovchinnikovm.android.vktop.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Attachment implements Parcelable {
    private String type;
    private Photo photo;
    private Photo postedPhoto;
    private Video video;
    private Audio audio;
    private Link link;
    private Poll poll;
    private Doc doc;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo.photo;
    }

    public void setPhoto(String photo) {
        this.photo.photo = photo;
    }

    public Integer getPhotoWidth() {
        return photo.width;
    }

    public void setPhotoWidth(Integer width) {
        this.photo.width = width;
    }

    public Integer getPhotoHeight() {
        return photo.height;
    }

    public void setPhotoHeight(Integer height) {
        this.photo.height = height;
    }

    public String getPostedPhoto() {
        return postedPhoto.photo;
    }

    public void setPostedPhoto(String photo) {
        this.postedPhoto.photo = photo;
    }

    public Integer getPostedPhotoWidth() {
        return postedPhoto.width;
    }

    public void setPostedPhotoWidth(Integer width) {
        this.postedPhoto.width = width;
    }

    public Integer getPostedPhotoHeight() {
        return postedPhoto.height;
    }

    public void setPostedPhotoHeight(Integer height) {
        this.postedPhoto.height = height;
    }

    public String getVideoTitle() {
        return video.title;
    }

    public void setVideoTitle(String title) {
        this.video.title = title;
    }

    public String getVideoDescription() {
        return video.description;
    }

    public void setVideoDescription(String description) {
        this.video.description = description;
    }

    public Integer getVideoDuration() {
        return video.duration;
    }

    public void setVideoDuration(Integer duration) {
        this.video.duration = duration;
    }

    public Integer getVideoViews() {
        return video.views;
    }

    public void setVideoViews(Integer views) {
        this.video.views = views;
    }

    public String getAudioArtist() {
        return audio.artist;
    }

    public void setAudioArtist(String artist) {
        this.audio.artist = artist;
    }

    public String getAudioTitle() {
        return audio.title;
    }

    public void setAudioTitle(String title) {
        this.audio.title = title;
    }

    public Integer getAudioDuration() {
        return audio.duration;
    }

    public void setAudioDuration(Integer duration) {
        this.audio.duration = duration;
    }

    public String getLinkTitle() {
        return link.title;
    }

    public void setLinkTitle(String title) {
        this.link.title = title;
    }

    public String getLinkUrl() {
        return link.url;
    }

    public void setLinkUrl(String url) {
        this.link.url = url;
    }

    public String getQuestion() {
        return poll.question;
    }

    public void setQuestion(String question) {
        this.poll.question = question;
    }

    public Integer getVotes() {
        return poll.votes;
    }

    public void setVotes(Integer votes) {
        this.poll.votes = votes;
    }

    public String getDocTitle() {
        return doc.title;
    }

    public void setDocTitle(String title) {
        this.doc.title = title;
    }

    public Integer getDocSize() {
        return doc.size;
    }

    public void setDocSize(Integer size) {
        this.doc.size = size;
    }

    public String getDocType() {
        return doc.type;
    }

    public void setDocType(String type) {
        this.doc.type = type;
    }



    private class Photo implements Serializable {
        @SerializedName("photo_604")
        private String photo;
        private Integer width;
        private Integer height;
    }

    private class Video implements Serializable {
        private String title;
        private String description;
        private Integer duration;
        private Integer views;
    }

    private class Audio implements Serializable {
        private String artist;
        private String title;
        private Integer duration;
    }

    private class Link implements Serializable {
        private String title;
        private String url;
    }

    private class Poll implements Serializable {
        private String question;
        private Integer votes;
    }

    private class Doc implements Serializable {
        private String title;
        private Integer size;
        @SerializedName("ext")
        private String type;
    }

    protected Attachment(Parcel in) {
        type = in.readString();
        photo = (Photo) in.readValue(Photo.class.getClassLoader());
        postedPhoto = (Photo) in.readValue(Photo.class.getClassLoader());
        video = (Video) in.readValue(Video.class.getClassLoader());
        audio = (Audio) in.readValue(Audio.class.getClassLoader());
        link = (Link) in.readValue(Link.class.getClassLoader());
        poll = (Poll) in.readValue(Poll.class.getClassLoader());
        doc = (Doc) in.readValue(Doc.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeValue(photo);
        dest.writeValue(postedPhoto);
        dest.writeValue(video);
        dest.writeValue(audio);
        dest.writeValue(link);
        dest.writeValue(poll);
        dest.writeValue(doc);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Attachment> CREATOR = new Parcelable.Creator<Attachment>() {
        @Override
        public Attachment createFromParcel(Parcel in) {
            return new Attachment(in);
        }

        @Override
        public Attachment[] newArray(int size) {
            return new Attachment[size];
        }
    };
}
