package com.simpletodo.aayushf.simpletodo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by ayfadia on 5/10/17.
 */

public class Notifier extends Service {

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
