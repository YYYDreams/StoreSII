package com.example.administrator.store3.activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.administrator.store3.R;
import com.example.administrator.store3.fragment.BaoBeiFragment;
import com.example.administrator.store3.fragment.DianPu_Fragment;

/**
 * Created by Administrator on 2016/5/24.
 * 我喜欢的主界面
 */
public class ILikeActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener,View.OnClickListener {
    /*单项选择器*/
    private RadioGroup radioGroup;
    /*选中状态的宝贝按钮*/
    private RadioButton radioButton1;
    /*没选中状态的店铺按钮*/
    private RadioButton radioButton2;
    /*没有选中状态的宝贝按钮*/
    private RadioButton radioButton11;
    /*选中状态的店铺按钮*/
    private RadioButton radioButton22;
    /*宝贝子页面*/
    private BaoBeiFragment baobeifragment;
    /*店铺子页面*/
    private DianPu_Fragment dianpufragment;
    /*返回按钮*/
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*加载主界面布局*/
        setContentView(R.layout.woxihuan);
        /*初始化控件*/
        setView();
    }
    /*控件初始化,添加事件监听*/
    private void setView() {
        imageView= (ImageView) findViewById(R.id.image0);
        imageView.setOnClickListener(this);
        radioGroup= (RadioGroup) findViewById(R.id.radioGroup_wxh);
        radioGroup.setOnCheckedChangeListener(this);
        radioButton1= (RadioButton) findViewById(R.id.radiobutoon_wxh1);
        radioButton2= (RadioButton) findViewById(R.id.radiobutoon_wxh2);
        radioButton11= (RadioButton) findViewById(R.id.radiobutoon_wxh11);
        radioButton22= (RadioButton) findViewById(R.id.radiobutoon_wxh22);
        baobeifragment=new BaoBeiFragment();
        dianpufragment=new DianPu_Fragment();
        //添加宝贝子页面
        getSupportFragmentManager().beginTransaction().replace(R.id.woxihuan, baobeifragment).commit();
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        /*单项选择器的监听事件*/
        switch (checkedId){
            case R.id.radiobutoon_wxh1:
                //点击wx1加载宝贝子页面
                radioButton11.setVisibility(View.GONE);
                radioButton22.setVisibility(View.GONE);
                radioButton1.setVisibility(View.VISIBLE);
                radioButton2.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.woxihuan, baobeifragment).commit();
                break;
            case R.id.radiobutoon_wxh2:
                //点击wxh2加载店铺子页面
                radioButton1.setVisibility(View.GONE);
                radioButton11.setVisibility(View.VISIBLE);
                radioButton2.setVisibility(View.GONE);
                radioButton22.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.woxihuan, dianpufragment).commit();
                break;
            case R.id.radiobutoon_wxh11:
                //点击wxh11加载宝贝子页面
                radioButton1.setVisibility(View.VISIBLE);
                radioButton11.setVisibility(View.GONE);
                radioButton2.setVisibility(View.VISIBLE);
                radioButton22.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.woxihuan, baobeifragment).commit();
                break;
            case R.id.radiobutoon_wxh22:
                //点击wxh22加载店铺子页面
                radioButton1.setVisibility(View.GONE);
                radioButton11.setVisibility(View.VISIBLE);
                radioButton2.setVisibility(View.GONE);
                radioButton22.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.woxihuan, dianpufragment).commit();
                break;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image0:
                /*点击返回按钮销毁主界面*/
                this.finish();
                break;
        }
    }
}
