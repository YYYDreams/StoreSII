package com.example.administrator.store3.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.entity.StoreLeiBie;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/22.
 *
 */
public class LV1_Fg1Adapter extends BaseAdapter {
    /*店铺类别数组*/
     private ArrayList<StoreLeiBie>list;
    /*上下文对象*/
     private Context context;
    /*选择状态数组*/
     private SparseBooleanArray selected;
    /*只能单选*/
     private boolean isSingle = true;
    /*上次选中的下标*/
     private int old = -1;
    /*构造方法*/
     public LV1_Fg1Adapter(Context context, ArrayList<StoreLeiBie> list) {
         this.context=context;
         this.list = list;
         selected = new SparseBooleanArray();
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
             /*加载item主布局*/
             convertView= LayoutInflater.from(context).inflate(R.layout.merchant_layout, null);
             /*初始化控件*/
             viewHolder=new ViewHolder();
             viewHolder.textView= (TextView) convertView.findViewById(R.id.text_merchant);
             convertView.setTag(viewHolder);
         }else {
             viewHolder= (ViewHolder) convertView.getTag();
         }
         if(selected.get(position)){
             /*判断是否被选中,给textview设置字体颜色*/
             viewHolder.textView.setTextColor(context.getResources().getColor(R.color.colorAccent));
         }else {
             viewHolder.textView.setTextColor(context.getResources().getColor(R.color.huise));
         }
         viewHolder.textView.setText(list.get(position).getName());
         return convertView;
     }
    /*此方法用于item选择标记*/
     public void setSelectedItem(int selected){
         if(isSingle = true && old != -1){
             this.selected.put(old, false);
         }
         this.selected.put(selected, true);
         old = selected;
     }

     class ViewHolder{
         TextView textView;
     }

}

