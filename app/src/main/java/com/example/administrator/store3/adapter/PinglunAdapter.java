package com.example.administrator.store3.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.administrator.store3.R;

/**
 * Created by Administrator on 2016/5/10.
 * 评论的适配器
 */
public class PinglunAdapter extends BaseAdapter {
    /*上下文*/
    private Context context;
    /*图片集合*/
    private int images[]={R.drawable.pingluntouxiang,R.drawable.pingluntouxiang,R.drawable.pingluntouxiang};
    /*构造方法*/
    public PinglunAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            /*加载item布局*/
            convertView=View.inflate(context,R.layout.pinglun_listview_item,null);
            /*控件初始化*/
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.pinglun_image);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setBackgroundDrawable(ContextCompat.getDrawable(context, images[position]));

        return convertView;
    }
    class ViewHolder{
        private ImageView imageView;
    }
}
