package com.example.eventsapp.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EventsViewModel extends AndroidViewModel {
    private FavoritesRepository repository;
    private LiveData<List<FavoriteEvents>> allEvents;

    public EventsViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoritesRepository(application);
        allEvents = repository.getAllEvents();
    }

    public void insert(FavoriteEvents event) {
        repository.insertEvent(event);
    }

    public void update(FavoriteEvents event) {
        repository.updateEvent(event);
    }

    public void delete(FavoriteEvents event) {
        repository.deleteEvent(event);
    }

    public LiveData<List<FavoriteEvents>> getAllEvents() {
        return allEvents;
    }

    public int getRowCount() {
        return repository.getRowCount();
    }
}
