package example.com.education.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import example.com.education.R;
import example.com.education.bean.UserLogin;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final String ACTION_NAME = "com.education.info";

    private Button bt_login;
    private TextView tv_register;
    private EditText et_username;
    private EditText et_password;
    private RadioButton rb_teacher;
    private RadioButton rb_student;
    private RadioButton rb_manager;
    private CheckBox cb_remember_pwd;

    //MyReceiver receiver = new MyReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       // registerBoradcastReceiver();

        bt_login = (Button) findViewById(R.id.bt_login);
        tv_register = (TextView) findViewById(R.id.tv_register);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        cb_remember_pwd = (CheckBox) findViewById(R.id.cb_remember_pwd);


        SharedPreferences preferences =getSharedPreferences("userLoginInfo", Context.MODE_PRIVATE);
        if(preferences.contains("username")) {
            et_username.setText(preferences.getString("username", ""));
            et_password.setText(preferences.getString("password", ""));
        }

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                    if (username.equals("")) {
                        Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (password.equals("")) {
                        Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                if (cb_remember_pwd.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("userLoginInfo",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username",username);
                    editor.putString("password",password);
                    editor.commit();
                }

                    login(username, password);


            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void login(final String username, final String password) {
        RequestParams params = new RequestParams("http://10.2.227.238:8080/Education/LoginForAppServlet");
        params.addQueryStringParameter("username",username);
        params.addQueryStringParameter("password",password);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析result
                Log.i(TAG, "onSuccess: " + result);
                JSONObject jsonObject = JSON.parseObject(result);
                int code = jsonObject.getInteger("code");
                if(code == 200) {
                    JSONObject resultData = jsonObject.getJSONObject("result");
                    UserLogin userLogin = new UserLogin();
                    userLogin = resultData.getObject("userLogin",UserLogin.class);

                    int roleType = userLogin.getLoginId();
                    Long id = userLogin.getId();
                    String trueName = userLogin.getTrueName();
                    String classId = userLogin.getClassId();
                    SharedPreferences preferences = getSharedPreferences("userlogin", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putLong("id",id);
                    editor.putInt("roleType",roleType);
                    editor.putString("trueName",trueName);
                    editor.putString("classId",classId);
                    editor.commit();

                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();

                } else {
                    String message = jsonObject.getString("message");
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
                }

            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }


   /* public void registerBoradcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("teacher");
        myIntentFilter.addAction("student");
        //注册广播
        registerReceiver(receiver, myIntentFilter);
    }*/



    }

