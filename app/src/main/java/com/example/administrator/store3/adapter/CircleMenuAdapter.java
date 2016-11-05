package com.example.administrator.store3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.util.CircleMenuItem;


import java.util.List;

/**
 * Created by wanghao on 2015/11/25.
 * 圆形菜单适配器
 */
public class CircleMenuAdapter extends BaseAdapter {
    /*菜单集合*/
    private List<CircleMenuItem> mCircleMenuItems;
    /*适配器构造方法*/
    public CircleMenuAdapter(List<CircleMenuItem> mCircleMenuItems) {
        this.mCircleMenuItems = mCircleMenuItems;
    }

    @Override
    public int getCount() {
        return mCircleMenuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mCircleMenuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            /*加载item布局*/
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.circle_menu_item,parent,false);
            /*初始化item控件*/
            viewHolder = new ViewHolder();
            viewHolder.iv =(ImageView)convertView.findViewById(R.id.img_item);
            viewHolder.tv = (TextView)convertView.findViewById(R.id.txt_item);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /*给圆形菜单添加数据*/
        CircleMenuItem circleMenuItem = mCircleMenuItems.get(position);
        viewHolder.iv.setImageResource(circleMenuItem.imageId);
        viewHolder.tv.setText(circleMenuItem.title);
        return convertView;
    }
    class ViewHolder{
        ImageView iv;
        TextView tv;
    }
}
