package com.tunahan.musiclistenappjava.model;

import com.google.gson.annotations.SerializedName;

public class Album {
    private long id;
    private String title;
    private String upc;
    private String cover;
    private String coverSmall;
    private String coverMedium;
    @SerializedName("cover_big")
    private String coverBig;
    private String coverXl;
    private String md5Image;
    private String tracklist;
    private String type;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCoverBig() {
        return coverBig;
    }

    public void setCoverBig(String coverBig) {
        this.coverBig = coverBig;
    }

    public String getCoverMedium() {
        return coverMedium;
    }

    public void setCoverMedium(String coverMedium) {
        this.coverMedium = coverMedium;
    }

    public String getCoverSmall() {
        return coverSmall;
    }

    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
    }

    public String getCoverXl() {
        return coverXl;
    }

    public void setCoverXl(String coverXl) {
        this.coverXl = coverXl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMd5Image() {
        return md5Image;
    }

    public void setMd5Image(String md5Image) {
        this.md5Image = md5Image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTracklist() {
        return tracklist;
    }

    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }
}
