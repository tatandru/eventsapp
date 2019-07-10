package com.example.eventsapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView txtCurrentDate;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);
        initUI();
        setCurrentDate();
        setCalendarClickListener();
    }

    /**
     * My functions
     */

    private void initUI() {
        calendarView = findViewById(R.id.calendarView);
        txtCurrentDate = findViewById(R.id.txt_currentDay);


    }

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
