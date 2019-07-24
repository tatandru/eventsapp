package com.example.eventsapp;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.eventsapp.retrofitAPI.Embedded;
import com.example.eventsapp.retrofitAPI.Event;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class UpcomingEventsFragment extends Fragment {
    final static String SHARED_FILTER_INTEGER = "sharedFilterInt";
    final static String SHARED_FILTER_START_DATE = "sharedStartDate";
    final static String SHARED_FILTER_END_DATE = "sharedEndDate";
    final static String SHARED_FILTER_VALIDATOR = "sharedValidator";

    final static String FILTER_MAX_VALUE_INTEGER = "filterMaxValue";
    final static String FILTER_START_DATE_STRING = "filterStartDate";
    final static String FILTER_END_DATE_STRING = "filterEndDate";
    final static String FILTER_VALIDATOR_BOOLEAN = "filterValidator";

    private ImageView imgFilter;
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
    private int filterPrice;
    private String startDate;
    private String endDate;
    private Boolean isFiltred = false;
    private ArrayList<Event> oldEventListRV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upcoming_events_layout, container, false);
        tvTitle = view.findViewById(R.id.tv_title_upcoming_events);
        rvItems = view.findViewById(R.id.rv_events_list);
        searchView = view.findViewById(R.id.sv_search_event);

        getSharedPrefData();
        Log.e("OnResumeUpcoming", filterPrice + " " + startDate + " " + endDate + " " + isFiltred);
        Log.e("onStartUpcomingView", filterPrice + "  " + startDate + "  " + endDate + " ");

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
        imgFilter = (ImageView) view.findViewById(R.id.img_filter_logo);


        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (eventListRV.size() < 1) {
                    Toast.makeText(getContext(), "No events found !", Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();

                } else {
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

            }
        });


        setOnQuerySearchView();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        discardSharedPref();
        Log.e("OnAttachUpcoming", filterPrice + " " + startDate + " " + endDate + " " + isFiltred);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        discardSharedPref();
        Log.e("OnDetachUpcoming", filterPrice + " " + startDate + " " + endDate + " " + isFiltred);
    }

    /***
     * My functions
     * */
    private void discardSharedPref() {

        filterPrice = -1;
        startDate = null;
        endDate = null;
        isFiltred = false;

        SharedPreferences.Editor editFilterMaxVal = getContext().getSharedPreferences(SHARED_FILTER_INTEGER, MODE_PRIVATE).edit();
        editFilterMaxVal.putInt(FILTER_MAX_VALUE_INTEGER, -1);

        SharedPreferences.Editor editStartDate = getContext().getSharedPreferences(SHARED_FILTER_START_DATE, MODE_PRIVATE).edit();
        editStartDate.putString(FILTER_START_DATE_STRING, null);

        SharedPreferences.Editor editEndDate = getContext().getSharedPreferences(SHARED_FILTER_END_DATE, MODE_PRIVATE).edit();
        editEndDate.putString(FILTER_END_DATE_STRING, null);

        SharedPreferences.Editor editValidator = getContext().getSharedPreferences(SHARED_FILTER_VALIDATOR, MODE_PRIVATE).edit();
        editValidator.putBoolean(FILTER_VALIDATOR_BOOLEAN, false);

        editFilterMaxVal.apply();
        editStartDate.apply();
        editEndDate.apply();
        editValidator.apply();

    }

    private void getSharedPrefData() {
        SharedPreferences prefsMaxValPrice = getContext().getSharedPreferences(SHARED_FILTER_INTEGER, MODE_PRIVATE);
        filterPrice = prefsMaxValPrice.getInt(FILTER_MAX_VALUE_INTEGER, -1);

        SharedPreferences prefsStartDate = getContext().getSharedPreferences(SHARED_FILTER_START_DATE, MODE_PRIVATE);
        startDate = prefsStartDate.getString(FILTER_START_DATE_STRING, null);

        SharedPreferences prefsEndDate = getContext().getSharedPreferences(SHARED_FILTER_END_DATE, MODE_PRIVATE);
        endDate = prefsEndDate.getString(FILTER_END_DATE_STRING, null);

        SharedPreferences prefsValidator = getContext().getSharedPreferences(SHARED_FILTER_VALIDATOR, MODE_PRIVATE);
        isFiltred = prefsValidator.getBoolean(FILTER_VALIDATOR_BOOLEAN, false);
    }

    private void setupList() {
        layoutManager = new LinearLayoutManager(getContext());  // use a linear layout manager vertical
        rvItems.setLayoutManager(layoutManager);

        generateDataSet();

        Log.e("UpcomingEventsFragment", embedded.getEventList().toString());

        if (isFiltred) {
            if (filterPrice != -1) {
                eventListRV = filterAfterPrice(oldEventListRV);
            }
            Log.e("UpcomingFiltratedPrice", "<<<|>>>" + eventListRV.toString());
            if (startDate.length() <= 0 && endDate.length() <= 0 && startDate != null && endDate != null && startDate != "" && endDate != "") {
                eventListRV = filterByDate(eventListRV);
            }
            Log.e("UpcomingFiltratedDates", "<<<|>>>" + eventListRV.toString());

        } else {
            eventListRV = oldEventListRV;
        }

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

        oldEventListRV = new ArrayList<>();
        eventListRV = new ArrayList<>();

        System.out.println(nameOfEventRetrieveFromHomePage);

        oldEventListRV.addAll(eventsFromHomePage);
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


    private ArrayList<Event> filterAfterPrice(List<Event> list) {
        ArrayList<Event> eventList = new ArrayList<>();
        for (Event e : list) {

            if (e.getPriceRangeList() != null) {
                if (filterPrice >= e.getPriceRangeList().get(0).getMaxPrice()) {
                    eventList.add(e);
                }
            } else {
                eventList.add(e);
            }
        }
        return eventList;
    }

    private ArrayList<Event> filterByDate(List<Event> list) {
        ArrayList<Event> eventList = new ArrayList<>();
        for (Event e : list) {

            String dateStart = e.getDates().getStartDate().getDayStartEvent();
            String dateEnd = e.getDates().getStartDate().getDayEndAndTime().split("T")[0];

            Log.e("DateFormatUpcomng", "Local :dateStart" + dateStart + "  " + "Global : startDate " + startDate + "\n");
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //todo : end date in event is not found

                if (!startDate.isEmpty() && (dateFormat.parse(startDate).before(dateFormat.parse(dateStart)) && dateFormat.parse(endDate).after(dateFormat.parse(dateEnd)))
                        || dateFormat.parse(startDate).equals(dateFormat.parse(dateStart)) || dateFormat.parse(endDate).equals(dateFormat.parse(dateEnd))) {
                    eventList.add(e);
                    Log.e("ObjFiltredByDate", e.toString() + "\n");

                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }


            Log.e("DatesOnUpcoming", dateStart + " " + dateEnd);
        }
        return eventList;
    }
}