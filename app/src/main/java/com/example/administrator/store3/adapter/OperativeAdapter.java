package com.example.administrator.store3.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.store3.R;

import java.util.List;

/**
 * Created by Administrator on 2016/7/5.
 */
public class OperativeAdapter extends BaseAdapter {
    /*上下文对象*/
    private Context context;
    /*数字集合*/
    private List<Integer>list;
    /*构造器*/
    public OperativeAdapter(Context context,List<Integer>list) {
        this.context = context;
        this.list = list;
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
        ViewHolder viewHolder =null;
        if(convertView==null){
            /*加载item布局*/
            convertView = View.inflate(context,R.layout.operativa_fg1_item1,null);
            /*初始化控件*/
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_opp);
            viewHolder.name = (TextView) convertView.findViewById(R.id.text1);
            viewHolder.price = (TextView) convertView.findViewById(R.id.text2);
            viewHolder.jifen = (TextView) convertView.findViewById(R.id.text3);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /*加载图片*/
        viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(context, list.get(position)));
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }
    /*实体类*/
    class ViewHolder{
        ImageView imageView;
        TextView name;
        TextView price;
        TextView jifen;
        CheckBox checkBox;
    }
}
