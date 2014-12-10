package net.suool.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.suool.adapter.OperatorAdapter;
import net.suool.mobileflowmonitor.R;
import net.suool.model.Operator;

import java.util.ArrayList;
import java.util.List;

public class OperatorActivity extends Activity {
    private String[] province;
    private String[] gdcity;
    private String[] setDate;
    private List<Operator> operatorList = new ArrayList<Operator>();
    private String pname;
    private String cname;
    private String oname;
    private String bname;
    private String dname;
    private int plocation;
    private int clocation;
    private int olocation;
    private int blocation;
    private int dlocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);
        province = getResources().getStringArray(R.array.provinceName);
        gdcity = getResources().getStringArray(R.array.gdcityname);
        setDate = getResources().getStringArray(R.array.setdatename);
        SharedPreferences dataPref = getSharedPreferences("data",MODE_PRIVATE);
        pname = dataPref.getString("province","未选择");
        cname = dataPref.getString("city","未选择");
        oname = dataPref.getString("operator","未选择");
        bname = dataPref.getString("brand","未选择");
        dname = dataPref.getString("setdate","未选择");
        /*初始化界面*/
        initOperator();

        final OperatorAdapter adapter = new OperatorAdapter(OperatorActivity.this,R.layout.singal_item,operatorList);
        final ListView listView = (ListView) this.findViewById(R.id.singallistview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                final Operator operator = operatorList.get(position);
                Log.d("Test", "点击的是：" + operator.getTitle());
                if (position == 0 || position==2) {
                    final Operator operator1 = operatorList.get(position + 1);
                    if (operator.getTitle().equals("您的省份")) {
                        final AlertDialog.Builder prodialog = new AlertDialog.Builder(OperatorActivity.this);
                        prodialog.setTitle("您的省份");
                        prodialog.setSingleChoiceItems(province, loadp(), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                pname = province[item];
                                cname = "未选择";
                                plocation = item;
                            }
                        });
                        prodialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                                editor.putString("province", pname);
                                editor.putInt("plocation", plocation);
                                editor.commit();
                                operator.setName(pname);
                                operator1.setName(cname);
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                        final AlertDialog alert = prodialog.create();
                        alert.show();
                    }
                    if (operator.getTitle().equals("选择运营商")) {
                        final String[] operatorss = {"中国移动", "中国联通", "中国电信"};
                        final AlertDialog.Builder opedialog = new AlertDialog.Builder(OperatorActivity.this);
                        opedialog.setTitle("选择运营商");
                        opedialog.setSingleChoiceItems(operatorss, loado(), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                oname = operatorss[item];
                                bname = "未选择";
                                olocation = item;
                            }
                        });
                        opedialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                                editor.putString("operator", oname);
                                editor.putInt("olocation", olocation);
                                editor.commit();
                                operator.setName(oname);
                                operator1.setName(bname);
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                        final AlertDialog alert = opedialog.create();
                        alert.show();
                    }
                }
                else if (position == 1) {

                    if (operator.getTitle().equals("您的城市")) {
                        Log.d("nnn","1");
                        if (pname.equals("未选择")) {
                            Toast.makeText(OperatorActivity.this, "请先选择好您的省份", Toast.LENGTH_LONG).show();
                        }
                        if (pname.equals("广东")) {
                            final AlertDialog.Builder citydialog = new AlertDialog.Builder(OperatorActivity.this);
                            citydialog.setTitle("您的城市");
                            citydialog.setSingleChoiceItems(gdcity, loadc(), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    cname = gdcity[item];
                                    clocation = item;
                                }
                            });
                            citydialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                                    editor.putString("city", cname);
                                    editor.putInt("clocation", clocation);
                                    editor.commit();
                                    operator.setName(cname);
                                    adapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            });
                            final AlertDialog alert = citydialog.create();
                            alert.show();
                        }
                        if (pname.equals("北京")) {
                            final String[] city = {"北京"};
                            final AlertDialog.Builder citydialog = new AlertDialog.Builder(OperatorActivity.this);
                            citydialog.setTitle("您的城市");
                            citydialog.setSingleChoiceItems(city, loadc(), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    cname = city[item];
                                    clocation = item;
                                }
                            });
                            citydialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                                    editor.putString("city", cname);
                                    editor.putInt("clocation", clocation);
                                    editor.commit();
                                    operator.setName(cname);
                                    adapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            });
                            final AlertDialog alert = citydialog.create();
                            alert.show();
                        }
                        if (pname.equals("上海")) {
                            final String[] city = {"上海"};
                            final AlertDialog.Builder citydialog = new AlertDialog.Builder(OperatorActivity.this);
                            citydialog.setTitle("您的城市");
                            citydialog.setSingleChoiceItems(city, loadc(), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    cname = city[item];
                                    clocation = item;
                                }
                            });
                            citydialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                                    editor.putString("city", cname);
                                    editor.putInt("clocation", clocation);
                                    editor.commit();
                                    operator.setName(cname);
                                    adapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            });
                            final AlertDialog alert = citydialog.create();
                            alert.show();
                        }
                        if (pname.equals("重庆")) {
                            final String[] city = {"重庆"};
                            final AlertDialog.Builder citydialog = new AlertDialog.Builder(OperatorActivity.this);
                            citydialog.setTitle("您的城市");
                            citydialog.setSingleChoiceItems(city, loadc(), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    cname = city[item];
                                    clocation = item;
                                }
                            });
                            citydialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                                    editor.putString("city", cname);
                                    editor.putInt("clocation", clocation);
                                    editor.commit();
                                    operator.setName(cname);
                                    adapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            });
                            final AlertDialog alert = citydialog.create();
                            alert.show();
                        }
                    }
                }
                else {
                    if (operator.getTitle().equals("选择品牌")) {
                        if (oname.equals("未选择")) {
                            Toast.makeText(OperatorActivity.this, "请先选择好您的运营商", Toast.LENGTH_LONG).show();
                        }
                        if (oname.equals("中国移动")) {
                            final String[] brand = {"动感地带", "神州行", "全球通", "和4G"};
                            final AlertDialog.Builder bradialog = new AlertDialog.Builder(OperatorActivity.this);
                            bradialog.setTitle("选择品牌");
                            bradialog.setSingleChoiceItems(brand, loadb(), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    bname = brand[item];
                                    blocation = item;
                                }
                            });
                            bradialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                                    editor.putString("brand", bname);
                                    editor.putInt("blocation", blocation);
                                    editor.commit();
                                    operator.setName(bname);
                                    adapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            });
                            final AlertDialog alert = bradialog.create();
                            alert.show();
                        }
                        if (oname.equals("中国联通")) {
                            final String[] brand = {"联通4G", "联通3G", "联通2G"};
                            final AlertDialog.Builder bradialog = new AlertDialog.Builder(OperatorActivity.this);
                            bradialog.setTitle("选择品牌");
                            bradialog.setSingleChoiceItems(brand, loadb(), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    bname = brand[item];
                                    blocation = item;
                                }
                            });
                            bradialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                                    editor.putString("brand", bname);
                                    editor.putInt("blocation", blocation);
                                    editor.commit();
                                    operator.setName(bname);
                                    adapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            });
                            final AlertDialog alert = bradialog.create();
                            alert.show();
                        }
                        if (oname.equals("中国电信")) {
                            final String[] brand = {"电信"};
                            final AlertDialog.Builder bradialog = new AlertDialog.Builder(OperatorActivity.this);
                            bradialog.setTitle("选择品牌");
                            bradialog.setSingleChoiceItems(brand, loadb(), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    bname = brand[item];
                                    blocation = item;
                                }
                            });
                            bradialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                                    editor.putString("brand", bname);
                                    editor.putInt("blocation", blocation);
                                    editor.commit();
                                    operator.setName(bname);
                                    adapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            });
                            final AlertDialog alert = bradialog.create();
                            alert.show();
                        }
                    }
                    if (operator.getTitle().equals("月结算日")) {
                        final AlertDialog.Builder datedialog = new AlertDialog.Builder(OperatorActivity.this);
                        datedialog.setTitle("月结算日");
                        datedialog.setSingleChoiceItems(setDate, loadd(), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                dname = setDate[item];
                                dlocation = item;
                            }
                        });
                        datedialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                                editor.putString("setdate", dname);
                                editor.putInt("dlocation", dlocation);
                                editor.commit();
                                operator.setName(dname);
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                        final AlertDialog alert = datedialog.create();
                        alert.show();
                    }
                }
            }
        });
    }

    private void initOperator(){
        Operator provices = new Operator("您的省份",pname,R.drawable.director_icon);
        operatorList.add(provices);
        Operator citys = new Operator("您的城市",cname,R.drawable.director_icon);
        operatorList.add(citys);
        Operator operators = new Operator("选择运营商",oname,R.drawable.director_icon);
        operatorList.add(operators);
        Operator brands = new Operator("选择品牌",bname,R.drawable.director_icon);
        operatorList.add(brands);
        Operator date = new Operator("月结算日",dname,R.drawable.director_icon);
        operatorList.add(date);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public int loadp(){
        SharedPreferences dataPref = getSharedPreferences("data",MODE_PRIVATE);
        return dataPref.getInt("plocation",0);
    }
    public int loadc(){
        SharedPreferences dataPref = getSharedPreferences("data",MODE_PRIVATE);
        return dataPref.getInt("clocation",0);
    }
    public int loado(){
        SharedPreferences dataPref = getSharedPreferences("data",MODE_PRIVATE);
        return dataPref.getInt("olocation",0);
    }
    public int loadb(){
        SharedPreferences dataPref = getSharedPreferences("data",MODE_PRIVATE);
        return dataPref.getInt("blocation",0);
    }
    public int loadd(){
        SharedPreferences dataPref = getSharedPreferences("data",MODE_PRIVATE);
        return dataPref.getInt("dlocation",0);
    }

}