package com.example.eventsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eventsapp.retrofitAPI.ApiConstans;
import com.example.eventsapp.retrofitAPI.BaseRouteEvents;
import com.example.eventsapp.retrofitAPI.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomepageFragment extends Fragment {


    private RetrofitClient retrofit;
    private BaseRouteEvents baseRouteEventsBody;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        retrofit = RetrofitClient.getInstance();
        loadEvents();
        return inflater.inflate(R.layout.fragment_homepage,container,false);
    }

    /**
     * Load Events From BaseRouteEvents
     */
    private void loadEvents(){
        Call<BaseRouteEvents> call = retrofit.getEventService().getEvents(ApiConstans.APP_KEY);


        call.enqueue(new Callback<BaseRouteEvents>() {
            @Override
            public void onResponse(Call<BaseRouteEvents> call, Response<BaseRouteEvents> response) {

                baseRouteEventsBody = response.body();

                for(int i = 0; i< baseRouteEventsBody.getEmbedded().getEventList().size(); i++){
                    System.out.println("------>"+
                            baseRouteEventsBody.getEmbedded().getEventList().get(i));
                    System.out.println();

                }

                }

            @Override
            public void onFailure(Call<BaseRouteEvents> call, Throwable t) {
                System.out.println("------>ERRRRORRR<------");
                t.printStackTrace();
            }
        });

    }

}
