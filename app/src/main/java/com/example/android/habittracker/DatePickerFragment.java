package com.example.android.habittracker;

import android.app.Dialog;
import android.os.Bundle;
import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    int mYear=2018;
    int mMonth=0;
    int mDay=0;

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mYear = year;
        mMonth = month + 1;
        mDay = dayOfMonth;

        TextView textView = getActivity().findViewById(R.id.begin_date);
        textView.setText(new StringBuffer().append(mYear).append("-").append(new DecimalFormat("00").format(mMonth)).append("-").append(new DecimalFormat("00").format(mDay)).append(" "));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH);
        int day = ca.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

}
