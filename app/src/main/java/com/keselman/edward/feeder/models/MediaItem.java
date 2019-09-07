package com.keselman.edward.feeder.models;

import com.google.gson.annotations.SerializedName;

public class MediaItem
{
    @SerializedName("src")
    private String src;

    @SerializedName("key")
    private String key;

    public void setSrc(String src){
        this.src = src;
    }
    public String getSrc(){
        return this.src;
    }
    public void setKey(String key){
        this.key = key;
    }
    public String getKey(){
        return this.key;
    }
}