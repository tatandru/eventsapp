package com.example.eventsapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEvent(FavoriteEvents event);
    @Update
    void updateEvent(FavoriteEvents event);
    @Delete
    void deleteEvent(FavoriteEvents event);
    @Query("Select * from FavoriteEvents")
    LiveData<List<FavoriteEvents>> getAllEvents();
    @Query("SELECT COUNT(id) FROM FavoriteEvents")
    int getRowCount();
    @Query("Select * from FavoriteEvents where eventName=:eventName")
    FavoriteEvents searchEventByName(String eventName);
    @Query("Select * from FavoriteEvents where startDate=:startDate")
    FavoriteEvents searchEventByStartDate(String startDate);
    @Query("Select * from FavoriteEvents where id=:id")
    FavoriteEvents searchEventById(int id);
    @Query("Select startDate from FavoriteEvents")
    List<String> getAllStartDates();
}
