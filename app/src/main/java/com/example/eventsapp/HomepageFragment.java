package com.example.eventsapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventsapp.adapters.CategoriesRVAdapter;
import com.example.eventsapp.retrofitAPI.ApiConstans;
import com.example.eventsapp.retrofitAPI.BaseRouteEvents;
import com.example.eventsapp.retrofitAPI.Embedded;
import com.example.eventsapp.retrofitAPI.RetrofitClient;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLOutput;
import java.util.Arrays;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class HomepageFragment extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 1;
    private RecyclerView rvItems;
    private RecyclerView.LayoutManager layoutManager;
    private AutocompleteSupportFragment autocomplete;
    private RetrofitClient retrofit;
    private BaseRouteEvents baseRouteEventsBody;
    private CategoriesRVAdapter adapter;
    private List<String> subCategoriesMain;
    private List<String> urlDataSetMain;
    private EditText searchBar;
    private List<String> subCategories;
    private ArrayList<String> imgThreeInList;
    private ArrayList<String> imgEventsInList;
    private ArrayList<String> imgUpcomingEventNameList;
    private String cityName;
    private Embedded embedded;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        retrofit = RetrofitClient.getInstance();
        rvItems = view.findViewById(R.id.rv_categories);
        searchBar = view.findViewById(R.id.et_search_bar);
        loadEvents();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupSearch();
        configureRequestButton();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                searchBar.setText(place.getName());
            }
        }
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

                embedded = baseRouteEventsBody.getEmbedded();

                int eventListSize = baseRouteEventsBody.getEmbedded().getEventList().size();
                int classificationListSize;
                List<String> eventClassificationList = new ArrayList<>();
                List<List<String>> urlList = new ArrayList<>();


                for (int i = 0; i < eventListSize; i++) {
                    classificationListSize = baseRouteEventsBody.getEmbedded().getEventList().get(i).getClassficationList().size();
                    int imageListSize = baseRouteEventsBody.getEmbedded().getEventList().get(i).getImgList().size();
                    List<String> imageList = new ArrayList<>();
                    System.out.println("------>" + baseRouteEventsBody.getEmbedded().getEventList().get(i));


                    for (int j = 0; j < classificationListSize; j++) {
                        String genreEventName = baseRouteEventsBody.getEmbedded().getEventList().get(i).getClassficationList().get(j).getGenre().getEventGenre();

                        eventClassificationList.add(genreEventName);

                    }

                    for (int j = 0; j < imageListSize; j++) {
                        String url = baseRouteEventsBody.getEmbedded().getEventList().get(i).getImgList().get(j).getImageURL();
                        imageList.add(url);
                    }
                    urlList.add(imageList);
                }

                imgThreeInList = new ArrayList<>();
                subCategories = new ArrayList<>();

                for (int i = 0; i < eventListSize; i++) {


                    for (int j = 0; j < baseRouteEventsBody.getEmbedded().getEventList().get(i).getClassficationList().size(); j++) {

                        String e = baseRouteEventsBody.getEmbedded().getEventList().get(i).getClassficationList().get(j).getGenre().getEventGenre();

                        if (!subCategories.contains(e)) {
                            subCategories.add(e);
                            imgThreeInList.add(urlList.get(i).get(3));
                            Log.e("HomepageFragment", "Lista de subcategorii se incarca");
                        }
                        Log.e("HomepageFragment", "Lista de subcategorii contine elementul ");
                    }

                }
                System.out.println(subCategories.toString());
                System.out.println(imgThreeInList.toString());

                setupList();
            }

            @Override
            public void onFailure(Call<BaseRouteEvents> call, Throwable t) {
                System.out.println("------>ERRRRORRR<------");
                t.printStackTrace();
            }
        });

    }

    private void setupList() {
        //  layoutManager = new LinearLayoutManager(getContext());  // use a linear layout manager vertical
        layoutManager = new GridLayoutManager(getContext(), 2);  // use a grid layout manager with 2 columns
        rvItems.setLayoutManager(layoutManager);


        generateDataSet();

        adapter = new CategoriesRVAdapter(subCategoriesMain, urlDataSetMain, getContext());
        adapter.setCategoryClickListener(new CategoriesRVAdapter.ItemClickListener() {
            @Override
            public void onClick(String os) {

                //Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_SHORT).show();
                try {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    UpcomingEventsFragment eventsFragment = new UpcomingEventsFragment();


                    retrieveImageOfEvent(os);
                    if (printMessage(imgEventsInList, os)) {
                        Bundle bundle = new Bundle();

                        bundle.putByteArray("As", object2Bytes(embedded));
                        bundle.putString("title", os);
                        bundle.putStringArrayList("img", imgEventsInList);
                        bundle.putStringArrayList("name_of_event", imgUpcomingEventNameList);
                        eventsFragment.setArguments(bundle); //data being send to SecondFragment
                        transaction.replace(R.id.fragment_container, eventsFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } else {
                        Toast.makeText(getContext(), os + " not found", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), os, Toast.LENGTH_SHORT).show();
                }
            }
        });
        rvItems.setAdapter(adapter);

    }


    static public byte[] object2Bytes(Object o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        return baos.toByteArray();
    }


    private void generateDataSet() {
        subCategoriesMain = new ArrayList<>();
        urlDataSetMain = new ArrayList<>();

        List<String> subCategList = subCategories;
        List<String> urlList = imgThreeInList;


        Log.e("TAG", "Lista de subcategorii" + subCategories.toString());
        for (String e : subCategList) {
            subCategoriesMain.add(e);
        }


        Log.e("TAG", imgThreeInList.toString());
        for (String e : urlList) {
            urlDataSetMain.add(e);
        }


    }

    private void setupSearch() {
        //Todo:de refacut cu intent https://developers.google.com/places/android-sdk/autocomplete#option_2_use_an_intent_to_launch_the_autocomplete_activity
        final int AUTOCOMPLETE_REQUEST_CODE = 1;
        String placesApiKey = "AIzaSyCxLXd2mtdZtf7UjTpymS45BWto4JumZ3k";
        Places.initialize(this.getActivity(), placesApiKey);
        PlacesClient placesClient = Places.createClient(this.getActivity());
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
        final Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .setTypeFilter(TypeFilter.CITIES)
                .build(this.getActivity());
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

            }
        });
    }

    private void requestReadLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
            addLocation();

        } else {
            addLocation();
        }
    }

    private void configureRequestButton() {
        Button requestButton = getView().findViewById(R.id.btn_request_location);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestReadLocationPermission();
            }
        });
    }

    private void retrieveImageOfEvent(String os) {
        imgEventsInList = new ArrayList<>();
        imgUpcomingEventNameList = new ArrayList<>();
        for (int i = 0; i < baseRouteEventsBody.getEmbedded().getEventList().size(); i++)
            for (int j = 0; j < baseRouteEventsBody.getEmbedded().getEventList().get(i).getClassficationList().size(); j++)
                if (os.equals(baseRouteEventsBody.getEmbedded().getEventList().get(i).getClassficationList().get(j).getGenre().getEventGenre())) {
                    System.out.println(baseRouteEventsBody.getEmbedded().getEventList().get(i).getEventName());
                    imgEventsInList.add(baseRouteEventsBody.getEmbedded().getEventList().get(i).getImgList().get(3).getImageURL());
                    imgUpcomingEventNameList.add(baseRouteEventsBody.getEmbedded().getEventList().get(i).getEventName());

                }


    }

    private boolean printMessage(List img, String os) {
        return img.size() >= 1;
    }

    private void addLocation() {
        LocationManager locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
        try {
            if (ContextCompat.checkSelfPermission(this.getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        // Called when a new location is found by the network location provider.
                        String latitude = Double.toString(location.getLatitude());
                        String longitude = Double.toString(location.getLongitude());
                        cityName = processLocation(latitude, longitude);
                        searchBar.setText(cityName);
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {
                    }

                    public void onProviderDisabled(String provider) {
                    }
                };

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String processLocation(String latitude, String longitude) {
        List<Address> address;
        String city = "";
        Geocoder geocoder = new Geocoder(this.getActivity());
        try {
            address = geocoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 3);
            city = address.get(0).getLocality();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return city;
    }
}
