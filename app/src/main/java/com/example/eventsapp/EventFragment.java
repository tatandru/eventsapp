package com.example.eventsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.eventsapp.database.EventsViewModel;
import com.example.eventsapp.database.FavoriteEvents;
import com.example.eventsapp.retrofitAPI.Event;

public class EventFragment extends Fragment {

    private TextView tv_event_name;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private ToggleButton favoriteButton;
    private Event event;
    private EventsViewModel favoritesViewModel;
    private ImageView eventImage;
    private FavoriteEvents newFavoriteEvent;
    private FavoriteEvents favoriteEvent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_details, container, false);
        initUi(view);
        favoritesViewModel = new EventsViewModel(getActivity().getApplication());
        eventFromUpcomingFragment();
        eventFromFavoriteFragment();
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        favoriteButton.setOnCheckedChangeListener((compoundButton, b) -> {
            if (isChecked(favoriteButton)) {
                if (newFavoriteEvent != null) {
                    favoritesViewModel.insert(newFavoriteEvent);
                } else {
                    if (favoriteEvent != null) {
                        favoritesViewModel.insert(favoriteEvent);
                    }
                }
            } else {
                if (newFavoriteEvent != null) {
                    favoritesViewModel.delete(newFavoriteEvent);
                } else {
                    if (favoriteEvent != null) {
                        favoritesViewModel.delete(favoriteEvent);
                    }
                }
            }

        });
    }

    private boolean isChecked(View View) {
        return favoriteButton.isChecked();
    }

    private String splitAtACharacter(String word) {
        String[] arrayUseToSplit = word.split("T");
        return arrayUseToSplit[0];
    }

    private void isFavorite(final Event event) {
        Thread thread = new Thread(() -> {
            if (favoritesViewModel.searchEventById(event.getIdEvent()) != null) {

                getActivity().runOnUiThread(() -> favoriteButton.setChecked(true));

            } else {
                getActivity().runOnUiThread(() -> favoriteButton.setChecked(false));

            }
        });

        thread.start();
    }

    private void initUi(View view) {
        eventImage = view.findViewById(R.id.iv_event_image);
        tv_event_name = view.findViewById(R.id.tv_description);
        tv_start_date = view.findViewById(R.id.tv_start_data);
        tv_end_date = view.findViewById(R.id.tv_end_data);
        favoriteButton = view.findViewById(R.id.btn_heart);
    }

    private void eventFromUpcomingFragment() {
        Bundle bundle = getArguments();
        try {
            event = (Event) UpcomingEventsFragment.bytes2Object(bundle.getByteArray("event"));
            isFavorite(event);
            Glide.with(this.getActivity()).load(event.getImgList().get(3).getImageURL()).into(eventImage);
            tv_event_name.setText(event.getEventName());
            tv_start_date.setText(event.getDates().getStartDate().getDayStartEvent());
            if (event.getDates().getStartDate().getDayEndAndTime() == null) {
                tv_end_date.setText("Date not found");
            } else {
                tv_end_date.setText(splitAtACharacter(event.getDates().getStartDate().getDayEndAndTime()));
            }
            newFavoriteEvent = new FavoriteEvents(event.getIdEvent(), event.getEventName(), event.getImgList().get(3).getImageURL(), event.getDates().getStartDate().getDayStartEvent(), tv_end_date.getText().toString());
            System.out.println(event.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void eventFromFavoriteFragment() {
        Bundle bundle = getArguments();
        try {
            favoriteEvent = (FavoriteEvents) FavoritesFragment.bytes2Object(bundle.getByteArray("event1"));
            Glide.with(this.getActivity()).load(favoriteEvent.getUrlImg()).into(eventImage);
            tv_event_name.setText(favoriteEvent.getEventName());
            tv_start_date.setText(favoriteEvent.getStartDate());
            tv_end_date.setText(favoriteEvent.getEndDate());
            favoriteButton.setChecked(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
