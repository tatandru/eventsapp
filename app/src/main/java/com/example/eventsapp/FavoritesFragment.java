package com.example.eventsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventsapp.adapters.FavoriteRecyclerViewAdapter;
import com.example.eventsapp.database.EventsViewModel;
import com.example.eventsapp.database.FavoriteEvents;

import java.util.List;


public class FavoritesFragment extends Fragment {
    private EventsViewModel favoritesViewModel;
    private RecyclerView recyclerViewFavorites;
    private List<FavoriteEvents> events;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.favorites_activity, container, false);
        recyclerViewFavorites = view.findViewById(R.id.rv_favoriteList);
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this.getContext()));
        final FavoriteRecyclerViewAdapter adapter = new FavoriteRecyclerViewAdapter(events, this.getContext());
        recyclerViewFavorites.setAdapter(adapter);
        favoritesViewModel = new EventsViewModel(getActivity().getApplication());
        favoritesViewModel.getAllEvents().observe(this, new Observer<List<FavoriteEvents>>() {
            @Override
            public void onChanged(List<FavoriteEvents> favoriteEvents) {
                adapter.setFavoriteEvents(favoriteEvents);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}
