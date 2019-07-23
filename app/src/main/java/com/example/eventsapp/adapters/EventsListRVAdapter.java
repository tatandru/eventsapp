package com.example.eventsapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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


public class EventsListRVAdapter extends RecyclerView.Adapter<EventsListRVAdapter.ItemsViewHolder> implements Filterable {

    private final LayoutInflater inflater;
    private List<String> eventsList;
    private List<String> urlImagesList;
    private List<Integer> idEventList;
    private List<Event> eventList;
    private ArrayList<Event> copyEventList;
    private ItemClickListener itemClickListener;
    private Context context;
    private Embedded embedded;
    private Event event;
    private MyFilter mFilter;

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
        this.copyEventList=new ArrayList<>();
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
    @Override
    public Filter getFilter() {

        if (mFilter == null) {
            copyEventList.clear();
            copyEventList.addAll(this.eventList);
            mFilter = new EventsListRVAdapter.MyFilter(this, copyEventList);
        }
        return mFilter;

    }
    public Filter resetFilter()
    {

        if (mFilter == null) {
            copyEventList.clear();
            mFilter = new EventsListRVAdapter.MyFilter(this, copyEventList);
        }
        return mFilter;
    }

    private static class MyFilter extends Filter {

        private final EventsListRVAdapter myAdapter;
        private final ArrayList<Event> originalList;
        private final ArrayList<Event> filteredList;

        private MyFilter(EventsListRVAdapter myAdapter, ArrayList<Event> originalList) {
            this.myAdapter = myAdapter;
            this.originalList = originalList;
            this.filteredList = new ArrayList<Event>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            filteredList.clear();
            final FilterResults results = new FilterResults();
            if (charSequence.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                if(charSequence.length()>2) {
                    final String filterPattern = charSequence.toString().toLowerCase().trim();
                    for (Event item : originalList) {
                        if (item.getEventName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }

                }
            }

            results.values = filteredList;
            results.count = filteredList.size();
            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            myAdapter.eventList.clear();
            myAdapter.eventList.addAll((ArrayList<Event>) filterResults.values);
            myAdapter.notifyDataSetChanged();

        }

    }
}
