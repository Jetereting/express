package us.eiyou.express;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import us.eiyou.express.model.User;
import us.eiyou.express.utils.NetUtils;

public class RegisterActivity extends AppCompatActivity {

    EditText et_name, et_password, et_password_re;
    String s_et_name, s_et_password, s_et_password_re;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(getClass().getSimpleName(), "onCreate");
        setContentView(R.layout.activity_register);
        initView();
        findViewById(R.id.b_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_et_name = et_name.getText().toString();
                s_et_password = et_password.getText().toString();
                s_et_password_re = et_password_re.getText().toString();

                if (!NetUtils.isNetWorked(getApplicationContext())) {
                    toast("亲, 木有网络 ( ⊙ o ⊙ ) ");
                } else if (TextUtils.isEmpty(s_et_name) || TextUtils.isEmpty(s_et_password) || TextUtils.isEmpty(s_et_password_re)) {
                    toast("亲, 不填信息完整, 不能完成注册, ~~~~(>_<)~~~~ ");
                } else if (!s_et_password.equals(s_et_password_re)) {
                    toast("亲, 刚才你手抖了下, 两次密码输入不一致");
                } else if (isPhoneNumberValid(s_et_name)) {
                    toast("亲, 请输入正确的手机号码");
                } else {
                    User bmobUser = new User();
                    bmobUser.setUsername(s_et_name);
                    bmobUser.setPassword(s_et_password);
                    bmobUser.setMobilePhoneNumber(s_et_name);
                    bmobUser.setPass_word(s_et_password);
                    bmobUser.signUp(getApplicationContext(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            toast("注册成功:");
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Log.e("onFailure", s);
                            if (s.contains("username")) {
                                toast("您的手机号已经注册过一个账户了");
                            } else if (s.contains("mobilePhoneNumber")) {
                                toast("您的手机号已经注册过一个账户了");
                            } else if (s.contains("Must be valid mobile number")) {
                                toast("亲, 请输入正确的手机号码");
                            }
                        }
                    });
                }
            }
        });
        findViewById(R.id.b_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password_re = (EditText) findViewById(R.id.et_password_re);
    }

    public void toast(String s){
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
    public static boolean isPhoneNumberValid(String phoneNumber) {
        return "1".equals(phoneNumber.substring(0, 0)) && phoneNumber.length() == 11;
    }

}
