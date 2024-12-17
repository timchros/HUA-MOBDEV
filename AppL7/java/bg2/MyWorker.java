package hua.dit.mobdev.ec.appl7.bg2;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

    private static final String TAG = "MyWorker";

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // A task that should be executed in the background - no UI update is necessary
        final int wait_sec = 10;
        Log.i(TAG, "doWork() START - wait ... " + wait_sec + " sec");
        for (int i = 0; i < wait_sec; i++) {
            Log.i(TAG, "doWork() index: " + i);
            try { Thread.sleep(1000); } catch (Throwable t) {}
        }
        Log.i(TAG, "doWork() END");
        return Result.success();
    }

}
