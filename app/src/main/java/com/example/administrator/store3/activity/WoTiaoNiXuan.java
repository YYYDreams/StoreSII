package com.example.administrator.store3.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.administrator.store3.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/4/29.
 * 我挑你选界面
 */
public class WoTiaoNiXuan extends Activity implements GestureDetector.OnGestureListener,View.OnClickListener{
    /*自定义图片画廊器*/
    private ViewFlipper flipper;
    /*初始化图片资源*/
    private int[]picture={R.drawable.s_sp_02,R.drawable.s_sp_01,R.drawable.cart_sp_01,R.drawable.sp_01,R.drawable.s_sp_02,R.drawable.s_sp_02,R.drawable.s_sp_02,R.drawable.s_sp_02 };
    /*手势监听核心类*/
    private GestureDetector detector;
    /*画廊中显示图片所在位置*/
    private int position=0;
    /*view数组*/
    private ArrayList<View>list;
    /*返回按钮*/
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*加载主布局*/
        setContentView(R.layout.wotiaonixuan);
        /*初始化控件*/
        initView();
    }
    /*初始化控件以及绑定事件监听*/
    private void initView() {
        imageView= (ImageView) findViewById(R.id.image0);
        imageView.setOnClickListener(this);
        detector = new GestureDetector(this);
        flipper= (ViewFlipper) findViewById(R.id.viewflipper4);
        list=new ArrayList<>();

        for(int i=0;i<picture.length;i++){
           // flipper.addView(addNextView(picture[i]));
            list.add(addNextView(picture[i]));
        }
        for (int i=0;i<picture.length;i++){
            flipper.addView(list.get(i));
        }
        //绑定监听
        flipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });

    }
    /*添加下一张图片*/
    public View addNextView(int id){
        View view=View.inflate(this,R.layout.wotiaonixuan_item,null);
        ViewHolder viewHolder=new ViewHolder();
        viewHolder.imageView= (ImageView) view.findViewById(R.id.imagess);
        viewHolder.imageView.setBackgroundDrawable(ContextCompat.getDrawable(this,id));
        viewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(this,id));
        return  view;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }
    @Override
    public void onLongPress(MotionEvent e) {
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() >= 120) {
            /*当触摸向左超过120,显示图片滑出的动画效果*/
            this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
            this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
            /*显示下一张图片*/
            this.flipper.showNext();
            /*移出画廊中滑出的图片*/
            flipper.removeView(list.get(position));
            position++;
            return true;
        } else if (e1.getX() - e2.getX() < -120) {
            /*当触摸向左超过120,显示图片滑出的动画效果*/
            this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
            this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
            /*显示下一张图片*/
            this.flipper.showNext();
            /*移出画廊中滑出的图片*/
            flipper.removeView(list.get(position));
            position++;
            return true;
        }

        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        detector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
    class ViewHolder{
        ImageView imageView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image0:
                /*点击返回按钮销毁主界面*/
                finish();
                break;
        }

    }

}
