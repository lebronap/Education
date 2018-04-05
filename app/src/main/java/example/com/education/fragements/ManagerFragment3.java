package example.com.education.fragements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.com.education.R;
import example.com.education.activities.ClassInfoActivity;
import example.com.education.adapter.MyAdapter;

/**
 * Created by ap on 2017/7/15.
 */

public class ManagerFragment3 extends Fragment {

    private MyAdapter adapter;
    private List<Map<String, Object>> data;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager3, container, false);
        listView = (ListView) view.findViewById(R.id.ll_listview);
        setData();
        adapter = new MyAdapter(getActivity(), data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ClassInfoActivity.class);
                intent.putExtra("classId", position + 1);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "长按的是第" + position + 1 + "项", Toast.LENGTH_LONG).show();
                return false;
            }
        });


        return view;
    }

    private void setData() {
        data = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("iv_good",R.mipmap.classone);
        map.put("tv_class_name","软件1501班");
        map.put("tv_student_number","人数：30");
        map.put("tv_class_teacher","班主任：张老师");
        map.put("tv_monitor","班长：张同学");
        data.add(map);

        Map<String,Object> map1 = new HashMap<>();
        map1.put("iv_good",R.mipmap.classtwo);
        map1.put("tv_class_name","软件1502班");
        map1.put("tv_student_number","人数：30");
        map1.put("tv_class_teacher","班主任：李老师");
        map1.put("tv_monitor","班长：王同学");
        data.add(map1);

        Map<String,Object> map2 = new HashMap<>();
        map2.put("iv_good",R.mipmap.classthree);
        map2.put("tv_class_name","软件1503班");
        map2.put("tv_student_number","人数：30");
        map2.put("tv_class_teacher","班主任：陈老师");
        map2.put("tv_monitor","班长：曾同学");
        data.add(map2);

        Map<String,Object> map3 = new HashMap<>();
        map3.put("iv_good",R.mipmap.classfour);
        map3.put("tv_class_name","软件1504班");
        map3.put("tv_student_number","人数：30");
        map3.put("tv_class_teacher","班主任：李老师");
        map3.put("tv_monitor","班长：王同学");
        data.add(map3);

        Map<String,Object> map4 = new HashMap<>();
        map4.put("iv_good",R.mipmap.classfive);
        map4.put("tv_class_name","软件1505班");
        map4.put("tv_student_number","人数：30");
        map4.put("tv_class_teacher","班主任：李老师");
        map4.put("tv_monitor","班长：王同学");
        data.add(map4);

        Map<String,Object> map5 = new HashMap<>();
        map5.put("iv_good",R.mipmap.classsix);
        map5.put("tv_class_name","软件1506班");
        map5.put("tv_student_number","人数：30");
        map5.put("tv_class_teacher","班主任：李老师");
        map5.put("tv_monitor","班长：王同学");
        data.add(map5);

        Map<String,Object> map6 = new HashMap<>();
        map6.put("iv_good",R.mipmap.classseven);
        map6.put("tv_class_name","软件1507班");
        map6.put("tv_student_number","人数：30");
        map6.put("tv_class_teacher","班主任：李老师");
        map6.put("tv_monitor","班长：王同学");
        data.add(map6);

        Map<String,Object> map7 = new HashMap<>();
        map7.put("iv_good",R.mipmap.classeight);
        map7.put("tv_class_name","软件1508班");
        map7.put("tv_student_number","人数：30");
        map7.put("tv_class_teacher","班主任：李老师");
        map7.put("tv_monitor","班长：王同学");
        data.add(map7);

        Map<String,Object> map8 = new HashMap<>();
        map8.put("iv_good",R.mipmap.classnine);
        map8.put("tv_class_name","软件1509班");
        map8.put("tv_student_number","人数：30");
        map8.put("tv_class_teacher","班主任：李老师");
        map8.put("tv_monitor","班长：王同学");
        data.add(map8);

        Map<String,Object> map9 = new HashMap<>();
        map9.put("iv_good",R.mipmap.classten);
        map9.put("tv_class_name","软件1510班");
        map9.put("tv_student_number","人数：30");
        map9.put("tv_class_teacher","班主任：李老师");
        map9.put("tv_monitor","班长：王同学");
        data.add(map9);

        Map<String,Object> map10 = new HashMap<>();
        map10.put("iv_good",R.mipmap.classeleven);
        map10.put("tv_class_name","软件1511班");
        map10.put("tv_student_number","人数：30");
        map10.put("tv_class_teacher","班主任：李老师");
        map10.put("tv_monitor","班长：王同学");
        data.add(map10);

        Map<String,Object> map11 = new HashMap<>();
        map11.put("iv_good",R.mipmap.classtwelve);
        map11.put("tv_class_name","软件1512班");
        map11.put("tv_student_number","人数：30");
        map11.put("tv_class_teacher","班主任：李老师");
        map11.put("tv_monitor","班长：王同学");
        data.add(map11);


    }

}