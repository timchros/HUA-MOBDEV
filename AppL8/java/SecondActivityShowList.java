package hua.dit.mobdev.ec.appl8;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hua.dit.mobdev.ec.appl8.list.MyData;
import hua.dit.mobdev.ec.appl8.list.MyDataAdapter;

public class SecondActivityShowList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_list);

        // List
        List<MyData> myDataList = createDataList();

        // Prepare Recycler View - Set Adapter with List Data
        RecyclerView list_recycler_view = findViewById(R.id.list_recycler_view);
        list_recycler_view.setAdapter(new MyDataAdapter(myDataList));
        list_recycler_view.setLayoutManager( new LinearLayoutManager(this));
    }

    private List<MyData> createDataList() {
        final List<MyData> list = new ArrayList<>();
        int n = 50;
        for (int i = 0; i < n; i++) {
            list.add(new MyData("Str1-" + i, "Str2-"+i, i));
        }
        return list;
    }

}