package com.example.administrator.store3.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.entity.Message;

import java.util.List;

/**
 * 消息的Adapter
 * Created by Administrator on 2016/6/18.
 */
public class SwipeAdapter extends BaseAdapter {
    /*上下文对象*/
    private Context mContext=null;
    /*消息数组*/
    private List<Message> data;
    /*构造器*/
    public SwipeAdapter(Context mContext,List<Message> date){
       this.mContext=mContext;
        this.data=date;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            /*加载item布局*/
            convertView= LayoutInflater.from(mContext).inflate(R.layout.message_adapter,null);
            holder=new ViewHolder();
            /*初始化控件*/
            holder.item_left= (RelativeLayout) convertView.findViewById(R.id.item_left);

            holder.iv_icon= (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_msg= (TextView) convertView.findViewById(R.id.tv_msg);
            holder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);

            convertView.setTag(holder);
        }else{//有直接获得ViewHolder
            holder= (ViewHolder) convertView.getTag();
        }
        Log.e("TAG","getView position="+position);
        Message msg=data.get(position);

        holder.tv_title.setText(msg.getTitle());
        holder.tv_msg.setText(msg.getMsg());
        holder.tv_time.setText(msg.getTime());

        holder.iv_icon.setImageResource(msg.getIcon_id());
        return convertView;
    }

    class ViewHolder{
        RelativeLayout item_left;

        TextView tv_title;
        TextView tv_msg;
        TextView tv_time;

        ImageView iv_icon;

    }

}
