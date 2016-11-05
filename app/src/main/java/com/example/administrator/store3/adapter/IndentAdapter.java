package com.example.administrator.store3.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.store3.R;

/**
 * Created by Administrator on 2016/7/15.
 * 定单适配器
 */
public class IndentAdapter extends BaseAdapter {
    /*上下文对象*/
    private Context context;
    /*构造方法*/
    public IndentAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView ==null){
            convertView = View.inflate(context, R.layout.indent_item,null);
        }
        return convertView;
    }
}
