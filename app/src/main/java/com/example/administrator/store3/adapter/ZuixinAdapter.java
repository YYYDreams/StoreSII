package com.example.administrator.store3.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.store3.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/27.
 */
/*
* 南玉最新Adapter显示
* */
public class ZuixinAdapter extends BaseAdapter {
    private ArrayList<String> mNameList;
    private ArrayList<Drawable> mDrawableList;
    private LayoutInflater mInflater;
    private Context mContext;
    LinearLayout.LayoutParams params;
    //第一步，设置接口
    private View.OnClickListener onMenoy;
    //第二步，设置接口方法

    public void setOnMenoy(View.OnClickListener onMenoy) {
        this.onMenoy=onMenoy;
    }



    public ZuixinAdapter(Context context, ArrayList<String> nameList, ArrayList<Drawable> drawableList){
        mNameList = nameList;
        mDrawableList = drawableList;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;


    }
    @Override
    public int getCount() {
        return mNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ItemViewTag viewTag;

        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.zuixin_adapter,null);
            viewTag=new ItemViewTag();
            viewTag.mIcon= (ImageView) convertView.findViewById(R.id.image0);
            viewTag.mName= (TextView) convertView.findViewById(R.id.t_View111);

            //第三步,设置接口回调，注意参数不是上下文，它需要ListView所在的Activity或者Fragment处理接口回调方法
            viewTag.textView= (TextView) convertView.findViewById(R.id.t_View111);
            viewTag.textView.setOnClickListener(onMenoy);

            convertView.setTag(viewTag);
        } else
        {
            viewTag = (ItemViewTag) convertView.getTag();
        }

        // set name
        viewTag.mName.setText(mNameList.get(position));
        // set icon
        viewTag.mIcon.setBackgroundDrawable(mDrawableList.get(position));

        viewTag.mIcon.setLayoutParams(params);

        return convertView;
    }
    class ItemViewTag
    {
        private ImageView mIcon;
        private TextView mName;
        private  TextView textView;

        /**
         * The constructor to construct a navigation view tag
         *
         * @param name
         *            the name view of the item
         * @param icon
         *            the icon view of the item
         */

    }




}
















