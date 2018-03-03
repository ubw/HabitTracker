package com.example.android.habittracker;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DecimalFormat;
import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    int mHour=0;
    int mMinute=0;


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;


        TextView textView = getActivity().findViewById(R.id.do_it_time);
        textView.setText(new StringBuffer().append(new DecimalFormat("00").format(mHour)).append(":").append(new DecimalFormat("00").format(mMinute)).append(" "));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar ca = Calendar.getInstance();
        int hour = ca.get(Calendar.HOUR_OF_DAY);
        int minute = ca.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog=new TimePickerDialog(getActivity(), this, hour, minute,true);
        return timePickerDialog;
    }

}

