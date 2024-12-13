package hua.dit.mobdev.ec.appl6;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import hua.dit.mobdev.ec.appl6.db.MyDatabase;
import hua.dit.mobdev.ec.appl6.db.MySingleton;
import hua.dit.mobdev.ec.appl6.db.MyTable;
import hua.dit.mobdev.ec.appl6.db.MyTableDAO;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int REQUEST_CODE = 123;

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

        final EditText input_text = findViewById(R.id.input_text);
        final CheckBox check_box = findViewById(R.id.check_box);
        final RadioGroup radio_group = findViewById(R.id.radio_group);
        final TextView output_text = findViewById(R.id.output_text);

        /* PART 1 - THREADS */

        final Button button_1 = findViewById(R.id.button_1);
        button_1.setOnClickListener((v) -> {

            // A Time-consuming Task (e.g., wait 20 sec ) on Main Thread
            // Un-comment .. for testing purposes - ** Causes a problem **
            // int main_thread_wait_sec = 20;
            // for (int i = 0; i <main_thread_wait_sec ; i++) {
            //     Log.d(TAG, "wait-seconds: " + i);
            //     try { Thread.sleep(1000); } catch (Throwable t){}
            // }

            new Thread(()->{

                // Get User Data
                final String user_text = input_text.getText().toString();
                final boolean user_is_adult =  check_box.isChecked();
                final int selected_radio_button_id = radio_group.getCheckedRadioButtonId();
                final RadioButton selected_rb = findViewById(selected_radio_button_id);
                final String user_sex_selected = selected_rb.getText().toString();

                // A Time-consuming Task (e.g., wait 20 sec ) on a Background Thread
                // Un-comment .. for testing purposes - NOT a problem
                int background_thread_wait_sec = 20;
                for (int i = 0; i <background_thread_wait_sec ; i++) {
                    Log.d(TAG, "wait-seconds: " + i);
                    try { Thread.sleep(1000); } catch (Throwable t){}
                }

                // UI Response (e.g., data given - for testing purposes)
                StringBuilder sb = new StringBuilder();
                sb.append("Text: ").append(user_text);
                sb.append(", is_adult: ").append(user_is_adult);
                sb.append(", sex: ").append(user_sex_selected);

                // Log data for testing purposes
                Log.i(TAG, sb.toString());

                // Attempt to directly update UI from this thread ** causes an problem **
                // output_text.setText(sb.toString());

                // Dictate UI thread to update UI
                output_text.post(()->{ output_text.setText(sb.toString());});

            }).start();

        }); // END OF button_1 - setOnClickListener(..)

        /* PART 2 - FILES */

        Button button_2 = findViewById(R.id.button_2);
        button_2.setOnClickListener((v) -> {

            // Create a Text File in an App-specific Folder
            final String txt_msg = "This is a simple message\nstored in the file created";
            new Thread(()->{
                try {
                    FileOutputStream fos = openFileOutput("txt_file_1.txt", MODE_PRIVATE);
                    fos.write(txt_msg.getBytes());
                    fos.flush();
                    fos.close();
                    Log.i(TAG, "File 1 successfully created !");
                } catch (Throwable t) {
                    throw new RuntimeException("File 1 processing problem", t);
                }
            }).start();

            // Create a Text File in a public Folder (Share it with other Apps) - part 1
            // Create an Intent
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TITLE, "txt_file_2.txt");
            startActivityForResult(intent, REQUEST_CODE);

        }); // EBD OF button_2 - setOnClickListener(..)

        /* PART 3 - DATABASES */

        Button button_3 = findViewById(R.id.button_3);
        button_3.setOnClickListener((v) -> {

            new Thread(()->{
                Log.d(TAG, "Accessing a SQLite DB using ROOM ...");

                // DB
                MyDatabase db = MySingleton.getInstance(getApplicationContext()).getDb();
                MyTableDAO myTableDAO = db.myTableDAO();
                // INSERT
                MyTable obj = new MyTable("My Data 2", 123, true);
                myTableDAO.storeMyTableObj(obj);
                Log.i(TAG, "Data successfully Stored !");
                // SELECT
                List<MyTable> myTableObjlist = myTableDAO.getMyTableObjALL();
                Log.i(TAG, "Data successfully Retrieved ! - " + myTableObjlist.size());
                Log.i(TAG, "List:: " + myTableObjlist);

                // Note: The above code cannot be executed in main thread !

            }).start();

        }); // EBD OF button_3 - setOnClickListener(..)

    } // END OF onCreate(..)

    // Create a Text File in a public Folder (Share it with other Apps) - part 2
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(resultCode, resultCode, resultData);

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            if (resultData != null) {
                final Uri uri = resultData.getData();
                // Perform operations on the document using its URI.

                new Thread(() -> {
                    try {
                        final String txt_msg = "This is ANOTHER simple message\nstored in the file created";
                        OutputStream os = getContentResolver().openOutputStream(uri);
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
                        bw.write(txt_msg);
                        bw.flush();
                        bw.close();
                        Log.i(TAG, "File 2 successfully created !");
                    } catch (Throwable t) {
                        throw new RuntimeException("File 2 processing problem", t);
                    }
                }).start();
            }
        }
    } // END OF onActivityResult(..)

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "on-destroy()");
    }

}