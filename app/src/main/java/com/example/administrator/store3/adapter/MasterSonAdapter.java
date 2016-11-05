package com.example.administrator.store3.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.entity.MasterSon;
import com.example.administrator.store3.util.ImageUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/7/12.
 */
public class MasterSonAdapter extends BaseAdapter {
    List<MasterSon>list;
    Context context;
    private int[] mIconIDs = new int[]{ R.drawable.s_sp_01, R.drawable.s_sp_02, R.drawable.s_sp_01,
            R.drawable.s_sp_01, R.drawable.s_sp_02, R.drawable.s_sp_01,
            R.drawable.s_sp_02, R.drawable.s_sp_01, R.drawable.s_sp_01 };
    public MasterSonAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return mIconIDs.length;
    }

    @Override
    public Object getItem(int position) {
        return mIconIDs[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            convertView = View.inflate(context, R.layout.masterson_item,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //viewHolder.imageView.setImageResource(mIconIDs[position]);
        viewHolder.imageView.setImageBitmap(ImageUtil.readBitMap(context,mIconIDs[position]));
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
