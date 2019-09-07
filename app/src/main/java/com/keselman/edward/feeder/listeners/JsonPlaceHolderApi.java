package com.keselman.edward.feeder.listeners;

import com.keselman.edward.feeder.models.Entry;
import com.keselman.edward.feeder.models.Root;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("link_json.json")
    Call<Root> getLinkPosts();

    @GET("video_json.json")
    Call<Root> getVideoPosts();

}
