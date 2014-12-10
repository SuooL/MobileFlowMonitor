package net.suool.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import net.suool.activity.AboutActivity;
import net.suool.activity.OperatorActivity;
import net.suool.activity.TcActivity;
import net.suool.adapter.SettingAdapter;
import net.suool.mobileflowmonitor.R;
import net.suool.model.Setting;
import net.suool.service.MyService;
import net.suool.ui.CheckSwitchButton;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends Activity {
    private List<Setting> settingList = new ArrayList<Setting>();
    private String[] line = {"无", "10M", "20M", "50M", "100M"};
    private int[] lineValue = {0, 10, 20, 50, 100};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settinglist);

        //初始化list
        initSetting();
        SettingAdapter adapter = new SettingAdapter(SettingActivity.this, R.layout.setting, settingList);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Setting setting = settingList.get(position);
                //Toast.makeText(SettingActivity.this, position+setting.getName(), Toast.LENGTH_SHORT).show();
                switch(position) {
                    case 0:
                        //设置套餐
                        setTc();
                        break;
                    case 1:
                        //设置运营商
                        setOperator();
                        break;
                    case 2:
                        //显示浮窗
                        setFloatView(view);
                        break;
                    case 3:
                        //流量预警
                        setLine();
                        break;
                    case 4:
                        //流量校正
                        break;
                    case 5:
                        //关于我们
                        about();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    //初始化list
    private  void initSetting(){
        Setting tc = new Setting("设置套餐", 0);
        settingList.add(tc);
        Setting yys = new Setting("设置运营商", 0);
        settingList.add(yys);
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        Setting fc;
        CheckSwitchButton fc_check;
        if(!pref.getBoolean("FloatWindowSelection", false)){
            fc = new Setting("显示浮窗", R.drawable.off);
        }
        else{
            fc = new Setting("显示浮窗", R.drawable.on);
        }
        settingList.add(fc);
        Setting llyj = new Setting("流量预警", 0);
        settingList.add(llyj);
        Setting lljz = new Setting("流量校正", 0);
        settingList.add(lljz);
        Setting gy = new Setting("关于我们", 0);
        settingList.add(gy);
    }

    //跳转至套餐设置Activity
    private void setTc(){
        Intent intentTc = new Intent(SettingActivity.this, TcActivity.class);
        startActivity(intentTc);
    }

    //设置运营商
    private void setOperator(){
        Intent intentOperator = new Intent(SettingActivity.this, OperatorActivity.class);
        startActivity(intentOperator);
    }

    //设置浮窗显示
    private void setFloatView(View view){
        ImageView settingImage;
        settingImage = (ImageView) view.findViewById(R.id.setting_image);
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        if(pref.getBoolean("FloatWindowSelection", false)){
            settingImage.setImageResource(R.drawable.off);
            Intent stopIntent = new Intent(SettingActivity.this, MyService.class);
            stopService(stopIntent);
            saveFloatWindowSelection(false);
        }
        else{
            settingImage.setImageResource(R.drawable.on);
            Intent startIntent = new Intent(this, MyService.class);
            startService(startIntent);
            saveFloatWindowSelection(true);
        }
    }

    //预警线dialog
    private void setLine(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(SettingActivity.this);
        dialog.setTitle("请选择流量预警线:");
        dialog.setCancelable(false);
        dialog.setSingleChoiceItems(line, loadLine(),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(SettingActivity.this, +which + "", Toast.LENGTH_SHORT).show();
                        saveLine(which);
                    }
                }
        );
        dialog.setPositiveButton("OK", null);
        dialog.show();
    }

    private void about(){
        Intent intentAbout = new Intent(SettingActivity.this, AboutActivity.class);
        startActivity(intentAbout);
    }

    //保存浮窗设置选择
    private void saveFloatWindowSelection(boolean bl){
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putBoolean("FloatWindowSelection", bl);
        editor.apply();
    }

    //保存预警线
    private void saveLine(int line){
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putInt("Line", line);
        editor.putInt("LineValue", lineValue[line]);
        editor.apply();
    }

    //读取预警线
    private int loadLine(){
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        return pref.getInt("Line", 0);
    }
}