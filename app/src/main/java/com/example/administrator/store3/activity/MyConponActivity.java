package com.example.administrator.store3.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.adapter.Fragement_Adapter;
import com.example.administrator.store3.fragment.DueTimeFragment;
import com.example.administrator.store3.fragment.OtherPresenterFragment;
import com.example.administrator.store3.fragment.ShopPrivilegeFragment;
import com.example.administrator.store3.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/23.
 *
 * 我的优惠券界面
 */
public class MyConponActivity extends ActionBarActivity implements View.OnClickListener{
    /*导航栏*/
    private TabLayout tableLayout;
    /*翻页器*/
    private ViewPager viewPager;
    /*fragment适配器*/
    private Fragement_Adapter fragement_adapter;
    /*已过期优惠券界面*/
    private ShopPrivilegeFragment shopPrivilegeFragment;
    /*未使用优惠券界面*/
    private OtherPresenterFragment otherPresenterFragment;
    /*已使用优惠券界面*/
    private DueTimeFragment dueTimeFragment;
    /*页面数组*/
    private List<Fragment>list_fg;
    /*标题数组*/
    private List<String>list_title;
    /*返回按钮*/
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*加载主页面布局*/
        setContentView(R.layout.wodeyouhuiquan_layout);
        /*初始化控件*/
        setView();
        //活动屏幕高度并存入Constant工具类中
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constant.displayHeight=displayMetrics.heightPixels;

    }
    /*初始化控件以及绑定监听事件*/
    private void setView() {
        imageView= (ImageView) findViewById(R.id.image0);
        imageView.setOnClickListener(this);
        tableLayout= (TabLayout) findViewById(R.id.tab_title);
        viewPager= (ViewPager) findViewById(R.id.pager_fragment);
        /*初始化导航栏*/
        initControls();
        fragement_adapter = new Fragement_Adapter(getSupportFragmentManager(),list_fg,list_title);
        viewPager.setAdapter(fragement_adapter);
        tableLayout.setupWithViewPager(viewPager);
    }
    /*初始化导航栏以及绑定标题对应的子页面*/
    private void initControls() {
        shopPrivilegeFragment=new ShopPrivilegeFragment();
        otherPresenterFragment=new OtherPresenterFragment();
        dueTimeFragment=new DueTimeFragment();

        list_fg=new ArrayList<>();
        list_fg.add(shopPrivilegeFragment);
        list_fg.add(otherPresenterFragment);
        list_fg.add(dueTimeFragment);

        list_title=new ArrayList<>();
        list_title.add("已过期");
        list_title.add("未使用");
        list_title.add("已使用");

        //设置TabLayout的模式
        tableLayout.setTabMode(TabLayout.MODE_FIXED);
        tableLayout.addTab(tableLayout.newTab().setText(list_title.get(0)));
        tableLayout.addTab(tableLayout.newTab().setText(list_title.get(1)));
        tableLayout.addTab(tableLayout.newTab().setText(list_title.get(2)));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image0:
                /*点击返回按钮销毁主界面*/
                MyConponActivity.this.finish();
                break;
        }
    }
}
