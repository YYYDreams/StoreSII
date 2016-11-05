package com.example.administrator.store3.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.store3.R;
import com.example.administrator.store3.activity.AfterServiceActivity;
import com.example.administrator.store3.activity.AllIndentActivity;
import com.example.administrator.store3.activity.ILikeActivity;
import com.example.administrator.store3.activity.MyConponActivity;
import com.example.administrator.store3.activity.MyGoldActivity;
import com.example.administrator.store3.activity.SetActivity;
import com.example.administrator.store3.activity.WoTiaoNiXuan;
import com.example.administrator.store3.addressactivity.PersonAddress;
import com.example.administrator.store3.addressservice.landDivideServeice;

/**
 * Created by Administrator on 2016/4/19.
 */

/**
* 我的的类
*/
public class MyMainFragment extends Fragment implements View.OnClickListener{
    /*设置按钮*/
    private LinearLayout shezhi;
    /*订单按钮*/
    private LinearLayout dingdan;
    /*优惠券按钮*/
    private LinearLayout gouwuquan;
    /*我挑你选按钮*/
    private LinearLayout wotiaonixuan;
    /*我喜欢按钮*/
    private LinearLayout woxihuan;
    /*地址按钮*/
    private LinearLayout dizhi;
    /*钻币按钮*/
    private LinearLayout zuanbi;
    /*售后服务*/
    private LinearLayout shouhoufuwu;
    /*倒计时*/
    private TimeCount time;
    private int next = -1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*加载主布局*/
        View view=inflater.inflate(R.layout.wode_fg,null);
        /*初始化控件*/
        setView(view);
        /*开启服务*/
        Intent i = new Intent(getActivity(), landDivideServeice.class);
        getActivity().startService(i);
        time = new TimeCount(200, 100);
        time.start();
        return view;
    }
    /*初始化控件以及绑定事件监听*/
    private void setView(View view) {
        shezhi= (LinearLayout) view.findViewById(R.id.ll_wdsz);
        dingdan= (LinearLayout) view.findViewById(R.id.ll_wddd);
        gouwuquan= (LinearLayout) view.findViewById(R.id.ll_wd1);
        wotiaonixuan= (LinearLayout) view.findViewById(R.id.ll_wd2);
        woxihuan= (LinearLayout) view.findViewById(R.id.ll_wd3);
        dizhi= (LinearLayout) view.findViewById(R.id.ll_wd4);
        zuanbi= (LinearLayout) view.findViewById(R.id.ll_wd5);
        shouhoufuwu= (LinearLayout) view.findViewById(R.id.ll_wd6);

        shezhi.setOnClickListener(this);
        dingdan.setOnClickListener(this);
        gouwuquan.setOnClickListener(this);
        wotiaonixuan.setOnClickListener(this);
        woxihuan.setOnClickListener(this);
        dizhi.setOnClickListener(this);
        zuanbi.setOnClickListener(this);
        shouhoufuwu.setOnClickListener(this);
    }

    Intent intent=new Intent();
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_wdsz:
                /*点击设置,进入设置页面*/
                intent.setClass(getActivity(),SetActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.ll_wddd:
                /*点击订单,进入订单页面*/
                intent.setClass(getActivity(),AllIndentActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.ll_wd1:
                /*点击优惠券,进入优惠券页面*/
                intent.setClass(getActivity(),MyConponActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.ll_wd2:
                /*点击我挑你选,进入我挑你选页面*/
                intent.setClass(getActivity(),WoTiaoNiXuan.class);
                getActivity().startActivity(intent);
                break;
            case R.id.ll_wd3:
                /*点击我喜欢,进入我喜欢页面*/
                intent.setClass(getActivity(),ILikeActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.ll_wd4:
                /*点击地址,进入地址设置页面*/
                //intent.setClass(getActivity(),MySiteActivity.class);
                intent.setClass(getActivity().getBaseContext(),PersonAddress.class);
                getActivity().startActivity(intent);
                break;
            case R.id.ll_wd5:
                /*点击钻币,进入钻币页面*/
                intent.setClass(getActivity(),MyGoldActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.ll_wd6:
                /*点击售后服务,进入售后服务页面*/
                intent.setClass(getActivity(),AfterServiceActivity.class);
                getActivity().startActivity(intent);
             /*   intent.setClass(getActivity(),MsgexActivity.class);
                getActivity().startActivity(intent);*/
                break;
        }
    }
    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish() {  //倒计时执行结束时操作
            next = 0;
        }

        @Override
        public void onTick(long millisUntilFinished) {  //倒计执行时操作
        }
    }
}
