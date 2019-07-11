package com.example.eventsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eventsapp.retrofitAPI.ApiConstans;
import com.example.eventsapp.retrofitAPI.BaseRouteEvents;
import com.example.eventsapp.retrofitAPI.RetrofitClient;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomepageFragment extends Fragment {

    private AutocompleteSupportFragment autocomplete;
    private RetrofitClient retrofit;
    private BaseRouteEvents baseRouteEventsBody;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        retrofit = RetrofitClient.getInstance();
        loadEvents();
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //setupSearch();
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

    private void initializeSearchBar() {
        autocomplete = (AutocompleteSupportFragment) this.getActivity().getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocomplete.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocomplete.setTypeFilter(TypeFilter.CITIES);
        autocomplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Toast.makeText(getActivity(), "sadfsadf", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(getActivity(), "Location does not exist!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initilizePlaces() {
        String placesApiKey = "AIzaSyDuqYtttuZVl-51XFiyhreLb4kxMjKqBVE";
        Places.initialize(getActivity(), placesApiKey);
        PlacesClient placesClient = Places.createClient(getActivity());
    }

}
