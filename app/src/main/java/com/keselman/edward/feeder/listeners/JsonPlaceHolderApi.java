package com.keselman.edward.feeder.listeners;

import com.keselman.edward.feeder.models.Entry;
import com.keselman.edward.feeder.models.Root;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("link_json.json")
    Observable<Root> getLinkPosts();

    @GET("video_json.json")
    Observable<Root> getVideoPosts();

}
