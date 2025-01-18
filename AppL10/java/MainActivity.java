package hua.dit.mobdev.ec.appl10;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import hua.dit.mobdev.ec.appl10.dialog.MyDialog1;
import hua.dit.mobdev.ec.appl10.dialog.MyDialog2;
import hua.dit.mobdev.ec.appl10.fragment.MyFragment2;
import hua.dit.mobdev.ec.appl10.receiver.MyAppReceiver;
import hua.dit.mobdev.ec.appl10.receiver.MySystemReceiver;
import hua.dit.mobdev.ec.appl10.service.MyForegroundService;
import hua.dit.mobdev.ec.appl10.service.MyService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "L10-MainActivity";

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

        /* Set Fragment 2 in Activity Layout UI */

        final MyFragment2 myFragment2 = new MyFragment2();
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_2, myFragment2);
        fragmentTransaction.commit();

        /* For testing purposes */

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Alert Title");
//        builder.setMessage("Alert Message");
//        builder.setPositiveButton("YES", (d,v)->{
//            Log.d(TAG, "YES");
//        });
//        builder.setNegativeButton("NO", (d,v)->{
//            Log.d(TAG, "NO");
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();


        /* Buttons for showing Dialogs */

        findViewById(R.id.dialog_button_1).setOnClickListener((v)->{
            new MyDialog1().show(getSupportFragmentManager(), "MyDialog1");
        });

        findViewById(R.id.dialog_button_2).setOnClickListener((v)->{
            new MyDialog2().show(getSupportFragmentManager(), "MyDialog2");
        });


        /* Buttons for starting Services */

        findViewById(R.id.dialog_button_3).setOnClickListener((v)->{
            startService(new Intent(this, MyService.class));
        });

        /* For testing purposes */

//        final String CHANNEL_ID = "C123";
//        NotificationChannel notificationChannel = new NotificationChannel(
//                CHANNEL_ID, "Channel 1", NotificationManager.IMPORTANCE_DEFAULT);
//        getSystemService(NotificationManager.class)
//                .createNotificationChannel(notificationChannel);
//
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(this, CHANNEL_ID);
//        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
//        notificationBuilder.setContentTitle("Notification Title ");
//        notificationBuilder.setContentText("Notification Text 1");
//        final int NOTIFICATION_ID = 123;
//        getSystemService(NotificationManager.class).notify(NOTIFICATION_ID, notificationBuilder.build());

        findViewById(R.id.dialog_button_4).setOnClickListener((v)->{
            startForegroundService(new Intent(this, MyForegroundService.class));
        });


        /* Buttons for Files */

        findViewById(R.id.dialog_button_5).setOnClickListener((v)->{
            // Read JSON File from app raw folder
            try {
                final InputStream jsonFileIS = getResources().openRawResource(R.raw.test_json);
                final BufferedReader reader = new BufferedReader(new InputStreamReader(jsonFileIS));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line); sb.append("\n");
                }
                reader.close();
                final String text = sb.toString();
                final JSONObject jo = new JSONObject(text);
                final String size = (String) jo.get("size");
                Log.d(TAG, "size: " + size);
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        });

        findViewById(R.id.dialog_button_6).setOnClickListener((v)->{
            // Get Permission and Write JSON File to Downloads
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
        });

    } // END OF onCreate(..)

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "requestCode: " + requestCode + " , grantResults: " + grantResults[0]);
        try{
            // Create JSON
            JSONObject jo = new JSONObject();
            jo.put("course", "mobile dev");
            jo.put("lecture", 10);
            JSONArray ja = new JSONArray();
            ja.put("Giannis");
            ja.put("Maria");
            ja.put("Dimitris");
            jo.put("participants", ja);
            Log.d(TAG, jo.toString());
            // Step 1 - Create File
            ContentValues cv = new ContentValues();
            cv.put(MediaStore.MediaColumns.DISPLAY_NAME, "lecture_10.json");
            cv.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain");
            cv.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
            Uri fileUri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, cv);
            // Step 2 - Write Content to File
            if (fileUri != null) {
                final OutputStream os = getContentResolver().openOutputStream(fileUri, "w");
                if (os != null) {
                    os.write(jo.toString().getBytes());
                    os.flush();
                    os.close();
                    Log.d(TAG, "File successfully create !");
                }
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    } // END OF onRequestPermissionsResult(..)

    private final MySystemReceiver mySystemReceiver = new MySystemReceiver();
    private final MyAppReceiver myAppReceiver = new MyAppReceiver();

    @Override
    protected void onStart() {
        super.onStart();
        // System Broadcast Receiver Registration
        IntentFilter intentFilter = new IntentFilter("android.intent.action.AIRPLANE_MODE");
        registerReceiver(mySystemReceiver, intentFilter, RECEIVER_EXPORTED);
        // App Broadcast Receiver Registration
        IntentFilter intentFilter2 = new IntentFilter(Intent.ACTION_SEND);
        registerReceiver(myAppReceiver, intentFilter2, RECEIVER_NOT_EXPORTED);

    } // END OF onStart()

    @Override
    protected void onResume() {
        super.onResume();
        // Broadcast a "App" Message for testing purposes
        sendBroadcast(new Intent(Intent.ACTION_SEND));
    } // END OF onResume()

    @Override
    protected void onStop() {
        super.onStop();
        // System Broadcast Receiver Un-registration
        unregisterReceiver(mySystemReceiver);
        // App Broadcast Receiver Un-registration
        unregisterReceiver(myAppReceiver);
    } // END OF onStop()

}
