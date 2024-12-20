package hua.dit.mobdev.ec.appl8;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerDialogFragment extends DialogFragment implements android.app.TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "TimePickerDialogFrag.";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the current time as the default values for the picker.
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        // Create a new instance of TimePickerDialog and return it.
        TimePickerDialog. OnTimeSetListener listener = this;
        boolean is24HourFormat = DateFormat.is24HourFormat(getActivity());
        return new TimePickerDialog(getActivity(), listener, hour, minute, is24HourFormat);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.i(TAG, "Selected Time: hourOfDay= " + hourOfDay + " , " + minute);
    }

}
