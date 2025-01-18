package hua.dit.mobdev.ec.appl10.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import androidx.annotation.NonNull;

public class MyService extends Service {

    private static final String TAG = "L10-MyService";

    public class MyServiceHandler extends Handler {

        public MyServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.d(TAG, "handleMessage -- " + msg.arg2);
            super.handleMessage(msg);
            int n = 5;
            for (int i = 0; i < n; i++) {
                Log.d(TAG, "work-i:" + i);
                try { Thread.sleep(1000); } catch (Throwable t) {}
            }
            stopSelf(msg.arg1);
        }
    }

    private MyServiceHandler myServiceHandler = null;

    public MyService() {
    }

    @Override
    public void onCreate() {
        HandlerThread handlerThread = new HandlerThread("HandlerThread", Process.THREAD_PRIORITY_BACKGROUND);
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        myServiceHandler = new MyServiceHandler(looper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message message = myServiceHandler.obtainMessage();
        message.arg1 = startId;
        message.arg2 = 123;
        myServiceHandler.sendMessage(message);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}