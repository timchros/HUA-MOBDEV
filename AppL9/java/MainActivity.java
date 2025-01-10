package hua.dit.mobdev.ec.appl9;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.CallLog;
import android.util.Log;
import android.widget.Button;
import android.Manifest;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import hua.dit.mobdev.ec.appl9.service.BoundService;
import hua.dit.mobdev.ec.appl9.service.StartedService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String date_pattern = "yyyy-MM-dd";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(date_pattern, Locale.ENGLISH);

    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /* Using a Started Service */

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener((v)->{
            Log.i(TAG, "Button 1 - For Started Service");
            startService(new Intent(this, StartedService.class));
        }); // END OF button1.setOnClickListener(..)

        /* Using a Bound Service */

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener((v)->{
            Log.i(TAG, "Button 2 - For Bound Service");
            startService(new Intent(this, BoundService.class));
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder iBinder) {
                    BoundService.MyBinder myBinder = (BoundService.MyBinder) iBinder;
                    myBinder.getService().doSth();
                }
                @Override
                public void onServiceDisconnected(ComponentName name) { }
            };
            bindService(new Intent(this, BoundService.class), serviceConnection, BIND_AUTO_CREATE);
            // unbindService(servConn);
        }); // END OF button2.setOnClickListener(..)

        /* Using Call Logs Build-In Content Provider */

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener((v)->{
            Log.i(TAG, "Button 3 - For Call Logs Content Provider");

            // Deal with Runtime Permissions
            int requestCode = 123;
            checkGetPermissions(requestCode, Manifest.permission.READ_CALL_LOG);

            // IMPORTANT NOTE
            // The first time executed... fails throwing FATAL EXCEPTION
            // since we DO NOT wait until user grant permission
            // Nevertheless, if we execute it again, it runs normally ...

            // Use CallLogs Provider
            Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                Log.i(TAG, "Calls-Count: " + cursor.getCount());
                // Cursor Indexes
                int phone_number_index = cursor.getColumnIndexOrThrow(CallLog.Calls.NUMBER);
                int call_type_index = cursor.getColumnIndexOrThrow(CallLog.Calls.TYPE);
                int call_date_index = cursor.getColumnIndexOrThrow(CallLog.Calls.DATE);
                int call_duration_index = cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION);
                // Iterate ...
                cursor.moveToPosition(-1);
                while (cursor.moveToNext()) {
                    String phone_number = cursor.getString(phone_number_index);
                    int call_type = cursor.getInt(call_type_index);
                    long call_date_time = cursor.getLong(call_date_index);
                    long call_duration = cursor.getLong(call_duration_index);
                    // Date
                    Date call_date = new Date(call_date_time);
                    String call_date_str = simpleDateFormat.format(call_date);
                    // Type
                    String call_type_name = null;
                    switch (call_type) {
                        case CallLog.Calls.OUTGOING_TYPE:
                            call_type_name = "OUTGOING";
                            break;
                        case CallLog.Calls.INCOMING_TYPE:
                            call_type_name = "INCOMING";
                            break;
                        case CallLog.Calls.MISSED_TYPE:
                            call_type_name = "MISSED";
                            break;
                        case CallLog.Calls.REJECTED_TYPE:
                            call_type_name = "REJECTED";
                            break;
                    }
                    // Data as One Line String
                    String call_str =
                        call_date_str + " : " + phone_number + " : " +
                        call_type_name + " : " + call_duration + " sec";
                    Log.i(TAG, call_str);
                }
                cursor.close();
            }
        }); // END OF button3.setOnClickListener(..)

    } // END OF onCreate(..)

    public void checkGetPermissions(int requestCode, String... permissions) {
        final List<String> permissionsList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PERMISSION_GRANTED) {
                permissionsList.add(permission);
            }
        }
        if (!permissionsList.isEmpty()) {
            String[] notGrantedPermissions = permissionsList.toArray(new String[0]);
            ActivityCompat.requestPermissions(this, notGrantedPermissions, requestCode);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}