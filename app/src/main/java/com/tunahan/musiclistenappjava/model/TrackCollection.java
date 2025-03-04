package com.tunahan.musiclistenappjava.model;

import java.util.List;

public class TrackCollection {
    private List<Track> data;
    private int total;

    public List<Track> getData() {
        return data;
    }

    public void setData(List<Track> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
