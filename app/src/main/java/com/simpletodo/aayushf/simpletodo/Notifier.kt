package com.simpletodo.aayushf.simpletodo

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log

import android.content.Context.NOTIFICATION_SERVICE

/**
 * Created by ayfadia on 5/10/17.
 */

class Notifier : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("SERVICEE", "STARTED")
        val l = intent.getLongExtra("notifprimk", 10)
        val helper = NotificationsDBHelper(context)
        val tn = helper.getNotifFromPK(l.toInt())
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val h = TasksDBHelper(context)
        val i = Intent(context, Main2Activity::class.java)
        i.putExtra("primknotif", h.getTaskFromPK(tn.taskpk).primk)
        i.putExtra("notifid", tn.notifpk)


        val pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT)

        val b = NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_check_black_24dp).setContentTitle(h.getTaskFromPK(tn.taskpk).task).setContentText(h.getTaskFromPK(tn.taskpk).category).setOngoing(true).addAction(R.drawable.ic_check_black_24dp, "DONE", pi)
        notificationManager.notify(tn.notifpk, b.build())


    }


}
