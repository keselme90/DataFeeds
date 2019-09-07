package com.keselman.edward.feeder.models;

import android.support.v7.widget.AppCompatImageView;

//TODO:: THIS IS FOR TESTING ONLY REMOVE IT LATER
public class Post {

    private String title;
    private String summary;
    private String type;
    private AppCompatImageView mImageView;

    public Post(String title, String summary, String type) {
        this.title = title;
        this.summary = summary;
        this.type = type;
    }

    public Post(String title, String summary) {
        this.title = title;
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
