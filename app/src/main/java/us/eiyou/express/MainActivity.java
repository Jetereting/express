package us.eiyou.express;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import us.eiyou.express.model.Ad;
import us.eiyou.express.model.Code;
import us.eiyou.express.model.Express_info;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv_bg;
    EditText et_express_number, et_phone;
    Spinner s_loc, s_express_name;
    String loc_select, express_name_select,s_ad;
    TextView tv_code,tv_context;
    Button b_code, b_send, b_loc_set, b_express_name_set,b_query;
    ArrayList<String> array_loc;
    ArrayList<String> array_express_name;
    BmobUser bmobUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(getClass().getSimpleName(), "onCreate");
        setContentView(R.layout.activity_main);
        initView();
        spinnerEvent();
        b_code.setOnClickListener(this);
        b_send.setOnClickListener(this);
        b_query.setOnClickListener(this);
        b_loc_set.setOnClickListener(this);
        b_express_name_set.setOnClickListener(this);
    }

    private void spinnerEvent() {
        array_loc.clear();
        String arrays_loc = getApplicationContext().getSharedPreferences("array_loc", Context.MODE_PRIVATE).getString("array_loc", "");
        if (arrays_loc.length() > 1) {
            arrays_loc = arrays_loc.substring(1, arrays_loc.length() - 1);
        }
        for(String s : arrays_loc.split(",")) {
            array_loc.add(s);
        }
        s_loc.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, array_loc));
        s_loc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loc_select = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        array_express_name.clear();
        String arrays_express_name = getApplicationContext().getSharedPreferences("array_express_name", Context.MODE_PRIVATE).getString("array_express_name", "");
        if (arrays_express_name.length() > 1) {
            arrays_express_name = arrays_express_name.substring(1, arrays_express_name.length() - 1);
        }
        for (String s : arrays_express_name.split(",")) {
            array_express_name.add(s);
        }
        s_express_name.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, array_express_name));
        s_express_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                express_name_select = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initView() {
        // 初始化 Bmob SDK
        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
        Bmob.initialize(this, "579a7850db3695c6b21115ca2c508009");
        bmobUser = BmobUser.getCurrentUser(getApplicationContext());
        if (bmobUser == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        iv_bg = (ImageView) findViewById(R.id.iv_bg);
        et_express_number = (EditText) findViewById(R.id.et_express_number);
        s_loc = (Spinner) findViewById(R.id.s_loc);
        s_express_name = (Spinner) findViewById(R.id.s_express_name);
        tv_code = (TextView) findViewById(R.id.tv_code);
        tv_context = (TextView) findViewById(R.id.tv_context);
        b_code = (Button) findViewById(R.id.b_code);
        b_query= (Button) findViewById(R.id.b_query);
        b_send = (Button) findViewById(R.id.b_send);
        b_loc_set = (Button) findViewById(R.id.b_loc_set);
        b_express_name_set = (Button) findViewById(R.id.b_express_name_set);
        et_phone = (EditText) findViewById(R.id.et_phone);

        iv_bg.getBackground().setAlpha(100);

        array_loc = new ArrayList<>();
        array_express_name = new ArrayList<>();

        BmobQuery<Ad> bmobQuery=new BmobQuery<>();
        bmobQuery.getObject(getApplicationContext(), "37703e9700", new GetListener<Ad>() {
            @Override
            public void onSuccess(Ad ad) {
                s_ad=ad.getAd();
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            获取确认码
            case R.id.b_code:
                BmobQuery<Code> query = new BmobQuery<Code>();
                query.getObject(getApplicationContext(), "9db6e3895a", new GetListener<Code>() {

                    @Override
                    public void onSuccess(Code object) {
                        // TODO Auto-generated method stub
                        tv_code.setText("" + object.getCode());
                        Code code = new Code();
                        if(object.getCode() >= 9999) {
                            code.setCode(1000);
                        }else{
                            code.setCode(object.getCode() + 1);
                        }
                        code.update(getApplicationContext(), "9db6e3895a", new UpdateListener() {

                            @Override
                            public void onSuccess() {
                                // TODO Auto-generated method stub
                                Log.i("bmob", "更新成功：");
                            }

                            @Override
                            public void onFailure(int code, String msg) {
                                // TODO Auto-generated method stub
                                Log.i("bmob", "更新失败：" + msg);
                            }
                        });
                    }

                    @Override
                    public void onFailure(int code, String arg0) {
                        // TODO Auto-generated method stub
                        toast("查询失败：" + arg0);
                        tv_code.setText("" + arg0);
                    }

                });
                break;
//            发送短信
            case R.id.b_send:
                if (TextUtils.isEmpty(et_express_number.getText().toString())) {
                    toast("亲，快递单号还没输入~");
                } else if (TextUtils.isEmpty(et_phone.getText().toString())) {
                    toast("亲，请输入收件人手机号");
                } else if (tv_code.getText().toString().equals("←")) {
                    toast("还没获取确认码~");
                } else {
                    SmsManager sms = SmsManager.getDefault();
                    List<String> texts = sms.divideMessage("您好，" + express_name_select + "您的快递在" + loc_select + "请有时间来取，快递单号：" + et_express_number.getText().toString() + "确认码：" + tv_code.getText().toString()+" "+s_ad);
                    for (String text : texts) {
                        sms.sendTextMessage(et_phone.getText().toString(), null, text, null, null);
                    }
                    tv_context.setText("您好，" + express_name_select + "您的快递在" + loc_select + "请有时间来取，快递单号：" + et_express_number.getText().toString() + "确认码：" + tv_code.getText().toString());
                    toast("短信已发送~");
                    Express_info express_info=new Express_info();
                    express_info.setCode(tv_code.getText().toString());
                    express_info.setExpress(express_name_select);
                    express_info.setLoc(loc_select);
                    express_info.setPhone(et_phone.getText().toString());
                    express_info.setUser(bmobUser.getUsername());
                    express_info.setNumber(et_express_number.getText().toString());
                    express_info.save(getApplicationContext(), new SaveListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                    tv_code.setText("←");
                    et_express_number.setText("");
                    et_phone.setText("");
                }

                break;
//            设置快递公司名字
            case R.id.b_express_name_set:
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("设置快递公司名字");
                dialog.show();
                final LinearLayout linearLayout = (LinearLayout) dialog.getWindow().findViewById(R.id.ll);
//                清除
                dialog.getWindow().findViewById(R.id.b_reset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        array_express_name.clear();
                        getApplicationContext().getSharedPreferences("array_express_name", Context.MODE_PRIVATE).edit().putString("array_express_name", array_express_name.toString()).commit();
                        linearLayout.removeAllViews();
                    }
                });
//                添加
                dialog.getWindow().findViewById(R.id.b_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText = new EditText(getApplicationContext());
                        editText.setTextColor(Color.parseColor("#000000"));
                        linearLayout.addView(editText);
                    }
                });
//                完成
                dialog.getWindow().findViewById(R.id.b_done).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = (EditText) dialog.getWindow().findViewById(R.id.et);
                        if (et.getText().toString() != "") {
                            array_express_name.add(et.getText().toString());
                            Log.e("array_express_name1", et.getText().toString());
                        }
                        for (View view : getAllChildViews(linearLayout)) {
                            if (view instanceof EditText) {
                                if (((EditText) view).getText().toString() != "") {
                                    array_express_name.add(((EditText) view).getText().toString());
                                    Log.e("array_express_name", ((EditText) view).getText().toString());
                                }
                            }
                        }
                        getApplicationContext().getSharedPreferences("array_express_name", Context.MODE_PRIVATE).edit().putString("array_express_name", array_express_name.toString()).commit();
                        spinnerEvent();
                        dialog.dismiss();
                    }
                });
                break;
