package net.suool.util;

/**
 * Created by SuooL on 2014/12/14 0014.
 */
public class enumFlags {
    private static int flags ;
    private static int dbFlags;
    private static int msgFlag = 0;
    private static long MobileTotalFront =0;
    private static long MobileTotalNow = 0;
    private static long MobileNew = 0;  // 接收的数据
    private static String message = null;

    public static final String LTnum = "10010";
    public static final String YDnum = "10086";
    public static final String DXnum = " ";
    public static final String LTmsg = "CXLL";
    public static final String YDmsg = "10086";
    public static final String DXmsg = " ";

    public static String num;
    public static String msg;

    public static int getFlags() {
        return flags;
    }

    public static void setFlags(int flags) {
        enumFlags.flags = flags;
    }

    public static int getDbFlags() {
        return dbFlags;
    }

    public static void setDbFlags(int dbFlags) {
        enumFlags.dbFlags = dbFlags;
    }

    public static int getMsgFlag() {
        return msgFlag;
    }

    public static void setMsgFlag(int msgFlag) {
        enumFlags.msgFlag = msgFlag;
    }

    public static long getMobileTotalFront() {
        return MobileTotalFront;
    }

    public static void setMobileTotalFront(long mobileTotalFront) {
        MobileTotalFront = mobileTotalFront;
    }

    public static long getMobileTotalNow() {
        return MobileTotalNow;
    }

    public static void setMobileTotalNow(long mobileTotalNow) {
        MobileTotalNow = mobileTotalNow;
    }

    public static long getMobileNew() {
        return MobileNew;
    }

    public static void setMobileNew(long mobileNew) {
        MobileNew = mobileNew;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        enumFlags.message = message;
    }

    public static String getMsg() {
        return msg;
    }

    public static void setMsg(String msg) {
        enumFlags.msg = msg;
    }

    public static String getNum() {
        return num;
    }

    public static void setNum(String num) {
        enumFlags.num = num;
    }
}
