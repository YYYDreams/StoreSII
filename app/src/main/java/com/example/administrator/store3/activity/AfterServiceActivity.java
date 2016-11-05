package com.example.administrator.store3.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.store3.R;
import com.example.administrator.store3.fragment.HuanHuoFragment;
import com.example.administrator.store3.fragment.TuiHuoFragment;
import com.example.administrator.store3.fragment.WeiXiuFragment;

/**
 * Created by Administrator on 2016/5/21.
 * 售后服务
 */
public class AfterServiceActivity extends FragmentActivity implements View.OnClickListener{
    /*返回按钮*/
    private ImageView imageView;
    /*退货子页面*/
    private TuiHuoFragment tuiHuoFragment;
    /*换货子页面*/
    private HuanHuoFragment huanHuoFragment;
    /*维修子页面*/
    private WeiXiuFragment weiXiuFragment;
    /*退货按钮*/
    private TextView tuihuo;
    /*换货按钮*/
    private TextView huanhuo;
    /*维修按钮*/
    private TextView weixiu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*加载主布局*/
        setContentView(R.layout.shouhoufuwu);
        /*初始化控件*/
        initViews();
    }
    /*初始化控件添加点击事件*/
    private void initViews() {
        imageView= (ImageView) findViewById(R.id.image0);
        imageView.setOnClickListener(this);
        tuiHuoFragment = new TuiHuoFragment();
        huanHuoFragment = new HuanHuoFragment();
        weiXiuFragment = new WeiXiuFragment();
        /*跳转到退货子界面*/
        getSupportFragmentManager().beginTransaction().replace(R.id.converview, tuiHuoFragment).commit();
        tuihuo = (TextView) findViewById(R.id.tuihuo);
        huanhuo = (TextView) findViewById(R.id.huanhuo);
        weixiu = (TextView) findViewById(R.id.weixiu);
        /*绑定点击事件*/
        tuihuo.setOnClickListener(this);
        huanhuo.setOnClickListener(this);
        weixiu.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image0:
                /*点击返回按钮销毁主界面*/
                this.finish();
                break;
            case R.id.tuihuo:
                /*点击退货按钮将其设置为亮色,其他按钮设置为灰色*/
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    tuihuo.setBackground(getResources().getDrawable(R.drawable.biankuang_1));
                    huanhuo.setBackground(getResources().getDrawable(R.drawable.biankuang));
                    weixiu.setBackground(getResources().getDrawable(R.drawable.biankuang));
                }
                /*跳转到退货子界面*/
                getSupportFragmentManager().beginTransaction().replace(R.id.converview, tuiHuoFragment).commit();
                break;
            case R.id.huanhuo:
                /*点击换货按钮将其设置为亮色,其他按钮设置为灰色*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    tuihuo.setBackground(getResources().getDrawable(R.drawable.biankuang));
                    huanhuo.setBackground(getResources().getDrawable(R.drawable.biankuang_1));
                    weixiu.setBackground(getResources().getDrawable(R.drawable.biankuang));
                }
                /*跳转到换货子界面*/
                getSupportFragmentManager().beginTransaction().replace(R.id.converview, huanHuoFragment).commit();
                break;
            case R.id.weixiu:
                /*点击维修按钮将其设置为亮色,其他按钮设置为灰色*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    tuihuo.setBackground(getResources().getDrawable(R.drawable.biankuang));
                    huanhuo.setBackground(getResources().getDrawable(R.drawable.biankuang));
                    weixiu.setBackground(getResources().getDrawable(R.drawable.biankuang_1));
                }
                /*跳转到维修子界面*/
                getSupportFragmentManager().beginTransaction().replace(R.id.converview, weiXiuFragment).commit();
                break;
        }
    }
}
