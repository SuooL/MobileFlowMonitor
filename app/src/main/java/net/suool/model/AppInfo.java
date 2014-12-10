package net.suool.model;

import android.graphics.drawable.Drawable;

/**
 * Created by SuooL on 2014/12/2 0002.
 */
public class AppInfo {
    //应用包名
    private String packname;
    //应用版本号
    private String appVersion;
    //应用名称
    private String appname;
    // 应用uid
    private int uid;
    //上传的数据
    private long tx;
    //下载的数据
    private long rx;
    // 总量
    private long total;
    //应用图标
    private Drawable appicon;
    //
    public Boolean isChecked;

    //该应用是否属于用户程序
    private boolean userapp;
    public boolean isUserapp() {
        return userapp;
    }
    public void setUserapp(boolean userapp) {
        this.userapp = userapp;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getPackname() {
        return packname;
    }
    public void setPackname(String packname) {
        this.packname = packname;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public long getTx() {
        return tx;
    }

    public void setTx(long tx) {
        this.tx = tx;
    }

    public long getRx() {
        return rx;
    }

    public void setRx(long rx) {
        this.rx = rx;
    }

    public long getTotal() {
        total = this.tx + this.rx;
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppVersion(){
        return appVersion;
    }

    public String getAppname() {
        return appname;
    }
    public void setAppname(String appname) {
        this.appname = appname;
    }
    public Drawable getAppicon() {
        return appicon;
    }
    public void setAppicon(Drawable appicon) {
        this.appicon = appicon;
    }

}
