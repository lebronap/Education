package example.com.education.fragements;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import example.com.education.R;

/**
 * Created by ap on 2017/7/15.
 */

public class AddFragment3 extends Fragment{

    private EditText ed_add_name;
    private EditText ed_add_studno;
    private EditText ed_classesid;
    private EditText ed_add_message;
    private Button bt_submit;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add3, container,false);
        initView(view);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentName = ed_add_name.getText().toString();
                String studno = ed_add_studno.getText().toString();
                String studentMessage = ed_add_message.getText().toString();
                String classesId = ed_classesid.getText().toString();

                if (studentName != null && studno != null && classesId != null && studentMessage != null ){
                    addMessage(studentName,studno,classesId,studentMessage);
                } else {
                    Toast.makeText(getActivity(), "请输入完整信息", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void addMessage(String studentName, String studno,String classesId, String studentMessage) {
        RequestParams params = new RequestParams("http://10.1.35.9:8080/Education/AddPatrolForAppServlet");
        params.addQueryStringParameter("studentName",studentName);
        params.addQueryStringParameter("studno",studno);
        params.addQueryStringParameter("classesId",classesId);
        params.addQueryStringParameter("studentMessage",studentMessage);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析result
                JSONObject jsonObject = JSON.parseObject(result);
                int code = jsonObject.getInteger("code");
                if(code ==200) {
                    Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "添加失败", Toast.LENGTH_LONG).show();
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



    private void initView(View view) {
        ed_add_name = (EditText) view.findViewById(R.id.ed_add_name);
        ed_add_studno = (EditText) view.findViewById(R.id.ed_add_studno);
        ed_classesid = (EditText) view.findViewById(R.id.ed_classesid);
        ed_add_message = (EditText) view.findViewById(R.id.ed_add_message);
        bt_submit = (Button) view.findViewById(R.id.bt_submit);
    }
}
