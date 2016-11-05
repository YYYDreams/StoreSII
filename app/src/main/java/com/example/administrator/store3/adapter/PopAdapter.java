package com.example.administrator.store3.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.fragment.NanyuFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/16.
 */
public class PopAdapter extends BaseAdapter {
    //上下文
    private Context context;
   /* *//*文字集合*//*
    private List<String> list=new ArrayList<>();*/
    //适配的构造方法
    String[]text={"衣服","裤子","上衣","外套","背心","秋裤","帽子","夹克",
           "超大运动裤","裤子","上衣","外套","背心","秋裤","帽子","夹克",
           "超大运动裤","裤子","上衣","外套","背心","秋裤","帽子","夹克","超大运动裤"};

   public PopAdapter(Context context){
       this.context=context;

   }

    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public Object getItem(int position) {
        return text[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            /*加载item布局*/
            convertView=View.inflate(context,R.layout.pop_adapter,null);
            /*初始化控件*/
            viewHolder=new ViewHolder();
            viewHolder.textView= (TextView) convertView.findViewById(R.id.text_pop1);
            viewHolder.textView.setText(text[position]);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
            viewHolder.textView.setText(text[position]);
        }
        viewHolder.textView.setText(text[position]);
        return convertView;
    }
    class ViewHolder{
        TextView textView;
    }
}
