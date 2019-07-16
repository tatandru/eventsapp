package com.example.eventsapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventsapp.R;
import com.example.eventsapp.adapters.CategoriesRVAdapter;
import com.example.eventsapp.adapters.EventsListRVAdapter;
import com.example.eventsapp.retrofitAPI.Embedded;
import com.example.eventsapp.retrofitAPI.Event;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UpcomingEventsFragment extends Fragment {
    private ImageView imageView;
    private RecyclerView rvItems;
    private EventsListRVAdapter adapter;
    private TextView tvTitle;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> eventTitleDataSet;
    private List<String> urlDateSet;
    private List<String> urlRetrieveFromServer;
    private List<String> nameOfEventRetrieveFromServer;
    private ArrayList<Event> mainList;
    private Embedded embedded;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upcoming_events_layout, container, false);
        tvTitle = view.findViewById(R.id.tv_title_upcoming_events);
        rvItems = view.findViewById(R.id.rv_events_list);

        Bundle bundle = getArguments();

        if (bundle != null) {
            urlRetrieveFromServer = new ArrayList<>();
            nameOfEventRetrieveFromServer = new ArrayList<>();
            mainList=new ArrayList<>();
            urlRetrieveFromServer = bundle.getStringArrayList("img");
            nameOfEventRetrieveFromServer = bundle.getStringArrayList("name_of_event");
            try {
                embedded = (Embedded) bytes2Object(bundle.getByteArray("As"));
                System.out.println(embedded.toString());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            System.out.println("DSadsa->>>>>>>"+embedded.toString());
            tvTitle.setText(String.valueOf(bundle.getString("title")));

        }
        System.out.println();
        setupList();
        imageView = (ImageView) view.findViewById(R.id.img_filter_logo);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FilterFragment()).commit();

            }
        });
        return view;
    }

    private void setupList() {
        layoutManager = new LinearLayoutManager(getContext());  // use a linear layout manager vertical
        rvItems.setLayoutManager(layoutManager);

        generateDataSet();

        adapter = new EventsListRVAdapter(eventTitleDataSet, urlDateSet, getContext());
        adapter.setCategoryClickListener(new EventsListRVAdapter.ItemClickListener() {
            @Override
            public void onClick(String os) {


                try {
                    System.out.println("WORKED" +
                            "sadsadsadasdas");
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    EventFragment eventsFragment = new EventFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", os);


                    //   bundle.putString("img", imgEventsInList);
                    //  bundle.putStringArrayList("name_of_event", imgUpcomingEventNameList);
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

        System.out.println(nameOfEventRetrieveFromServer);
        eventTitleDataSet.addAll(nameOfEventRetrieveFromServer);
        urlDateSet.addAll(urlRetrieveFromServer);


    }
    static public Object bytes2Object( byte raw[] )
            throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream( raw );
        ObjectInputStream ois = new ObjectInputStream( bais );
        Object o = ois.readObject();
        return o;
    }
}