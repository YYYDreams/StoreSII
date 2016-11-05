package com.example.administrator.store3.customview;

/**
 * 可以监听滑动距离的滑动条
 * Created by Administrator on 2016/5/10.
 */
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 *
 * 自定义scrollview,带有滑动位置监听
 *
 * */
import com.example.administrator.store3.util.ScrollViewListener;

public class ObservableScrollView extends ScrollView {

    private ScrollViewListener scrollViewListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }
//获得滑动位置的值
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

}
