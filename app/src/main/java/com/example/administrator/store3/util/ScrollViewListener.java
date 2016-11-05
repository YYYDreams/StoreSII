package com.example.administrator.store3.util;

import com.example.administrator.store3.customview.ObservableScrollView;

/**
 *  ScrollViewListener监听接口
 * Created by Administrator on 2016/5/10.
 */
public interface ScrollViewListener {

    void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);

}
