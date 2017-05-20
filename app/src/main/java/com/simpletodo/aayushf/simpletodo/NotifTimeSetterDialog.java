package com.simpletodo.aayushf.simpletodo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Date;

/**
 * Created by ayfadia on 5/10/17.
 */

public class NotifTimeSetterDialog extends DialogFragment {
    int myear, mmonth, mday, mhour, mminute;
    int taskpk;
    Calendar c = Calendar.getInstance();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.notiftimepicker, null);
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        b.setView(v);
        Button datesetter = (Button) v.findViewById(R.id.datentd);
        Button timesetter = (Button) v.findViewById(R.id.timentd);
        datesetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        timesetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });
        Button donebutton = (Button) v.findViewById(R.id.donentd);
        donebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date d = new Date(myear, mmonth, mday, mhour, mminute);
                long time = d.getTime();

                NotifTimeSetterListener listener = (NotifTimeSetterListener) getActivity();
                listener.timeSet(time, taskpk);

            }
        });
        Log.d("NOTIFTS", "NOTIFTS CREATED");


        return b.create();

    }

    public void showDateDialog() {
        DatePickerDialog.OnDateSetListener l = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myear = year - 1970;
                mmonth = month;
                mday = dayOfMonth;
            }
        };

        DatePickerDialog dpd = new DatePickerDialog(getActivity(), l, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dpd.show();

    }

    public void showTimeDialog() {
        TimePickerDialog.OnTimeSetListener l = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mhour = hourOfDay;
                mminute = minute;
            }
        };
        TimePickerDialog tpd = new TimePickerDialog(getActivity(), l, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
        tpd.show();
    }

    public void setTaskPK(int taskpk) {
        this.taskpk = taskpk;

    }

    public interface NotifTimeSetterListener {
        public void timeSet(long time, int taskpk);
    }
}
