package hua.dit.mobdev.ec.appl5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String SHOW_LOC_COUNT_KEY = "show_location_count";

    private int show_location_count = 0;

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

        // Activity State (if any) - saved in MEMORY
        // Used .. Activity re-created (e.g., screen rotation)
        if (savedInstanceState != null && savedInstanceState.containsKey(SHOW_LOC_COUNT_KEY)) {
            // Load State Data
            show_location_count = savedInstanceState.getInt(SHOW_LOC_COUNT_KEY);
            // Log
            Log.d(TAG, "Activity state (click count) LOADED !");
        }

        // Widget
        final EditText location = findViewById(R.id.main_activity_input_field);

        // Shared Preferences (if any) - saved on DISK
        final SharedPreferences sp = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
        String default_location = sp.getString("default_location", "");

        // Update Widget
        location.setText(default_location);

        // BUTTON 1 Listener
        Button button1 = findViewById(R.id.main_activity_button_1);
        button1.setOnClickListener((v) -> {
            String location_str = location.getText().toString().trim();
            if (location_str.isEmpty()) {
                // Show message on screen
                Toast.makeText(this, "Location NOT specified yet !", Toast.LENGTH_LONG).show();
            } else {
                // Update internal Activity Status
                show_location_count += 1;
                // Log Status (for testing purposes)
                Log.i(TAG, "show_location_count:" + show_location_count);
                // Show Location on Google Map
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                Uri location_uri = Uri.parse("geo:0,0?q="+location_str);
                intent1.setData(location_uri);
                startActivity(intent1);
            }
        }); // End of button1.setOnClickListener(...)

        // BUTTON 2 Listener
        Button button2 = findViewById(R.id.main_activity_button_2);
        button2.setOnClickListener((v) -> {
            Intent intent2 = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent2);
        }); // End of button2.setOnClickListener(...)

        // BUTTON 3 Listener
        Button button3 = findViewById(R.id.main_activity_button_3);
        button3.setOnClickListener((v) -> {
            // READ FILE
            try {
                InputStream is = getResources().openRawResource(R.raw.text_file);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line;
                while ( (line = br.readLine()) != null ) {
                    sb.append(line).append(" ");
                }
                Log.d(TAG, "FILE TEXT: " + sb);
                br.close();
                is.close();
            } catch (Throwable t) {
                Log.e(TAG, "READ FILE error", t);
            }
            // WRITE FILE
            try {
                FileOutputStream fos = openFileOutput("new_text_file.txt", MODE_PRIVATE);
                String msg = "Button Pressed: " + show_location_count;
                fos.write(msg.getBytes());
                fos.close();
                Log.i(TAG, "FILE successfully created !");
            } catch (Throwable t) {
                Log.e(TAG, "WRITE FILE error", t);
            }
        }); // End of button3.setOnClickListener(...)

    } // END OF onCreate(..)

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Store State Data
        outState.putInt(SHOW_LOC_COUNT_KEY, show_location_count);
        // Log
        Log.d(TAG, "Activity state (click count) saved ...");
    }

}