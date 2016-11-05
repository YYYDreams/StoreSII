package com.example.administrator.store3.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.activity.MyConponActivity;
import com.example.administrator.store3.entity.Conpon;
import com.example.administrator.store3.util.Constant;

import java.util.List;

/**
 * Created by Administrator on 2016/5/23.
 *
 * 优惠券适配器
 */
public class DianPuYouhuiAdapter extends BaseAdapter {
    /*上下文对象*/
    private Context context;
   // List<Conpon> list;
    /*数组*/
    private int []list={1,2,3,4,5};
    /*类别状态值*/
    private int Type;
    /*构造方法*/
    public DianPuYouhuiAdapter(Context context,int Type ) {
        this.context=context;
        this.Type = Type;
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
            convertView=View.inflate(context, R.layout.dianpuyouhui_item,null);
            /*初始化item控件*/
            viewHolder=new ViewHolder();
            //item的高度设置
            RelativeLayout ll= (RelativeLayout) convertView.findViewById(R.id.rll);
            /*//设置线性布局的属性
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) ll
                    .getLayoutParams();
            //设置高度
            linearParams.height = (int) (Constant.displayHeight * 0.1f + 0.5f);
            ll.setLayoutParams(linearParams);*/
            /*根据位置设置购物券的颜色*/
            if(position%3==0){
                //当item位置是3的倍数时,设置黄色背景
                ll.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.gouwuqian_bg));
            }else if(position%3==1){
                //当item位置是3的倍数余1时,设置绿色背景
                ll.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.gouwuquan2));
            }else if(position%3==2){
                //当item位置是3的倍数余2时,设置蓝色背景
                ll.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.gouwuqian3));
            }
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.image_conpon);
            viewHolder.textView1= (TextView) convertView.findViewById(R.id.dianpu_conpon);
            viewHolder.textView2= (TextView) convertView.findViewById(R.id.ruler_copon);
            viewHolder.textView3= (TextView) convertView.findViewById(R.id.time_compon);
            viewHolder.textView4= (TextView) convertView.findViewById(R.id.money_compon);
            viewHolder.biaoji = (ImageView) convertView.findViewById(R.id.guoqi);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        /*根据类别显示相应的是否过期是否使用的标记*/
        if(Type==0){
            viewHolder.biaoji.setImageResource(R.drawable.yiguoqi);
        }else if(Type==1){

        }else if(Type==2){
            viewHolder.biaoji.setImageResource(R.drawable.yishiyong);
        }
        return convertView;
    }
    /*控件实体类*/
   class ViewHolder{
       ImageView imageView;  //优惠券图片
       TextView textView1;   //优惠券的店铺
       TextView textView2;   //优惠券的规则
       TextView textView3;   //优惠券的使用时期
       TextView textView4;   //优惠券的额度
       ImageView biaoji;
   }


}
