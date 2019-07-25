package com.example.eventsapp;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eventsapp.retrofitAPI.Event;
import com.ramotion.fluidslider.FluidSlider;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class FilterFragment extends Fragment {
    final static String SHARED_FILTER_INTEGER = "sharedFilterInt";
    final static String SHARED_FILTER_START_DATE = "sharedStartDate";
    final static String SHARED_FILTER_END_DATE = "sharedEndDate";
    final static String SHARED_FILTER_VALIDATOR = "sharedValidator";

    final static String FILTER_MAX_VALUE_INTEGER = "filterMaxValue";
    final static String FILTER_START_DATE_STRING = "filterStartDate";
    final static String FILTER_END_DATE_STRING = "filterEndDate";
    final static String FILTER_VALIDATOR_BOOLEAN = "filterValidator";

    private TextView tv_start_date;
    private TextView tv_end_date;
    private TextView tv_to;
    private Button btn_filter;
    private String end_date;
    private String start_date;
    private TextView tv_min_price;
    private TextView tv_max_price;
    private int counter = 0;
    private int filterPrice;
    private DatePickerDialog.OnDateSetListener dp_start_date;
    private DatePickerDialog.OnDateSetListener dp_end_date;
    private List<Event> eventListRV;
    private FluidSlider fluidSlider;
    private boolean isFiltred = false;


    @TargetApi(Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.filter_events, container, false);
        tv_start_date = view.findViewById(R.id.tv_start_date);
        tv_end_date = view.findViewById(R.id.tv_end_date);
        btn_filter = view.findViewById(R.id.btn_filter);
        tv_to = view.findViewById(R.id.tv_to);
        fluidSlider = view.findViewById(R.id.seekBar_price);

        clickOnStartDate();

        Bundle bundle = getArguments();

        if (bundle != null) {
            eventListRV = new ArrayList<>();
            try {

                eventListRV = (List<Event>) UpcomingEventsFragment.bytes2Object(bundle.getByteArray("eventListOnOneCategory"));
                System.out.println(eventListRV.size());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        final Double max = getMaxPrice();
        final Double min = getMinPrice();
        final Double total = max - min;
        final float interval = 1f;

        fluidSlider.setStartText(String.valueOf(min));
        fluidSlider.setEndText(String.valueOf(max));
        fluidSlider.setBeginTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {


                Log.d("D", "setBeginTrackingListener");
                return Unit.INSTANCE;
            }
        });
        fluidSlider.setEndTrackingListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                if (tv_end_date.getVisibility() == View.GONE) {
                    turnOnButtonVisbility();
                }
                Log.d("D", "setEndTrackingListener");
                return Unit.INSTANCE;
            }
        });
        fluidSlider.setPositionListener(pos -> {
            final String value = String.valueOf((int) (min + total * pos));
            fluidSlider.setBubbleText(value);
            filterPrice = (int) (min + total * pos);
            Log.e("FilterPrice", "-------------------->" + filterPrice);
            return Unit.INSTANCE;
        });
        fluidSlider.setPosition(interval);


        clickOnStartDate();


        if (bundle != null) {
            eventListRV = new ArrayList<>();

            try {

                eventListRV = (List<Event>) UpcomingEventsFragment.bytes2Object(bundle.getByteArray("eventListOnOneCategory"));
                System.out.println(eventListRV.size());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        tv_start_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_to.setVisibility(View.VISIBLE);
                tv_end_date.setVisibility(View.VISIBLE);
                if (!CheckDates(tv_start_date.getText().toString(), tv_end_date.getText().toString())) {
                    turnOffButtonVisbility();
                    //  Toast.makeText(getContext(), "Set end date to be greater then start date", Toast.LENGTH_LONG).show();
                } else {
                    turnOnButtonVisbility();
                }

            }
        });
        clickOnEndDate();
        tv_end_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (CheckDates(tv_start_date.getText().toString(), tv_end_date.getText().toString())) {
                    turnOnButtonVisbility();

                } else {
                    turnOffButtonVisbility();
                    Toast.makeText(getContext(), "Set end date to be greater then start date", Toast.LENGTH_LONG).show();
                }

            }
        });


        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    isFiltred = true;
                    sendSharePrefData();
                    getFragmentManager().popBackStack();

                } catch (Exception e) {

                }
            }
        });

        return view;

    }

    private void sendSharePrefData() {
        SharedPreferences.Editor editFilterMaxVal = getContext().getSharedPreferences(SHARED_FILTER_INTEGER, MODE_PRIVATE).edit();
        editFilterMaxVal.putInt(FILTER_MAX_VALUE_INTEGER, filterPrice);

        SharedPreferences.Editor editStartDate = getContext().getSharedPreferences(SHARED_FILTER_START_DATE, MODE_PRIVATE).edit();
        editStartDate.putString(FILTER_START_DATE_STRING, tv_start_date.getText().toString());

        SharedPreferences.Editor editEndDate = getContext().getSharedPreferences(SHARED_FILTER_END_DATE, MODE_PRIVATE).edit();
        editEndDate.putString(FILTER_END_DATE_STRING, tv_end_date.getText().toString());

        SharedPreferences.Editor editValidator = getContext().getSharedPreferences(SHARED_FILTER_VALIDATOR, MODE_PRIVATE).edit();
        editValidator.putBoolean(FILTER_VALIDATOR_BOOLEAN, isFiltred);

        editFilterMaxVal.apply();
        editStartDate.apply();
        editEndDate.apply();
        editValidator.apply();

    }

    private void clickOnEndDate() {
        tv_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dp_start_date,
                        year, month, day);
                dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dp_end_date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = year + "-" + month + "-" + day;
                tv_end_date.setText(date);
            }
        };
    }

    private void clickOnStartDate() {
        tv_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dp_end_date,
                        year, month, day);
                dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dp_start_date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = year + "-" + month + "-" + day;
                tv_start_date.setText(date);
            }

        };
    }

    private void turnOnButtonVisbility() {
        btn_filter.setVisibility(View.VISIBLE);

    }

    private double getMaxPrice() {
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < eventListRV.size(); i++) {
            if (eventListRV.get(i).getPriceRangeList() != null)
                list.add(eventListRV.get(i).getPriceRangeList().get(0).getMaxPrice());

        }
        Collections.sort(list);
        if (list.size() > 0)
            return list.get(list.size() - 1);
        return 0;
    }

    private double getMinPrice() {
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < eventListRV.size(); i++) {
            if (eventListRV.get(i).getPriceRangeList() != null)
                list.add(eventListRV.get(i).getPriceRangeList().get(0).getMinPrice());
            else
                list.add(0.0);
        }
        Collections.sort(list);
        return list.get(0);

    }

    private void turnOffButtonVisbility() {
        btn_filter.setVisibility(View.INVISIBLE);

    }

    public static boolean CheckDates(String d1, String d2) {
        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
        boolean b = false;
        try {
            if (dfDate.parse(d1).before(dfDate.parse(d2))) {
                b = true;//If start date is before end date
            } else if (dfDate.parse(d1).equals(dfDate.parse(d2))) {
                b = true;//If two dates are equal
            } else {
                b = false; //If start date is after the end date
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }

}
