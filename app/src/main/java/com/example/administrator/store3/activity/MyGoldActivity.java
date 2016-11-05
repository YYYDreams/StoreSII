package com.example.administrator.store3.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.store3.R;

/**
 * Created by Administrator on 2016/5/21.
 * 我的钻币界面
 */
public class MyGoldActivity extends Activity implements View.OnClickListener{
    /*返回按钮*/
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*加载主界面布局*/
        setContentView(R.layout.wodejinbi);
        /*初始化控件*/
        initViews();
    }
    /*初始化控件以及天加监听事件*/
    private void initViews() {
        imageView= (ImageView) findViewById(R.id.image0);
        imageView.setOnClickListener(this);
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
