package hua.dit.mobdev.ec.appl8.list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hua.dit.mobdev.ec.appl8.R;


public class MyDataAdapter extends RecyclerView.Adapter<MyDataViewHolder> {

    private static final String TAG = "MyDataAdapter";

    private final List<MyData> list;

    public MyDataAdapter(List<MyData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cardView = inflater.inflate(R.layout.activity_second_element, parent, false);
        return new MyDataViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyDataViewHolder holder, int position) {
        MyData data = list.get(position);
        holder.data_string_1_textview.setText(data.str1);
        holder.data_string_2_textview.setText(data.str2);
        holder.data_integer_value_textview.setText("" + data.intValue);
        holder.view.setOnClickListener((v)->{
            Log.w(TAG, "List - Element Selected: " + holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
