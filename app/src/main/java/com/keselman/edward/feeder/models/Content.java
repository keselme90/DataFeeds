package com.keselman.edward.feeder.models;

import com.google.gson.annotations.SerializedName;

public class Content
{
    @SerializedName("src")
    private String src;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
