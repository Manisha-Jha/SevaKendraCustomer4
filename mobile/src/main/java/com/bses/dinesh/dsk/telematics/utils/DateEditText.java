package com.bses.dinesh.dsk.telematics.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateEditText {
    private Activity mActivity;
    private TextView mTextView;
    private Calendar mCalendar;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private OnDateSelectedListener onDateSelectedListener;

    public DateEditText(Activity mActivity, TextView mEditText,
                        Context onDateSelectedListener) {
        this.mActivity = mActivity;
        this.mTextView = mEditText;
        this.mCalendar = Calendar.getInstance();
        setCalendarDefaultDate();
        this.onDateSelectedListener = (OnDateSelectedListener) onDateSelectedListener;
        setupEditTextClickListener();
    }

    private void setCalendarDefaultDate() {
        mCalendar.setTimeInMillis(new Date().getTime());
    }

    private void setupEditTextClickListener() {
        // mEditText.setFocusable(false);

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                onDateSelected(datePicker, i, i1, i2);
            }
        };

//        mTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new DatePickerDialog(mActivity, onDateSetListener,
//                        mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
//                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, onDateSetListener,
                mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void onDateSelected(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, monthOfYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String formattedDate = sdf.format(mCalendar.getTime());
        onDateSelectedListener.OnDateSelected(mTextView, formattedDate);
    }

    public interface OnDateSelectedListener {
        void OnDateSelected(TextView mTextView, String newDate); //forwards date in 'dd/mm/yyyy' format.
    }
}
