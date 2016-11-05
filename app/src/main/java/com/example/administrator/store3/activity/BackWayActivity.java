package com.example.administrator.store3.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.store3.R;

/**
 * Created by Administrator on 2016/7/11.
 * 售后服务的跳转界面返回方式界面
 */
public class BackWayActivity extends Activity implements View.OnClickListener{
    /*返回按钮*/
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*加载主布局*/
        setContentView(R.layout.fanhuifangshi);
        /*初始化控件*/
        setView();
    }
    /*初始化控件绑定监听事件*/
    private void setView() {
       back = (ImageView) findViewById(R.id.image0);
       back.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image0:
                /*点击返回按钮销毁掉主界面*/
                finish();
                break;
        }
    }
}
