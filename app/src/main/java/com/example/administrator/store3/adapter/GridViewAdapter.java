package com.example.administrator.store3.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.store3.R;

import java.util.ArrayList;

/**
 * gridview自定义adpter
 * Created by Administrator on 2016/4/29.
 */
public class GridViewAdapter extends BaseAdapter {
    /*图片集合*/
    private ArrayList<Drawable>list1;
    /*文字集合*/
    private ArrayList<String>list2;
    /*上下文对象*/
    private Context context;
    /*适配器实体类*/
    public GridViewAdapter(Context context,ArrayList<Drawable>list1,ArrayList<String>list2) {
        this.context = context;
        this.list1=list1;
        this.list2=list2;
    }

    @Override
    public int getCount() {
        return list1.size();
    }

    @Override
    public Object getItem(int position) {
        return list1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            /*加载item布局*/
            convertView=View.inflate(context, R.layout.gradview_item,null);
            /*初始化item控件*/
            viewHolder=new ViewHolder();
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.image_gradview);
            viewHolder.textView= (TextView) convertView.findViewById(R.id.text_gradview);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();

        }
        /*加载图片*/
        viewHolder.imageView.setBackgroundDrawable(list1.get(position));
        viewHolder.textView.setText(list2.get(position));

        return convertView;
    }
    /*控件实体类*/
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
