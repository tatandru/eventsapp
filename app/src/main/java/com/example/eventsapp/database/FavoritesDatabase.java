package com.example.eventsapp.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {FavoriteEvents.class}, version = 1)
public abstract class FavoritesDatabase extends RoomDatabase {
    private static String DATABASE_NAME="asdf";
    private static FavoritesDatabase instance;

    public abstract FavoritesDao favoritesDao();

    public static synchronized FavoritesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.inMemoryDatabaseBuilder(context, FavoritesDatabase.class)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateFavoritesAsyncTask(instance).execute();
        }
    };
    private static class PopulateFavoritesAsyncTask extends AsyncTask<Void,Void,Void>{
        private FavoritesDao favoritesDao;
        private PopulateFavoritesAsyncTask(FavoritesDatabase database){
            favoritesDao= database.favoritesDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            favoritesDao.insertEvent(new FavoriteEvents(0,"","asdf","asd","asds"));
            return null;
        }
    }
}
