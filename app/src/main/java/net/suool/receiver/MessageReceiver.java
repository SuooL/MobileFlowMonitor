package net.suool.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import net.suool.application.MyApplication;
import net.suool.util.enumFlags;

public class MessageReceiver extends BroadcastReceiver {
    public MessageReceiver() {
    }

    private static final String strRes = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
            /*
             * 判断是否是SMS_RECEIVED事件被触发
             */
        if (intent.getAction().equals(strRes)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
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
                Toast.makeText(MyApplication.getmContext(), address, Toast.LENGTH_SHORT).show();
                Log.d("MyTest", "发送方的号码是：" + address);
                Log.d("MyTest", "发送的信息内容是："+fullMessage);

            }
        }
    }
}
