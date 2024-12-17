package hua.dit.mobdev.ec.appl7.bg1;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.Executor;

public class MyWork {

    private static final String TAG = "MyWork";

    private final Executor executor;
    private final Handler handler;

    private final TextView textView;

    public MyWork(Executor executor, Handler handler, TextView textView) {
        this.executor = executor;
        this.handler = handler;
        this.textView = textView;
    }

    public void doMyWork() {
        this.executor.execute(()->{
            final int wait_sec = 20;
            Log.i(TAG, "doMyWork() START - wait... " + wait_sec  + " sec.");
            for (int i = 0; i < wait_sec; i++) {
                Log.i(TAG, "doMyWork() index: " + i);
                try { Thread.sleep(1000); } catch (Throwable t) {}
            }
            Log.i(TAG, "doMyWork() END");
            // Update UI
            updateAppUI("OK !!!");
        });
    }

    private void updateAppUI(String response) {
        this.handler.post(()->{
            this.textView.setText(response);
        });
    }

}
