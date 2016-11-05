package com.example.administrator.store3.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.store3.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/31.
 */
public class NanYuCaiDanPopouRight_Adapter extends BaseAdapter{
    private Context context;
    private ArrayList<String> itemList;

    public NanYuCaiDanPopouRight_Adapter(Context context){
        this.context=context;

    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.nanyucaidanpopouright_adapter,null);
            viewHolder=new ViewHolder();
            viewHolder.textView= (TextView) convertView.findViewById(R.id.t_View2);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(itemList.get(position));


        return convertView;
    }
    class ViewHolder{
        TextView textView;
    }
}
