package net.suool.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import net.suool.receiver.BootReceiver;

public class ShutDownService extends Service {

    private final String Tag = "MyTest";

    public ShutDownService() {
}



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){   // invoke when the service was made
        super.onCreate();
        Log.d(Tag, "onCreate was started!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //读取SharedPreferences中需要的数据
        SharedPreferences preferences = getSharedPreferences("count", MODE_PRIVATE);

        int count = preferences.getInt("count", 0);

        return super.onStartCommand(intent, flags, startId);   //  invoke when the service was started
    }

    @Override
    public void onDestroy(){
        super.onDestroy();   // invoke when the service was finish
    }


    public void getTrafficFlowSummary(){

    }
}
