package example.com.education.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import example.com.education.R;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText et_password;
    private EditText et_password1;
    private Button bt_change_pwd;
    private String trueName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
        SharedPreferences preferences = getSharedPreferences("userlogin", Context.MODE_PRIVATE);
        trueName = preferences.getString("trueName","");

        bt_change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = et_password.getText().toString();
                String password1 = et_password1.getText().toString();

                if (password.equals("")&& password1.equals("")) {
                    Toast.makeText(ChangePasswordActivity.this, "请输入信息", Toast.LENGTH_SHORT).show();
                }else {
                    if (passwordEqual(password,password1)){
                        changePassword(password,trueName);
                    }else {
                        Toast.makeText(ChangePasswordActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void changePassword(String password, String trueName) {
        RequestParams params = new RequestParams("http://10.1.35.9:8080/Education/ChangePasswordForAppServlet");
        params.addQueryStringParameter("trueName",trueName);
        params.addQueryStringParameter("password",password);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析result
                //Log.i(TAG, "onSuccess: " + result);
                JSONObject jsonObject = JSON.parseObject(result);
                int code = jsonObject.getInteger("code");
                if(code ==200) {
                    Toast.makeText(ChangePasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                } else {
                    String message = jsonObject.getString("message");
                    Toast.makeText(ChangePasswordActivity.this, "修改失败", Toast.LENGTH_LONG).show();
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



    private void initView() {
        et_password = (EditText) findViewById(R.id.et_password);
        et_password1 = (EditText) findViewById(R.id.et_password1);
        bt_change_pwd = (Button) findViewById(R.id.bt_change_pwd);
    }

    private boolean passwordEqual(String password,String password1){
        if (password.equals(password1)){
            return true;
        }else{
            return false;
        }
    }
}
