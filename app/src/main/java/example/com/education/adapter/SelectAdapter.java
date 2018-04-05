package example.com.education.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import example.com.education.R;

/**
 * Created by ap on 2017/7/16.
 */

public class SelectAdapter extends BaseAdapter{
    private List<Map<String,Object>> data;
    private Context context;

    public SelectAdapter(Context context, List<Map<String, Object>> data){
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
        SelectAdapter.ViewHolder viewHolder = null;
        if (viewHolder == null){
            viewHolder = new SelectAdapter.ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_class_select,null);
            viewHolder.tv_student_name = (TextView) convertView.findViewById(R.id.tv_student_name);
            viewHolder.tv_studno = (TextView) convertView.findViewById(R.id.tv_studno);
            viewHolder.tv_student_message = (TextView) convertView.findViewById(R.id.tv_student_message);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (SelectAdapter.ViewHolder)convertView.getTag();
        }
        viewHolder.tv_student_name.setText((String) data.get(position).get("tv_student_name"));
        viewHolder.tv_studno.setText((String) data.get(position).get("tv_studno"));
        viewHolder.tv_student_message.setText((String) data.get(position).get("tv_student_message"));

        return convertView;
    }
    class ViewHolder{
        TextView tv_student_name;
        TextView tv_studno;
        TextView tv_student_message;
    }
}




