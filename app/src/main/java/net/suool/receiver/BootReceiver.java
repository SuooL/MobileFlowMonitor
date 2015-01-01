package net.suool.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import net.suool.application.MyApplication;
import net.suool.service.BootMonitorService;
import net.suool.util.enumFlags;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    private static final String TAG = "MyTest";

    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Boot this system , BootBroadcastReceiver onReceive()");

        if (intent.getAction().equals(ACTION_BOOT)) {
            Log.i(TAG, "BootBroadcastReceiver onReceive(), Do thing!");
            enumFlags.setFlags(-1);
        }

        Intent intentSer = new Intent(context, BootMonitorService.class);
        context.startService(intentSer);

    }
}
