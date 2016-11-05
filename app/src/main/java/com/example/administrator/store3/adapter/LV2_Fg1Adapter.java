package com.example.administrator.store3.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.example.administrator.store3.R;
import com.example.administrator.store3.StoreApplication;
import com.example.administrator.store3.activity.BaiduMapActivity;
import com.example.administrator.store3.activity.PictureActivity;
import com.example.administrator.store3.customview.CircleImageView;
import com.example.administrator.store3.entity.Store;
import com.example.administrator.store3.util.UrlUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/23.
 */
public class LV2_Fg1Adapter extends BaseAdapter {
    /*店铺数组*/
    private ArrayList<Store>list;
    /*上下文对象*/
    private Context context;
    /*imagloader设置*/
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.drawable.s_sp_02) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.drawable.s_sp_02) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.s_sp_02) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                    // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
            .build(); // 创建配置过得DisplayImageOption对象

    /*构造方法*/
    public LV2_Fg1Adapter(Context context, ArrayList<Store> list) {
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
        ViewHold_lv2 viewHold_lv2=null;
        MyListener myListener=null;
        if (convertView==null){
            /*加载item布局*/
            convertView=View.inflate(context, R.layout.dianpu_listview2_item,null);
            viewHold_lv2=new ViewHold_lv2();
            //可以理解为从vlist获取view  之后把view返回给ListView
            myListener=new MyListener(position);
            viewHold_lv2.Logo= (CircleImageView) convertView.findViewById(R.id.image1_lv2);
            viewHold_lv2.distance= (TextView) convertView.findViewById(R.id.distance);
            viewHold_lv2.locator= (ImageView) convertView.findViewById(R.id.locator);
            viewHold_lv2.picture= (ImageView) convertView.findViewById(R.id.image2_lv2);
            viewHold_lv2.storeName= (TextView) convertView.findViewById(R.id.storeName);
            viewHold_lv2.locator.setOnClickListener(myListener);
            viewHold_lv2.picture.setOnClickListener(myListener);
            convertView.setTag(viewHold_lv2);
        }else {
            viewHold_lv2= (ViewHold_lv2) convertView.getTag();

        }
        //加载图片
        String logo= UrlUtil.url+list.get(position).getLogo();
        String storeImg= UrlUtil.url+list.get(position).getStoreImg();
        ImageLoader.getInstance().displayImage(logo, viewHold_lv2.Logo,options);
        ImageLoader.getInstance().displayImage(storeImg, viewHold_lv2.picture, options);
        float distanse=(Float.valueOf(list.get(position).getDistace()))/1000;
        viewHold_lv2.distance.setText("距离" + distanse + "km");
        viewHold_lv2.storeName.setText(list.get(position).getStoreName());
        return convertView;
    }
    //item实体类
    class ViewHold_lv2{
        CircleImageView Logo;
        ImageView locator;
        TextView distance;
        ImageView picture;
        TextView storeName;
    }
    /*
    * 给item中的空间添加点击事件
    *
    * */
    private class MyListener implements View.OnClickListener {
        int mPosition;
        Intent intent;
        public MyListener(int inPosition){
            mPosition= inPosition;
            intent=new Intent();
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.locator:
                    /*点击定位图标,将终点坐标传给导航主页面*/
                    intent.setClass(context, BaiduMapActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putDouble("lat",Double.parseDouble(list.get(mPosition).getLatStr()));
                    bundle.putDouble("long",Double.parseDouble(list.get(mPosition).getLngStr()));
                    bundle.putString("addrs",list.get(mPosition).getAddress());
                    intent.putExtra("终点",bundle);
                    context.startActivity(intent);
                    break;
                case R.id.image2_lv2:
                    /*点击图片,跳转到子页面*/
                    intent.setClass(context, PictureActivity.class);
                    context.startActivity(intent);
                    break;
            }
        }

    }

}
