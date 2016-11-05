package com.example.administrator.store3.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.store3.R;

/**
 * Created by Administrator on 2016/6/29.
 */
public class SetActivity extends Activity implements View.OnClickListener {
    private LinearLayout mLinearLayout0,mLinearLayout1,mLinearLayout2;
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_activity);
        setViews();
    }

    private void setViews() {
        mLinearLayout0= (LinearLayout) findViewById(R.id.line0);
        mLinearLayout1= (LinearLayout) findViewById(R.id.line1);
        mLinearLayout2= (LinearLayout) findViewById(R.id.line2);
        mImageView= (ImageView) findViewById(R.id.image0);
        mLinearLayout0.setOnClickListener(this);
        mLinearLayout1.setOnClickListener(this);
        mLinearLayout2.setOnClickListener(this);
        mImageView.setOnClickListener(this);
    }

    Intent intent=new Intent();
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.line0:
               intent=new Intent(this,GeRenZiLiaoActivity.class);
                startActivity(intent);
                break;
            case R.id.line1:
               intent=new Intent(this,GeRenZiLiaoActivity.class);
                startActivity(intent);
                break;
            case R.id.line2:
                intent=new Intent(this,GeRenZiLiaoActivity.class);
                startActivity(intent);
                break;
            case R.id.image0:
               finish();
                break;
        }
    }
}
