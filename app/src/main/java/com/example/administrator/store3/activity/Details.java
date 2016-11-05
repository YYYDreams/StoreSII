package com.example.administrator.store3.activity;



import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.adapter.NanyufragmentAdapter;
import com.example.administrator.store3.customview.CircleImageView;
import com.example.administrator.store3.fragment.DetailsMJXFragment;
import com.example.administrator.store3.fragment.DetailsPPFragment;
import com.example.administrator.store3.fragment.DetailsXQFragment;
import com.example.administrator.store3.wenhao.DetailsPopouWindown;
import com.example.administrator.store3.wenhao.viewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/5/6.
 * 南玉详情页面
 */
public class Details extends ActionBarActivity implements View.OnClickListener {
    private ImageView imageView0,imageView1;
    private TabLayout tab_title;
    private ViewPager vpager;
    private List<String> list_title;                                      //tab名称列表
    private List<View> listViews;
    private List<Fragment> list_fragment;
    private viewAdapter vAdapter;
    private NanyufragmentAdapter fAdapter;                               //定义以fragment为切换的adapter
    private DetailsPPFragment ppFragment;
    private DetailsMJXFragment mjxFragment;
    private DetailsXQFragment xqFragment;
    private  TextView tv,tv1,tv2;
    PopupWindow popupWindow;
    private TextView goStore;
    private LinearLayout linearLayout;
    private View inflaterView;
    private DetailsPopouWindown detailsPopouWindown;
    private CircleImageView imageView;
    private Animation detailsAnim;
    private ViewPager viewPager; // android-support-v4中的滑动组件
    private List<ImageView> imageViews; // 滑动的图片集合
    private int[] imageResId; // 图片ID
    private List<View> dots; // 图片标题正文的那些点
    private int currentItem = 0; // 当前图片的索引号
    //按指定频率周期执行某个任务
    private ScheduledExecutorService scheduledExecutorService;
    // 切换当前显示的图片
    private Handler handler=new Handler(){
        public  void handleMessage(android.os.Message msg){
            viewPager.setCurrentItem(currentItem);//切换当前显示的图片
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.details);
        initControls();
        setAnimation();
       fragmentChange();
       // setViews();
        popupwindown();
        initPictures();
    }

