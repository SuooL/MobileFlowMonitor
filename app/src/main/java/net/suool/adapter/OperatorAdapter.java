package net.suool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.suool.mobileflowmonitor.R;
import net.suool.model.Operator;

import java.util.List;

/**
 * Created by Administrator on 2014/12/4.
 */
public class OperatorAdapter extends ArrayAdapter<Operator> {
    private int resourceId;
    public OperatorAdapter(Context context, int textViewResourceId, List<Operator> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position,View convertView, ViewGroup parent){
        Operator operator = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView opeTitle = (TextView) view.findViewById(R.id.ope_title);
        TextView opeName = (TextView) view.findViewById(R.id.ope_name);
        ImageView directIcon = (ImageView) view.findViewById(R.id.director_icon);
        opeTitle.setText(operator.getTitle());
        opeName.setText(operator.getName());
        directIcon.setImageResource(operator.getImageId());
        return view;
    }
}
