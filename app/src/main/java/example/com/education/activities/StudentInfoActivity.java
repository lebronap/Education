package example.com.education.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
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
import example.com.education.bean.Student;

public class StudentInfoActivity extends AppCompatActivity {

    private static final String TAG = "StudentInfoActivity";
    Map<String, String> map = new HashMap<>();
    private static String trueName;
    private static String number;
    private static String classes;
    private static String sex;
    private static String dname;
    private static String birthday;
    private static String tel;

    private RelativeLayout rl_trueName;
    private TextView tv_trueName;
    private RelativeLayout rl_number;
    private TextView tv_number;
    private RelativeLayout rl_classes;
    private TextView tv_classes;
    private RelativeLayout rl_sex;
    private TextView tv_sex;
    private RelativeLayout rl_dname;
    private TextView tv_dname;
    private RelativeLayout rl_birthday;
    private TextView tv_birthday;
    private RelativeLayout rl_tel;
    private TextView tv_tel;
    private Button bt_sure;
    private Long id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        rl_trueName = (RelativeLayout) findViewById(R.id.rl_trueName);
        tv_trueName = (TextView) findViewById(R.id.tv_trueName);

        rl_number = (RelativeLayout) findViewById(R.id.rl_number);
        tv_number = (TextView) findViewById(R.id.tv_number);

        rl_classes = (RelativeLayout) findViewById(R.id.rl_classes);
        tv_classes = (TextView) findViewById(R.id.tv_classes);

        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        tv_sex = (TextView) findViewById(R.id.tv_sex);

        rl_dname = (RelativeLayout) findViewById(R.id.rl_dname);
        tv_dname = (TextView) findViewById(R.id.tv_dname);

        rl_birthday = (RelativeLayout) findViewById(R.id.rl_birthday);
        tv_birthday = (TextView) findViewById(R.id.tv_birthday);

        rl_tel = (RelativeLayout) findViewById(R.id.rl_tel);
        tv_tel = (TextView) findViewById(R.id.tv_tel);

        bt_sure = (Button) findViewById(R.id.bt_sure);
        SharedPreferences preferences = getSharedPreferences("userlogin", Context.MODE_PRIVATE);
        id = preferences.getLong("id",0);


        studentInfo(id);


        rl_trueName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(StudentInfoActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(StudentInfoActivity.this);
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
                final EditText editText = new EditText(StudentInfoActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(StudentInfoActivity.this);
                builder.setTitle("学号");
                builder.setView(editText);
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
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

        rl_classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(StudentInfoActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(StudentInfoActivity.this);
                builder.setTitle("班级");
                builder.setView(editText);
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                editText.setText(tv_classes.getText().toString());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_classes.setText(editText.getText().toString());
                        classes = editText.getText().toString();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        rl_sex.setOnClickListener(new View.OnClickListener() {
            private int IDSex;

            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(StudentInfoActivity.this);
                final String[] data = {"男", "女"};
                builder.setTitle("性别");
                builder.setSingleChoiceItems(data, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        IDSex = which;
                        Toast.makeText(StudentInfoActivity.this, data[which], Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_sex.setText(data[IDSex]);
                        sex = data[IDSex];
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        rl_dname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(StudentInfoActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(StudentInfoActivity.this);
                builder.setTitle("学院");
                builder.setView(editText);
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                editText.setText(tv_dname.getText().toString());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_dname.setText(editText.getText().toString());
                        dname = editText.getText().toString();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        rl_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(StudentInfoActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(StudentInfoActivity.this);
                builder.setTitle("生日");
                builder.setView(editText);
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                editText.setText(tv_birthday.getText().toString());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_birthday.setText(editText.getText().toString());
                        birthday = editText.getText().toString();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        rl_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(StudentInfoActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(StudentInfoActivity.this);
                builder.setTitle("联系电话");
                builder.setView(editText);
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setText(tv_tel.getText().toString());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_tel.setText(editText.getText().toString());
                        tel = editText.getText().toString();
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
                map.put("classes", classes);
                map.put("sex", sex);
                map.put("dname", dname);
                map.put("birthday", birthday);
                map.put("tel", tel);
                updateStudentInfo(id, map);
            }
        });

    }


    private void studentInfo(Long id) {
        RequestParams params = new RequestParams("http://10.1.35.9:8080/Education/GetStudentInfoServlet");
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
                    Student student = new Student();
                    student = resultData.getObject("studentInfo", Student.class);

                    tv_trueName.setText(student.getTrueName());
                    trueName = student.getTrueName();
                    tv_number.setText(student.getNumber());
                    number = student.getNumber();
                    tv_sex.setText(student.getSex());
                    sex = student.getSex();
                    tv_birthday.setText(student.getBirthday());
                    birthday = student.getBirthday();
                    tv_classes.setText(student.getClasses());
                    classes = student.getClasses();
                    tv_dname.setText(student.getDname());
                    dname = student.getDname();
                    tv_tel.setText(student.getTel());
                    tel = student.getTel();
                } else {
                    String message = jsonObject.getString("message");
                    Toast.makeText(StudentInfoActivity.this, message, Toast.LENGTH_LONG).show();
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


    private void updateStudentInfo(Long id, Map<String, String> map) {
        RequestParams params = new RequestParams("http://10.1.35.9:8080/Education/StudentCenterUpdateForApp");
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
                    Toast.makeText(StudentInfoActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                } else {
                    String message = jsonObject.getString("message");
                    Toast.makeText(StudentInfoActivity.this, message, Toast.LENGTH_LONG).show();
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