package hua.dit.mobdev.ec.appl4;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_ui);

        // OnClickListener using an Anonymous Class (for testing purposes)

        findViewById(R.id.first_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Data from Fields
                final EditText edittext = findViewById(R.id.user_name);
                final String user_name = edittext.getText().toString();
                final Spinner spinner = findViewById(R.id.user_sex);
                final String user_sex = spinner.getSelectedItem().toString();
                // Prepare Message
                final String msg = "User: "+ user_name + " , Sex: " + user_sex;
                // Log Message - View using Logcat
                Log.d(TAG, msg);
                // Show message on Screen (for a short period of time)
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                // Go to next Screen using an Explicit Intent
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.setData(Uri.parse("other-data"));
                intent.putExtra("name", user_name);
                intent.putExtra("sex", user_sex);
                startActivity(intent);
            }
        });

    }

}