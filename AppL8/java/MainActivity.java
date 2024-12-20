package hua.dit.mobdev.ec.appl8;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import hua.dit.mobdev.ec.appl8.db.Sex;
import hua.dit.mobdev.ec.appl8.db.SexDao;
import hua.dit.mobdev.ec.appl8.db.User;
import hua.dit.mobdev.ec.appl8.db.UserDB;
import hua.dit.mobdev.ec.appl8.db.UserDao;
import hua.dit.mobdev.ec.appl8.db.UserWithSex;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

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

        Button button_1 = findViewById(R.id.button_1);
        button_1.setOnClickListener((v)->{
            // Go to another "page" for presenting List Data
            Intent showListIntent = new Intent(this, SecondActivityShowList.class);
            startActivity(showListIntent);
        }); // END OF button_1.setOnClickListener(..)

        Button button_2 = findViewById(R.id.button_2);
        button_2.setOnClickListener((v)->{
            // Show TimePickerDialog and Handle "OK" (time set) button
            new TimePickerDialogFragment().show(getSupportFragmentManager(), "Time Picker");
        }); // END OF button_2.setOnClickListener(..)

        Button button_3 = findViewById(R.id.button_3);
        button_3.setOnClickListener((v)->{
            new Thread(()->{
                final UserDB db = Room.databaseBuilder(getApplicationContext(),
                        UserDB.class, "user_dn.sqlite").build();
                // Sex Data
                SexDao sexDao = db.sexDao();
                if ( sexDao.getSexALL().isEmpty()) {
                    final List<Sex> sexList = new ArrayList<>();
                    sexList.add(new Sex("male"));
                    sexList.add(new Sex("female"));
                    sexList.add(new Sex("other"));
                    // Store Data and Show IDs
                    List<Long> sexIds = sexDao.insert(sexList);
                    Log.i(TAG, "Sex Data - sexIds: " + sexIds.size() + " :: " + sexIds);
                }
                // User Data
                UserDao userDao = db.userDao();
                // Insert a NEW User .. each time
                long user_sex_id = sexDao.getSexByName("male").id;
                long user_id = userDao.storeData(new User("Manolis", 24, user_sex_id));
                Log.i(TAG, "New User ID: " + user_id);
                // Show ALL ...
                List<User> userList = userDao.getUserALL();
                Log.i(TAG, userList.size() + " :: " + userList);
                List<UserWithSex> userwithsexList = userDao.getUserWithSexList();
                Log.i(TAG, userwithsexList.size() + " :: " + userwithsexList);
            }).start();
        }); // END OF button_3.setOnClickListener(..)

        Button button_4 = findViewById(R.id.button_4);
        button_4.setOnClickListener((v)->{
            new Thread(()->{
                // Insert User using Content Provider
                ContentValues cv = new ContentValues();
                cv.put("name", "Maria");
                cv.put("age", 20);
                cv.put("sex", "female");
                Uri uri = getContentResolver().insert(MyContentProvider.CONTENT_URI, cv);
                Log.i(TAG, "Content Provider - Data Inserted ! uri= " + uri);
                // Query Data using Content Provider
                Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI, null, null, null, null);
                Log.i(TAG, "Content Provider - Query Response:");
                if (cursor != null) {
                    cursor.moveToPosition(-1);
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(0);
                        int age = cursor.getInt(1);
                        String sex = cursor.getString(2);
                        Log.i(TAG, "User: " + name + " , " + age + " , " + sex);
                    }
                    cursor.close();
                }
            }).start();
        }); // END OF button_1.setOnClickListener(..)

    } // END OF onCreate(..)

}