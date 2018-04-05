package example.com.education.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import example.com.education.R;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private EditText et_username;
    private EditText et_password;
    private EditText et_password1;
    private RadioGroup rg_a1;
    private RadioButton rb_student;
    private RadioButton rb_teacher;
    private Button bt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password1 = (EditText) findViewById(R.id.et_password1);
        rg_a1 = (RadioGroup) findViewById(R.id.rg_a1);
        rb_student = (RadioButton) findViewById(R.id.rb_student);
        rb_teacher = (RadioButton) findViewById(R.id.rb_teacher);
        bt_register = (Button) findViewById(R.id.bt_register);



        rg_a1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {



                bt_register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String username = et_username.getText().toString();
                        String password = et_password.getText().toString();
                        String password1 = et_password1.getText().toString();


                        if (username != "" && password !=""&& password1 !="") {
                            if (passwordEqual(password,password1)){
                                        if (rb_student.getId() == rg_a1.getCheckedRadioButtonId()){
                                            registerInfo(username,password,1);
                                        }else{
                                            registerInfo(username,password,2);
                                        }
                            }else {
                                Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(RegisterActivity.this, "请输入信息", Toast.LENGTH_SHORT).show();
                        }

                    }

                });
            }
        });

    }

    private boolean passwordEqual(String password,String password1){
        if (password.equals(password1)){
            return true;
        }else{
            return false;
        }
    }


    private void registerInfo(String username,String password,int roleType){
        RequestParams params = new RequestParams("http://10.1.35.9:8080/Education/InsertForAppServlet");
                params.addQueryStringParameter("username",username);
                params.addQueryStringParameter("password",password);
                params.addParameter("roleType",roleType);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                //解析result
                Log.i(TAG, "onSuccess: " + result);
                JSONObject jsonObject = JSON.parseObject(result);
                int code = jsonObject.getInteger("code");
                if(code ==200) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String message = jsonObject.getString("message");
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
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
    }

