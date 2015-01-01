package net.suool.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import net.suool.mobileflowmonitor.R;

public class TcActivity extends Activity{
    private EditText total;
    private EditText rest;
    private Button confirm;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_tc);

        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        total = (EditText)findViewById(R.id.totalFlow);
        rest = (EditText)findViewById(R.id.restFlow);
        confirm = (Button) findViewById(R.id.Btn_Tc);
        float totalflow = pref.getFloat("TotalFlow", 0);
        float restflow = pref.getFloat("RestFlow", 0);
        if(totalflow!=0) {
            total.setText(Float.toString(totalflow));
        }
        if(restflow!=0) {
            rest.setText(Float.toString(restflow));
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                String totalValue = total.getText().toString();
                if(totalValue.length()!=0) {
                    editor.putFloat("TotalFlow", Float.parseFloat(totalValue));
                }
                else{
                    editor.putFloat("TotalFlow", 0);
                }
                String restValue = rest.getText().toString();
                if(restValue.length()!=0) {
                    editor.putFloat("RestFlow", Float.parseFloat(restValue));
                }
                else{
                    editor.putFloat("RestFlow", 0);
                }
                editor.apply();
                finish();
            }
        });
    }
}
