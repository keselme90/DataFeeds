package com.keselman.edward.feeder;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.keselman.edward.feeder.listeners.JsonPlaceHolderApi;
import com.keselman.edward.feeder.models.Entry;
import com.keselman.edward.feeder.models.Root;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static final String TAG = "Api";
    static final String BASE_URL = "http://assets-production.applicaster.com/applicaster-employees/israel_team/Elad/assignment/";

    private static Api sInstance = null;

    private JsonPlaceHolderApi mJsonPlaceHolderApi;

    public static Api getInstance() {
        if (sInstance == null) {
            sInstance = new Api();
        }

        return sInstance;
    }

    // Build retrofit once when creating a single instance
    private Api() {
        // Implement a method to build your retrofit
        buildRetrofit(BASE_URL);
    }

    private void buildRetrofit(String baseUrl) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        this.mJsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

//        Call<Root> call = jsonPlaceHolderApi.getLinkPosts();
//        call.enqueue(this);
    }

    public JsonPlaceHolderApi getUserService() {
        return this.mJsonPlaceHolderApi;
    }

}
