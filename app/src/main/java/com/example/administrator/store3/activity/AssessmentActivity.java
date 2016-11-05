package com.example.administrator.store3.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.administrator.store3.R;

/**
 * Created by Administrator on 2016/5/21.
 * 待评价页面
 */
public class AssessmentActivity extends Activity implements View.OnClickListener{
    /*返回按钮*/
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*加载主界面*/
        setContentView(R.layout.daipingjia);
        /*初始化控件*/
        iniitViews();
    }
    /*初始化控件以及绑定事件监听*/
    private void iniitViews() {
        imageView= (ImageView) findViewById(R.id.image0);
        imageView.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image0:
                /*点击返回按钮,销毁主界面*/
                this.finish();
                break;
        }
    }
}
