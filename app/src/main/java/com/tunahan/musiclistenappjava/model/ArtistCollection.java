package com.tunahan.musiclistenappjava.model;

import java.util.List;

public class ArtistCollection {
    private List<Artist> data;
    private int total;

    public List<Artist> getData() {
        return data;
    }

    public void setData(List<Artist> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
