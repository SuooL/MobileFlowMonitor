package net.suool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.suool.mobileflowmonitor.R;
import net.suool.model.Setting;

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
        ImageView settingImage = (ImageView)view.findViewById(R.id.setting_image);
        TextView settingName = (TextView)view.findViewById(R.id.setting_text);
        settingImage.setImageResource(setting.getImageId());
        settingName.setText(setting.getName());
        return view;
    }
}
