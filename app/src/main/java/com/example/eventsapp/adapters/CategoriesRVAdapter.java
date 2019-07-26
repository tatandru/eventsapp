package com.example.eventsapp.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.eventsapp.R;
import com.example.eventsapp.retrofitAPI.Event;

import java.util.ArrayList;
import java.util.List;


public class CategoriesRVAdapter extends RecyclerView.Adapter<CategoriesRVAdapter.ItemsViewHolder> {

    private final LayoutInflater inflater;
    private List<String> categoriesList;
    private List<String> urlImagesList;
    private List<Event> allEventList;
    private ItemClickListener itemClickListener;
    private Context context;

    public void setCategoryClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public CategoriesRVAdapter(List<String> items, List<String> urlImages, List<Event> eventCounterList, Context context) {
        this.categoriesList = new ArrayList<>(2);
        this.urlImagesList = new ArrayList<>(2);
        this.allEventList = new ArrayList<>(2);
        this.categoriesList = items;
        this.urlImagesList = urlImages;
        this.allEventList = eventCounterList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public interface ItemClickListener {
        void onClick(String item);
    }

    public void resetEventCounterList() {
        allEventList.clear();
    }

    public List<String> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<String> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public List<Event> getAllEventList() {
        return allEventList;
    }

    public void setAllEventList(List<Event> allEventList) {
        this.allEventList = allEventList;
    }

    public List<String> getUrlImagesList() {
        return urlImagesList;
    }

    public void setUrlImagesList(List<String> urlImagesList) {
        this.urlImagesList = urlImagesList;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemsViewHolder(inflater.inflate(R.layout.category_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemsViewHolder citiesViewHolder, int i) {


        final String item = this.categoriesList.get(i);
        final String url = this.urlImagesList.get(i);
        int counter = 0;


        for (int j = 0; j < allEventList.size(); j++) {
            Event e = allEventList.get(j);
            for (int z = 0; z < e.getClassficationList().size(); z++) {

                String suCateg = e.getClassficationList().get(z).getGenre().getEventGenre();

                if (suCateg.equals(item)) {
                    counter++;
                }
            }
        }
        citiesViewHolder.txtItem.setText(item);
        if (counter == 1) {
            citiesViewHolder.tvEventCounter.setText(counter + " event found");
        } else {
            citiesViewHolder.tvEventCounter.setText(counter + " events found");
        }


        Glide.with(context)
                .load(url)
                .fitCenter()
                .into(citiesViewHolder.categoryIcon);

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
        private final TextView tvEventCounter;


        ItemsViewHolder(final View itemView) {
            super(itemView);
            this.locationItemRoot = itemView.findViewById(R.id.cl_categoryRootItem);
            this.txtItem = itemView.findViewById(R.id.txt_categoryItem);
            this.categoryIcon = itemView.findViewById(R.id.img_categoryItem);
            this.tvEventCounter = itemView.findViewById(R.id.tv_events_counter);

        }
    }
}
