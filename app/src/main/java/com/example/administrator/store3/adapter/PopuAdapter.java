package com.example.administrator.store3.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.store3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/13.
 * 弹窗适配器
 */
public class PopuAdapter extends BaseAdapter {
    /*上下文对象*/
    private Context context;
    /*文字集合*/
    private List<String>list=new ArrayList<>();
    /*适配器构造方法*/
    public PopuAdapter(Context context, List<String> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            /*加载item布局*/
            convertView=View.inflate(context, R.layout.popup_window1_item,null);
            /*初始化控件*/
            viewHolder=new ViewHolder();
            viewHolder.textView= (TextView) convertView.findViewById(R.id.textvieW);
            viewHolder.textView.setText(list.get(position));
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
            viewHolder.textView.setText(list.get(position));
        }
        viewHolder.textView.setText(list.get(position));
        return convertView;
    }
    class ViewHolder{
        TextView textView;
    }

}
