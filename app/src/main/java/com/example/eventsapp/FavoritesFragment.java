package com.example.eventsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventsapp.adapters.FavoriteRecyclerViewAdapter;
import com.example.eventsapp.database.EventsViewModel;
import com.example.eventsapp.database.FavoriteEvents;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;


public class FavoritesFragment extends Fragment {
    private EventsViewModel favoritesViewModel;
    private RecyclerView recyclerViewFavorites;
    private List<FavoriteEvents> events;
    private FavoriteRecyclerViewAdapter adapter;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.favorites_activity, container, false);
        recyclerViewFavorites = view.findViewById(R.id.rv_favoriteList);
        textView = view.findViewById(R.id.tv_no_favorites);
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new FavoriteRecyclerViewAdapter(events, this.getContext());
        recyclerViewFavorites.setAdapter(adapter);
        favoritesViewModel = new EventsViewModel(getActivity().getApplication());
        favoritesViewModel.getAllEvents().observe(this, favoriteEvents -> {
            adapter.setFavoriteEvents(favoriteEvents);
            if (adapter.getItemCount() != 0) {
                textView.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter.setCategoryClickListener((item, event) -> {
            try {
                Bundle bundle = new Bundle();
                bundle.putByteArray("event1", object2Bytes(event));
                EventFragment eventFragment = new EventFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                eventFragment.setArguments(bundle);
                transaction.replace(R.id.fragment_container, eventFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }


        });
    }

    static Object bytes2Object(byte[] raw)
            throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArray = new ByteArrayInputStream(raw);
        ObjectInputStream ois = new ObjectInputStream(byteArray);
        return ois.readObject();
    }

    static byte[] object2Bytes(Object o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        return baos.toByteArray();
    }
}
