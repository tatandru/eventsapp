package com.example.eventsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.eventsapp.database.EventsViewModel;
import com.example.eventsapp.database.FavoriteEvents;

import java.util.List;

public class EventFragment extends Fragment {

    private TextView tv_event_name;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private ImageView eventImage;
    private ToggleButton favoriteButton;
    private FavoriteEvents event;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_details, container, false);

        eventImage = view.findViewById(R.id.iv_event_image);
        tv_event_name = view.findViewById(R.id.tv_description);
        tv_start_date = view.findViewById(R.id.tv_start_data);
        favoriteButton = view.findViewById(R.id.btn_heart);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Glide.with(this.getContext()).load(bundle.getString("imageEvent")).into(eventImage);
            tv_event_name.setText(bundle.getString("title"));
            tv_start_date.setText(bundle.getString("startDate"));
        }
        event = new FavoriteEvents(bundle.getString("title"), bundle.getString("imageEvent"), bundle.getString("startDate"));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        favoriteButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Bundle bundle = new Bundle();
                bundle.putString("eventName", event.getEventName());
                bundle.putString("urlImg", event.getUrlImg());
                bundle.putString("startDate", event.getStartDate());
                if (isChecked(favoriteButton)) {
                    bundle.putBoolean("favorite", true);
                } else {
                    bundle.putBoolean("favorite", false);
                }
                FavoritesFragment favoritesFragment = new FavoritesFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                favoritesFragment.setArguments(bundle);
                transaction.replace(R.id.fragment_container, favoritesFragment);
                transaction.commit();
                favoriteButton.setChecked(false);
            }
        });
    }

    private boolean isChecked(View View) {
        return favoriteButton.isChecked();
    }
}