//            设置快递地点
            case R.id.b_loc_set:
                final Dialog dialog1 = new Dialog(MainActivity.this);
                dialog1.setContentView(R.layout.dialog);
                dialog1.setTitle("设置快递地点");
                dialog1.show();
                final LinearLayout linearLayout1 = (LinearLayout) dialog1.getWindow().findViewById(R.id.ll);
//                清除
                dialog1.getWindow().findViewById(R.id.b_reset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        array_loc.clear();
                        getApplicationContext().getSharedPreferences("array_loc", Context.MODE_PRIVATE).edit().putString("array_loc", array_loc.toString()).commit();
                        linearLayout1.removeAllViews();
                    }
                });
//                添加
                dialog1.getWindow().findViewById(R.id.b_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText = new EditText(getApplicationContext());
                        editText.setTextColor(Color.parseColor("#000000"));
                        linearLayout1.addView(editText);
                    }
                });
//                完成
                dialog1.getWindow().findViewById(R.id.b_done).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = (EditText) dialog1.getWindow().findViewById(R.id.et);
                        if (et.getText().toString() != "") {
                            array_loc.add(et.getText().toString());
                        }
                        for (View view : getAllChildViews(linearLayout1)) {
                            if (view instanceof EditText) {
                                if (((EditText) view).getText().toString() != "") {
                                    array_loc.add(((EditText) view).getText().toString());
                                    Log.e("array_loc_add", ((EditText) view).getText().toString());
                                }
                            }
                        }
                        getApplicationContext().getSharedPreferences("array_loc", Context.MODE_PRIVATE).edit().putString("array_loc", array_loc.toString()).commit();
                        spinnerEvent();
                        dialog1.dismiss();
                    }
                });
                break;
//            查询
            case R.id.b_query:
                startActivity(new Intent(getApplicationContext(),ExpressInfoActivity.class));
                break;
        }
    }

    public void toast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    private List<View> getAllChildViews(View parent) {
        List<View> allchildren = new ArrayList<View>();
        if (parent instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) parent;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                allchildren.add(viewchild);
            }
        }
        return allchildren;
    }
}
