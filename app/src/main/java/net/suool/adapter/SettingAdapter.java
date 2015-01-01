package net.suool.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.suool.activity.SettingActivity;
import net.suool.application.MyApplication;
import net.suool.mobileflowmonitor.R;
import net.suool.model.Setting;
import net.suool.service.FlowService;
import net.suool.ui.CheckSwitchButton;

import java.util.List;

//list项
public class SettingAdapter extends ArrayAdapter<Setting> {
    private int resourceId;

    public SettingAdapter(Context context, int textViewResourceId, List<Setting> objects)
    {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Setting setting = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        //ImageView settingImage = (ImageView)view.findViewById(R.id.setting_image);
        CheckSwitchButton btn = (CheckSwitchButton)view.findViewById(R.id.btn);
        btn.setVisibility(View.GONE);

        if(setting.getIsButton()){
            btn.setVisibility(View.VISIBLE);


            if(setting.getChecked()){
                btn.setChecked(true);
            }
            else{
                btn.setChecked(false);
            }
            btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    if(isChecked){
                        // 状态开
                        Intent startIntent = new Intent(MyApplication.getmContext(), FlowService.class);
                        MyApplication.getmContext().startService(startIntent);
                        SharedPreferences.Editor editor = MyApplication.getmContext().getSharedPreferences("data", MyApplication.getmContext().MODE_PRIVATE).edit();
                        editor.putBoolean("FloatWindowSelection", true);
                        editor.apply();
                        //Toast.makeText(MyApplication.getmContext(),"on", Toast.LENGTH_SHORT).show();
                    }else{
                        // 状态关
                        Intent stopIntent = new Intent(MyApplication.getmContext(), FlowService.class);
                        MyApplication.getmContext().stopService(stopIntent);
                        SharedPreferences.Editor editor = MyApplication.getmContext().getSharedPreferences("data", MyApplication.getmContext().MODE_PRIVATE).edit();
                        editor.putBoolean("FloatWindowSelection", false);
                        editor.apply();
                        //Toast.makeText(MyApplication.getmContext(),"off", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        TextView settingName = (TextView)view.findViewById(R.id.setting_text);
        //settingImage.setImageResource(setting.getImageId());
        settingName.setText(setting.getName());
        return view;
    }
}
