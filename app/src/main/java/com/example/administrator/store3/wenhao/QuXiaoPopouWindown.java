package com.example.administrator.store3.wenhao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.activity.AllIndentActivity;
import com.example.administrator.store3.activity.ConfirmOrderActivity;

/**
 * Created by Administrator on 2016/8/11.
 */
public class QuXiaoPopouWindown implements View.OnClickListener,PopupWindow.OnDismissListener {
    private Context context;
    private AllIndentActivity activity;
    private PopupWindow popupWindow;
    private TextView quxiao;
    private TextView queding;

    public QuXiaoPopouWindown(Context context,AllIndentActivity activity) {
        this.context = context;
        this.activity = activity;
        // 自定义的一个控件作为显示内容
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.quxiaodingdan, null);
        quxiao = (TextView) contentView.findViewById(R.id.quxiao);
        queding = (TextView) contentView.findViewById(R.id.queding);
        quxiao.setOnClickListener(this);
        queding.setOnClickListener(this);

        // 将加载的视图view载入PopubWindow,并且设置popupwindow这个组件的动画效果
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 当popWindow消失时的监听
        popupWindow.setOnDismissListener(this);
    }

    public void showAsDropDown(View v) {
        // showAsDropDown(View anchor)：相对某个控件的位置（正左下方），无偏移
        // showAtLocation(View parent, int gravity, int x, int
        // y)：相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        // 设置setFocusable(true)，要不然点击弹窗其他地方以及返回键，弹窗都不会退出
        // 也才能让popupWindow里面的布局控件获得点击的事件，否则就被它的父亲view给拦截了
        popupWindow.setFocusable(true);
        //popupWindow.setAnimationStyle(R.style.popwin_anim_style);//设置窗口显示的动画效果
        //更新
        popupWindow.update();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.quxiao:
                popupWindow.dismiss();
                break;
            case R.id.queding:
                popupWindow.dismiss();
                break;
        }

    }

    @Override
    public void onDismiss() {
        WindowManager.LayoutParams lp=activity.getWindow().getAttributes();
        lp.alpha = 1f;
        activity.getWindow().setAttributes(lp);
    }
}
