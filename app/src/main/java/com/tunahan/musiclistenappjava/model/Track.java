package com.tunahan.musiclistenappjava.model;

public class Track {
    private long id;
    private boolean readable;
    private String title;
    private String titleShort;
    private String titleVersion;
    private String isrc;
    private String link;
    private int duration;
    private int rank;
    private boolean explicitLyrics;
    private int explicitContentLyrics;
    private int explicitContentCover;
    private String preview;
    private String md5Image;
    private long timeAdd;
    private Artist artist;
    private Album album;
    private String type;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getExplicitContentCover() {
        return explicitContentCover;
    }

    public void setExplicitContentCover(int explicitContentCover) {
        this.explicitContentCover = explicitContentCover;
    }

    public int getExplicitContentLyrics() {
        return explicitContentLyrics;
    }

    public void setExplicitContentLyrics(int explicitContentLyrics) {
        this.explicitContentLyrics = explicitContentLyrics;
    }

    public boolean isExplicitLyrics() {
        return explicitLyrics;
    }

    public void setExplicitLyrics(boolean explicitLyrics) {
        this.explicitLyrics = explicitLyrics;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIsrc() {
        return isrc;
    }

    public void setIsrc(String isrc) {
        this.isrc = isrc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMd5Image() {
        return md5Image;
    }

    public void setMd5Image(String md5Image) {
        this.md5Image = md5Image;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isReadable() {
        return readable;
    }

    public void setReadable(boolean readable) {
        this.readable = readable;
    }

    public long getTimeAdd() {
        return timeAdd;
    }

    public void setTimeAdd(long timeAdd) {
        this.timeAdd = timeAdd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleShort() {
        return titleShort;
    }

    public void setTitleShort(String titleShort) {
        this.titleShort = titleShort;
    }

    public String getTitleVersion() {
        return titleVersion;
    }

    public void setTitleVersion(String titleVersion) {
        this.titleVersion = titleVersion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
