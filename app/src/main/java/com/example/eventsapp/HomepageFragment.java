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

import java.util.ArrayList;
import java.util.List;

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
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    /**
     * Load Events From BaseRouteEvents
     */
    private void loadEvents() {
        Call<BaseRouteEvents> call = retrofit.getEventService().getEvents(ApiConstans.APP_KEY);


        call.enqueue(new Callback<BaseRouteEvents>() {
            @Override
            public void onResponse(Call<BaseRouteEvents> call, Response<BaseRouteEvents> response) {

                baseRouteEventsBody = response.body();
                int eventListSize = baseRouteEventsBody.getEmbedded().getEventList().size();
                List<String> eventClassificationList = new ArrayList<>();
                List<List<String>> urlList = new ArrayList<>();


                for (int i = 0; i < eventListSize; i++) {

                    int classificationListSize = baseRouteEventsBody.getEmbedded().getEventList().get(i).getClassficationList().size();
                    int imageListSize = baseRouteEventsBody.getEmbedded().getEventList().get(i).getImgList().size();
                    List<String> imageList = new ArrayList<>();
                    System.out.println("------>" + baseRouteEventsBody.getEmbedded().getEventList().get(i));


                    for (int j = 0; j < classificationListSize; j++) {
                        String classificationName = baseRouteEventsBody.getEmbedded().getEventList().get(i).getClassficationList().get(j).getSegment().getEventType();
                        if (!eventClassificationList.contains(classificationName)) {
                            eventClassificationList.add(classificationName);
                        }
                    }


                    for (int j = 0; j < imageListSize; j++) {
                        String url = baseRouteEventsBody.getEmbedded().getEventList().get(i).getImgList().get(j).getImageURL();
                        imageList.add(url);
                    }
                    urlList.add(imageList);
                }
                System.out.println(eventClassificationList.toString());
                System.out.println(urlList.toString());


            }

            @Override
            public void onFailure(Call<BaseRouteEvents> call, Throwable t) {
                System.out.println("------>ERRRRORRR<------");
                t.printStackTrace();
            }
        });

    }

}
