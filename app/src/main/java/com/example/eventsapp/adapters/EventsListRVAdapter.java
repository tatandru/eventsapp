package com.example.eventsapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventsapp.R;
import com.example.eventsapp.retrofitAPI.Embedded;
import com.example.eventsapp.retrofitAPI.Event;

import java.util.ArrayList;
import java.util.List;


public class EventsListRVAdapter extends RecyclerView.Adapter<EventsListRVAdapter.ItemsViewHolder> {

    private final LayoutInflater inflater;
    private List<String> eventsList;
    private List<String> urlImagesList;
    private List<Integer> idEventList;
    private List<Event> eventList;
    private ItemClickListener itemClickListener;
    private Context context;
    private Embedded embedded;
    private Event event;


    public void setCategoryClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public EventsListRVAdapter(Event event, Embedded embedded, List<Event> eventList, List<String> items, List<String> urlImages, Context context) {
        this.eventsList = new ArrayList<>(2);
        this.urlImagesList = new ArrayList<>(2);
        this.idEventList = new ArrayList<>(2);
        this.embedded = embedded;
        this.eventList = eventList;
        this.event = event;
        this.eventsList = items;
        this.idEventList = idEventList;
        this.urlImagesList = urlImages;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public interface ItemClickListener {
        void onClick(String item, Event event);
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemsViewHolder(inflater.inflate(R.layout.upcoming_event_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder citiesViewHolder, int i) {

        final Event event = this.eventList.get(i);
        final String item = event.getEventName();
        final String url = event.getImgList().get(i).getImageURL();


        Log.e("EventsListRVAdapter", "Event : > " + item);
        Glide.with(context).load(url).into(citiesViewHolder.imgEvent);
        citiesViewHolder.txtItem.setText(item);


        citiesViewHolder.locationItemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onClick(item, event);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {
        private final View locationItemRoot;
        private final TextView txtItem;
        private final ImageView imgEvent;


        ItemsViewHolder(final View itemView) {
            super(itemView);
            this.locationItemRoot = itemView.findViewById(R.id.cl_upcoming_root_item);
            this.txtItem = itemView.findViewById(R.id.txt_upcoming_event_item);
            this.imgEvent = itemView.findViewById(R.id.img_upcoming_event);

        }
    }
}
