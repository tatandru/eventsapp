package com.example.eventsapp;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.eventsapp.retrofitAPI.Embedded;
import com.example.eventsapp.retrofitAPI.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FilterFragment extends Fragment {


    private TextView tv_start_date;
    private TextView tv_end_date;
    private TextView tv_to;
    private SeekBar seekBar;
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
    private boolean isFiltred=false;

    public interface DataPassListener{
        public void passData(String data);
    }
    DataPassListener mCallback;
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try
        {
            mCallback = (DataPassListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+ " must implement OnImageClickListener");
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_events, container, false);
        tv_start_date = view.findViewById(R.id.tv_start_date);
        tv_end_date = view.findViewById(R.id.tv_end_date);
        btn_filter = view.findViewById(R.id.btn_filter);
        tv_to = view.findViewById(R.id.tv_to);
        seekBar = view.findViewById(R.id.seekBar_price);
        tv_max_price = view.findViewById(R.id.tv_price_max);
        tv_min_price = view.findViewById(R.id.tv_price_min);

        clickOnStartDate();




        final Bundle bundle = getArguments();


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
            tv_max_price.setText(getMaxPrice() + "");
            tv_min_price.setText(getMinPrice() + "");
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
                turnOnButtonVisbility();
            }
        });

        seekBar.setMin((int) getMinPrice());
        seekBar.setMax((int) getMaxPrice());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                turnOnButtonVisbility();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                System.out.println(seekBar.getProgress());
                filterPrice = seekBar.getProgress();

            }
        });


        // Suppose that when a button clicked second FragmentB will be inflated
        // some data on FragmentA will pass FragmentB
        // Button passDataButton = (Button).........

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.getId() == R.id.passDataButton) {
                    mCallback.passData("Text to pass FragmentB");
                }
            }
        });
    }
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventListRV=filterAfterPrice(eventListRV);
                try {
              /*      isFiltred=true;
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    UpcomingEventsFragment eventsFragment = new UpcomingEventsFragment();
                    Bundle bundle1 = new Bundle();
                   // bundle1.putInt("seekBar_value", seekBar.getProgress());
                    System.out.println(isFiltred);
                    bundle1.putBoolean("isFiltred",isFiltred);
                   // bundle1.putByteArray("filtred_list",HomepageFragment.object2Bytes(eventListRV));
                    eventsFragment.setArguments(bundle1);
                    transaction.replace(R.id.fragment_container, eventsFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();*/
                    getFragmentManager().popBackStack();



                } catch (Exception e) {

                }
            }
        });

        return view;

    }
    private void printLis(List<Event>list)
    {
        for (Event e:list) {
            System.out.println("____>"+e);

        }
    }
    private  List<Event>filterByDate(List<Event> list)
    {
        List<Event> eventList = new ArrayList<>();
        for (Event e : list) {

            if (equals(e.getDates().getStartDate().getDayStartEvent())) {

                if (filterPrice >= e.getPriceRangeList().get(0).getMaxPrice()) {
                    eventList.add(e);
                }
            } else {
                eventList.add(e);
            }
        }
        return eventList;
    }


    private List<Event> filterAfterPrice(List<Event> list) {
        List<Event> eventList = new ArrayList<>();
        for (Event e : list) {

            if (e.getPriceRangeList()!=null) {
                if (filterPrice >= e.getPriceRangeList().get(0).getMaxPrice()) {
                    eventList.add(e);
                }
            } else {
                eventList.add(e);
            }
        }
        return eventList;
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
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dp_end_date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
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
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dp_start_date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
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


}
