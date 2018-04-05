package example.com.education.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import example.com.education.R;
import example.com.education.bean.Patroller;

public class PatrollerInfoActivity extends AppCompatActivity {

    private static final String TAG = "PatrollerInfoActivity";
    Map<String, String> map = new HashMap<>();
    private static String trueName;
    private static String number;
    private RelativeLayout rl_trueName;
    private TextView tv_trueName;
    private RelativeLayout rl_number;
    private TextView tv_number;
    private Button bt_sure;
    private Long id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patroller_info);
        initView();
        SharedPreferences preferences = getSharedPreferences("userlogin", Context.MODE_PRIVATE);
        id = preferences.getLong("id",0);
        patrillerInfo(id);

        rl_trueName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(PatrollerInfoActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(PatrollerInfoActivity.this);
                builder.setTitle("姓名");
                builder.setView(editText);
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                editText.setText(tv_trueName.getText().toString());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_trueName.setText(editText.getText().toString());
                        trueName = editText.getText().toString();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        rl_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(PatrollerInfoActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(PatrollerInfoActivity.this);
                builder.setTitle("学号");
                builder.setView(editText);
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
                editText.setText(tv_number.getText().toString());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_number.setText(editText.getText().toString());
                        number = editText.getText().toString();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        bt_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put("trueName", trueName);
                map.put("number", number);

                updatePatrollerInfo(id, map);
            }
        });


    }

    private void updatePatrollerInfo(Long id, Map<String, String> map) {
        RequestParams params = new RequestParams("http://10.1.35.9:8080/Education/PatrollerCenterUpdateForApp");
        params.addParameter("id", id);
        for (Map.Entry<String, String> me : map.entrySet()) {
            params.addQueryStringParameter(me.getKey(), me.getValue());
        }
        //params.addParameter("map",map);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = JSON.parseObject(result);
                int code = jsonObject.getInteger("code");
                if (code == 200) {
                    Toast.makeText(PatrollerInfoActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                } else {
                    String message = jsonObject.getString("message");
                    Toast.makeText(PatrollerInfoActivity.this, message, Toast.LENGTH_LONG).show();
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


    private void patrillerInfo(Long id) {
        RequestParams params = new RequestParams("http://10.1.35.9:8080/Education/GetPatrollerInfoServlet");
        params.addParameter("id", id);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析result
                Log.i(TAG, "onSuccess: " + result);
                JSONObject jsonObject = JSON.parseObject(result);
                int code = jsonObject.getInteger("code");
                if (code == 200) {
                    JSONObject resultData = jsonObject.getJSONObject("result");
                    Patroller patroller = new Patroller();
                    patroller = resultData.getObject("patrollerInfo", Patroller.class);

                    tv_trueName.setText(patroller.getTrueName());
                    trueName = patroller.getTrueName();
                    tv_number.setText(patroller.getNumber());
                    number = patroller.getNumber();

                } else {
                    String message = jsonObject.getString("message");
                    Toast.makeText(PatrollerInfoActivity.this, message, Toast.LENGTH_LONG).show();
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
        rl_trueName = (RelativeLayout) findViewById(R.id.rl_trueName);
        tv_trueName = (TextView) findViewById(R.id.tv_trueName);

        rl_number = (RelativeLayout) findViewById(R.id.rl_number);
        tv_number = (TextView) findViewById(R.id.tv_number);

        bt_sure = (Button) findViewById(R.id.bt_sure);
    }
}
