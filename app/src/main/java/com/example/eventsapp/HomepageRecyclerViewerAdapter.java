package com.example.eventsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class HomepageRecyclerViewAdapter extends RecyclerView.Adapter<HomepageRecyclerViewAdapter.HomepageRecyclerViewHolder> {
    private final LayoutInflater inflater;

    public HomepageRecyclerViewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HomepageRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomepageRecyclerViewHolder(inflater.inflate(R.layout.homepage_category_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomepageRecyclerViewHolder holder, int position) {
        holder.firstCategory.setText("");
        holder.secondCategory.setText("");
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    class HomepageRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView firstCategory;
        TextView secondCategory;

        HomepageRecyclerViewHolder(@NonNull View view) {
            super(view);
            this.firstCategory = view.findViewById(R.id.rv_first_category);
            this.secondCategory = view.findViewById(R.id.rv_second_category);
        }
    }
}
