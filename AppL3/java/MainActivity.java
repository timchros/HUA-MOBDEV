package hua.dit.mobdev.ec.appl3;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Points to the Automatically Generated XML File (Hello Message)
        // setContentView(R.layout.activity_main);
        /* TODO: Select either Linear or Constraint Layout */
        // Linear Layout
        // setContentView(R.layout.main_activity_linear_layout);
        // Constrain Layout
        setContentView(R.layout.main_activity_constrain_layout);

        // Show Widgets below bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Log Message - View it using Logcat (window)
        Log.d(TAG, "on-create");

        // Add OnClickListener to Login Button
        final Button login_button_widget = findViewById(R.id.login_button);
        login_button_widget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Login Button - onClick()");
                // Get Data
                final EditText username = findViewById(R.id.username);
                final EditText password = findViewById(R.id.password);
                final String msg = username.getText() + " , " + password.getText();
                // Log Data
                Log.i(TAG, msg);
                // Show Data on the Screen
                final TextView output = findViewById(R.id.output_area);
                output.setText(msg);
            }
        });

    } // End of onCreate(..)

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "on-start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "on-resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "on-pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "on-stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "on-destroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "on-RE-start");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Check Orientation and Log the appropriate message
        if ( newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i(TAG, "ORIENTATION - PORTRAIT");
        } else {
            Log.i(TAG, "ORIENTATION - OTHER !");
        }
    }

}