package com.example.administrator.store3.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.store3.R;

/**
 * Created by Administrator on 2016/5/21.
 * 我的地址
 */
public class  MySiteActivity extends Activity implements View.OnClickListener{
    /*返回按钮*/
    private ImageView imageView;
    /*新建地址按钮*/
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*加载主布局*/
        setContentView(R.layout.wodedizhi);
        /*初始化控件*/
        initViews();
    }
    /*初始化控件以及绑定事件*/
    private void initViews() {
        imageView= (ImageView) findViewById(R.id.image0);
        imageView.setOnClickListener(this);
        button= (Button) findViewById(R.id.btn0);
        button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image0:
                /*点击返回按钮销毁主界面*/
               finish();
                break;
            case R.id.btn0:
                /*点击跳转页面,添加新的地址*/
                Intent intent=new Intent(this,NewShouhuoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
