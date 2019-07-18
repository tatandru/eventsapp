package com.example.eventsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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

import java.io.IOException;

public class EventFragment extends Fragment {

    private TextView tv_event_name;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private ToggleButton favoriteButton;
    private Event event;
    private EventsViewModel favoritesViewModel;
    private ImageView eventImage;
    private FavoriteEvents favoriteEvent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_details, container, false);
        Bundle bundle = getArguments();
        try {
            event = (Event) UpcomingEventsFragment.bytes2Object(bundle.getByteArray("event"));
            System.out.println(event.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        eventImage = view.findViewById(R.id.iv_event_image);
        tv_event_name = view.findViewById(R.id.tv_description);
        tv_start_date = view.findViewById(R.id.tv_start_data);
        favoriteButton = view.findViewById(R.id.btn_heart);
        Glide.with(this.getContext()).load(event.getImgList().get(3).getImageURL()).into(eventImage);
        tv_event_name.setText(event.getEventName());
        tv_start_date.setText(event.getDates().getStartDate().toString());
        favoritesViewModel = new EventsViewModel(getActivity().getApplication());
        favoriteEvent = new FavoriteEvents(event.getEventName(), event.getImgList().get(3).getImageURL(), event.getDates().getStartDate().getDayStartEvent());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        favoriteButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (isChecked(favoriteButton))
                    favoritesViewModel.insert(favoriteEvent);
            }
        });
    }

    private boolean isChecked(View View) {
        return favoriteButton.isChecked();
    }
}
