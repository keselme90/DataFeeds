package com.keselman.edward.feeder.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Root
{

    @SerializedName("entry")
    private List<Entry> entry;

    public List<Entry> getEntry() {
        return entry;
    }

    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }
}
