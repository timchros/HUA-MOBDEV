package hua.dit.mobdev.ec.appl4;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity_ui);

        // Get Intent Data and Show them
        Intent intent = getIntent();
        if (intent != null) {
            // Get Data
            Uri data = intent.getData();
            Log.i(TAG, "Intent - Data:: " + data);
            Bundle bundle = intent.getExtras();
            final String user_name =
                    (bundle != null) ? bundle.getString("name") : null;
            final String user_sex =
                    (bundle != null) ? bundle.getString("sex") : null;
            final String msg = "Name: " + user_name + " and Sex: " + user_sex;
            // Log Message - View using Logcat
            Log.i(TAG, "Intent - Extras:: " + msg);
            // Show data on Activity Screen
            TextView textview = findViewById(R.id.second_activity_text);
            textview.setText(msg);
        }

        // OnClickListener using a Lambda Expression

        // Implicit Intent - Case 1
        findViewById(R.id.second_button).setOnClickListener((v) -> {
            Log.d(TAG, "Second Button Pressed !");
            // Start another App (Web Browser) using an Implicit Intent
            String url = "https://dit.hua.gr/";
            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setData(Uri.parse(url));
            startActivity(intent1);
        });

        // Implicit Intent - Case 2 - Check Intent Filters
        findViewById(R.id.third_button).setOnClickListener((v) -> {
            Log.d(TAG, "Third Button Pressed !");
            // Start another "local" Activity using an Implicit Intent
            Intent intent2 = new Intent(Intent.ACTION_SEND);
            startActivity(intent2);
        });

    } // End of method: onCreate

}