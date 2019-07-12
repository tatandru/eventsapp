package com.example.eventsapp.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eventsapp.R;

import java.util.ArrayList;
import java.util.List;


public class CategoriesRVAdapter extends RecyclerView.Adapter<CategoriesRVAdapter.ItemsViewHolder> {

    private final LayoutInflater inflater;
    private List<String> categoriesList;
    private ItemClickListener itemClickListener;

    public void setCategoryClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public CategoriesRVAdapter(List<String> items, Context context) {
        this.categoriesList = new ArrayList<>(2);
        this.categoriesList = items;
        this.inflater = LayoutInflater.from(context);
    }

    public interface ItemClickListener {
        void onClick(String item);
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemsViewHolder(inflater.inflate(R.layout.favorite_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder citiesViewHolder, int i) {

        final String item = this.categoriesList.get(i);

        citiesViewHolder.txtItem.setText(item);


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
        //    private final TextView txtItem2;


        ItemsViewHolder(final View itemView) {
            super(itemView);
            this.locationItemRoot = itemView.findViewById(R.id.cl_favoriteItem);
            this.txtItem = itemView.findViewById(R.id.txt_favoriteItemTitle);
            //  this.txtItem2 = itemView.findViewById(R.id.rv_second_category);

        }
    }
}
