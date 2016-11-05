package com.example.administrator.store3.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import com.example.administrator.store3.R;
import com.example.administrator.store3.adapter.Fragement_Adapter;
import com.example.administrator.store3.fragment.AllStore_Fragment;
import com.example.administrator.store3.fragment.NewProducts_Fragment;
import com.example.administrator.store3.fragment.NewTrends_Fragment;
import com.example.administrator.store3.fragment.StoreFristPage_Fragment;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/29.
 * 品牌专店界面
 */
public class BrandStoreActivity extends ActionBarActivity {
    /*导航栏*/
    private TabLayout tableLayout;
    /*翻页器*/
    private ViewPager viewPager;
    /*商店首页子页面*/
    private StoreFristPage_Fragment storeFristPage_fragment;
    /*所有店铺*/
    private AllStore_Fragment allStore_fragment;
    /*最新产品*/
    private NewProducts_Fragment newProducts_fragment;
    /*微淘动态*/
    private NewTrends_Fragment newTrends_fragment;
    /*页面数组*/
    private ArrayList<Fragment>list_fragment;
    /*导航标题栏数组*/
    private ArrayList<String>list_titile;
    /*页面适配器*/
    private Fragement_Adapter fragement_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brand_store_mian);
        initView();//初始化view
        initControls();//设置导航
    }
    private void initControls() {
        //初始化待添加的fragment
        storeFristPage_fragment=new StoreFristPage_Fragment();
        allStore_fragment=new AllStore_Fragment();
        newProducts_fragment=new NewProducts_Fragment();
        newTrends_fragment=new NewTrends_Fragment();
        //将数据封装list
        list_fragment=new ArrayList<>();
        list_fragment.add(storeFristPage_fragment);
        list_fragment.add(allStore_fragment);
        list_fragment.add(newProducts_fragment);
        list_fragment.add(newTrends_fragment);

        list_titile=new ArrayList<>();
        list_titile.add("店铺首页");
        list_titile.add("全部宝贝");
        list_titile.add("新品上架");
        list_titile.add("微淘动态");
        //设置导航模式
        tableLayout.setTabMode(TabLayout.MODE_FIXED);
        //添加导航栏内容
        tableLayout.addTab(tableLayout.newTab().setText(list_titile.get(0)));
        tableLayout.addTab(tableLayout.newTab().setText(list_titile.get(1)));
        tableLayout.addTab(tableLayout.newTab().setText(list_titile.get(2)));
        tableLayout.addTab(tableLayout.newTab().setText(list_titile.get(3)));
        //初始化适配器
        fragement_adapter=new Fragement_Adapter(getSupportFragmentManager(),list_fragment,list_titile);
        //关联适配器
        viewPager.setAdapter(fragement_adapter);
        //导航栏关联viewpager
        tableLayout.setupWithViewPager(viewPager);

    }
    //初始化view
    private void initView() {
        tableLayout= (TabLayout) findViewById(R.id.tab_title);
        viewPager= (ViewPager) findViewById(R.id.pager);
    }
}
