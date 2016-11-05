package com.example.administrator.store3.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.administrator.store3.R;
import com.example.administrator.store3.activity.SearchActivity;
import com.example.administrator.store3.adapter.Fragement_Adapter;
import com.example.administrator.store3.zxing.activity.CaptureActivity;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/4/15.
 * 南玉页面
 */
public class NanyuFragment extends Fragment implements View.OnClickListener,PopupWindow.OnDismissListener{
    /*按钮*/
    private ImageView iView1,iView2,imageView0,imageView1;
    /*导航栏标题*/
    private TabLayout tab_title;
    /*翻页页面*/
    private ViewPager vpager;
    /*标题数组*/
    private List<String> list_title;                                      //tab名称列表
    /*页面数组*/
    private List<Fragment> list_fragment;
    /*页面适配器*/
    private Fragement_Adapter fAdapter;                               //定义以fragment为切换的adapter
    /*销量页面*/
    private XiaoliangFragment xFragment;
    /*最新页面*/
    private ZuixinFragment zFragment;
    /*南玉关注页面*/
    private Nanyuguanzhu gFragment;
    /*南玉推荐页面*/
    private Nanyutuijian tFragment;
    // 声明PopupWindow对象的引用
    private PopupWindow popupWindow;
    /*返回按钮*/
    private ImageView imageView;
    /*搜索框*/
    private LinearLayout mLinearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*加载主布局*/
        View view=inflater.inflate(R.layout.chenyixing,null);
        /*初始化控件*/
        initControls(view);
        fragmentChange();
        return view;
    }
    /**
     * 采用viewpager中切换fragment
     */
    private void fragmentChange() {
        list_fragment = new ArrayList<>();
        xFragment = new XiaoliangFragment();
        zFragment = new ZuixinFragment();
        gFragment = new Nanyuguanzhu();
        tFragment = new Nanyutuijian();

        list_fragment.add(zFragment);
        list_fragment.add(xFragment);
        list_fragment.add(gFragment);
        list_fragment.add(tFragment);

        list_title = new ArrayList<>();
        list_title.add("最新");
        list_title.add("销量");
        list_title.add("关注");
        list_title.add("推荐");

        fAdapter = new Fragement_Adapter(getActivity().getSupportFragmentManager(),list_fragment,list_title);
        vpager.setAdapter(fAdapter);
        //将tabLayout与viewpager连起来
        tab_title.setupWithViewPager(vpager);
    }
    /**
     * 初始化控件
     */
    private void initControls(View view) {
        tab_title = (TabLayout)view.findViewById(R.id.tab_title);
        vpager = (ViewPager)view.findViewById(R.id.viewPager);
        iView1= (ImageView) view.findViewById(R.id.iV1);
        iView2= (ImageView) view.findViewById(R.id.image1);
        imageView= (ImageView) view.findViewById(R.id.iamges);
        mLinearLayout= (LinearLayout) view.findViewById(R.id.line_seach);
        iView1.setOnClickListener(this);
        iView2.setOnClickListener(this);
        imageView.setOnClickListener(this);
        mLinearLayout.setOnClickListener(this);
    }
    /**
     * 设置监听
     */
    Intent intent=new Intent();
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iV1:
                intent=new Intent(getActivity(),CaptureActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.image1:
                getPopupWindow();
                // 这里是位置显示方式,在屏幕的左侧
                popupWindow.showAtLocation(iView2,Gravity.RIGHT,0,50);
                //设置背景色变暗
                WindowManager.LayoutParams lp1=getActivity().getWindow().getAttributes();
                lp1.alpha = 0.4f;
                getActivity().getWindow().setAttributes(lp1);
                break;
            case R.id.iamges:
                getFragmentManager().popBackStack();
                break;
            case R.id.line_seach:
                intent=new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
    /***
     * 获取PopupWindow实例
     */
    public void getPopupWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopupWindow();
        }
    }
    /**
     * 创建PopupWindow
     */
    private void initPopupWindow() {
        // 获取自定义布局文件的视图
        View popview=getActivity().getLayoutInflater().inflate(R.layout.nanyucaidantankuang_main, null);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
      //  popupWindow=new PopupWindow(popview,200, FancyCoverFlow.LayoutParams.MATCH_PARENT,true);
        popupWindow=new PopupWindow(popview,200,700,true);
        popupWindow.setOnDismissListener(this);
        // 设置动画效果
        popupWindow.setAnimationStyle(R.style.AnimationFade);
        // 点击其他地方消失
        popview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
    }
    @Override
    public void onDismiss() {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 1f;
       getActivity().getWindow().setAttributes(lp);
    }
}




















