package net.suool.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import net.suool.service.BootMonitorService;

public class BootLongReceiver extends BroadcastReceiver {
    public BootLongReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent i = new Intent(context, BootMonitorService.class);
        context.startService(i);
    }
}
