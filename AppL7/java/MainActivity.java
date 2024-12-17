package hua.dit.mobdev.ec.appl7;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import hua.dit.mobdev.ec.appl7.bg1.MyWork;
import hua.dit.mobdev.ec.appl7.bg2.MyWorker;
import hua.dit.mobdev.ec.appl7.db1.MyDbHelper;
import hua.dit.mobdev.ec.appl7.db2.MyDao;
import hua.dit.mobdev.ec.appl7.db2.MyDatabase;
import hua.dit.mobdev.ec.appl7.db2.MyTable;
import hua.dit.mobdev.ec.appl7.db2.MyTableSubset;

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

        EditText string_value_text = findViewById(R.id.string_value);
        EditText integer_value_text = findViewById(R.id.integer_value);
        TextView output_area = findViewById(R.id.output_area);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener((v)->{
            new Thread(()->{
                final MyDbHelper myDbHelper = MySingleton.getInstance(getApplicationContext()).getMyDbHelper();
                SQLiteDatabase db = myDbHelper.getWritableDatabase();
                // UI data
                String the_string_value = string_value_text.getText().toString();
                Integer the_integer_value = Integer.getInteger(integer_value_text.getText().toString().trim());
                // INSERT
                ContentValues data = new ContentValues();
                data.put("string_value", the_string_value);
                data.put("integer_value", the_integer_value);
                long row_id = db.insert("my_table", null, data);
                Log.d(TAG, "DB1 - Data Inserted - Row ID: " + row_id);
                // QUERY
                Cursor cursor = db.rawQuery("select * from my_table", null);
                final int id_index = cursor.getColumnIndex("id");
                final int string_value_index = cursor.getColumnIndex("string_value");
                final int integer_value_index = cursor.getColumnIndex("integer_value");
                cursor.moveToPosition(-1);
                while(cursor.moveToNext()) {
                    int id = cursor.getInt(id_index);
                    String string_value = cursor.getString(string_value_index);
                    int integer_value = cursor.getInt(integer_value_index);
                    // Data as String
                    String str = "DB1 - row: { id: " + id +
                            " , string_value: " + string_value +
                            " , integer_value: " + integer_value + " }";
                    Log.i(TAG, str);
                }
                cursor.close();
            }).start();
        }); // END OF button.setOnClickListener(..)

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener((v)->{
            new Thread(()->{
                final MyDatabase myDatabase = MySingleton.getInstance(getApplicationContext()).getMyRoomDatabase();
                final MyDao myDao = myDatabase.myDao();
                // UI DATA
                String the_string_value = string_value_text.getText().toString();
                Integer the_integer_value = Integer.getInteger(integer_value_text.getText().toString().trim());
                // INSERT DATA
                long row_id = myDao.insertDataVersion2(new MyTable(the_string_value, the_integer_value, new Date()));
                Log.d(TAG, "DB2 - Data inserted - row_id: " + row_id);
                // GET DATA
                List<MyTable> myTableObjList1 = myDao.getMyTableALL();
                Log.d(TAG, "DB2 - ALL Records: " + myTableObjList1.size() + " ::" + myTableObjList1);
                // Horizontal Data Filtering
                int min_id = 5;
                List<MyTable> myTableObjList2 = myDao.getMyTableAllWithGreaterIdValue(min_id);
                Log.d(TAG, "DB2 - Case 1: " + myTableObjList2.size() + " ::" + myTableObjList2);
                // Vertical Data Filtering
                List<MyTableSubset> myTableSubsetObjList = myDao.getMyTableAll2Columns();
                Log.d(TAG, "DB2 - Case 2: " + myTableSubsetObjList.size() + " ::" + myTableSubsetObjList);
            }).start();
        }); // END OF button2.setOnClickListener(..)

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener((v)->{
            MySingleton mySingleton  = MySingleton.getInstance(getApplicationContext());
            Executor executor = mySingleton.getExecutorService();
            Handler handler = mySingleton.getHandler();
            new MyWork(executor, handler, output_area).doMyWork();
        }); // END OF button3.setOnClickListener(..)

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener((v)->{
            OneTimeWorkRequest workRequest =
                    new OneTimeWorkRequest.Builder(MyWorker.class).build();
            final String workUID = "WORK-ID-123";
            final ExistingWorkPolicy workPolicy = ExistingWorkPolicy.KEEP;
            WorkManager.getInstance(getApplicationContext())
                    .enqueueUniqueWork(workUID, workPolicy, workRequest);
        }); // END OF button4.setOnClickListener(..)

    } // END OF onCreate(..)

    @Override
    protected void onDestroy() {
        MySingleton.getInstance(getApplicationContext()).close();
        super.onDestroy();
    }

}