package hua.dit.mobdev.ec.appl10.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MySystemReceiver extends BroadcastReceiver {

    private static final String TAG = "L10-MySystemReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isAirplaneModeEnabled = intent.getBooleanExtra("state", false);
        Log.d(TAG, "onReceive - isAirplaneModeEnabled: " + isAirplaneModeEnabled);
    }

}