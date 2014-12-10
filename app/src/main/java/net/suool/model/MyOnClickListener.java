package net.suool.model;

import android.view.View;

/**
 * Created by SuooL on 2014/12/5 0005.
 */
public class MyOnClickListener implements View.OnClickListener {

    private static MyOnClickListener instance = null;

    private MyOnClickListener() {
    }

    public static MyOnClickListener getInstance() {
        if (instance == null)
            instance = new MyOnClickListener() ;
        return instance;
    }

    @Override
    public void onClick(View view) {
        //TODO: do something here

    }
}
