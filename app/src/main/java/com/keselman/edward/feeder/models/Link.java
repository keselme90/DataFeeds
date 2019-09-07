package com.keselman.edward.feeder.models;

import com.google.gson.annotations.SerializedName;

public class Link
{
    @SerializedName("href")
    private String href;

    public void setHref(String href){
        this.href = href;
    }
    public String getHref(){
        return this.href;
    }
}