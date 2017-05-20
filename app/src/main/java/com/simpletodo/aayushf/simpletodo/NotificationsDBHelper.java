package com.simpletodo.aayushf.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by ayfadia on 5/10/17.
 */

public class NotificationsDBHelper extends SQLiteOpenHelper {
    public NotificationsDBHelper(Context context) {
        super(context, "NOTIFICATIONS.DB", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE NOTIFSTABLE(NOTIFPK INTEGER PRIMARY KEY AUTOINCREMENT, TASKPK INTEGER, TIME INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addNotifToDb(TaskNotification tn) {
        ContentValues cv = new ContentValues();
        cv.put("TASKPK", tn.taskpk);
        cv.put("TIME", tn.time);
        long pk = NotificationsDBHelper.this.getWritableDatabase().insert("NOTIFSTABLE", null, cv);
        return pk;
    }

    public ArrayList<TaskNotification> getAllNotifs() {
        Cursor c = NotificationsDBHelper.this.getWritableDatabase().rawQuery("SELECT * FROM NOTIFSTABLE ", null);
        c.moveToFirst();
        ArrayList<TaskNotification> notifs = new ArrayList<>();
        while (c.moveToNext()) {
            notifs.add(new TaskNotification(c.getInt(c.getColumnIndex("NOTIFPK")), c.getInt(c.getColumnIndex("TIME")), c.getInt(c.getColumnIndex("TASKPK"))));

        }
        return notifs;
    }

    public TaskNotification getNotifFromPK(int pk) {
        Cursor c = NotificationsDBHelper.this.getWritableDatabase().rawQuery("SELECT * FROM NOTIFSTABLE WHERE NOTIFPK =  " + String.valueOf(pk), null);
        c.moveToFirst();
        return new TaskNotification(c.getInt(c.getColumnIndex("NOTIFPK")), c.getInt(c.getColumnIndex("TIME")), c.getInt(c.getColumnIndex("TASKPK")));

    }
}
