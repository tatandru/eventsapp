package com.example.eventsapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventsapp.adapters.ItemRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView rvItems;
    private RecyclerView.LayoutManager layoutManager;
    private ItemRecycleViewAdapter adapter;
    private List<String> dataSet;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.favorites_activity, container, false);
        rvItems = view.findViewById(R.id.rv_favoriteList);
        setupList();
        return view;

    }

    private void setupList() {

        layoutManager = new LinearLayoutManager(getContext());  // use a linear layout manager vertical
//        layoutManager = new GridLayoutManager(this,3);  // use a grid layout manager with 3 columns
        rvItems.setLayoutManager(layoutManager);

        generateDataSet();

        adapter = new ItemRecycleViewAdapter(dataSet, getContext());
        adapter.setItemClickListener(new ItemRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onClick(String os) {
                Toast.makeText(getContext(), os, Toast.LENGTH_SHORT).show();
            }
        });
        rvItems.setAdapter(adapter);
    }


    private void generateDataSet() {
        dataSet = new ArrayList<>();


        List<String> myList = new ArrayList<>();


        myList.add("Hello_1");
        myList.add("Hello_2");
        myList.add("Hello_3");

        Log.e("TAG", myList.toString());

        for (String c : myList) {
            dataSet.add(c);
        }
    }
}
