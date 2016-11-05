package com.example.administrator.store3.wenhao;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.adapter.PayAdapter;

/**
 * Created by Administrator on 2016/7/15.
 *
 */
public class PayDetailsPopouWindow implements View.OnClickListener,PopupWindow.OnDismissListener ,AdapterView.OnItemClickListener{
    private Context context;
    private PopupWindow popupWindow;
    private Activity activity;
    private ListView mListView;
    private PayAdapter payAdapter;
    private LinearLayout mLinerLayout_a;
    private LinearLayout mLinerLayout_b;
    private LinearLayout mLinerLayout_c;
    private TextView mTextView_a;
    private ImageView mImageView_a;
    private TextView mTextView_b;
    private View contentView;
    private String[]banks = {"交通银行储蓄卡(5448)","建设银行储蓄卡(5448)","工商银行储蓄卡(5448)",
            "招商银行储蓄卡(5448)","中国银行储蓄卡(5448)"};
    public PayDetailsPopouWindow(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        // 自定义的一个控件作为显示内容
        contentView = LayoutInflater.from(context).inflate(
                R.layout.popu_fukuan, null);
        initpopup();
        initView();
    }

    private void initpopup() {
        // 将加载的视图view载入PopubWindow,并且设置popupwindow这个组件的动画效果
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 当popWindow消失时的监听
        popupWindow.setOnDismissListener(this);
    }
       //初始化view
    private void initView() {
        mListView = (ListView) contentView.findViewById(R.id.listview);
        mLinerLayout_a = (LinearLayout) contentView.findViewById(R.id.ll1);
        mLinerLayout_b = (LinearLayout) contentView.findViewById(R.id.ll2);
        mLinerLayout_c = (LinearLayout) contentView.findViewById(R.id.ll3);
        mTextView_a = (TextView) contentView.findViewById(R.id.back);
        mImageView_a = (ImageView) contentView.findViewById(R.id.back2);
        mTextView_b = (TextView) contentView.findViewById(R.id.textka);
        payAdapter = new PayAdapter(context,banks);
        mListView.setAdapter(payAdapter);
        mLinerLayout_b.setOnClickListener(this);
        mTextView_a.setOnClickListener(this);
        mImageView_a.setOnClickListener(this);
        mListView.setOnItemClickListener(this);
    }

    public void showAsDropDown(View v) {
        // showAsDropDown(View anchor)：相对某个控件的位置（正左下方），无偏移
        // showAtLocation(View parent, int gravity, int x, int
        // y)：相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        // 设置setFocusable(true)，要不然点击弹窗其他地方以及返回键，弹窗都不会退出
        // 也才能让popupWindow里面的布局控件获得点击的事件，否则就被它的父亲view给拦截了
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);//设置窗口显示的动画效果
        //更新
        popupWindow.update();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                popupWindow.dismiss();
                break;
            case R.id.back2:
                mLinerLayout_c.setVisibility(View.GONE);
                mLinerLayout_a.setVisibility(View.VISIBLE);
                break;
            case R.id.ll2:
                mLinerLayout_a.setVisibility(View.GONE);
                mLinerLayout_c.setVisibility(View.VISIBLE);
                break;
        }


    }

    @Override
    public void onDismiss() {
        WindowManager.LayoutParams lp=activity.getWindow().getAttributes();
        lp.alpha = 1f;
        activity.getWindow().setAttributes(lp);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        payAdapter.setSelectedItem(position);
        payAdapter.notifyDataSetChanged();
        mTextView_b.setText(banks[position]);
        mTextView_b.postInvalidate();
        mLinerLayout_c.setVisibility(View.GONE);
        mLinerLayout_a.setVisibility(View.VISIBLE);
    }
}
