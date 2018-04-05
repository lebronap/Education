package example.com.education.adapter;

/**
 * Created by ap on 2017/6/24.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.x;

import java.util.List;

import example.com.education.R;
import example.com.education.bean.Meinv;


/**
 * Created by David on 2017/6/24.
 */

public class NewAdapter extends BaseAdapter {


    Context mContext;
    List<Meinv> data;

    public NewAdapter(Context context, List<Meinv> data) {
        this.mContext = context;
        this.data = data;
    }

    public void refresh(List<Meinv> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_meinv, parent, false);
        ImageView iv_image = (ImageView) view.findViewById(R.id.iv_image);
        TextView tv_title = (TextView) view.findViewById(R.id.title);
        TextView tv_ctime = (TextView) view.findViewById(R.id.ctime);
        TextView tv_description = (TextView) view.findViewById(R.id.description);

        x.image().bind(iv_image, data.get(position).getPicUrl());
        tv_title.setText(data.get(position).getTitle());
        tv_ctime.setText(data.get(position).getCtime());
        tv_description.setText(data.get(position).getDescription());
        return view;
    }
}