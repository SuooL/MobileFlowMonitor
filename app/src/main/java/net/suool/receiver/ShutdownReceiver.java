package net.suool.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.util.Log;


public class ShutdownReceiver extends BroadcastReceiver {
    public ShutdownReceiver() {
    }

    private static final String TAG = "MyTest";

    private static final String ACTION_SHUTDOWN = "android.intent.action.ACTION_SHUTDOWN";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Shut down this system, ShutdownBroadcastReceiver onReceive()");
        if (intent.getAction().equals(ACTION_SHUTDOWN)) {

            Log.i(TAG, "ShutdownBroadcastReceiver onReceive(), Do thing!");
        }
    }
}
