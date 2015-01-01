package net.suool.fragment_content;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.TrafficStats;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.suool.activity.MainActivity;
import net.suool.application.MyApplication;
import net.suool.data.DBHelper;
import net.suool.mobileflowmonitor.R;
import net.suool.model.AppInfo;
import net.suool.model.MyOnClickListener;
import net.suool.provider.TrafficInfoProvider;
import net.suool.ui.CheckSwitchButton;
import net.suool.ui.RefreshableView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by SuooL on 2014/12/1 0001.
 */
public class FragmentContentList extends Fragment {

    private ListView lvAppInfo;
    private DBHelper dbHelper = DBHelper.getInstance(MyApplication.getmContext(),"Monitor2.db", null, 2);
    public SQLiteDatabase database = dbHelper.getWritableDatabase();
    private CheckSwitchButton mCheckSwithcButton;
    RefreshableView refreshableView;

    public static List<AppInfo> listApp;

    public String TAG = "MyTest";

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        listApp = getTrafficInfos();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("Contentfragment", "Contentfragment_onCreateView");
        View view = inflater.inflate(R.layout.fragment_content_list, container, false);

        lvAppInfo = (ListView) view.findViewById(R.id.app_list);

        lvAppInfo.setAdapter(new TrafficAdapter());

        refreshableView = (RefreshableView) view.findViewById(R.id.refreshable_view);

        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, 0);

        if(! isRoot()){
            Toast.makeText(MyApplication.getmContext(), "您的手机没有Root，无法限制应用联网权限", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    public class TrafficAdapter extends BaseAdapter {
        //封装单个具有Intenet权限的应用的流量信息

        public int getCount() {
            return listApp.size();
        }

        public Object getItem(int position) {
            return listApp.get(position);
        }

        public long getItemId(int position) {
            return position;
        }
        //ListView中显示多少个Item，该方法就被调用多少次
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder = new ViewHolder();
            final AppInfo info = listApp.get(position);
            //复用缓存的View
            if(convertView==null){
                view = View.inflate(MyApplication.getmContext(), R.layout.app_list_item, null);
                holder.iv_icon = (ImageView) view.findViewById(R.id.app_icon);
                holder.tv_name = (TextView) view.findViewById(R.id.app_name);
                holder.tv_data = (TextView) view.findViewById(R.id.app_data);

                view.setTag(holder);
            }else{
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            mCheckSwithcButton = (CheckSwitchButton) view.findViewById(R.id.mCheckSwithcButton);
            // 判断手机是否root来决定是否可以限制应用联网
            mCheckSwithcButton.setEnabled(isRoot());
            // 按钮的状态改变监听
            mCheckSwithcButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    if(isChecked){
                        // 状态开， 数据库中更新该APP可以联网
                        database.execSQL("update TBL_APP_FLAG_CATEGORY set FLAG =? where UID =? ",
                                new String[]{"1", String.valueOf(info.getUid())});
                        //Toast.makeText(MyApplication.getmContext(), info.getAppname()+info.getUid()+"允许联网", Toast.LENGTH_SHORT).show();
                    }else{
                        // 状态关， 数据库中更新该APP不可以联网
                        database.execSQL("update TBL_APP_FLAG_CATEGORY set FLAG =? where UID =? ",
                                new String[]{"0", String.valueOf(info.getUid())});
                      //  Toast.makeText(MyApplication.getmContext(), info.getAppname()+info.getUid()+"已关闭联网", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            // 查询数据库的状态
            Cursor cursor = database.rawQuery("select * from TBL_APP_FLAG_CATEGORY where UID = ?", new String[]{String.valueOf(info.getUid())});
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                mCheckSwithcButton.setChecked(cursor.getInt(cursor.getColumnIndex("FLAG")) == 1);
             //   Log.d(TAG, "数据库查询成功"+info.getUid()+" flag "+cursor.getInt(cursor.getColumnIndex("FLAG")));
            } else{
                ContentValues values = new ContentValues();
                values.put("UID", info.getUid());
                values.put("FLAG", 1);
                database.insert("TBL_APP_FLAG_CATEGORY", null, values);
             //   Log.d(TAG, "数据库插入更新成功"+info.getUid());
                mCheckSwithcButton.setChecked(true);
            }

            holder.iv_icon.setImageDrawable(info.getAppicon());
            holder.tv_name.setText(info.getAppname());
            //下载所产生的流量
            long rx = info.getRx();
            //上传所产生的流量
            long tx = info.getTx();
            //增强程序的健壮性.因为在模拟器上运行时返回值为-1.
            if(rx<0){
                rx = 0;
            }
            if(tx<0){
                tx = 0;
            }
            // 总流量
            long total = tx + rx;
            holder.tv_data.setText(Formatter.formatFileSize(MyApplication.getmContext(), total));


            return view;
        }
    }
    //通过static的修饰，保证了栈内存中存在唯一一份字节码且被共用
    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_data;
    }

    public List<AppInfo> getTrafficInfos() {
        //获取到配置权限信息的应用程序
        PackageManager pm = MyApplication.getmContext().getPackageManager();
        List<PackageInfo> packinfos = pm
                .getInstalledPackages(PackageManager.GET_PERMISSIONS);
        //存放具有Internet权限信息的应用
        List<AppInfo> trafficInfos = new ArrayList<AppInfo>();
        for(PackageInfo packinfo : packinfos){
            //获取该应用的所有权限信息
            String[] permissions = packinfo.requestedPermissions;
            if(permissions!=null&&permissions.length>0){
                for(String permission : permissions){
                    //筛选出具有Internet权限的应用程序
                    if("android.permission.INTERNET".equals(permission)){
                        //用于封装具有Internet权限的应用程序信息
                        AppInfo appInfo = new AppInfo();
                        //封装应用信息
                        appInfo.setPackname(packinfo.packageName);
                        appInfo.setAppicon(packinfo.applicationInfo.loadIcon(pm));
                        appInfo.setAppname(packinfo.applicationInfo.loadLabel(pm).toString());
                        //获取到应用的uid（user id）
                        int uid = packinfo.applicationInfo.uid;
                        appInfo.setUid(uid);
                        //TrafficStats对象通过应用的uid来获取应用的下载、上传流量信息
                        appInfo.setRx(TrafficStats.getUidRxBytes(uid));
                        appInfo.setTx(TrafficStats.getUidTxBytes(uid));
                        trafficInfos.add(appInfo);
                        appInfo = null;
                        break;
                    }
                }
            }
        }
        return trafficInfos;
    }

    /**
     * 判断手机是否ROOT
     */
    public boolean isRoot() {

        boolean root = false;

        try {
            if ((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists())) {
                root = false;
            } else {
                root = true;
            }

        } catch (Exception e) {
        }

        return root;
    }

}
