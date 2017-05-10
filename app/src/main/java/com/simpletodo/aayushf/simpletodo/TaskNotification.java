package com.simpletodo.aayushf.simpletodo;

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
