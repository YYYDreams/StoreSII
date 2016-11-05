package com.example.administrator.store3.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.util.Constant;

/**
 * Created by Administrator on 2016/5/24.
 */
public class ILikeDPAdapter extends BaseAdapter{
    /*上下文对象*/
    private Context context;
    /*集合*/
    private int[]list={1,2,3,4,5};
    /*构造方法*/
    public ILikeDPAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return list[position];
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
            convertView=View.inflate(context, R.layout.dianpu_fragment_item,null);
            /*初始化item控件*/
            viewHolder=new ViewHolder();
           /* LinearLayout ll= (LinearLayout) convertView.findViewById(R.id.ll_linear);
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) ll
                    .getLayoutParams();
            linearParams.height = (int) (Constant.displayHeight * 0.1f + 0.5f);
            ll.setLayoutParams(linearParams);*/
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.image_sss);
            viewHolder.dianming= (TextView) convertView.findViewById(R.id.text_sss);
            viewHolder.shangxinzhi= (TextView) convertView.findViewById(R.id.text_xxx);
            viewHolder.button= (Button) convertView.findViewById(R.id.button_sss);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    /*实体类*/
    class ViewHolder{
        ImageView imageView;
        TextView dianming;
        TextView shangxinzhi;
        Button button;

    }
}
