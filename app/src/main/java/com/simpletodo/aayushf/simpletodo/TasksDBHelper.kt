package com.simpletodo.aayushf.simpletodo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.text.SimpleDateFormat
import android.util.Log
import java.text.ParseException
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.Date
import java.util.HashSet

/**
 * Created by ayfadia on 4/17/17.
 */

class TasksDBHelper(context: Context) : SQLiteOpenHelper(context, "TASKS.db", null, 1) {
    internal var sdf = SimpleDateFormat("yyyy-MM-dd")

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE TASKSTABLE(PRIMK INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT, POINTS INTEGER, CATEGORY TEXT, TASKCOLOUR INTEGER, DATEADDED TEXT, DATEPENDING TEXT, DONE BOOLEAN  )")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    fun addTaskToDB(t: Task) {

        val `val` = ContentValues()
        `val`.put("TASK", t.task)
        `val`.put("POINTS", t.points)
        `val`.put("CATEGORY", t.category)
        `val`.put("DONE", t.done)
        `val`.put("TASKCOLOUR", t.colour)
        `val`.put("DATEADDED", sdf.format(t.dateadded))
        `val`.put("DATEPENDING", sdf.format(t.datepending))

        val db = this.writableDatabase
        val i = db.insert("TASKSTABLE", null, `val`)
        db.close()
        Log.d("HELPER", i.toString())


    }

    fun setDone(primk: Int, done: Boolean) {
        val doneint: Int
        if (done) {
            doneint = 1
        } else {
            doneint = 0
        }
        this.writableDatabase.execSQL("UPDATE TASKSTABLE SET DONE =" + doneint.toString() + " WHERE PRIMK = " + primk.toString())
        Log.d("HELPER", "SETDONE CALLED" + doneint.toString() + done.toString())
    }

    fun deleteTaskFromDB(primk: Int) {
        this.writableDatabase.execSQL("DELETE FROM TASKSTABLE WHERE PRIMK = " + primk.toString())


    }

    @SuppressLint("NewApi")
    fun getArrayListFromCursor(c: Cursor): ArrayList<Task> {
        var t: Task
        val tasks = ArrayList<Task>()
        c.moveToFirst()
        if (c.count != 0) {
            do {
                var dateadded: Date? = null
                try {
                    dateadded = sdf.parse(c.getString(c.getColumnIndex("DATEADDED")))
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                var datepending: Date? = null
                try {
                    datepending = sdf.parse(c.getString(c.getColumnIndex("DATEPENDING")))
                } catch (e: ParseException) {
                    e.printStackTrace()
                }


                t = Task(c.getString(c.getColumnIndex("TASK")), c.getInt(c.getColumnIndex("POINTS")), c.getString(c.getColumnIndex("CATEGORY")), c.getInt(c.getColumnIndex("PRIMK")), c.getInt(c.getColumnIndex("TASKCOLOUR")), c.getInt(c.getColumnIndex("DONE")) == 1, dateadded!!, datepending!!)
                Log.d("GetArrayFromCursor", (c.getInt(c.getColumnIndex("DONE")) == 1).toString())
                tasks.add(t)

            } while (c.moveToNext())
        }

        Log.d("HELPER", "TASKS RETURNED " + tasks.size.toString())
        return tasks


    }

    val allTasks: ArrayList<Task>
        get() {
            val c = this@TasksDBHelper.writableDatabase.rawQuery("SELECT * FROM TASKSTABLE", null)
            return getArrayListFromCursor(c)
        }

    fun getPendingTasks(tagtodisplay: String): ArrayList<Task> {
        val c = this@TasksDBHelper.writableDatabase.rawQuery("SELECT * FROM TASKSTABLE WHERE DONE = 0 AND CATEGORY LIKE '%$tagtodisplay%'", null)
        Log.d("GETALLTASKS: " + tagtodisplay, c.count.toString())
        return getSortedArrayList(getArrayListFromCursor(c))

    }

    fun getDoneTasks(tagtodisplay: String): ArrayList<Task> {
        val c = this@TasksDBHelper.writableDatabase.rawQuery("SELECT * FROM TASKSTABLE WHERE DONE = 1 AND CATEGORY LIKE '%$tagtodisplay%'", null)
        Log.d("GETALLTASKS:" + tagtodisplay, c.count.toString())
        return getArrayListFromCursor(c)

    }

    fun getSortedArrayList(unsorted: ArrayList<Task>): ArrayList<Task> {
        Collections.sort(unsorted) { o1, o2 -> o1.datepending.compareTo(o2.datepending) }
        return unsorted


    }

    fun getDone(primk: Int): Boolean {
        val c = this@TasksDBHelper.writableDatabase.rawQuery("SELECT * FROM TASKSTABLE WHERE PRIMK = " + primk.toString(), null)
        c.moveToFirst()
        return c.getInt(c.getColumnIndex("DONE")) > 0
    }

    fun getTaskFromPK(primk: Int): Task {
        val c = this@TasksDBHelper.writableDatabase.rawQuery("SELECT * FROM taskstable WHERE PRIMK = " + primk.toString(), null)
        c.moveToFirst()
        var dateadded: Date? = null
        try {
            dateadded = sdf.parse(c.getString(c.getColumnIndex("DATEADDED")))
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        var datepending: Date? = null
        try {
            datepending = sdf.parse(c.getString(c.getColumnIndex("DATEPENDING")))
        } catch (e: ParseException) {
            e.printStackTrace()
        }


        return Task(c.getString(c.getColumnIndex("TASK")), c.getInt(c.getColumnIndex("POINTS")), c.getString(c.getColumnIndex("CATEGORY")), c.getInt(c.getColumnIndex("PRIMK")), c.getInt(c.getColumnIndex("TASKCOLOUR")), c.getInt(c.getColumnIndex("DONE")) == 1, dateadded!!, datepending!!)


    }

    val allCategories: Array<String>
        get() {
            val str = HashSet<String>()
            val c = this@TasksDBHelper.writableDatabase.rawQuery("SELECT * FROM TASKSTABLE", null)
            c.moveToFirst()
            while (c.moveToNext()) {
                val strings = c.getString(c.getColumnIndex("CATEGORY")).split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (s in strings) {
                    if (str.contains(s)) {
                        break
                    }
                    str.add(s)

                }
            }
            return str.toTypedArray()
        }

    val totalPoints: Int
        get() {
            var t = ArrayList<Task>()
            t = getDoneTasks("")
            var points = 0
            for (task in t) {
                points += task.points

            }
            return points
        }

    fun getDonePointsOfTag(tag: String): Int {
        var t = ArrayList<Task>()
        t = getDoneTasks(tag)
        var points = 0
        for (task in t) {
            points += task.points

        }
        return points

    }

    fun getPendingPointsOfTag(tag: String): Int {
        var t = ArrayList<Task>()
        t = getPendingTasks(tag)
        var points = 0
        for (task in t) {
            points += task.points

        }
        return points

    }

    fun getDoneTasksOfTag(tag: String): Int {
        var t = ArrayList<Task>()
        t = getDoneTasks(tag)
        var points = 0
        for (task in t) {
            points += 1

        }
        return points


    }

    fun getPendingTasksOfTag(tag: String): Int {
        var t = ArrayList<Task>()
        t = getPendingTasks(tag)
        var points = 0
        for (task in t) {
            points += 1

        }
        return points


    }

    val tagsList: ArrayList<TagItem>
        get() {
            val t = ArrayList<TagItem>()
            val str = allCategories
            for (s in str) {
                val ti = TagItem(s, getDoneTasksOfTag(s), getPendingTasksOfTag(s), 0, 0)
                t.add(ti)
            }
            Log.d("GETTAGLIST", "CALLED")
            return t
        }

}
