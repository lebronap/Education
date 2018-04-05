package example.com.education.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import example.com.education.R;


/**
 * Created by ap on 2017/6/23.
 */

public class MyAdapter extends BaseAdapter {

    private List<Map<String,Object>> data;
    private Context context;

    public MyAdapter(Context context, List<Map<String, Object>> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_class,null);
            viewHolder.iv_good = (ImageView) convertView.findViewById(R.id.iv_good);
            viewHolder.tv_class_name = (TextView) convertView.findViewById(R.id.tv_class_name);
            viewHolder.tv_student_number = (TextView) convertView.findViewById(R.id.tv_student_number);
            viewHolder.tv_class_teacher = (TextView) convertView.findViewById(R.id.tv_class_teacher);
            viewHolder.tv_monitor = (TextView) convertView.findViewById(R.id.tv_monitor);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.iv_good.setImageResource((Integer) data.get(position).get("iv_good"));
        viewHolder.tv_class_name.setText((String) data.get(position).get("tv_class_name"));
        viewHolder.tv_student_number.setText((String) data.get(position).get("tv_student_number"));
        viewHolder.tv_class_teacher.setText((String) data.get(position).get("tv_class_teacher"));
        viewHolder.tv_monitor.setText((String) data.get(position).get("tv_monitor"));

        return convertView;
    }
    class ViewHolder{
        ImageView iv_good;
        TextView tv_class_name;
        TextView tv_student_number;
        TextView tv_class_teacher;
        TextView tv_monitor;
    }
}

