package com.example.eventsapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoritesDao {
    @Insert
    void insertEvent(FavoriteEvents event);
    @Update
    void updateEvent(FavoriteEvents event);
    @Delete
    void deleteEvent(FavoriteEvents event);
    @Query("Select * from FavoriteEvents")
    LiveData<List<FavoriteEvents>> getAllEvents();

}
