package com.simpletodo.aayushf.simpletodo

/**
 * Created by ayfadia on 5/10/17.
 */

class TaskNotification {
    internal var taskpk: Int = 0
    internal var notifpk: Int = 0
    internal var time: Long = 0

    constructor(notifprimk: Int, time: Long, taskpk: Int) {
        this.taskpk = taskpk
        this.time = time
        this.notifpk = notifprimk
    }

    constructor(taskpk: Int, time: Long) {
        this.taskpk = taskpk
        this.time = time
    }

}
