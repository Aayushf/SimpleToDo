package com.simpletodo.aayushf.simpletodo;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.TextView;

/**
 * Created by ayfadia on 5/10/17.
 */

public class TaskNotification {
    int taskpk, notifpk;
    long time;
    public TaskNotification(int notifprimk, long time, int taskpk){
        this.taskpk = taskpk;
        this.time = time;
        this.notifpk = notifprimk;
    }

    public TaskNotification(int taskpk, long time) {
        this.taskpk = taskpk;
        this.time = time;
    }

}
