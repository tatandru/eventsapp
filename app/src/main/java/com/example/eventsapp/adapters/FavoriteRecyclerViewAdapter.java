package com.example.eventsapp.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eventsapp.R;
import com.example.eventsapp.database.FavoriteEvents;
import com.example.eventsapp.retrofitAPI.Event;

import java.util.ArrayList;
import java.util.List;


public class FavoriteRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteRecyclerViewAdapter.favoriteViewHolder> {
    private List<FavoriteEvents> favoriteEvents;
    private LayoutInflater inflater;
    private Context context;
    private ItemClickListener itemClickListener;

    public void setCategoryClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FavoriteRecyclerViewAdapter(List<FavoriteEvents> favoriteEvents, Context context) {
        this.favoriteEvents = new ArrayList<>(2);
        this.context = context;
    }

    public interface ItemClickListener {
        void onClick(ImageView item, FavoriteEvents event);
    }

    @NonNull
    @Override
    public favoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_row, parent, false);
        return new favoriteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final favoriteViewHolder holder, int position) {
        final FavoriteEvents event = favoriteEvents.get(position);
        holder.title.setText(event.getEventName());
        Glide.with(context).load(event.getUrlImg()).fitCenter().into(holder.eventImage);
        holder.eventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onClick(holder.eventImage, event);

                }
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onClick(holder.eventImage, event);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteEvents.size();
    }

    public void setFavoriteEvents(List<FavoriteEvents> events) {
        this.favoriteEvents = events;
        notifyDataSetChanged();
    }

    class favoriteViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final ImageView eventImage;


        favoriteViewHolder(final View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.rv_title);
            this.eventImage = itemView.findViewById(R.id.rv_image);

        }
    }
}
