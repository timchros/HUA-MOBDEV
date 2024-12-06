package hua.dit.mobdev.ec.appl5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Widget
        final EditText default_location = findViewById(R.id.second_activity_input_field);

        // Update Widget with default location (if any) from Shared Preferences
        final SharedPreferences sp = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
        default_location.setText(sp.getString("default_location", ""));

        // BUTTON Listener
        final Button button = findViewById(R.id.second_activity_button);
        button.setOnClickListener((v) -> {
            // Get new Location
            String new_default_location = default_location.getText().toString();
            // Store new Location in Shared Preferences
            SharedPreferences.Editor editor =  sp.edit();
            editor.putString("default_location", new_default_location);
            editor.apply();
            // Go back
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            startActivity(intent);
        }); // END OF button.setOnClickListener(..)

    }

}