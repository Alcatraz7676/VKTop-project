package com.ovchinnikovm.android.vktop.entities;

import java.io.Serializable;

public class Photo implements Serializable {
    private String photoUrl;
    private Double heightToWidthRatio;

    public Photo(String photoUrl, Double heightToWidthRatio) {
        this.photoUrl = photoUrl;
        this.heightToWidthRatio = heightToWidthRatio;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Double getHeightToWidthRatio() {
        return heightToWidthRatio;
    }
}
