package com.simpletodo.aayushf.simpletodo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

/**
 * Created by ayfadia on 5/10/17.
 */

class NotificationsDBHelper(context: Context) : SQLiteOpenHelper(context, "NOTIFICATIONS.DB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE NOTIFSTABLE(NOTIFPK INTEGER PRIMARY KEY AUTOINCREMENT, TASKPK INTEGER, TIME INTEGER)")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    fun addNotifToDb(tn: TaskNotification): Long {
        val cv = ContentValues()
        cv.put("TASKPK", tn.taskpk)
        cv.put("TIME", tn.time)
        val pk = this@NotificationsDBHelper.writableDatabase.insert("NOTIFSTABLE", null, cv)
        return pk
    }

    val allNotifs: ArrayList<TaskNotification>
        get() {
            val c = this@NotificationsDBHelper.writableDatabase.rawQuery("SELECT * FROM NOTIFSTABLE ", null)
            c.moveToFirst()
            val notifs = ArrayList<TaskNotification>()
            while (c.moveToNext()) {
                notifs.add(TaskNotification(c.getInt(c.getColumnIndex("NOTIFPK")), c.getInt(c.getColumnIndex("TIME")).toLong(), c.getInt(c.getColumnIndex("TASKPK"))))

            }
            return notifs
        }

    fun getNotifFromPK(pk: Int): TaskNotification {
        val c = this@NotificationsDBHelper.writableDatabase.rawQuery("SELECT * FROM NOTIFSTABLE WHERE NOTIFPK =  " + pk.toString(), null)
        c.moveToFirst()
        return TaskNotification(c.getInt(c.getColumnIndex("NOTIFPK")), c.getInt(c.getColumnIndex("TIME")).toLong(), c.getInt(c.getColumnIndex("TASKPK")))

    }
}
