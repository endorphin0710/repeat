package com.behemoth.repeat.model;

public class Search {

    private String title;
    private String thumbnail;

    public Search(){}

    public Search(String title){
        this.title = title;
    }

    public Search(String title, String thumbnail){
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String text) {
        this.title = text;
    }

    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

}
