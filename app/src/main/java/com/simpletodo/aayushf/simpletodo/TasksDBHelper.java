package com.simpletodo.aayushf.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by ayfadia on 4/17/17.
 */

public class TasksDBHelper extends SQLiteOpenHelper {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");



    public TasksDBHelper(Context context) {
        super(context, "TASKS.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TASKSTABLE(PRIMK INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT, PRIORITY INTEGER, CATEGORY TEXT, TASKCOLOUR INTEGER, DATEADDED TEXT, DATEPENDING TEXT, DONE BOOLEAN  )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
    public void addTaskToDB(Task t){

        ContentValues val = new ContentValues();
        val.put("TASK", t.task);
        val.put("PRIORITY", t.priority);
        val.put("CATEGORY", t.category);
        val.put("DONE", t.done);
        val.put("TASKCOLOUR", t.colour);
        val.put("DATEADDED", sdf.format(t.dateadded));
        val.put("DATEPENDING", sdf.format(t.datepending));

        SQLiteDatabase db = this.getWritableDatabase();
        long i = db.insert("TASKSTABLE", null, val);
        db.close();
        Log.d("HELPER", String.valueOf(i));


    }
    public void setDone(int primk, boolean done){
        int doneint;
        if (done){
            doneint = 1;
        }else{
            doneint = 0;
        }
        this.getWritableDatabase().execSQL("UPDATE TASKSTABLE SET DONE =" +String.valueOf(doneint)+ " WHERE PRIMK = " + String.valueOf(primk));
        Log.d("HELPER", "SETDONE CALLED"+String.valueOf(doneint)+String.valueOf(done));
    }
    public void deleteTaskFromDB(int primk){
        this.getWritableDatabase().execSQL("DELETE FROM TASKSTABLE WHERE PRIMK = "+String.valueOf(primk));



    }
    public ArrayList<Task> getArrayListFromCursor(Cursor c) {
        Task t;
        ArrayList<Task> tasks = new ArrayList<Task>();
        c.moveToFirst();
        while(c.moveToNext()){
            Date dateadded = null;
            try {
                dateadded = sdf.parse(c.getString(c.getColumnIndex("DATEADDED")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date datepending = null;
            try {
                datepending = sdf.parse(c.getString(c.getColumnIndex("DATEPENDING")));
            } catch (ParseException e) {
                e.printStackTrace();
            }


            t = new Task(c.getString(c.getColumnIndex("TASK")), c.getInt(c.getColumnIndex("PRIORITY")), c.getString(c.getColumnIndex("CATEGORY")), c.getInt(c.getColumnIndex("PRIMK")), c.getInt(c.getColumnIndex("TASKCOLOUR")), ((c.getInt(c.getColumnIndex("DONE")))==1), dateadded, datepending);
            Log.d("GetArrayFromCursor", String.valueOf(((c.getInt(c.getColumnIndex("DONE")))==1)));
            tasks.add(t);

        }
        Log.d("HELPER", "TASKS RETURNED");
        return tasks;


    }
    public ArrayList<Task> getAllTasks(){
        Cursor c = TasksDBHelper.this.getWritableDatabase().rawQuery("SELECT * FROM TASKSTABLE", null);
        return getArrayListFromCursor(c);
    }
    public ArrayList<Task> getPendingTasks(){
        Cursor c = TasksDBHelper.this.getWritableDatabase().rawQuery("SELECT * FROM TASKSTABLE WHERE DONE = 0", null);
        return getSortedArrayList(getArrayListFromCursor(c));

    }
    public ArrayList<Task> getDoneTasks(){
        Cursor c = TasksDBHelper.this.getWritableDatabase().rawQuery("SELECT * FROM TASKSTABLE WHERE DONE = 1", null);
        return getArrayListFromCursor(c);

    }
    public ArrayList<Task> getSortedArrayList(ArrayList<Task> unsorted){
        Collections.sort(unsorted, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.datepending.compareTo(o2.datepending);
            }
        });
        return unsorted;


    }
    public boolean getDone(int primk){
        Cursor c = TasksDBHelper.this.getWritableDatabase().rawQuery("SELECT * FROM TASKSTABLE WHERE PRIMK = "+String.valueOf(primk), null);
        c.moveToFirst();
        return c.getInt(c.getColumnIndex("DONE"))>0;
    }

}
