package com.example.eventsapp.database;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public class FavoritesRepository {
    private String DB_NAME = "db_task";
    private FavoritesDao favoritesDao;
    private LiveData<List<FavoriteEvents>> events;
    private FavoritesDatabase favoritesDatabase;

    FavoritesRepository(Application application) {
        favoritesDatabase = FavoritesDatabase.getInstance(application);
        favoritesDao = favoritesDatabase.favoritesDao();
        events = favoritesDao.getAllEvents();
    }
    LiveData<List<FavoriteEvents>> getAllEvents(){
        return events;
    }
    public String searchEvent(List<FavoriteEvents> events,String searchedEvent){
        for(int i =0;i<events.size();i++){
            if(events.get(i).getEventName().equals(searchedEvent)){
                System.out.println(searchedEvent);
            }
        }
        return searchedEvent;
    }

    void insertEvent(FavoriteEvents event){
        new InsertEventAsyncTask(favoritesDao).execute(event);
    }
    void updateEvent(FavoriteEvents event){
        new UpdateEventAsyncTask(favoritesDao).execute(event);
    }
    void deleteEvent(FavoriteEvents event){
        new DeleteEventAsyncTask(favoritesDao).execute(event);
    }

    private static class InsertEventAsyncTask extends AsyncTask<FavoriteEvents,Void,Void>{
        private FavoritesDao favoritesDao;
        private InsertEventAsyncTask(FavoritesDao favoritesDao ){
            this.favoritesDao=favoritesDao;
        }
        @Override
        protected Void doInBackground(FavoriteEvents... favoriteEvents) {
            favoritesDao.insertEvent(favoriteEvents[0]);
            return null;
        }
    }
    private static class UpdateEventAsyncTask extends AsyncTask<FavoriteEvents,Void,Void>{
        private FavoritesDao favoritesDao;
        private UpdateEventAsyncTask(FavoritesDao favoritesDao ){
            this.favoritesDao=favoritesDao;
        }
        @Override
        protected Void doInBackground(FavoriteEvents... favoriteEvents) {
            favoritesDao.updateEvent(favoriteEvents[0]);
            return null;
        }
    }
    private static class DeleteEventAsyncTask extends AsyncTask<FavoriteEvents,Void,Void>{
        private FavoritesDao favoritesDao;
        private DeleteEventAsyncTask(FavoritesDao favoritesDao ){
            this.favoritesDao=favoritesDao;
        }
        @Override
        protected Void doInBackground(FavoriteEvents... favoriteEvents) {
            favoritesDao.deleteEvent(favoriteEvents[0]);
            return null;
        }
    }
}