package net.suool.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import net.suool.application.MyApplication;
import net.suool.data.DBHelper;
import net.suool.receiver.BootLongReceiver;
import net.suool.receiver.BootReceiver;
import net.suool.util.enumFlags;

import java.util.Calendar;

public class BootMonitorService extends Service {


    private SmsReceiver smsReceiver;

    private DBHelper dbHelper = DBHelper.getInstance(MyApplication.getmContext(),"Monitor2.db", null, 2);
    public SQLiteDatabase database = dbHelper.getWritableDatabase();
    private Calendar cal = Calendar.getInstance();

    private int iDay;


    private final String Tag = "MyTest";

    public BootMonitorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){   // invoke when the service was made
        super.onCreate();
        //动态注册接受短信消息
        IntentFilter localIntentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        //设置优先级
        localIntentFilter.setPriority(Integer.MAX_VALUE);
        smsReceiver = new SmsReceiver();
        registerReceiver(smsReceiver, localIntentFilter);
        iDay = cal.get(Calendar.DAY_OF_MONTH);
        Log.d(Tag, "onCreate was started!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(Tag, "service running.");
            //    Toast.makeText(MyApplication.getmContext(),"服务正在运行", Toast.LENGTH_SHORT).show();
                getTrafficFlowSummary();
            }
        }).start();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anMin = 30 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anMin;
        Intent i = new Intent(this, BootLongReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);

        return super.onStartCommand(intent, flags, startId);   //  invoke when the service was started
    }


    // 获取总的流量信息
    public void getTrafficFlowSummary(){

        // 开机第一次读取数据
        if(enumFlags.getFlags() < 0){
         //   MobileTotalFront = MobileTotalNow = getMobileRxBytes() +  getMobileTxBytes();
            enumFlags.setMobileTotalFront(getMobileRxBytes() +  getMobileTxBytes());
            enumFlags.setMobileTotalNow(getMobileRxBytes() +  getMobileTxBytes());
            enumFlags.setFlags(1);
            Log.d(Tag, "第一次读取数据"+enumFlags.getMobileTotalNow());
            // 更新数据库数据
            updateDB(iDay, enumFlags.getMobileTotalNow());
        }
        // 开机第一次之后
        else {
            enumFlags.setMobileTotalNow(getMobileRxBytes() +  getMobileTxBytes());
            enumFlags.setMobileNew(enumFlags.getMobileTotalNow()-enumFlags.getMobileTotalFront());
            Log.d(Tag,"上一次数据"+enumFlags.getMobileTotalFront());

            // 重置之前数据
            enumFlags.setMobileTotalFront(enumFlags.getMobileTotalNow());

            Log.d(Tag,"第一次之后"+enumFlags.getMobileNew());
            // 更新数据库数据
            updateDB(iDay, enumFlags.getMobileNew());
        }
    }


    //获取通过Mobile连接收到的字节总数，不包含WiFi
    public long getMobileRxBytes() {
        //
        if (TrafficStats.getMobileRxBytes() == TrafficStats.UNSUPPORTED ){
            return 0;
        }
        return TrafficStats.getMobileRxBytes();
    }

    //获取通过Mobile连发送的字节总数，不包含WiFi
    public long getMobileTxBytes() {
        if (TrafficStats.getMobileRxBytes() == TrafficStats.UNSUPPORTED ){
            return 0;
        }
        return TrafficStats.getMobileTxBytes();
    }

    public void updateDB(int iDay,long dataUsage){
      //  float flowNew = Float.parseFloat(String.valueOf(dataUsage/(1024*1024.0)));
        Cursor cursor = database.rawQuery("select * from TBL_MONTH_FLOW_CATEGORY where DAY = ?",
                new String[]{String.valueOf(iDay)});
        // 尚无此记录
        if (cursor.getCount() == 0){
            // 插入记录
            ContentValues contentValues = new ContentValues();
            contentValues.put("DAY", iDay);
            contentValues.put("FLOW", dataUsage);
            database.insert("TBL_MONTH_FLOW_CATEGORY", null, contentValues);
        }
        else {
            // 更新记录
            cursor.moveToFirst();
            long old = cursor.getLong(cursor.getColumnIndex("FLOW"));
            Log.d(Tag, "表中之前的数据是："+old);
            database.execSQL("update TBL_MONTH_FLOW_CATEGORY set FLOW = ? where DAY = ? ",
                    new String[]{String.valueOf(old+dataUsage), String.valueOf(iDay)});
        }
    }


    private class SmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){

                Bundle bundle = intent.getExtras();
               // if
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < pdus.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }
                    String address = messages[0].getOriginatingAddress();
                    String fullMessage = "";
                    for (SmsMessage message : messages) {
                        fullMessage += message.getMessageBody();
                    }
                    enumFlags.setMessage(fullMessage);
                    Toast.makeText(MyApplication.getmContext(), "号码是："+address, Toast.LENGTH_SHORT).show();
                    Log.v("Test", "发送方的号码是：" + address);
                    Log.v("Test", "发送的信息内容是："+fullMessage);
                 //   Toast.makeText(MyApplication.getmContext(), )

            }

            Log.v("Test", "MyService收到短信发来的消息");
            Toast.makeText(MyApplication.getmContext(), "接收到信息！", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(smsReceiver);
    }
}
