package net.suool.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Gravity;
import android.view.WindowManager;

import net.suool.application.MyApplication;
import net.suool.mobileflowmonitor.R;
import net.suool.widget.MyFloatView;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service{
    private WindowManager wm = null;
    private WindowManager.LayoutParams wmParams = null;
    private MyFloatView myFV = null;

    private long  lastTotalRxBytes;
    private long lastTimeStamp;

    private Timer timer = new Timer();

    public static final int UPDATE_TEXT = 1;

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        lastTotalRxBytes = getTotalRxBytes();
        lastTimeStamp = System.currentTimeMillis();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        createView();
        timer.schedule(task, 1000, 1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        timer.cancel();
        wm.removeView(myFV);
    }
    //更新UI
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch(msg.what){
                case UPDATE_TEXT:
                    String str = String.format("%1$.2f", getSpeed());
                    //Log.i("myMsg", "format|||"+str+"kb/s");
                    myFV.setText(str+"kb/s");
                    break;
                default:
                    break;
            }
        }
    };

    private void createView(){
        myFV = new MyFloatView(getApplicationContext());
        myFV.setText("0.00kb/s");
        myFV.setBackgroundResource(R.drawable.corner);
        myFV.setTextSize(12);
        myFV.setGravity(Gravity.CENTER_HORIZONTAL);

        //获取WindowManager
        wm=(WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //设置LayoutParams(全局变量）相关参数
        wmParams = ((MyApplication)getApplication()).getMywmParams();

        /**
         *以下都是WindowManager.LayoutParams的相关属性
         * 具体用途可参考SDK文档
         */
        wmParams.type= WindowManager.LayoutParams.TYPE_PHONE;   //设置window type
        wmParams.format= PixelFormat.RGBA_8888;   //设置图片格式，效果为背景透明

        //设置Window flag
        /*
         * FLAG_NOT_TOUCH_MODAL    = 0x00000020;(十六进制)-->32(十进制)-->100000(二进制)
         * FLAG_NOT_FOCUSABLE      = 0x00000008;(十六进制)--> 8(十进制)-->001000(二进制)
         * LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE = 101000(二进制)-->40(十进制)-->0x0028(十六进制)
         **/
        wmParams.flags= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        /*
         * 下面的flags属性的效果形同“锁定”。
         * 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
         wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL
                               | LayoutParams.FLAG_NOT_FOCUSABLE
                               | LayoutParams.FLAG_NOT_TOUCHABLE;
        */

        wmParams.gravity= Gravity.START|Gravity.TOP;   //调整悬浮窗口至左上角
        //以屏幕左上角为原点，设置x、y初始值
        wmParams.x=0;
        wmParams.y=0;

        //设置悬浮窗口长宽数据
        wmParams.width=200;
        wmParams.height=80;

        //显示myFloatView图像
        wm.addView(myFV, wmParams);
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            //Log.i("myMsg", "task|||"+getSpeed()+"kb/s");
            Message message = new Message();
            message.what = UPDATE_TEXT;
            handler.sendMessage(message);
        }
    };

    //计算速度
    public double getSpeed(){
        long nowTotalRxBytes = getTotalRxBytes();
        //nowTotalRxBytes = 600 + lastTotalRxBytes;
        long nowTimeStamp = System.currentTimeMillis();
        double speed = ((nowTotalRxBytes - lastTotalRxBytes) / 1024.0);//毫秒转换
        //Log.i("myMsg", "speed="+speed);
        //long s = nowTimeStamp - lastTimeStamp;
        //Log.i("myMsg", "systemtime="+s);
        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;

        return speed;
    }

    //获取总流量
    public long getTotalRxBytes(){
        return TrafficStats.getTotalRxBytes()==TrafficStats.UNSUPPORTED?0:(TrafficStats.getTotalRxBytes());
    }

    //预警dialog
    private void showAlertDialog(Context mContext) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("警告");
        builder.setMessage("本月剩余流量已不足"+"是否关闭2G/3G移动数据");
        builder.setPositiveButton("关闭", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                turnoffTrafficFlow();
            }
        });
        builder.setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();//need a <span style="font-family: 'Microsoft YaHei';">AlertDialog</span>
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//use alert.
        dialog.show();
    }

    //关闭移动数据
    private void turnoffTrafficFlow(){
        //Log.i("myMsg", "turnoff");
    }
}
