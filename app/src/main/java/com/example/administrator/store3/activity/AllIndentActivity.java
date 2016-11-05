package com.example.administrator.store3.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.adapter.AllIndentAdapter;

/**
 * Created by Administrator on 2016/5/21.
 * 全部订单页面
 */
public class AllIndentActivity extends Activity implements View.OnClickListener{
    /*订单列表*/
    private ListView listView;
    /*列表适配器*/
    private AllIndentAdapter allIndentAdapter;
    /*返回按钮*/
    private ImageView imageView;
    /*待付款布局*/
    private LinearLayout linearLayout1;
    /*待发货布局*/
    private LinearLayout linearLayout2;
    /*待收货布局*/
    private LinearLayout linearLayout3;
    /*待评价布局*/
    private LinearLayout linearLayout4;
    /*待退款/售后布局*/
    private LinearLayout linearLayout5;
    /*待付款图标*/
    private ImageView imageView1;
    /*待发货图标*/
    private ImageView imageView2;
    /*待收货图标*/
    private ImageView imageView3;
    /*待评价图标*/
    private ImageView imageView4;
    /*待售后图标*/
    private ImageView imageView5;
    /*待付款数量*/
    private TextView textView1;
    /*待发货数量*/
    private TextView textView2;
    /*待收货数量*/
    private TextView textView3;
    /*待评价数量*/
    private TextView textView4;
    /*待售后数量*/
    private TextView textView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*加载页面布局*/
        setContentView(R.layout.qbdd_listview);
        /*初始化订单列表*/
        listView= (ListView) findViewById(R.id.list_qndd);
        /*新建订单列表适配器*/
        allIndentAdapter=new AllIndentAdapter(this);
        /*订单列表绑定适配器*/
        listView.setAdapter(allIndentAdapter);
        /*初始化控件以及绑定监听事件*/
        initViews();
    }
    /*初始化控件以及绑定监听事件*/
    private void initViews() {
        imageView= (ImageView) findViewById(R.id.image0);
        imageView.setOnClickListener(this);
        linearLayout1 = (LinearLayout) findViewById(R.id.dfk);
        linearLayout2 = (LinearLayout) findViewById(R.id.dfh);
        linearLayout3 = (LinearLayout) findViewById(R.id.dsh);
        linearLayout4 = (LinearLayout) findViewById(R.id.dpj);
        linearLayout5 = (LinearLayout) findViewById(R.id.dtk);
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        linearLayout4.setOnClickListener(this);
        linearLayout5.setOnClickListener(this);
        imageView1 = (ImageView) findViewById(R.id.dfk_img);
        imageView2 = (ImageView) findViewById(R.id.dfh_img);
        imageView3 = (ImageView) findViewById(R.id.dsh_img);
        imageView4 = (ImageView) findViewById(R.id.dpj_img);
        imageView5 = (ImageView) findViewById(R.id.dtk_img);
        textView1 = (TextView) findViewById(R.id.dfk_tv);
        textView2 = (TextView) findViewById(R.id.dfh_tv);
        textView3 = (TextView) findViewById(R.id.dsh_tv);
        textView4 = (TextView) findViewById(R.id.dpj_tv);
        textView5 = (TextView) findViewById(R.id.dtk_tv);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image0:
                /*点击返回图标,销毁当前页面*/
                this.finish();
                break;
            case R.id.dfk:
                /*点击待付款布局,代付款图标设置为亮色,其他图标设置成暗色*/
                imageView1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daifukuan));
                imageView2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daifahuo2));
                imageView3.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.paijian2));
                imageView4.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daipingjia2));
                imageView5.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daituikuan2));
                textView1.setVisibility(View.GONE);
                textView2.setVisibility(View.VISIBLE);
                textView3.setVisibility(View.VISIBLE);
                textView4.setVisibility(View.VISIBLE);
                textView5.setVisibility(View.VISIBLE);
                break;
            case R.id.dfh:
                /*点击待发货布局,待发货图标设置为亮色,其他图标设置成暗色*/
                imageView1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daifukuan2));
                imageView2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daifahuo));
                imageView3.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.paijian2));
                imageView4.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daipingjia2));
                imageView5.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daituikuan2));
                textView1.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.GONE);
                textView3.setVisibility(View.VISIBLE);
                textView4.setVisibility(View.VISIBLE);
                textView5.setVisibility(View.VISIBLE);
                break;
            case R.id.dsh:
                /*点击待收货布局,待收货图标设置为亮色,其他图标设置为暗色*/
                imageView1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daifukuan2));
                imageView2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daifahuo2));
                imageView3.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.paijian));
                imageView4.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daipingjia2));
                imageView5.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daituikuan2));
                textView1.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
                textView3.setVisibility(View.GONE);
                textView4.setVisibility(View.VISIBLE);
                textView5.setVisibility(View.VISIBLE);
                break;
            case R.id.dpj:
                /*点击待评价布局,待评价图标设置为亮色,其他图标设置为暗色*/
                imageView1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daifukuan2));
                imageView2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daifahuo2));
                imageView3.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.paijian2));
                imageView4.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daipingjia));
                imageView5.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daituikuan2));
                textView1.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
                textView3.setVisibility(View.VISIBLE);
                textView4.setVisibility(View.GONE);
                textView5.setVisibility(View.VISIBLE);
                break;
            case R.id.dtk:
                /*点击待退款布局,待退款图标设置为亮色,其他图标设置为暗色*/
                imageView1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daifukuan2));
                imageView2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daifahuo2));
                imageView3.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.paijian2));
                imageView4.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daipingjia2));
                imageView5.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.daituikuan));
                textView1.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
                textView3.setVisibility(View.VISIBLE);
                textView4.setVisibility(View.VISIBLE);
                textView5.setVisibility(View.GONE);
                break;
        }
    }
}
