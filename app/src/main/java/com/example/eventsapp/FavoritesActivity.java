package com.example.eventsapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.eventsapp.adapters.ItemRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView rvItems;
    private RecyclerView.LayoutManager layoutManager;
    private ItemRecycleViewAdapter adapter;
    private List<String> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_activity);

        setupList();
    }

    /**
     * My functions
     */


    private void setupList() {
        rvItems = findViewById(R.id.rv_favoriteList);


        layoutManager = new LinearLayoutManager(this);  // use a linear layout manager vertical
//        layoutManager = new GridLayoutManager(this,3);  // use a grid layout manager with 3 columns
        rvItems.setLayoutManager(layoutManager);

        generateDataSet();

        adapter = new ItemRecycleViewAdapter(dataSet, this);
        adapter.setItemClickListener(new ItemRecycleViewAdapter.ItemClickListener() {
            @Override
            public void onClick(String os) {
                Toast.makeText(FavoritesActivity.this, os, Toast.LENGTH_SHORT).show();
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
