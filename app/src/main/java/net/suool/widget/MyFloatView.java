package net.suool.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

import net.suool.application.MyApplication;

public class MyFloatView extends TextView {
    Context context = null;

    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;

    private WindowManager wm=(WindowManager)getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

    //此wmParams为获取的全局变量，用以保存悬浮窗口的属性
    private WindowManager.LayoutParams wmParams = ((MyApplication)getContext().getApplicationContext()).getMywmParams();

    public MyFloatView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取相对屏幕的坐标，即以屏幕左上角为原点
        x = event.getRawX();
        y = event.getRawY()-75;   //25是系统状态栏的高度
        //Log.i("currP", "currX"+x+"==!==currY"+y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取相对View的坐标，即以此View左上角为原点
                mTouchStartX =  event.getX();
                mTouchStartY =  event.getY();
                //Log.i("startP", "startX"+mTouchStartX+"====startY"+mTouchStartY);
                break;
            case MotionEvent.ACTION_MOVE:
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                updateViewPosition();
                mTouchStartX=mTouchStartY=0;
                break;
        }
        return true;
    }

    private void updateViewPosition(){
        //更新浮动窗口位置参数
        wmParams.x=(int)(x-mTouchStartX);
        wmParams.y=(int)(y-mTouchStartY);
        SharedPreferences.Editor editor = MyApplication.getmContext().getSharedPreferences("data", MyApplication.getmContext().MODE_PRIVATE).edit();
        editor.putInt("FloatWindowX", wmParams.x);
        editor.putInt("FloatWindowY", wmParams.y);
        editor.apply();
        wm.updateViewLayout(this, wmParams);
    }
}
