package net.suool.data;

/**
 * Created by SuooL on 2014/11/29 0029.
 */
public class MobileFlowMonitorDBInfo {
    private static String TableNames[] = {
            "TBL_MONTH_FLOW_CATEGORY",  // 月流量记录数据库表
            "TBL_APP_FLOW_CATEGORY",    // APP月流量记录数据库表
            "TBL_APP_FLAG_CATEGORY"
    };//表名


    private static String FieldNames[][] = {
            {"ID", "DAY", "FLOW"},
            {"ID", "UID", "DAY", "FLOW","FLAG"},
            {"ID", "UID", "FLAG"}
    };//字段名

    private static String FieldTypes[][] = {
            {"INTEGER PRIMARY KEY AUTOINCREMENT", "TEXT", "INTEGER"},
            {"INTEGER PRIMARY KEY AUTOINCREMENT", "INTEGER", "TEXT", "INTEGER","INTEGER"},
            {"INTEGER PRIMARY KEY AUTOINCREMENT", "INTEGER", "INTEGER"}
    };//字段类型

    public MobileFlowMonitorDBInfo() {
        // TODO Auto-generated constructor stub
    }

    public static String[] getTableNames() {
        return TableNames;
    }

    public static String[][] getFieldNames() {
        return FieldNames;
    }

    public static String[][] getFieldTypes() {
        return FieldTypes;
    }
}
