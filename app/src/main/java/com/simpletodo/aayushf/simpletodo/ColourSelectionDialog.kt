package com.simpletodo.aayushf.simpletodo

import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log

/**
 * Created by ayfadia on 4/24/17.
 */

class ColourSelectionDialog : DialogFragment() {
    var colourSelected: Int = 0
    internal lateinit var mListener: ColourSelectionDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as ColourSelectionDialogListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Choose Colour")
        builder.setPositiveButton("Choose") { dialog, which -> mListener.onDialogPositiveClick(this@ColourSelectionDialog) }
        Log.d("COLOURSELECTOR", "ONCREATE")

        val ocl = DialogInterface.OnClickListener { dialog, which ->
            colourSelected = which
            mListener.onDialogPositiveClick(this@ColourSelectionDialog)
        }

        builder.setItems(Task.coloursnames, ocl)


        return builder.create()

    }

    interface ColourSelectionDialogListener {
        fun onDialogPositiveClick(d: DialogFragment)
    }
}
