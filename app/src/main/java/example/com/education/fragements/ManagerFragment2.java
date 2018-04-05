package example.com.education.fragements;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.com.education.R;
import example.com.education.adapter.SelectAdapter;
import example.com.education.bean.SelectStudent;

/**
 * Created by ap on 2017/7/14.
 */

public class ManagerFragment2 extends Fragment {

    private SelectAdapter adapter;
    private List<Map<String,Object>> data;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager2, container,false);
        listView = (ListView) view.findViewById(R.id.ll_listview);
        SharedPreferences preferences = getActivity().getSharedPreferences("userlogin", Context.MODE_PRIVATE);
        String classId = preferences.getString("classId","");
        setData(classId);
       /* adapter = new SelectAdapter(getActivity(),data);
        listView.setAdapter(adapter);*/
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), UpdateInfoActivity.class);
                intent.putExtra("student_message_id",position);
                startActivity(intent);
            }
        });*/
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "长按的是第"+ position +"项", Toast.LENGTH_LONG).show();
                return  false;
            }
        });

        return view;
    }

    private void setData(String classId) {
        RequestParams params = new RequestParams("http://10.1.35.9:8080/Education/SelectStudentMessageForAppServlet");
        params.addQueryStringParameter("classId",classId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("bean","resilt="+result);
                JSONObject jsonObject = JSON.parseObject(result);
                int code1 = jsonObject.getInteger("code");
                if (code1 == 200) {
                    JSONArray list = jsonObject.getJSONArray("result");
                    data = new ArrayList<>();
                    for (int i = 0 ; i < list.size() ; i ++ ){
                        Log.e("bean","i="+i);
                        SelectStudent student = new SelectStudent();
                        student = ((JSONObject)list.get(i)).getObject("studentInfo", SelectStudent.class);
                        String studentName = student.getStudentName();
                        String studno = student.getStudno();
                        String message = student.getMessage();
                        Map<String, Object> map = new HashMap<>();
                        map.put("tv_student_name", "姓名：" + studentName);
                        map.put("tv_studno", "学号：" + studno);
                        map.put("tv_student_message", "记录：" + message);
                        data.add(map);
                    }
                    adapter = new SelectAdapter(getActivity(), data);
                    listView.setAdapter(adapter);
                } else {
                    String message = jsonObject.getString("message");
                    Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_LONG).show();
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
