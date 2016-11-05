package com.example.administrator.store3.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.activity.MasterSonActivity;
import com.example.administrator.store3.customview.CircleImageView;
import com.example.administrator.store3.entity.Master;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/24.
 * 大师适配
 */
public class MasterAdapter extends BaseAdapter {
    /*上下文对象*/
    private Context mCtx;
    /*控件实体类对象*/
    private ViewHolder viewHolder=null;
    /*Master对象集合*/
    private List<Master>list=new ArrayList<>();
    /*构造器*/
    public MasterAdapter(Context mCtx, List<Master>list) {
        list.clear();
        this.mCtx = mCtx;
        this.list= list;
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

        if(convertView==null){
            /*加载界面*/
            convertView= View.inflate(mCtx, R.layout.sample_item,null);
            /*初始化控件*/
            viewHolder = new ViewHolder();
            viewHolder.itemTv = (TextView) convertView.findViewById(R.id.text1_master);//控件绑定xml
            viewHolder.itemIv = (CircleImageView) convertView.findViewById(R.id.thumbnail);
            viewHolder.zhan = (ImageView) convertView.findViewById(R.id.zhan);
            viewHolder.zhanshu = (TextView) convertView.findViewById(R.id.zhanshu);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.itemTv.setText(list.get(position).getName());
        viewHolder.zhanshu.setTextColor(Color.GRAY);
        MyLisener myLisener=new MyLisener(position);//初始化监听类
        ImageLoader.getInstance().displayImage(list.get(position).getImage(), viewHolder.itemIv);//将图片显示在图片控件上
        if(!list.get(position).isSelected()){
            //没有选中的图片变为默认图片
            viewHolder.zhan.setImageBitmap(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.dianzan1));
            viewHolder.zhanshu.setTextColor(Color.GRAY);//红色字体变成灰色字体
            int z =list.get(position).getPraise();
            viewHolder.zhanshu.setText(z + "");
        }else {
            //将当前选中的图片变为选中图片
            viewHolder.zhan.setImageBitmap(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.dianzan2));
            viewHolder.zhanshu.setTextColor(Color.RED);//灰色字体变成红色字体
            int z =list.get(position).getPraise()+1;
            viewHolder.zhanshu.setText(z + "");
           // list.get(position).setPraise(z);
        }
        viewHolder.itemIv.setOnClickListener(myLisener); //给图片控件添加点击监听
        viewHolder.zhan.setOnClickListener(myLisener);  //给zhan控件添加点击监听
        return convertView;
    }

    /**
     * 创建监听类
     */
    private class MyLisener implements View.OnClickListener {
        int position;
        Intent intent;
        public MyLisener(int position) {
            this.position=position;
            intent=new Intent();
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.thumbnail: //当点击的是头像时,跳转页面
                    intent.putExtra("id", list.get(position).getId());
                    intent.setClass(mCtx, MasterSonActivity.class);
                    mCtx.startActivity(intent);
                    break;
                case R.id.zhan: //当点击的是赞时
                    if(list.get(position).isSelected()){
                        list.get(position).setIsSelected(false);
                    }else {
                        list.get(position).setIsSelected(true);
                    }
                    MasterAdapter.this.notifyDataSetChanged();
                    break;
            }
        }
    }
    /**
     * item实体类
     */
  private class ViewHolder {
        public TextView itemTv;
        public CircleImageView itemIv;
        public ImageView zhan;
        public TextView zhanshu;
    }

}
