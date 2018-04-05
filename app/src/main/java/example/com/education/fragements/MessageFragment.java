package example.com.education.fragements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import example.com.education.R;
import example.com.education.activities.WebActivity;
import example.com.education.adapter.NewAdapter;
import example.com.education.bean.Meinv;

/**
 * Created by ap on 2017/7/6.
 */

public class MessageFragment extends Fragment {

    private static final String TAG = "MessageFragment";

    private ListView lv_news;
    private List<Meinv> list;
    private NewAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    int xnum = 10;
    public MessageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_message, container, false);

        lv_news = (ListView) view.findViewById(R.id.lv_news);

        getData(xnum);
        adapter = new NewAdapter(getActivity(), list);
        lv_news.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorYellow,R.color.colorGreen);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lv_news.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xnum=xnum+7;
                        getData(xnum);
                        adapter = new NewAdapter(getActivity(), list);
                        lv_news.setAdapter(adapter);
                    }
                },2500);

                adapter.notifyDataSetChanged();

                //swipeRefreshLayout.setRefreshing(true);
            }
        });

        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", list.get(position).getUrl());
                startActivity(intent);
            }
        });

        return view;
    }

    private void getData(int num) {
        list = new ArrayList<>();
        RequestParams params = new RequestParams("http://api.tianapi.com/it/");
        params.addQueryStringParameter("key","385c5d3d64e8d084a30d147907afb5a9");
        params.addParameter("num",num);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析result

                Log.i(TAG, "onSuccess: " + result);
                JSONObject jsonObject = JSON.parseObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("newslist");
                for (Object object: jsonArray) {
                    JSONObject object1  = (JSONObject)object;
                    Meinv meinv = new Meinv();
                    meinv.setCtime(object1.get("ctime").toString());
                    meinv.setTitle(object1.get("title").toString());
                    meinv.setDescription(object1.get("description").toString());
                    meinv.setPicUrl(object1.get("picUrl").toString());
                    meinv.setUrl(object1.get("url").toString());
                    list.add(meinv);
                }
                Collections.reverse(list);
                //adapter.refresh(list);
                adapter = new NewAdapter(getActivity(), list);
                lv_news.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Log.e(TAG, "onError: "+ex.getMessage());

            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                Log.e(TAG, "onError: "+cex.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onFinished() {
            }
        });

    }

}
