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
import example.com.education.bean.Teacher;

public class TeacherInfoActivity extends AppCompatActivity {

    private static final String TAG = "StudentInfoActivity";
    Map<String, String> map = new HashMap<>();
    private static String trueName;
    private static String number;
    private static String classes;
    private static String sex;
    private static String depart;
    private static String birthday;
    private static String prof;
    private static String address;

    private RelativeLayout rl_trueName;
    private TextView tv_trueName;
    private RelativeLayout rl_number;
    private TextView tv_number;
    private RelativeLayout rl_classes;
    private TextView tv_classes;
    private RelativeLayout rl_sex;
    private TextView tv_sex;
    private RelativeLayout rl_depart;
    private TextView tv_depart;
    private RelativeLayout rl_birthday;
    private TextView tv_birthday;
    private RelativeLayout rl_prof;
    private TextView tv_prof;
    private RelativeLayout rl_address;
    private TextView tv_address;
    private Button bt_sure;
    private Long id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_info);
        initView();
        SharedPreferences preferences = getSharedPreferences("userlogin", Context.MODE_PRIVATE);
        id = preferences.getLong("id",0);

        teacherInfo(id);
        rl_trueName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(TeacherInfoActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(TeacherInfoActivity.this);
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
                final EditText editText = new EditText(TeacherInfoActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(TeacherInfoActivity.this);
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
                final EditText editText = new EditText(TeacherInfoActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(TeacherInfoActivity.this);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(TeacherInfoActivity.this);
                final String[] data = {"男", "女"};
                builder.setTitle("性别");
                builder.setSingleChoiceItems(data, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        IDSex = which;
                        Toast.makeText(TeacherInfoActivity.this, data[which], Toast.LENGTH_SHORT).show();
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

        rl_depart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(TeacherInfoActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(TeacherInfoActivity.this);
                builder.setTitle("部门");
                builder.setView(editText);
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                editText.setText(tv_depart.getText().toString());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_depart.setText(editText.getText().toString());
                        depart = editText.getText().toString();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        rl_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(TeacherInfoActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(TeacherInfoActivity.this);
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

        rl_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(TeacherInfoActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(TeacherInfoActivity.this);
                builder.setTitle("职称");
                builder.setView(editText);
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                editText.setText(tv_prof.getText().toString());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_prof.setText(editText.getText().toString());
                        prof = editText.getText().toString();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        rl_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(TeacherInfoActivity.this);
                final AlertDialog.Builder builder = new AlertDialog.Builder(TeacherInfoActivity.this);
                builder.setTitle("地址");
                builder.setView(editText);
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                editText.setText(tv_address.getText().toString());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_address.setText(editText.getText().toString());
                        address = editText.getText().toString();
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
                map.put("depart", depart);
                map.put("birthday", birthday);
                map.put("prof",prof);
                map.put("address", address);
                updateTeacherInfo(id, map);
            }
        });

    }

    private void updateTeacherInfo(Long id, Map<String, String> map) {
        RequestParams params = new RequestParams("http://10.1.35.9:8080/Education/TeacherCenterUpdateForApp");
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
                    Toast.makeText(TeacherInfoActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                } else {
                    String message = jsonObject.getString("message");
                    Toast.makeText(TeacherInfoActivity.this, message, Toast.LENGTH_LONG).show();
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



    private void teacherInfo(Long id) {
        RequestParams params = new RequestParams("http://10.1.35.9:8080/Education/GetTeacherInfoServlet");
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
                    Teacher teacher = new Teacher();
                    teacher = resultData.getObject("TeacherInfo", Teacher.class);

                    tv_trueName.setText(teacher.getTrueName());
                    trueName = teacher.getTrueName();
                    tv_number.setText(teacher.getNumber());
                    number = teacher.getNumber();
                    tv_sex.setText(teacher.getSex());
                    sex = teacher.getSex();
                    tv_birthday.setText(teacher.getBirthday());
                    birthday = teacher.getBirthday();
                    tv_classes.setText(teacher.getClasses());
                    classes = teacher.getClasses();
                    tv_depart.setText(teacher.getDepart());
                    depart = teacher.getDepart();
                    tv_prof.setText(teacher.getProf());
                    prof = teacher.getProf();
                    tv_address.setText(teacher.getAddress());
                    address = teacher.getAddress();
                } else {
                    String message = jsonObject.getString("message");
                    Toast.makeText(TeacherInfoActivity.this, message, Toast.LENGTH_LONG).show();
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

        rl_classes = (RelativeLayout) findViewById(R.id.rl_classes);
        tv_classes = (TextView) findViewById(R.id.tv_classes);

        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        tv_sex = (TextView) findViewById(R.id.tv_sex);

        rl_depart = (RelativeLayout) findViewById(R.id.rl_depart);
        tv_depart = (TextView) findViewById(R.id.tv_depart);

        rl_birthday = (RelativeLayout) findViewById(R.id.rl_birthday);
        tv_birthday = (TextView) findViewById(R.id.tv_birthday);

        rl_prof = (RelativeLayout) findViewById(R.id.rl_prof);
        tv_prof = (TextView) findViewById(R.id.tv_prof);

        rl_address = (RelativeLayout) findViewById(R.id.rl_address);
        tv_address = (TextView) findViewById(R.id.tv_address);

        bt_sure = (Button) findViewById(R.id.bt_sure);
    }
}
