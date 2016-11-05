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
 * Created by Administrator on 2016/7/4.
 * 带有标记状态的弹窗适配器
 */
public class PopupAdapter extends BaseAdapter {
    /*boolean数列,用来标记列表项*/
    private SparseBooleanArray selected;
    /*状态标记*/
    private boolean isSingle = true;
    /*下标记录*/
    private int old = -1;
    /*文字集合*/
    private String[] dis;
    /*上下文对象*/
    private Context context;
    /*构造方法*/
    public PopupAdapter(Context contexts,String[]dis) {
        this.dis=dis;
        this.context = contexts;
        selected = new SparseBooleanArray();
    }

    @Override
    public int getCount() {
        return this.dis.length;
    }

    @Override
    public Object getItem(int position) {
        return this.dis[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            /*加载布局*/
            convertView= View.inflate(context, R.layout.pop_item,null);
            /*初始化控件*/
            viewHolder=new ViewHolder();
            viewHolder.tv= (TextView) convertView.findViewById(R.id.text_pop);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.image_pop);
            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();
        //根据选中的位置,来设置选中项的字体颜色
        if(selected.get(position)){
            viewHolder.tv.setTextColor(context.getResources().getColor(R.color.bobo));
            viewHolder.iv.setVisibility(View.VISIBLE);
        }else {
            viewHolder.tv.setTextColor(context.getResources().getColor(R.color.huise));
            viewHolder.iv.setVisibility(View.INVISIBLE);
        }
        viewHolder.tv.setText(this.dis[position]);

        return convertView;
    }
    class ViewHolder{
        TextView tv;
        ImageView iv;
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
