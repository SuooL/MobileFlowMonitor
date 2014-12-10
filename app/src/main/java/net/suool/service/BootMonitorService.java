package net.suool.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BootMonitorService extends Service {
    public BootMonitorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
