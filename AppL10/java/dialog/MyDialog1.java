package hua.dit.mobdev.ec.appl10.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class MyDialog1 extends DialogFragment {

    private static final String TAG = "L10-MyDialog1";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Create Alert Dialog using AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
            .setTitle("Alert Title")
            .setMessage("Alert Message")
            .setPositiveButton("YES", (d,v)->{
                Log.d(TAG, "YES");
            })
            .setNegativeButton("NO", (d,v)->{
                Log.d(TAG, "NO");
            });
        return builder.create();
    }

}
