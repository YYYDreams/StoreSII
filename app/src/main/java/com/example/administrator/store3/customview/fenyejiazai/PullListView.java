package com.example.administrator.store3.customview.fenyejiazai;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/8/5.
 */
public class PullListView extends ListView implements Pullable{
    public PullListView(Context context) {
        super(context);
    }

    public PullListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PullListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        super.setOnItemClickListener(listener);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        super.setOnScrollListener(l);
    }

    @Override
    public boolean canPullDown() {
        if (getCount() == 0)
        {
            //没有item的时候可以下拉刷新
            return true;
        } else if (getFirstVisiblePosition() == 0
                && getChildAt(0).getTop() >= 0)
        {
            // 滑到顶部了
            return true;
        } else
            return false;
    }

    @Override
    public boolean canPullUp() {
        if (getCount() == 0)
        {
            // 没有item的时候可以上啦加载
            return true;
        } else if (getLastVisiblePosition() == (getCount() - 1))
        {
            // 滑到底部了
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
                    && getChildAt(
                    getLastVisiblePosition()
                            - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
                return true;
        }
        return false;
    }
}