    /**
     * 图片轮播方法
     */
    private void initPictures() {
        imageResId=new int[]{R.drawable.yanjing,R.drawable.logo,
                R.drawable.bjt,R.drawable.aaa,R.drawable.bj};

        imageViews=new ArrayList<>();
        //初始化图片资源
        for(int i=0;i<imageResId.length;i++){
            ImageView imageView=new ImageView(this);
            imageView.setImageResource(imageResId[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
        }
        dots=new ArrayList<>();
        dots.add(findViewById(R.id.v_dot0));
        dots.add(findViewById(R.id.v_dot1));
        dots.add(findViewById(R.id.v_dot2));
        dots.add(findViewById(R.id.v_dot3));
        dots.add(findViewById(R.id.v_dot4));

        viewPager = (ViewPager) findViewById(R.id.vp);
        viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
        // 设置一个监听器，当ViewPager中的页面改变时调用
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        goStore= (TextView) findViewById(R.id.t_View0);
        goStore.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        scheduledExecutorService= Executors.newSingleThreadScheduledExecutor();
        // 当Details显示出来后，每两秒钟切换一次图片显示
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(),1,3, TimeUnit.SECONDS);
        super.onStart();
    }

    @Override
    protected void onStop() {
        // 当Details不可见的时候停止切换
        scheduledExecutorService.shutdown();
        super.onStop();
    }
    /**
     * 换行切换任务
     */
    private  class ScrollTask implements Runnable{
        @Override
        public void run() {
            synchronized (viewPager) {
                Log.e("TAG", "currentItem=" + currentItem);
                currentItem = (currentItem + 1) % imageViews.size();
                handler.obtainMessage().sendToTarget();//通过Handler切换图片
            }
        }
    }
    /**
     * 当ViewPager中页面的状态发生改变时调用
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener{
        private int oldPosition = 0;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentItem=position;
            dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            dots.get(position).setBackgroundResource(R.drawable.dot_focused);
            oldPosition=position;
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    /**
     * 填充ViewPager页面的适配器
     */
    private class MyAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return imageResId.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager)container).addView(imageViews.get(position));
            return imageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView((View) object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {

        }
        public Parcelable saveState(){
            return  null;
        }

        @Override
        public void startUpdate(ViewGroup container) {

        }

        @Override
        public void finishUpdate(ViewGroup container) {

        }
    }
    private void setAnimation() {

        detailsAnim= AnimationUtils.loadAnimation(this,R.anim.xuanzhuantupian);
        LinearInterpolator lin = new LinearInterpolator();
        detailsAnim.setInterpolator(lin);
        //图片绑定旋转动画
        if (detailsAnim != null) {
            imageView.startAnimation(detailsAnim);
        }
    }
    /**
     * 设置popupwindown的方法
     * 设置其参数
     */
    private void popupwindown() {
        //创建PopupWindow实例，同时传入弹出窗口的显示高度和宽度以及是否设置焦点
        popupWindow = new PopupWindow(inflaterView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        //如果不设置PopupWindow背景，无论是点击外部区域还是Back键都没法dismiss弹窗
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //设置显示的动画
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        tv= (TextView) findViewById(R.id.t_View2);
        tv1= (TextView) findViewById(R.id.t_View3);
        tv.setOnClickListener(this);
        tv1.setOnClickListener(this);
       detailsPopouWindown=new DetailsPopouWindown(this,this);
    }

    /**
     * 采用viewpager中切换fragment
     */
    private void fragmentChange() {
        list_fragment = new ArrayList<>();

        ppFragment = new DetailsPPFragment();
        xqFragment= new DetailsXQFragment();
        mjxFragment = new DetailsMJXFragment();

        list_fragment.add(ppFragment);
        list_fragment.add(xqFragment);
        list_fragment.add(mjxFragment);

        list_title = new ArrayList<>();
        list_title.add("品牌");
        list_title.add("详情");
        list_title.add("买家秀");
        fAdapter = new NanyufragmentAdapter(getSupportFragmentManager(),list_fragment,list_title);
        vpager.setAdapter(fAdapter);
        //将tabLayout与viewpager连起来
        tab_title.setupWithViewPager(vpager);

    }
    /**
     * 初始化控件
     */
    private void initControls() {

        tab_title = (TabLayout) findViewById(R.id.tab_title1);
        vpager = (ViewPager) findViewById(R.id.viewPager1);
        imageView0= (ImageView) findViewById(R.id.image0);
        tv2= (TextView) findViewById(R.id.t_View0);
        imageView= (CircleImageView) findViewById(R.id.image_details);
        imageView1= (ImageView) findViewById(R.id.image_pingjia);
        imageView1.setOnClickListener(this);
    }
    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_pingjia:
                Intent intent1=new Intent(this,CommentActivity.class);
                startActivity(intent1);
                break;
            case R.id.image0:
                Details.this.finish();
                break;
            case R.id.t_View2:
                detailsPopouWindown.showAsDropDown(v);
                //设置背景色变暗
                WindowManager.LayoutParams lp=getWindow().getAttributes();
                lp.alpha = 0.4f;
                getWindow().setAttributes(lp);
                break;
            case R.id.t_View3:
                detailsPopouWindown.showAsDropDown(v);
                //设置背景色变暗
                WindowManager.LayoutParams lp1=getWindow().getAttributes();
                lp1.alpha = 0.4f;
                getWindow().setAttributes(lp1);
                break;
            case R.id.t_View0:
                Intent intent= new Intent();
                intent.setClass(this,PictureActivity.class);
                startActivity(intent);
            default:
                break;
        }

    }


}
