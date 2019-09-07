package com.keselman.edward.feeder.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
public class MediaGroup
{
    @SerializedName("type")
    private String type;

    @SerializedName("media_item")
    private List<MediaItem> mediaItem;

    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setMedia_item(List<MediaItem> media_item){
        this.mediaItem = media_item;
    }
    public List<MediaItem> getmediaItem(){
        return this.mediaItem;
    }
}

