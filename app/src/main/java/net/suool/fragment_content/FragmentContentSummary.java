package net.suool.fragment_content;

/**
 * Created by SuooL on 2014/12/1 0001.
 */
import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dacer.androidcharts.BarView;

import net.suool.application.MyApplication;
import net.suool.data.DBHelper;
import net.suool.mobileflowmonitor.R;
import net.suool.util.SendMessage;
import net.suool.util.enumFlags;

import java.util.ArrayList;
import java.util.Calendar;


public class FragmentContentSummary extends Fragment implements View.OnClickListener{

    private LinearLayout linearLayout_day;
    private Button buttonCorrect;
    private DBHelper dbHelper = DBHelper.getInstance(MyApplication.getmContext(),"Monitor.db", null, 2);
    private SQLiteDatabase database = dbHelper.getWritableDatabase();
    private Calendar calendar = Calendar.getInstance();

    private int iDay, maxDay;

    @Override
    public void onAttach(Activity activity){

        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.v("Contentfragment", "Contentfragment!!!_onCreateView");

        SharedPreferences pref = MyApplication.getmContext().getSharedPreferences("data", MyApplication.getmContext().MODE_PRIVATE);
        iDay = calendar.get(Calendar.DAY_OF_MONTH);
        maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        View  contentView = inflater.inflate(R.layout.fragment_content_summary, container, false);

        final BarView barView = (BarView)contentView.findViewById(R.id.bar_view);
     //   final PieView pieView = (PieView)contentView.findViewById(R.id.pie_view);
        TextView textView = (TextView) contentView.findViewById(R.id.textView);
        TextView textView_used = (TextView) contentView.findViewById(R.id.flowUsed_num);
        TextView textView_total = (TextView)contentView.findViewById(R.id.flowTotal_num);


        textView_total.setText(String.valueOf(pref.getFloat("TotalFlow", 0f))+" M");
        textView_used.setText("0.0 M");
        buttonCorrect = (Button) contentView.findViewById(R.id.button_correct);
        buttonCorrect.setOnClickListener(this);



        // 显示柱状图上方的数字
        barView.setDrawTest(true);
        //初始化图
        randomSet(barView);
      //  set(pieView);
//
//        if (enumFlags.getMsgFlag()>0){
//            Intent intent = new Intent(getActivity(), GetMessageService.class);
//            getActivity().startService(intent);
//        }

        return contentView;
    }

    public void onClick(View v){
        SendMessage sendMessage = new SendMessage(getActivity());
        sendMessage.send(enumFlags.LTnum,enumFlags.LTmsg);
    }

    private void randomSet(BarView barView){

        // 初始化下标
        ArrayList<String> test = new ArrayList<String>();
        for (int i=0; i<maxDay; i++){
            test.add(String.valueOf(i+1));
        }
        barView.setBottomTextList(test);

        ArrayList<Double> flowList = new ArrayList<Double>();
        for (int i = 0; i < iDay; i++){
            Cursor cursor = database.rawQuery("select * from TBL_MONTH_FLOW_CATEGORY where DAY = ?",
                    new String[]{String.valueOf(i+1)});
            if (cursor.getCount() == 0) {
                Log.d("MyTest", (i + 1) + "的数据不存在！");
                flowList.add(0.0);
            }
            else {
                cursor.moveToFirst();
             //   BigDecimal b = new BigDecimal(String.valueOf(cursor.getInt(cursor.getColumnIndex("FLOW"))));
                Log.d("MyTest", "读取得到的数据是："+cursor.getLong(cursor.getColumnIndex("FLOW")));
                double d = cursor.getLong(cursor.getColumnIndex("FLOW"))/(1024.0*1024.0);
                flowList.add(d);
            }
        }
        int max = GetMaxValue(flowList).intValue();

        barView.setDataList(flowList, max+10);
    }

    public Double GetMaxValue(ArrayList<Double> arrList){
        Double max = arrList.get(0);
        for (int i = 1; i<arrList.size(); i++){
            if(max < arrList.get(i)){
                max = arrList.get(i);
            }
        }
        return max;
    }
}
