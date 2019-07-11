package com.example.eventsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarFragment extends Fragment {

    private CalendarView calendarView;
    private TextView txtCurrentDate;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.calendar_activity, container, false);
        calendarView = view.findViewById(R.id.calendarView);
        txtCurrentDate = view.findViewById(R.id.txt_currentDay);
        setCurrentDate();
        setCalendarClickListener();
        return view;
    }

    
    /**
     * My functions
     */

    private void setCurrentDate() {
        Long l = calendarView.getDate();
        String dateString = new SimpleDateFormat("cccc, LLLL d").format(new Date(l));
        txtCurrentDate.setText(dateString);
    }

    private void setCalendarClickListener() {

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //todo: show information of the event , if there is one
            }
        });
    }
}
