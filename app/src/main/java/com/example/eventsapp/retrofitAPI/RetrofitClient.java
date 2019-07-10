package com.example.eventsapp.retrofitAPI;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private Retrofit retrofit;
    private OkHttpClient client;

    private EventService eventService;

    private RetrofitClient() {

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        client = okHttpBuilder.build();

        retrofit = new Retrofit.Builder().baseUrl(ApiConstans.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        eventService = retrofit.create(EventService.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }

        return instance;
    }

    public EventService getEventService() {
        return eventService;
    }
}
