package net.suool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.suool.mobileflowmonitor.R;
import net.suool.model.AppInfo;

import java.util.List;

/**
 * Created by SuooL on 2014/12/2 0002.
 */
public class AppInfoAdapter extends ArrayAdapter<AppInfo> {

    private int resourceId;

    public AppInfoAdapter(Context context, int listViewResourceId, List<AppInfo> objects) {
        super(context, listViewResourceId, objects);
        resourceId = listViewResourceId;
    }

}
