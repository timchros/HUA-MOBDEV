package hua.dit.mobdev.ec.appl10.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyAppReceiver extends BroadcastReceiver {

    private static final String TAG = "L10-MyAppReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive - app Broadcasted Data");
    }

}