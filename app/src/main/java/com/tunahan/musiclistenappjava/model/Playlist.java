package com.tunahan.musiclistenappjava.model;

public class Playlist {
    private long id;
    private String title;
    private String description;
    private int duration;
    private boolean isPublic;
    private boolean isLovedTrack;
    private boolean collaborative;
    private int nbTracks;
    private int fans;
    private String link;
    private String share;
    private String picture;
    private String pictureSmall;
    private String pictureMedium;
    private String pictureBig;
    private String pictureXl;
    private String checksum;
    private String tracklist;
    private String creationDate;
    private String addDate;
    private String modDate;
    private String md5Image;
    private String pictureType;
    private Creator creator;
    private String type;
    private TrackList tracks;

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public boolean isCollaborative() {
        return collaborative;
    }

    public void setCollaborative(boolean collaborative) {
        this.collaborative = collaborative;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isLovedTrack() {
        return isLovedTrack;
    }

    public void setLovedTrack(boolean lovedTrack) {
        isLovedTrack = lovedTrack;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
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

    public String getModDate() {
        return modDate;
    }

    public void setModDate(String modDate) {
        this.modDate = modDate;
    }

    public int getNbTracks() {
        return nbTracks;
    }

    public void setNbTracks(int nbTracks) {
        this.nbTracks = nbTracks;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPictureBig() {
        return pictureBig;
    }

    public void setPictureBig(String pictureBig) {
        this.pictureBig = pictureBig;
    }

    public String getPictureMedium() {
        return pictureMedium;
    }

    public void setPictureMedium(String pictureMedium) {
        this.pictureMedium = pictureMedium;
    }

    public String getPictureSmall() {
        return pictureSmall;
    }

    public void setPictureSmall(String pictureSmall) {
        this.pictureSmall = pictureSmall;
    }

    public String getPictureType() {
        return pictureType;
    }

    public void setPictureType(String pictureType) {
        this.pictureType = pictureType;
    }

    public String getPictureXl() {
        return pictureXl;
    }

    public void setPictureXl(String pictureXl) {
        this.pictureXl = pictureXl;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
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

    public TrackList getTracks() {
        return tracks;
    }

    public void setTracks(TrackList tracks) {
        this.tracks = tracks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

