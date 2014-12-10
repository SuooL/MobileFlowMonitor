package net.suool.application;

import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

/**
 * Created by SuooL on 2014/11/30 0030.
 */
public class MyApplication extends Application {
    private static Context mContext;
    private WindowManager.LayoutParams wmParams=new WindowManager.LayoutParams();
    @Override
    public void onCreate() {
        mContext = getApplicationContext();
    }
    public static Context getmContext() {
        return mContext;
    }


    public WindowManager.LayoutParams getMywmParams(){
        return wmParams;
    }
}

