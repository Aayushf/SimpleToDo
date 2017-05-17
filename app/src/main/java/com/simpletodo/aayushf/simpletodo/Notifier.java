package com.simpletodo.aayushf.simpletodo;

        import android.app.Notification;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.app.Service;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.os.IBinder;
        import android.support.annotation.Nullable;
        import android.support.v4.app.NotificationCompat;
        import android.util.Log;
        import android.widget.TextView;

        import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by ayfadia on 5/10/17.
 */

public class Notifier extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("SERVICEE" , "STARTED");
        long l = intent.getLongExtra("notifprimk", 10);
        NotificationsDBHelper helper = new NotificationsDBHelper(context);
        TaskNotification tn = helper.getNotifFromPK((int) l);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        TasksDBHelper h = new TasksDBHelper(context);
        Intent i = new Intent(context, Main2Activity.class);
        i.putExtra("primknotif", h.getTaskFromPK(tn.taskpk).primk);
        i.putExtra("notifid", tn.notifpk);

        PendingIntent pi = PendingIntent.getActivity(context,0,i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.ic_check_black_24dp).setContentTitle(h.getTaskFromPK(tn.taskpk).task).setContentText(h.getTaskFromPK(tn.taskpk).category).setOngoing(true).addAction(R.drawable.ic_check_black_24dp, "DONE", pi);
        notificationManager.notify(tn.notifpk, b.build());


    }


}
