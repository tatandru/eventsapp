package com.example.eventsapp.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FavoritesRepository {
    private String DB_NAME = "db_task";
    private FavoritesDao favoritesDao;
    private LiveData<List<FavoriteEvents>> events;
    private FavoritesDatabase favoritesDatabase;
    private FavoriteEvents event;
    private Integer integer;

    FavoritesRepository(Application application) {
        favoritesDatabase = FavoritesDatabase.getInstance(application);
        favoritesDao = favoritesDatabase.favoritesDao();
        events = favoritesDao.getAllEvents();
    }

    LiveData<List<FavoriteEvents>> getAllEvents() {
        return events;
    }

    void insertEvent(FavoriteEvents event) {
        new InsertEventAsyncTask(favoritesDao).execute(event);
    }

    void updateEvent(FavoriteEvents event) {
        new UpdateEventAsyncTask(favoritesDao).execute(event);
    }

    void deleteEvent(FavoriteEvents event) {
        new DeleteEventAsyncTask(favoritesDao).execute(event);
    }
    int getRowCount(){
        return favoritesDao.getRowCount();
    }
    FavoriteEvents searchEventByName(String eventName){
        return favoritesDao.searchEventByName(eventName);
    }
    FavoriteEvents searchEventByStartDate(String startDate){
        return favoritesDao.searchEventByStartDate(startDate);
    }
    FavoriteEvents searchEventById(int id) {
        return favoritesDao.searchEventById(id);
    }

    private static class InsertEventAsyncTask extends AsyncTask<FavoriteEvents, Void, Void> {
        private FavoritesDao favoritesDao;

        private InsertEventAsyncTask(FavoritesDao favoritesDao) {
            this.favoritesDao = favoritesDao;
        }

        @Override
        protected Void doInBackground(FavoriteEvents... favoriteEvents) {
            favoritesDao.insertEvent(favoriteEvents[0]);
            return null;
        }
    }

    private static class UpdateEventAsyncTask extends AsyncTask<FavoriteEvents, Void, Void> {
        private FavoritesDao favoritesDao;

        private UpdateEventAsyncTask(FavoritesDao favoritesDao) {
            this.favoritesDao = favoritesDao;
        }

        @Override
        protected Void doInBackground(FavoriteEvents... favoriteEvents) {
            favoritesDao.updateEvent(favoriteEvents[0]);
            return null;
        }
    }

    private static class DeleteEventAsyncTask extends AsyncTask<FavoriteEvents, Void, Void> {
        private FavoritesDao favoritesDao;

        private DeleteEventAsyncTask(FavoritesDao favoritesDao) {
            this.favoritesDao = favoritesDao;
        }

        @Override
        protected Void doInBackground(FavoriteEvents... favoriteEvents) {
            favoritesDao.deleteEvent(favoriteEvents[0]);
            return null;
        }
    }
//    private static class GetRowCountAsyncTask extends AsyncTask<Void,Void,Integer>{
//        private FavoritesDao favoritesDao;
//        private GetRowCountAsyncTask(FavoritesDao favoritesDao){
//            this.favoritesDao=favoritesDao;
//        }
//
//        @Override
//        protected Integer doInBackground(Void... voids) {
//            return favoritesDao.getRowCount();
//        }
//    }
}