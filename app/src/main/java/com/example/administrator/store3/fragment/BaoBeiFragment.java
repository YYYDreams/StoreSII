package com.example.administrator.store3.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.adapter.BaobeiAdapter1;
import com.example.administrator.store3.adapter.BaobeiAdapter2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 * 全部宝贝
 *
 */
public class BaoBeiFragment extends Fragment implements View.OnClickListener{
    /*全部宝贝按钮*/
    private TextView textView1;
    /*失效宝贝按钮*/
    private TextView textView2;
    /*删除按钮*/
    private ImageView imageView;
    /*取消按钮*/
    private TextView textView3;
    /*全部宝贝的网格布局*/
    private GridView gridView1;
    /*失效宝贝的网格布局*/
    private GridView gridView2;
    /*全部宝贝线性布局*/
    private LinearLayout ll_1;
    /*失效宝贝线性布局*/
    private LinearLayout ll_2;
    /*删除的线性布局*/
    private LinearLayout ll_sc;
    /*全选的选择框*/
    private CheckBox checkBox;
    /*底部删除按钮*/
    public static Button button;
    /*删除个数*/
    public static int num=0;
    /*全部宝贝的适配器*/
    private BaobeiAdapter1 baobeiAdapter1;
    /*失效宝贝的适配器*/
    private BaobeiAdapter2 baobeiAdapter2;
    /*选择框初始为不选中状态*/
    boolean checked=false;
    /*初始状态选择框为不显示*/
    boolean display=false;
    /*用于判断当前显示的是哪个布局*/
    boolean isTF=true;
    /*数组存放选择框状态*/
    List<Boolean>list1=new ArrayList<>();
    List<Boolean>list2=new ArrayList<>();
    /*item个数*/
    int[]images={1,2,3,4,5};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*加载主布局*/
        View view=inflater.inflate(R.layout.baobei_fragment,null);
        /*初始化控件*/
        setView(view);
        return view;
    }
    //初始化控件,并添加监听事件
    private void setView(View view) {
        textView1= (TextView) view.findViewById(R.id.quanbubaobei);
        textView2= (TextView) view.findViewById(R.id.shixiaobaobei);
        imageView= (ImageView) view.findViewById(R.id.shanchu);
        textView3= (TextView) view.findViewById(R.id.quxiao);
        checkBox = (CheckBox) view.findViewById(R.id.quanxuan);
        button   = (Button) view.findViewById(R.id.btn_sc);

        gridView1= (GridView) view.findViewById(R.id.gridview_quanbu);
        gridView2= (GridView) view.findViewById(R.id.gridview_shixiao);

        ll_1= (LinearLayout) view.findViewById(R.id.ll_1);
        ll_2= (LinearLayout) view.findViewById(R.id.ll_2);
        ll_sc= (LinearLayout) view.findViewById(R.id.ll_sc);

        setList(images.length);
        baobeiAdapter1=new BaobeiAdapter1(getContext(),images,list1);
        baobeiAdapter2=new BaobeiAdapter2(getContext(),images,list2);

        gridView1.setAdapter(baobeiAdapter1);
        gridView2.setAdapter(baobeiAdapter2);
        textView1.setTextColor(getResources().getColor(R.color.bobo));

        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        imageView.setOnClickListener(this);
        textView3.setOnClickListener(this);

        button.setText("删除" + "(" + String.valueOf(num) + ")");
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checked = true;
                    setList(images.length);
                } else {
                    checked = false;
                    setList(images.length);
                }
                if (isTF) {
                    baobeiAdapter1.notifyDataSetChanged();
                } else {
                    baobeiAdapter2.notifyDataSetChanged();
                }
            }
        });

    }

    public void setList(int a) {
        list1.clear();
        list2.clear();
        for (int i=0;i<a;i++){
            list1.add(checked);
            list2.add(checked);
        }
    }

    @Override
    public void onDestroy() {
        num=0;
        checkBox.setChecked(false);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.quanbubaobei:
                checkBox.setChecked(false);
                num=0;
                textView1.setTextColor(getResources().getColor(R.color.bobo));//文字设置为绿色
                textView2.setTextColor(Color.GRAY);//文字设置为灰色
                ll_1.setVisibility(View.VISIBLE);  //点击全部宝贝ll_1显示
                ll_2.setVisibility(View.GONE);     //点击全部宝贝ll_2隐藏
                imageView.setVisibility(View.VISIBLE); //删除图标显示
                textView3.setVisibility(View.GONE);   //取消文字显示
                display=false;
                isTF=true;
                baobeiAdapter1.setcheckDisplay(display);
                baobeiAdapter1.notifyDataSetChanged();
                ll_sc.setVisibility(View.GONE);  //底部全部删除栏隐藏
                break;
            case R.id.shixiaobaobei:
                checkBox.setChecked(false);
                num=0;
                textView1.setTextColor(Color.GRAY);  //文字设置为灰色
                textView2.setTextColor(getResources().getColor(R.color.bobo));  //文字设置为绿色
                ll_1.setVisibility(View.GONE);    //点击失效宝贝ll_1隐藏
                ll_2.setVisibility(View.VISIBLE); //点击失效宝贝ll_2显示
                imageView.setVisibility(View.VISIBLE);//删除图标显示
                textView3.setVisibility(View.GONE);   //取消文字显示
                display=false;
                isTF=false;
                baobeiAdapter2.setcheckDisplay(display);
                baobeiAdapter2.notifyDataSetChanged();
                ll_sc.setVisibility(View.GONE);  //底部全部删除栏隐藏
                break;
            case R.id.shanchu:
                imageView.setVisibility(View.GONE);  //删除图标显示
                textView3.setVisibility(View.VISIBLE);//取消文字显示
                ll_sc.setVisibility(View.VISIBLE); //底部全部删除栏显示
                checkBox.setChecked(false);
                display=true;
                if(isTF){
                    baobeiAdapter1.setcheckDisplay(display);
                    baobeiAdapter1.notifyDataSetChanged();
                }else {
                    baobeiAdapter2.setcheckDisplay(display);
                    baobeiAdapter2.notifyDataSetChanged();
                }
                break;
            case R.id.quxiao:
                checkBox.setChecked(false);
                num=0;
                imageView.setVisibility(View.VISIBLE);
                textView3.setVisibility(View.GONE);
                display=false;
                ll_sc.setVisibility(View.GONE);
                if(isTF){
                    baobeiAdapter1.setcheckDisplay(display);
                    baobeiAdapter1.notifyDataSetChanged();
                }else {
                    baobeiAdapter2.setcheckDisplay(display);
                    baobeiAdapter2.notifyDataSetChanged();
                }
                break;
        }
    }
}
