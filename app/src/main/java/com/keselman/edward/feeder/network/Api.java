package com.keselman.edward.feeder.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.keselman.edward.feeder.listeners.JsonPlaceHolderApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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

    private Api() {

        buildRetrofit(BASE_URL);
    }

    private void buildRetrofit(String baseUrl) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        this.mJsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

    }

    public JsonPlaceHolderApi getUserService() {
        return this.mJsonPlaceHolderApi;
    }

}
