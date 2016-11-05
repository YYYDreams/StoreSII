package com.example.administrator.store3.customview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.administrator.store3.R;
import com.example.administrator.store3.adapter.PopupAdapter;

/**
 * Created by Administrator on 2016/7/4.
 * 自定义弹窗
 */
public class PopupWindowss extends PopupWindow implements PopupWindow.OnDismissListener,AdapterView.OnItemClickListener{
    private Context context;
    private PopupWindow popupWindow;
    private PopupAdapter popuAdapter;
    public PopupWindowss(Context context ,int dess  ,String[]strs) {
        this.context = context;
        View contentView = LayoutInflater.from(context).inflate(
                dess, null);
        ListView listView = (ListView) contentView.findViewById(R.id.pop_list);
        popuAdapter = new PopupAdapter(context,strs);
        listView.setAdapter(popuAdapter);
        // 将加载的视图view载入PopubWindow,并且设置popupwindow这个组件的动画效果
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.transparent)));
        // 当popWindow消失时的监听
        popupWindow.setOnDismissListener(this);
        listView.setOnItemClickListener(this);
    }

    public void showAsDropDown(View v) {
        popupWindow.showAsDropDown(v);//相对某个控件的位置（正左下方），无偏移
        // showAtLocation(View parent, int gravity, int x, int
        // y)：相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        //popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        // 设置setFocusable(true)，要不然点击弹窗其他地方以及返回键，弹窗都不会退出
        // 也才能让popupWindow里面的布局控件获得点击的事件，否则就被它的父亲view给拦截了
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
       // popupWindow.setAnimationStyle(R.style.popwin_anim_style_s);//设置窗口显示的动画效果
        popupWindow.getContentView().setOnTouchListener(new View.OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                popupWindow.setFocusable(false);
                popupWindow.dismiss();
                return true;
            }

        });
        //更新
        popupWindow.update();
    }

    @Override
    public void onDismiss() {
        popupWindow.dismiss();
    }

    @Override
    public boolean isShowing() {
        return super.isShowing();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        popuAdapter.setSelectedItem(position);
        onDismiss();
    }
}
