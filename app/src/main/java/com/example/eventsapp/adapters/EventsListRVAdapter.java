package com.example.eventsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventsapp.R;

import java.util.ArrayList;
import java.util.List;


public class EventsListRVAdapter extends RecyclerView.Adapter<EventsListRVAdapter.ItemsViewHolder> {

    private final LayoutInflater inflater;
    private List<String> categoriesList;
    private List<String> urlImagesList;
    private ItemClickListener itemClickListener;
    private Context context;

    public void setCategoryClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }



    public EventsListRVAdapter(List<String> items, List<String> urlImages, Context context) {
        this.categoriesList = new ArrayList<>(2);
        this.urlImagesList = new ArrayList<>(2);
        this.categoriesList = items;
        this.urlImagesList = urlImages;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public interface ItemClickListener {
        void onClick(String item);
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemsViewHolder(inflater.inflate(R.layout.upcoming_event_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder citiesViewHolder, int i) {

        final String item = this.categoriesList.get(i);
        final String url = this.urlImagesList.get(i);

        citiesViewHolder.txtItem.setText(item);

        Glide.with(context).load(url).into(citiesViewHolder.categoryIcon);


        citiesViewHolder.locationItemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onClick(item);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {
        private final View locationItemRoot;
        private final TextView txtItem;
        private final ImageView categoryIcon;


        ItemsViewHolder(final View itemView) {
            super(itemView);
            this.locationItemRoot = itemView.findViewById(R.id.cl_upcoming_root_item);
            this.txtItem = itemView.findViewById(R.id.txt_upcoming_event_item);
            this.categoryIcon = itemView.findViewById(R.id.img_upcoming_event);

        }
    }
}
