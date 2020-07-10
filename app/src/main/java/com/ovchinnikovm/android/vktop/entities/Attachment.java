package com.ovchinnikovm.android.vktop.entities;

import com.google.gson.annotations.SerializedName;

public class Attachment {
    private String type;
    private Photo photo;
    private Photo postedPhoto;
    private Video video;
    private Audio audio;
    private Link link;

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

    public String getPostedPhoto() {
        return postedPhoto.photo;
    }

    public void setPostedPhoto(String photo) {
        this.postedPhoto.photo = photo;
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

    private class Photo {
        @SerializedName("photo_604")
        private String photo;
    }

    private class Video {
        private String title;
        private String description;
        private Integer duration;
    }

    private class Audio {
        private String artist;
        private String title;
        private Integer duration;
    }

    public class Link {
        private String title;
        private String url;
    }
}
