package com.keselman.edward.feeder.models;

import com.google.gson.annotations.SerializedName;

public class Type
{
    @SerializedName("value")
    private String value;

    public void setValue(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
}
