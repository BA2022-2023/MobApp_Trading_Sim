package net.ictcampus.mobapp_trading_sim;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public synchronized static AppDatabase getInstance(Context context){
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "contactapp")
                .allowMainThreadQueries()
                .build();
    }
    public abstract UserDao userDao();
}