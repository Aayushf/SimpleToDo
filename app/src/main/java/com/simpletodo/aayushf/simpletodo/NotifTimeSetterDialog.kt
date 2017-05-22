package com.simpletodo.aayushf.simpletodo

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DialogFragment
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker

import java.util.Date

/**
 * Created by ayfadia on 5/10/17.
 */

class NotifTimeSetterDialog : DialogFragment() {
    internal var myear: Int = 0
    internal var mmonth: Int = 0
    internal var mday: Int = 0
    internal var mhour: Int = 0
    internal var mminute: Int = 0
    internal var taskpk: Int = 0
    internal var c = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        val v = LayoutInflater.from(activity).inflate(R.layout.notiftimepicker, null)
        val b = AlertDialog.Builder(activity)
        b.setView(v)
        val datesetter = v.findViewById(R.id.datentd) as Button
        val timesetter = v.findViewById(R.id.timentd) as Button
        datesetter.setOnClickListener { showDateDialog() }
        timesetter.setOnClickListener { showTimeDialog() }
        val donebutton = v.findViewById(R.id.donentd) as Button
        donebutton.setOnClickListener {
            val d = Date(myear, mmonth, mday, mhour, mminute)
            val time = d.time

            val listener = activity as NotifTimeSetterListener
            listener.timeSet(time, taskpk)
        }
        Log.d("NOTIFTS", "NOTIFTS CREATED")


        return b.create()

    }

    fun showDateDialog() {
        val l = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myear = year - 1970
            mmonth = month
            mday = dayOfMonth
        }

        val dpd = DatePickerDialog(activity, l, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
        dpd.show()

    }

    fun showTimeDialog() {
        val l = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            mhour = hourOfDay
            mminute = minute
        }
        val tpd = TimePickerDialog(activity, l, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false)
        tpd.show()
    }

    fun setTaskPK(taskpk: Int) {
        this.taskpk = taskpk

    }

    interface NotifTimeSetterListener {
        fun timeSet(time: Long, taskpk: Int)
    }
}
