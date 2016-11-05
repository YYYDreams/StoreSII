package com.example.administrator.store3.activity;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.administrator.store3.R;
import com.example.administrator.store3.adapter.GridViewAdapter;
import com.example.administrator.store3.adapter.HorizontalListViewAdapter;
import com.example.administrator.store3.customview.HorizontalListView;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/23.
 * 商店详情界面
 */
public class PictureActivity extends FragmentActivity implements GestureDetector.OnGestureListener,View.OnTouchListener,View.OnClickListener{
    /*自定义横向列表*/
    private HorizontalListView mhorizontalListView;     //横向listview
    /*横向列表适配器*/
    private HorizontalListViewAdapter horizontalListViewAdapter;   //横向的listviewadapter
    /*商品的网格列表*/
    private GridView gridView;
    /*图片展示画廊*/
    private ViewFlipper flipper;
    /*手势监听核心类*/
    private GestureDetector detector;     //手势监听核心类
    /*布局1*/
    private LinearLayout linearLayout1;
    /*布局2*/
    private LinearLayout linearLayout2;
    /*布局3*/
    private LinearLayout linearLayout3;
    /*返回按钮*/
    private ImageView imageView;
    /*标题栏*/
    private TextView textView;
    /*初始化网格列表商品图片*/
    private int[] mIconIDs = new int[]{R.drawable.s_sp_02, R.drawable.s_sp_02, R.drawable.s_sp_02, R.drawable.s_sp_02, R.drawable.s_sp_02, R.drawable.s_sp_02};
    /*初始化横向列表选项*/
    private String[] mTitles = new String[]{"面膜", "面膜", "面膜", "面膜", "面膜", "面膜","面膜",
            "面膜", "面膜", "面膜", "面膜", "面膜","面膜", "面膜", "面膜", "面膜", "面膜", "面膜"};
    /*图片数组*/
    private ArrayList<Drawable>list1=new ArrayList<>();
    /*选项数组*/
    private ArrayList<String>list2=new ArrayList<>();
    /*网格类别的适配器*/
    private GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*加载主布局*/
        setContentView(R.layout.picture_layout);
        /*初始化数据*/
        inintData();
        /*初始化控件*/
        setView();
    }
    /*初始化数据*/
    private void inintData() {
        for(int i=0;i<24;i++){
            Drawable drawable= ContextCompat.getDrawable(this,R.drawable.sp_01);
            String s="衣服";
            list1.add(drawable);
            list2.add(s);
        }
    }

    //初始化view
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private void setView() {
        linearLayout1= (LinearLayout) findViewById(R.id.ll_pic1);
        linearLayout2= (LinearLayout) findViewById(R.id.ll_pic2);
        linearLayout3= (LinearLayout) findViewById(R.id.ll_pic3);

        textView= (TextView) findViewById(R.id.empty_view);
       imageView= (ImageView) findViewById(R.id.images_fanhui);
        imageView.setOnClickListener(this);
        textView.setText("面膜");

        //处理触摸事件的响应
        detector = new GestureDetector(this);
        //滑动控件初始化
        flipper = (ViewFlipper) this.findViewById(R.id.ViewFlipper1);
        for(int i=0;i<mIconIDs.length;i++){
            flipper.addView(addTextView(mIconIDs[i]));
        }
        flipper.setOnTouchListener(this);

        //横向listview初始化以及设置适配器
        mhorizontalListView= (HorizontalListView) findViewById(R.id.horizontallistview2);
        mhorizontalListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        horizontalListViewAdapter=new HorizontalListViewAdapter(this,mTitles);
        mhorizontalListView.setAdapter(horizontalListViewAdapter);

        //网格布局初始化以及设置适配器
        gridView= (GridView) findViewById(R.id.gridview);
        gridView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        gridViewAdapter=new GridViewAdapter(this,list1,list2);
        gridView.setAdapter(gridViewAdapter);

        //设置滚动条滑动监听及位置变化对控件的显示控制
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        //空闲的时候显示
                        if (getScrollY() > 200) {
                            linearLayout1.setVisibility(View.GONE);
                            linearLayout2.setVisibility(View.GONE);
                        }
                        if (getScrollY() < 5) {
                            linearLayout1.setVisibility(View.VISIBLE);
                            linearLayout2.setVisibility(View.VISIBLE);
                            linearLayout2.setAnimation(AnimationUtils.loadAnimation(PictureActivity.this, R.anim.push_top_in));
                            linearLayout1.setAnimation(AnimationUtils.loadAnimation(PictureActivity.this, R.anim.push_top_in));
                            gridView.smoothScrollToPosition(0);
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    /**
    * 获取当前滑动的高度
    * */
    public int getScrollY() {
        View c = gridView.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = gridView.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight() ;
    }

    private View addTextView(int id) {
        ImageView iv = new ImageView(this);
        iv.setImageResource(id);
        return iv;
    }
/**
* 由一个MotionEventACTION_DOWN触发的，但是他没有任何限制，也就是说当用户点击的时候，
* 首先MotionEventACTION_DOWN，onDown就会执行，如果在按下的瞬间没有松开或者是拖动的时候
* onShowPress就会执行，如果是按下的时间超过瞬间（这块我也不太清楚瞬间的时间差是多少，
* 一般情况下都会执行onShowPress），拖动了，就不执行onShowPress。
* */
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }
/**
* 用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发
* 它与onDown()的区别，强调的是没有松开或者拖动的状态
* */
    @Override
    public void onShowPress(MotionEvent e) {

    }
/**
*
* 用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发
*这个事件执行的顺序是onDown-》onShowPress-》onSingleTapUp
*
* */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
//Touch了 滑动时触发。
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
       // Log.e("Y1,Y2","Y1="+e1.getY()+"  "+"Y2="+e2.getY());
        return false;
    }
/**
* 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
*这个事件执行的顺序是onDown-》onShowPress-》onLongPress
*
* */
    @Override
    public void onLongPress(MotionEvent e) {

    }
/**
* 用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE,1个ACTION_UP触发
*
* */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() >= 120) {
            //为翻页设置动画效果
            this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in1));
            this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out1));
            this.flipper.showNext();
            return true;
        } else if (e1.getX() - e2.getX() < -120) {
            this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in1));
            this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out1));
            this.flipper.showPrevious();
            return true;
        }
        return false;
    }

/*
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        detector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
*/

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        detector.onTouchEvent(event);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.images_fanhui:
                finish();
                break;
        }
    }
}
