package com.simpletodo.aayushf.simpletodo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ayfadia on 4/25/17.
 */

public class AdderDialog extends DialogFragment {
    public interface AdderListener{
        public void Done(Task t);
    }

    AdderListener mListener;
    int colour;
    LayoutInflater inflater;
    View v;
    RelativeLayout rl;
    CardView cvadder;
    int mayear, mamonth, maday, mpyear, mpmonth, mpday;
    Date dateadded, datepending;
    Button fbcolour;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        mayear = c.get(Calendar.YEAR);
        mamonth = c.get(Calendar.MONTH);
        maday = c.get(Calendar.DAY_OF_MONTH);
        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.adder, null);
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        b.setView(v);
        Log.d("ADDERDIALOG", "OnCreate");
        final EditText ettag = (EditText)v.findViewById(R.id.cvettags);
        rl = (RelativeLayout)v.findViewById(R.id.rlinsidecv);
        fbcolour = (Button)v.findViewById(R.id.colourfb);
        final EditText ettask = (EditText)v.findViewById(R.id.cvettask);
        cvadder = (CardView)v.findViewById(R.id.cvadder);
        Log.d("ADDERDIALOG", "OnAttach");
        final EditText etpoints = (EditText)v.findViewById(R.id.etpoints);
        Button fbdone = (Button)v.findViewById(R.id.donefb);
        fbdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Task t = new Task(ettask.getText().toString(), Integer.valueOf(etpoints.getText().toString()), ettag.getText().toString(), colour, false, dateadded, datepending);
              mListener.Done(t);

            }
        });


        fbcolour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment d = new ColourSelectionDialog();
                d.show(getFragmentManager(), "Choose Colour");
                Log.d("ADDERDIALOG", "FABCLICKED");

            }
        });
        final Button fbdateadded = (Button)v.findViewById(R.id.dateaddedfb);
        fbdateadded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(true);



            }
        });
        Button fbdatepending = (Button)v.findViewById(R.id.datependingfb);
        fbdatepending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(false);


            }
        });





        return b.create();

    }
    public void changeColour(int colour){
        rl.setBackgroundColor(Task.colours[colour]);
        cvadder.setCardBackgroundColor(Task.colours[colour]);
        fbcolour.setText(Task.coloursnames[colour]);
        this.colour = colour;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);



        mListener = (AdderListener)context;


    }
    public void showDateDialog(final boolean add){
        final SimpleDateFormat format = new SimpleDateFormat("EEE - dd MMM ");

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(add){
                    mayear = year;
                mamonth = month;
                maday = dayOfMonth;
                    Button bdateadded = (Button)v.findViewById(R.id.dateaddedfb);

                dateadded = new Date(mayear-1900, mamonth, maday);
                bdateadded.setText("ADDED ON "+format.format(dateadded));
                }
                else{
                    mpyear = year;
                    mpmonth = month;
                    mpday = dayOfMonth;
                                        Button bdatepending = (Button)v.findViewById(R.id.datependingfb);

                    datepending = new Date(mpyear-1900, mpmonth, mpday);
                    bdatepending.setText("DUE ON "+format.format(datepending));


                }
                Log.d("DATEPICKER", "Year = "+String.valueOf(year));
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.Theme_AppCompat_Light_Dialog, listener, mayear, mamonth, maday);
        datePickerDialog.show();
    }

}