package com.example.eventsapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventsapp.adapters.CategoriesRVAdapter;
import com.example.eventsapp.adapters.ItemRecycleViewAdapter;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomepageFragment extends Fragment {
    private RecyclerView rvItems;

    private RecyclerView.LayoutManager layoutManager;
    private AutocompleteSupportFragment autocomplete;
    private RetrofitClient retrofit;
    private BaseRouteEvents baseRouteEventsBody;
    private CategoriesRVAdapter adapter;
    private List<String> dataSet;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        retrofit = RetrofitClient.getInstance();
        rvItems = view.findViewById(R.id.rv_categories);
        loadEvents();
        setupList();
        return view;
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

                for (int i = 0; i < baseRouteEventsBody.getEmbedded().getEventList().size(); i++) {
                    System.out.println("------>" +
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
    private void setupList() {
      //  layoutManager = new LinearLayoutManager(getContext());  // use a linear layout manager vertical
        layoutManager = new GridLayoutManager(getContext(),2);  // use a grid layout manager with 2 columns
        rvItems.setLayoutManager(layoutManager);

        generateDataSet();

        adapter = new CategoriesRVAdapter(dataSet, getContext());
        adapter.setCategoryClickListener(new CategoriesRVAdapter.ItemClickListener() {
            @Override
            public void onClick(String os) {

              try {
                  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UpcomingEventsFragment()).commit();
              }catch (Exception e)
              { e.printStackTrace();
                  Toast.makeText(getContext(), os, Toast.LENGTH_SHORT).show();
              }}
        });
        rvItems.setAdapter(adapter);

    }


    private void generateDataSet() {
        dataSet = new ArrayList<>();


        List<String> myList = new ArrayList<>();


        myList.add("Sports");
        myList.add("Music");
        myList.add("Art & Theatre");
        myList.add("Family");
        myList.add("Fairs & Exhibitions");
        myList.add("Comedy");
        myList.add("Festivals");
        myList.add("Clubs");

        Log.e("TAG", myList.toString());

        for (String c : myList) {
            dataSet.add(c);
        }
    }
}
