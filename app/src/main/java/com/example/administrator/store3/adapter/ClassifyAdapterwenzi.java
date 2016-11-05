package com.example.administrator.store3.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.entity.ClassifyWenZi;

import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
//显示文字的Adapter
public class ClassifyAdapterwenzi extends BaseAdapter {
    Context context;
    List<ClassifyWenZi> classifyWenZi;
    SparseBooleanArray selected;   //boolean数列,用来标记列表项
    boolean isSingle = true;   //状态标记
    int old = -1;
    int d;



    public ClassifyAdapterwenzi(Context context, List<ClassifyWenZi> classifyWenZi,int d){
        this.context=context;
        this.classifyWenZi=classifyWenZi;
        this.d=d;
        selected=new SparseBooleanArray();

    }


    @Override
    public int getCount() {

        return classifyWenZi.size();
    }

    @Override
    public Object getItem(int position) {
        return classifyWenZi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.class_textview,null);
            viewHolder=new ViewHolder();
           viewHolder.tView= (TextView) convertView.findViewById(R.id.t_View);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();

        }
        viewHolder.tView.setText(classifyWenZi.get(position).getName());

        //根据选中的位置,来设置选中项的字体颜色
        if(selected.get(position)){
            viewHolder.tView.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }else {
            viewHolder.tView.setTextColor(context.getResources().getColor(R.color.huise));
        }

        return convertView;

    }


    class ViewHolder {
        TextView tView;
    }
    /**
     * item的选中回调方法
     * */
    public void setSelectedItem(int selected){
        if(isSingle = true && old != -1){
            this.selected.put(old, false);
        }
        this.selected.put(selected, true);
        old = selected;
    }

}
















