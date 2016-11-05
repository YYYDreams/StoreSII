package com.example.administrator.store3.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.store3.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/29.
 */
public class SFPageAdapter extends BaseAdapter{
    Context context;
    ArrayList<Drawable>list;

    public SFPageAdapter(Context context,ArrayList<Drawable>list) {
        this.context = context;
        this.list=list;
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
        if (convertView==null){
            convertView=View.inflate(context, R.layout.brand_store_fg1_item,null);
            viewHolder=new ViewHolder();
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.image_brand_store_fg1);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();

        }
        viewHolder.imageView.setBackgroundDrawable(list.get(position));


        return convertView;
    }
    class ViewHolder{
        ImageView imageView;

    }
}
