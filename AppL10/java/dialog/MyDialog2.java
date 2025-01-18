package hua.dit.mobdev.ec.appl10.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import hua.dit.mobdev.ec.appl10.R;

public class MyDialog2 extends DialogFragment {

    private static final String TAG = "L10-MyDialog2";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Convert XML to View Java Object
        View view = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        // Create Alert Dialog using AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
            .setView(view)
            .setPositiveButton("Submit", (d,v)->{
                Log.d(TAG, "Submit");
            })
            .setNegativeButton("Cancel", (d,v)->{
                Log.d(TAG, "Cancel");
            });
        return builder.create();
    }

}
