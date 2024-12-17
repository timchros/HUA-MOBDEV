package hua.dit.mobdev.ec.appl7;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.room.Room;

import java.io.Closeable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hua.dit.mobdev.ec.appl7.db1.MyDbHelper;
import hua.dit.mobdev.ec.appl7.db2.MyDatabase;

public class MySingleton implements Closeable {

    private static volatile MySingleton instance;

    private final MyDbHelper myDbHelper;
    private final MyDatabase myRoomDatabase;
    private final ExecutorService executorService;
    private final Handler handler;

    private MySingleton(Context context) {
        // Initialize DB1 - Helper
        this.myDbHelper =
            new MyDbHelper(context, "test_db1.sqlite3");
        // Initialize DB2 - Room Database
        this.myRoomDatabase =
            Room.databaseBuilder(context, MyDatabase.class, "test_db2.sqlite3").build();
        // Executor - Threads Pool
        this.executorService =
            Executors.newFixedThreadPool(4);
        // Handler - Main Thread "TODOs" List
        this.handler =
            HandlerCompat.createAsync(Looper.getMainLooper());
    }

    public MyDbHelper getMyDbHelper() {
        return myDbHelper;
    }

    public MyDatabase getMyRoomDatabase() {
        return myRoomDatabase;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public Handler getHandler() {
        return handler;
    }

    public void close() {
        if (this.myDbHelper != null) myDbHelper.close();
        if (this.myRoomDatabase != null) myRoomDatabase.close();
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
