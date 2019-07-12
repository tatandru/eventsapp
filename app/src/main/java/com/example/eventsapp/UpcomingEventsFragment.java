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

import com.example.eventsapp.R;

public class UpcomingEventsFragment extends Fragment {
    private ImageView imageView;
    private TextView tvTitle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.upcoming_events_layout,container,false);
        tvTitle=view.findViewById(R.id.tv_title_upcoming_events);
        Bundle bundle=getArguments();

       if(bundle!=null) {
           System.out.println(bundle.getString("title"));

        tvTitle.setText(String.valueOf(bundle.getString("title")));
       }System.out.println();
        imageView = (ImageView) view.findViewById(R.id.img_filter_logo);
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FilterFragment()).commit();
            }
        });
    return view;
    }

}
