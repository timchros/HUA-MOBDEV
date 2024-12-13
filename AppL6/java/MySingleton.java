package hua.dit.mobdev.ec.appl6.db;

import android.content.Context;

import androidx.room.Room;

public class MySingleton {

    private static volatile  MySingleton instance;

    private MyDatabase db;

    private MySingleton(Context context) {
        db = Room.databaseBuilder(context, MyDatabase.class, "my-database.sqlite").build();
    }

    public MyDatabase getDb() {
        return  db;
    }

    public static MySingleton getInstance(Context context) {
        if (instance == null) {
            synchronized (MySingleton.class) {
                if (instance == null) {
                    instance = new MySingleton(context);
                }
            }
        }
        return instance;
    }

}
