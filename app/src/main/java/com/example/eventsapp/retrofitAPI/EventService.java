package com.example.eventsapp.retrofitAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface EventService {
    @GET(ApiConstans.EVENT)
    Call<BaseRouteEvents> getEvents(@Query("apikey") String appKey);
}
