package com.example.administrator.store3.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.adapter.PinglunAdapter;
import com.example.administrator.store3.customview.MyListView;
import com.example.administrator.store3.customview.ObservableScrollView;
import com.example.administrator.store3.util.ScrollViewListener;

/**
 * Created by Administrator on 2016/5/9.
 * 活动详情页面
 */
public class IntroductionActivity extends Activity implements View.OnClickListener,ScrollViewListener {
    /*前景图片*/
    private ImageView img_front;
    /*查看更多按钮*/
    private TextView seeMore;
    /*评论布局*/
    private LinearLayout linearLayout;
    /*评论列表*/
    private MyListView listView;                 //自定义listview
    /*评论适配器*/
    private PinglunAdapter adapter;
    /*可以监听滑动距离的滑动条*/
    private ObservableScrollView scrollView;    //自定义scrollview
    /*旋转动画*/
    private Animation operatingAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*加载主界面布局*/
        setContentView(R.layout.introduction_main);
        /*设置动画*/
        setAnim();
        /*初始化控件*/
        setView();
    }
    /*初始化控件以及添加绑定事件*/
    private void setView() {
        scrollView= (ObservableScrollView) findViewById(R.id.scrollview);
        scrollView.setScrollViewListener(this);   //滑动条设置滑动监听,获得滑动距离
        seeMore= (TextView) findViewById(R.id.gengduo);
        linearLayout= (LinearLayout) findViewById(R.id.pinglun);
        listView= (MyListView) findViewById(R.id.pinglu_listview);
        adapter=new PinglunAdapter(this);
        listView.setAdapter(adapter);
    }
    /*设置旋转动画效果*/
    private void setAnim() {
        //旋转动画效果的设置
        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.xuanzhuantupian);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        //图片绑定旋转动画
        img_front= (ImageView) findViewById(R.id.image_introduction);
        if (operatingAnim != null) {
            img_front.startAnimation(operatingAnim);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
            }
        }).start();
    }
   int current=0;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case R.id.gengduo:   //点击展开listview,再点击收起listview
               current++;
               if(current%2==1){  //点击次数为奇数时,评论栏显示
                   linearLayout.setVisibility(View.VISIBLE);
               }else{
                   linearLayout.setVisibility(View.GONE);//点击次数为0或者偶数时,评论栏隐藏
               }
        }
    }
//对scrollview的滑动位置监听,
    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if(y>=483){
            img_front.clearAnimation();
        }else {
            img_front.startAnimation(operatingAnim);
        }


    }
//当离开此页面停止动画旋转
    @Override
    protected void onStop() {
        super.onStop();
        img_front.clearAnimation();
    }
}
