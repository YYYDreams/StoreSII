package com.example.administrator.store3.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.administrator.store3.R;
import java.util.Random;

/**
 * Created by Administrator on 2016/5/21.
 * 自定义全部订单适配器
 */
public class AllIndentAdapter extends BaseAdapter {
    /*初始化图片资源*/
    private int images[]={R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo,R.drawable.logo,};
    /*主界面上下文*/
    private Context context;
    /*等待付款状态*/
    private static int WAIT_PAYFOE = 0;
    /*等待发货状态*/
    private static int WAIT_SHIPMENTS=1;
    /*等待收货状态*/
    private static int WAIT_TAKEOVERGOODS=2;
    /*交易成功状态*/
    private static int TRADE_SECCESSFUL=3;
    /*交易完成状态*/
    private static int AFTER_SALE=4;
    /*状态值*/
    private int stat;

    /*适配器构造方法*/
    public AllIndentAdapter(Context context) {
        this.context=context;
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
        /*随机状态值0到4*/
        stat=new Random().nextInt(5);
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=View.inflate(context,R.layout.quanbudd_adapter,null);
            viewHolder=new ViewHolder();
            //viewHolder.imageView= (ImageView) convertView.findViewById(R.id.image6);
            viewHolder.listView = (ListView) convertView.findViewById(R.id.listview);
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.t_View1);
            viewHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.ll1);
            viewHolder.mTextView_a = (TextView) convertView.findViewById(R.id.t_View7);
            viewHolder.mTextView_b = (TextView) convertView.findViewById(R.id.t_View8);
            viewHolder.mTextView_c = (TextView) convertView.findViewById(R.id.t_View9);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        //viewHolder.imageView.setImageResource(images[position]);
        /*根据状态只设置按钮的功能*/
        if(stat==WAIT_PAYFOE){
            viewHolder.linearLayout.setVisibility(View.GONE);
            viewHolder.mTextView.setText("待付款");
            viewHolder.mTextView_a.setVisibility(View.GONE);
            viewHolder.mTextView_b.setText("取消订单");
            viewHolder.mTextView_c.setText("去付款");
        }else if(stat==WAIT_SHIPMENTS){
            viewHolder.linearLayout.setVisibility(View.GONE);
            viewHolder.mTextView.setText("待发货");
            viewHolder.mTextView_a.setVisibility(View.GONE);
            viewHolder.mTextView_b.setText("我要催单");
            viewHolder.mTextView_c.setText("查看物流");
        }else if(stat==WAIT_TAKEOVERGOODS){
            viewHolder.linearLayout.setVisibility(View.VISIBLE);
            viewHolder.mTextView.setText("待收货");
            viewHolder.mTextView_a.setVisibility(View.VISIBLE);
            viewHolder.mTextView_a.setText("我要催单");
            viewHolder.mTextView_b.setText("查看物流");
            viewHolder.mTextView_c.setText("确认收货");
        }else if(stat==TRADE_SECCESSFUL){
            viewHolder.linearLayout.setVisibility(View.GONE);
            viewHolder.mTextView.setText("交易成功");
            viewHolder.mTextView_a.setVisibility(View.GONE);
            viewHolder.mTextView_b.setText("申请售后");
            viewHolder.mTextView_c.setText("去评价");
        }else if(stat==AFTER_SALE){
            viewHolder.linearLayout.setVisibility(View.GONE);
            viewHolder.mTextView.setText("交易完成");
            viewHolder.mTextView_a.setVisibility(View.GONE);
            viewHolder.mTextView_b.setText("申请退款");
            viewHolder.mTextView_c.setText("申请售后");
        }
        /*嵌套一个商品表*/
        IndentAdapter indentAdapter = new IndentAdapter(context);
        viewHolder.listView.setAdapter(indentAdapter);
        /*计算商品列表的高度*/
        setListViewHeightBasedOnChildren(viewHolder.listView);
        return convertView;
    }
    //此方法用于计算listview的高度
    private void setListViewHeightBasedOnChildren(ListView listView) {
        android.widget.ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    /**
     * 实体类
     * */
    class ViewHolder{
        ImageView imageView;
        ListView listView;
        LinearLayout linearLayout;
        TextView mTextView;
        TextView mTextView_a;
        TextView mTextView_b;
        TextView mTextView_c;
    }
}
