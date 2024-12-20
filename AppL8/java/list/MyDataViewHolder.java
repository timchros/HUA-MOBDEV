package hua.dit.mobdev.ec.appl8.list;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import hua.dit.mobdev.ec.appl8.R;

public class MyDataViewHolder extends RecyclerView.ViewHolder {

    TextView data_string_1_textview;
    TextView data_string_2_textview;
    TextView data_integer_value_textview;
    View view;

    public MyDataViewHolder(@NonNull View itemView) {
        super(itemView);
        data_string_1_textview = itemView.findViewById(R.id.data_string_1);
        data_string_2_textview = itemView.findViewById(R.id.data_string_2);
        data_integer_value_textview = itemView.findViewById(R.id.data_integer_value);
        view = itemView;
    }

}
