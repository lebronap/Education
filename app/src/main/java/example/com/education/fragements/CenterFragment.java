package example.com.education.fragements;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;
import java.util.Map;

import example.com.education.R;
import example.com.education.activities.ChangePasswordActivity;
import example.com.education.activities.MyInfoActivity;
import example.com.education.activities.PatrollerInfoActivity;
import example.com.education.activities.StudentInfoActivity;
import example.com.education.activities.TeacherInfoActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ap on 2017/7/6.
 */

public class CenterFragment extends Fragment {


    private ImageView iv_head;
    private TextView tv_name;
    private ImageView iv_qr_code;
    private List<Map<String,Object>> data;
    private RelativeLayout rl_all_message;
    private RelativeLayout rl_up_password;
    private RelativeLayout rl_info_hand_password;
    private RelativeLayout rl_check_updates;
    private RelativeLayout rl_setting;
    private Long id;
    private String trueName;
    private int roleType;
    //private String headUri1;
    private BroadcastReceiver mBroadcastReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_center,container,false);
        /*mBroadcastReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(action.equals("changeSuccess")){
                    getHeadUri(id,roleType);
                }
            }

        };
        registerBoradcastReceiver();*/

        iv_head = (ImageView) view.findViewById(R.id.iv_head);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        iv_qr_code = (ImageView) view.findViewById(R.id.iv_qr_code);
        rl_all_message = (RelativeLayout) view.findViewById(R.id.rl_all_message);
        rl_up_password = (RelativeLayout) view.findViewById(R.id.rl_up_password);
        rl_info_hand_password = (RelativeLayout) view.findViewById(R.id.rl_info_hand_password);
        rl_check_updates = (RelativeLayout) view.findViewById(R.id.rl_check_updates);
        rl_setting = (RelativeLayout) view.findViewById(R.id.rl_setting);

        SharedPreferences preferences = getActivity().getSharedPreferences("userlogin", Context.MODE_PRIVATE);
        id = preferences.getLong("id",0);
        roleType = preferences.getInt("roleType",0);
        trueName = preferences.getString("trueName","");
        tv_name.setText(trueName);

        getHeadUri(id,roleType);

        iv_qr_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("我的二维码");
                builder.setView(R.layout.dialog);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyInfoActivity.class);
                //intent.putExtra("headUri",headUri1);
                startActivity(intent);
            }
        });

        rl_all_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roleType == 1) {
                    Intent intent = new Intent(getActivity(), StudentInfoActivity.class);
                    startActivityForResult(intent, 1);
                } else if (roleType == 2){
                    Intent intent = new Intent(getActivity(), TeacherInfoActivity.class);
                    startActivityForResult(intent, 1);
                } else {
                    Intent intent = new Intent(getActivity(), PatrollerInfoActivity.class);
                    startActivityForResult(intent, 1);
                }
            }
        });

        rl_up_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ChangePasswordActivity.class));
            }
        });

        rl_check_updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "最新版本，无需更新", Toast.LENGTH_SHORT).show();
            }
        });

        rl_info_hand_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://jwc.tjpu.edu.cn/"));
                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            iv_head.setImageURI(Uri.parse(data.getStringExtra("imageUri")));
            Toast.makeText(getActivity(), "更换成功", Toast.LENGTH_SHORT).show();
        }
    }

    private void getHeadUri(Long id, int roleType) {

        RequestParams params = new RequestParams("http://10.1.35.9:8080/Education/GetHeadServlet");
        params.addParameter("id",id);
        params.addParameter("roleType",roleType);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = JSON.parseObject(result);
                int code = jsonObject.getInteger("code");
                if(code == 200) {
                    JSONObject resultData = jsonObject.getJSONObject("result");
                    String headUri = resultData.getString("headUri");
                    //headUri1=headUri;
                    iv_head.setImageURI(Uri.parse(headUri));
                } else {
                    String message = jsonObject.getString("message");
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
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
        myIntentFilter.addAction("changeSuccess");
        //注册广播
        getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
    }*/

}
