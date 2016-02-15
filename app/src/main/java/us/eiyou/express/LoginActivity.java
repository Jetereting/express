package us.eiyou.express;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import us.eiyou.express.utils.NetUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_name, et_password;
    Button b_login, b_register;
    String name, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(getClass().getSimpleName(), "onCreate");
        setContentView(R.layout.activity_login);
        initView();
        getUserInfo();
        b_login.setOnClickListener(this);
        b_register.setOnClickListener(this);
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        b_login = (Button) findViewById(R.id.b_login);
        b_register = (Button) findViewById(R.id.b_register);
    }

    //记住用户名和密码的
    private void getUserInfo() {
        SharedPreferences sp = getSharedPreferences("UserInfo", 0);
        et_name.setText(sp.getString("username", null));
        et_password.setText(sp.getString("password", null));
    }

    private void saveUserInfo(String username, String password) {
        SharedPreferences sp = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_login:
                name = et_name.getText().toString();
                password = et_password.getText().toString();
                if (!NetUtils.isNetWorked(getApplicationContext())) {
                    toast("亲, 木有网络 ( ⊙ o ⊙ ) ");
                } else if (name.equals("") || password.equals("")) {
                    toast("亲, 请输入用户名和密码");
                    break;
                } else {
                    BmobUser bmobUser = new BmobUser();
                    bmobUser.setUsername(name);
                    bmobUser.setPassword(password);
                    bmobUser.login(getApplicationContext(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            toast("登录成功！");
                            //保存用户信息
                            saveUserInfo(name, password);
                            // 跳转到主页
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            toast("亲, 用户名或密码错误");
                        }
                    });
                }break;
            case R.id.b_register:
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                break;
        }
    }

    public void toast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        return true;
    }
}
