package com.simpletodo.aayushf.simpletodo;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by ayfadia on 4/24/17.
 */

public class ColourSelectionDialog extends DialogFragment {
    public interface ColourSelectionDialogListener{
        public void onDialogPositiveClick(DialogFragment d);
    }
    ColourSelectionDialogListener mListener;
    public int colourselected;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (ColourSelectionDialogListener)context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Colour");
        builder.setPositiveButton("Choose", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogPositiveClick(ColourSelectionDialog.this);
            }
        });
        Log.d("COLOURSELECTOR", "ONCREATE");

        DialogInterface.OnClickListener ocl = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                colourselected = which;
                mListener.onDialogPositiveClick(ColourSelectionDialog.this);



            }
        };

        builder.setItems(Task.coloursnames,ocl );








        return builder.create();

    }
    public int getColourSelected(){
        return colourselected;
    }
}
