package hua.dit.mobdev.ec.appl9.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BoundService extends Service {

    private static final String TAG = "BoundService";

    public class MyBinder extends Binder {
        public BoundService getService() {
            return BoundService.this;
        }
    }

    private final MyBinder myBinder = new MyBinder();

    public BoundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        return myBinder;
    }

    public void doSth() {
        Log.d(TAG, "doSth()");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind()");
        return true;
    }

}