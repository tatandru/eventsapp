package com.example.eventsapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventsapp.adapters.EventsListRVAdapter;
import com.example.eventsapp.database.EventsViewModel;
import com.example.eventsapp.database.FavoriteEvents;
import com.example.eventsapp.database.FavoritesDatabase;
import com.example.eventsapp.retrofitAPI.Embedded;
import com.example.eventsapp.retrofitAPI.Event;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class UpcomingEventsFragment extends Fragment {
    public static final String DATA_RECEIVE ="data_received" ;
    private ImageView imageView;
    private RecyclerView rvItems;
    private EventsListRVAdapter adapter;
    private TextView tvTitle;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> eventTitleDataSet;
    private List<String> urlDateSet;
    private List<String> urlRetrieveFromServer;
    private List<String> nameOfEventRetrieveFromHomePage;
    private List<Integer> idEventListFromServer;
    private Embedded embedded;
    private Event thisEvent;
    private List<Integer> idEventsDataSet;
    private TextView txtIdEvent;
    private List<Event> eventListRV;
    private List<Event> eventsFromHomePage;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upcoming_events_layout, container, false);
        tvTitle = view.findViewById(R.id.tv_title_upcoming_events);
        rvItems = view.findViewById(R.id.rv_events_list);

        searchView=view.findViewById(R.id.sv_search_event);

        Bundle bundle = getArguments();

        if (bundle != null) {
            urlRetrieveFromServer = new ArrayList<>();
            nameOfEventRetrieveFromHomePage = new ArrayList<>();
            idEventListFromServer = new ArrayList<>();
            eventsFromHomePage = new ArrayList<>();
            urlRetrieveFromServer = bundle.getStringArrayList("img");
            nameOfEventRetrieveFromHomePage = bundle.getStringArrayList("name_of_event");
            idEventListFromServer = bundle.getIntegerArrayList("idEvents");


            try {
                embedded = (Embedded) bytes2Object(bundle.getByteArray("As"));
                eventsFromHomePage = (List<Event>) bytes2Object(bundle.getByteArray("eventListSubcategories"));

                System.out.println(" Events  >>>>" + eventsFromHomePage.toString());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            for (Event e : eventsFromHomePage) {
                Log.e("UpcomingEventsFragment", e + "\n");
            }


            System.out.println("DSadsa->>>>>>>" + embedded.toString());
            tvTitle.setText(String.valueOf(bundle.getString("title")));

        }
        System.out.println();
        setupList();
        imageView = (ImageView) view.findViewById(R.id.img_filter_logo);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    FilterFragment filterFragment = new FilterFragment();
                    Bundle bundle1 = new Bundle();
                    for (int i = 0; i < eventListRV.size(); i++) {
                        System.out.println(eventListRV.get(i));
                    }
                    for (int i = 0; i < eventListRV.size(); i++) {
                        System.out.println(">>>>>>" + eventListRV.get(i));
                    }
                    bundle1.putByteArray("eventListOnOneCategory", HomepageFragment.object2Bytes(eventListRV));
                    filterFragment.setArguments(bundle1);
                    transaction.replace(R.id.fragment_container, filterFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        setOnQuerySearchView();
        return view;
    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of LoginFragment");
        Bundle bundle = getArguments();
        Boolean f = bundle.getBoolean("isFiltred");
        System.out.println("sdaasd" + f);
     /*   try {
          //  eventListRV= (List<Event>) bytes2Object(bundle.getByteArray("filter_list"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        super.onResume();
    }
    private void setupList() {
        layoutManager = new LinearLayoutManager(getContext());  // use a linear layout manager vertical
        rvItems.setLayoutManager(layoutManager);

        generateDataSet();

        Log.e("UpcomingEventsFragment", embedded.getEventList().toString());

        adapter = new EventsListRVAdapter(thisEvent, embedded, eventListRV, eventTitleDataSet, urlDateSet, getContext());
        adapter.setCategoryClickListener(new EventsListRVAdapter.ItemClickListener() {
            @Override
            public void onClick(String os, Event event) {

                try {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    System.out.println("WORKED" +
                            "sadsadsadasdas");
                    bundle.putString("title", os);
                    retrieveImageOfEvent(os, event);
                    bundle.putByteArray("event", HomepageFragment.object2Bytes(event));
                    EventFragment eventsFragment = new EventFragment();
                    eventsFragment.setArguments(bundle); //data being send to SecondFragment
                    transaction.replace(R.id.fragment_container, eventsFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), os, Toast.LENGTH_SHORT).show();
                }
            }
        });
        rvItems.setAdapter(adapter);
    }


    private void generateDataSet() {
        eventTitleDataSet = new ArrayList<>();
        urlDateSet = new ArrayList<>();
        eventListRV = new ArrayList<>();

        System.out.println(nameOfEventRetrieveFromHomePage);
        //   eventTitleDataSet.addAll(nameOfEventRetrieveFromHomePage);
        //   urlDateSet.addAll(urlRetrieveFromServer);

        eventListRV.addAll(eventsFromHomePage);







    }

    private void retrieveImageOfEvent(String eventName, Event event) {


        Log.e("UpcomingEventsFragment", event.toString());


    }

    static Object bytes2Object(byte[] raw)
            throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArray = new ByteArrayInputStream(raw);
        ObjectInputStream ois = new ObjectInputStream(byteArray);
        return ois.readObject();
    }
    private void setOnQuerySearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               // resetSearchView();
                adapter.getFilter().filter(query);
                searchView.onActionViewCollapsed();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return true;


            }
        });
    }
}