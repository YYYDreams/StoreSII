package com.example.administrator.store3.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.administrator.store3.R;
import com.example.administrator.store3.wenhao.PayDetailsPopouWindow;

/**
 * Created by Administrator on 2016/7/14.
 * 确认订单页面
 */
public class ConfirmOrderActivity extends Activity implements OnClickListener{
    /*返回按钮*/
    private ImageView mImageView_a;
    /*地址线性布局*/
    private LinearLayout mLinearLayout;
    /*结算按钮*/
    private TextView mTextView_a;
    /*结算弹出框*/
    private PayDetailsPopouWindow mPayDetailsPopouWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*加载主页面布局*/
        setContentView(R.layout.querendingdan);
        /*初始化控件*/
        initView();
    }
    /*初始化控件并添加事件*/
    private void initView() {
        mImageView_a = (ImageView) findViewById(R.id.back);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll1);
        mTextView_a = (TextView) findViewById(R.id.jiesuan);
        mTextView_a.setOnClickListener(this);
        mLinearLayout.setOnClickListener(this);
        mImageView_a.setOnClickListener(this);
        mPayDetailsPopouWindow = new PayDetailsPopouWindow(this,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                /*点击返回按钮,销毁主界面*/
                finish();
                break;
            case R.id.ll1:
                /*点击此控件跳转到我的地址页面*/
                Intent intent = new Intent();
                intent.setClass(this,MySiteActivity.class);
                startActivity(intent);
                break;
            case R.id.jiesuan:
                /*点击结算,弹出结算框*/
                mPayDetailsPopouWindow.showAsDropDown(v);
                //设置背景色变暗
                WindowManager.LayoutParams lp1=getWindow().getAttributes();
                lp1.alpha = 0.4f;
                getWindow().setAttributes(lp1);
                break;
        }
    }
}
