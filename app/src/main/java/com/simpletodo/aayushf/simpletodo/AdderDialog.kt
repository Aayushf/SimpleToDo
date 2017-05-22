package com.simpletodo.aayushf.simpletodo

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.CardView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView

import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by ayfadia on 4/25/17.
 */

class AdderDialog : DialogFragment() {
    internal lateinit  var mListener: AdderListener
    internal var colour: Int = 0
    internal lateinit var inflater: LayoutInflater
    internal lateinit var v: View
    internal lateinit var rl: RelativeLayout
    internal lateinit var cvadder: CardView
    internal var mayear: Int = 0
    internal var mamonth: Int = 0
    internal var maday: Int = 0
    internal var mpyear: Int = 0
    internal var mpmonth: Int = 0
    internal var mpday: Int = 0
    internal lateinit var dateadded: Date
    internal lateinit var datepending: Date
    internal lateinit var fbcolour: Button

    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        val c = Calendar.getInstance()
        mayear = c.get(Calendar.YEAR)
        mamonth = c.get(Calendar.MONTH)
        maday = c.get(Calendar.DAY_OF_MONTH)
        inflater = activity.layoutInflater
        v = inflater.inflate(R.layout.adder, null)
        val b = AlertDialog.Builder(activity)
        b.setView(v)
        Log.d("ADDERDIALOG", "OnCreate")
        val ettag = v.findViewById(R.id.cvettags) as EditText
        rl = v.findViewById(R.id.rlinsidecv) as RelativeLayout
        fbcolour = v.findViewById(R.id.colourfb) as Button
        val ettask = v.findViewById(R.id.cvettask) as EditText
        cvadder = v.findViewById(R.id.cvadder) as CardView
        Log.d("ADDERDIALOG", "OnAttach")
        val etpoints = v.findViewById(R.id.etpoints) as EditText
        val fbdone = v.findViewById(R.id.donefb) as Button
        fbdone.setOnClickListener {
            val t = Task(ettask.text.toString(), Integer.valueOf(etpoints.text.toString())!!, ettag.text.toString(), colour, false, dateadded, datepending)
            mListener.Done(t)
        }


        fbcolour.setOnClickListener {
            val d = ColourSelectionDialog()
            d.show(fragmentManager, "Choose Colour")
            Log.d("ADDERDIALOG", "FABCLICKED")
        }
        val fbdateadded = v.findViewById(R.id.dateaddedfb) as Button
        fbdateadded.setOnClickListener { showDateDialog(true) }
        val fbdatepending = v.findViewById(R.id.datependingfb) as Button
        fbdatepending.setOnClickListener { showDateDialog(false) }


        return b.create()

    }

    fun changeColour(colour: Int) {
        (v.findViewById(R.id.tvaddtask) as TextView).setTextColor(Task.colours[colour])
        cvadder.setCardBackgroundColor(Task.colours[colour])
        fbcolour.text = Task.coloursnames[colour]
        this.colour = colour
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)


        mListener = context as AdderListener


    }

    fun showDateDialog(add: Boolean) {
        val format = SimpleDateFormat("EEE - dd MMM ")

        val listener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            if (add) {
                mayear = year
                mamonth = month
                maday = dayOfMonth
                val bdateadded = v.findViewById(R.id.dateaddedfb) as Button

                dateadded = Date(mayear - 1900, mamonth, maday)
                bdateadded.text = "ADDED ON " + format.format(dateadded)
            } else {
                mpyear = year
                mpmonth = month
                mpday = dayOfMonth
                val bdatepending = v.findViewById(R.id.datependingfb) as Button

                datepending = Date(mpyear - 1900, mpmonth, mpday)
                bdatepending.text = "DUE ON " + format.format(datepending)


            }
            Log.d("DATEPICKER", "Year = " + year.toString())
        }
        val datePickerDialog = DatePickerDialog(activity, R.style.Theme_AppCompat_Light_Dialog, listener, mayear, mamonth, maday)
        datePickerDialog.show()
    }

    interface AdderListener {
        fun Done(t: Task)
    }

}