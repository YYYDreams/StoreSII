package com.example.administrator.store3.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.store3.R;

/**
 * Created by Administrator on 2016/7/15.
 */
public class PayAdapter extends BaseAdapter {
    /*boolean数列,用来标记列表项*/
    private SparseBooleanArray selected;
    /*状态标记*/
    private boolean isSingle = true;
    /*下标记录*/
    private int old = -1;
    /*上下文对象*/
    private Context context;
    /*文字集合*/
    private String[]banks ;
    /*构造器*/
    public PayAdapter(Context context, String[] banks) {
        this.context = context;
        this.banks = banks;
        selected = new SparseBooleanArray();
    }

    @Override
    public int getCount() {
        return banks.length;
    }

    @Override
    public Object getItem(int position) {
        return banks[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            /*加载item布局*/
            convertView = View.inflate(context, R.layout.fukuan_item,null);
            /*初始化控件*/
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.text);
            viewHolder.imageView1 = (ImageView) convertView.findViewById(R.id.image2);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /*加载数据*/
        viewHolder.textView.setText(banks[position]);
        //根据选中的位置,来设置选中项的字体颜色
        if(selected.get(position)){
            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.bobo));
            viewHolder.imageView1.setVisibility(View.VISIBLE);
        }else {
            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.huise));
            viewHolder.imageView1.setVisibility(View.GONE);
        }
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
        ImageView imageView1;
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
