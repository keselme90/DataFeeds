package com.keselman.edward.feeder.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
public class Entry
{
    @SerializedName("type")
    private Type type;

    @SerializedName("title")
    private String title;

    @SerializedName("summary")
    private String summary;

    @SerializedName("published")
    private String published;

    @SerializedName("content")
    private Content content;

    @SerializedName("link")
    private Link link;

    @SerializedName("media_group")
    private List<MediaGroup> mediaGroup;

    public void setType(Type type){
        this.type = type;
    }
    public Type getType(){
        return this.type;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setSummary(String summary){
        this.summary = summary;
    }
    public String getSummary(){
        return this.summary;
    }
    public void setPublished(String published){
        this.published = published;
    }
    public String getPublished(){
        return this.published;
    }
    public void setContent(Content content){
        this.content = content;
    }
    public Content getContent(){
        return this.content;
    }
    public void setLink(Link link){
        this.link = link;
    }
    public Link getLink(){
        return this.link;
    }
    public void setMedia_group(List<MediaGroup> media_group){
        this.mediaGroup = media_group;
    }
    public List<MediaGroup> getMedia_group(){
        return this.mediaGroup;
    }
}