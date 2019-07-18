package com.example.eventsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.eventsapp.retrofitAPI.Embedded;
import com.example.eventsapp.retrofitAPI.Event;

import java.io.IOException;

public class EventFragment extends Fragment {

    TextView tv_event_name;
    TextView tv_start_date;
    TextView tv_end_date;
    ImageView imageView;
    TextView txtIdEvent;
    Event event;

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
        imageView = view.findViewById(R.id.imageView);
        tv_event_name = view.findViewById(R.id.tv_description);
        tv_start_date = view.findViewById(R.id.tv_start_data);
        if (bundle != null) {
            Glide.with(this.getContext()).load(event.getImgList().get(3).getImageURL()).into(imageView);
            tv_event_name.setText(event.getEventName());
            tv_start_date.setText(event.getDates().getStartDate().toString());

        }
        return view;
    }
}
