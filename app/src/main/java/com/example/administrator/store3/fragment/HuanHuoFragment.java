package com.example.administrator.store3.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.activity.BackWayActivity;

/**
 * Created by Administrator on 2016/7/11.
 * 换货页面
 */
public class HuanHuoFragment extends Fragment implements View.OnClickListener{
    /*下一步按钮*/
    private TextView next;
    /*按钮减*/
    private Button sub;
    /*按钮加*/
    private Button add;
    /*数量显示*/
    private TextView jianshu;
    /*初始值*/
    private int str =1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*加载主界面*/
        View view = inflater.inflate(R.layout.huanhuo_fglayout,null);
        /*初始化控件*/
        initView(view);
        return view;
    }
    /*初始化控件以及绑定事件监听*/
    private void initView(View view) {
        next = (TextView) view.findViewById(R.id.xiayibu);
        next.setOnClickListener(this);
        sub = (Button) view.findViewById(R.id.btn_sub);
        add = (Button) view.findViewById(R.id.btn_add);
        sub.setOnClickListener(this);
        add.setOnClickListener(this);
        jianshu = (TextView) view.findViewById(R.id.product_num);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.xiayibu:
                /*点击下一步按钮跳转页面*/
                Intent intent = new Intent();
                intent.setClass(getContext(), BackWayActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.btn_sub:
                /*点击减号按钮数量减一*/
                str--;
                jianshu.setText(str+"");
                jianshu.postInvalidate();
                break;
            case R.id.btn_add:
                /*点击加号按钮数量加一*/
                str++;
                jianshu.setText(str + "");
                jianshu.postInvalidate();
                break;
        }
    }
}
