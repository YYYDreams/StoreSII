package com.example.administrator.store3.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.store3.R;

/**
 * Created by Administrator on 2016/5/21.
 * 待收货页面
 */
public class ReceiptActivity extends Activity implements View.OnClickListener{
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.woxihuan);
        initViews();
    }
    private void initViews() {
        imageView= (ImageView) findViewById(R.id.image0);
        imageView.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image0:
               finish();
                break;
        }
    }
}
