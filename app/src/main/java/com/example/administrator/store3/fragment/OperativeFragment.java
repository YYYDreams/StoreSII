package com.example.administrator.store3.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.activity.SearchActivity;
import com.example.administrator.store3.adapter.OperativeAdapter;
import com.example.administrator.store3.customview.PopupWindowss;
import com.example.administrator.store3.customview.fenyejiazai.PullToRefreshLayout;
import com.example.administrator.store3.customview.staggeredgridview.StaggeredGridView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/20.
 * 活动界面
 */
public class OperativeFragment extends Fragment implements View.OnClickListener,PullToRefreshLayout.OnRefreshListener{
    /*瀑布流网格布局*/
    private StaggeredGridView mGridView;
    /*图片集合*/
    private int[] drowable;
    /*数组*/
    private List<Integer>list = new LinkedList<>();
    /*活动适配器*/
    private OperativeAdapter operativeAdapter;
    /*返回按钮*/
    private ImageView mImageView;
    /*类型按钮*/
    private TextView textView1;
    /*时间按钮*/
    private TextView textView2;
    /*刷新时间*/
    private TextView timeTv;
    /*时间格式*/
    private SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");//时间格式
    /*时间*/
    private String time;
    /*类型弹窗*/
    private PopupWindowss popupWindowss;
    /*时间弹窗*/
    private PopupWindowss popupWindowss1;
    /*搜索框*/
    private LinearLayout mLinearLayout;
    /*弹框数据*/
    String[]diss = {"全部","满立减","满就送","积分兑换","限时折扣"} ;
    String[]sort = {"全部","三天内"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*加载布局*/
        View view=inflater.inflate(R.layout.operative_mian,null);
        /*初始化控件*/
        initData();
        initView(view);
        initDataAndView(view);
        return view;
    }
    private void initDataAndView(View view) {
        ((PullToRefreshLayout) view.findViewById(R.id.refresh_view))
                .setOnRefreshListener(this);
        mGridView = (StaggeredGridView) view.findViewById(R.id.grid_view);
        operativeAdapter = new OperativeAdapter(getContext(),list);
        mGridView.setAdapter(operativeAdapter);

    }
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)

    private void initView(View view) {
        //初始化弹出窗口
        initPopu();
        //初始化按钮并为他们添加点击事件
        textView1 = (TextView) view.findViewById(R.id.leixing);
        textView2 = (TextView) view.findViewById(R.id.shijian);
        timeTv = (TextView) view.findViewById(R.id.time_sx);
        mLinearLayout= (LinearLayout) view.findViewById(R.id.line_seach);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        mLinearLayout.setOnClickListener(this);
        //点击返回键,关闭此fragement
        mImageView= (ImageView) view.findViewById(R.id.image0);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.image0:
                        getFragmentManager().popBackStack();
                        break;
                }
            }
        });
    }
    //初始化数据
    private void initData() {
        drowable = new int[]{
                R.drawable.s_sp_01, R.drawable.s_sp_02, R.drawable.s_sp_01,
                R.drawable.s_sp_01, R.drawable.s_sp_02, R.drawable.s_sp_01,
                R.drawable.s_sp_02, R.drawable.s_sp_01, R.drawable.s_sp_01,
        };
        for(int i=0;i<9;i++){
            list.add(drowable[i]);
        }
    }
    private void initPopu(){
        //创建popupwindow对象
        popupWindowss = new PopupWindowss(getContext(),R.layout.popup_window,diss);
        popupWindowss1= new PopupWindowss(getContext(),R.layout.popup_window,sort);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.leixing:
                //点击类型按钮,然后判断popupwindow是否正在显示
                //如果正在显示则关闭,如果没有则显示
                if(popupWindowss.isShowing()){
                    popupWindowss.onDismiss();
                }else {
                    popupWindowss.showAsDropDown(v);
                }
                break;
            case R.id.shijian:
                //点击类型按钮,然后判断popupwindow是否正在显示
                //如果正在显示则关闭,如果没有则显示
                if(popupWindowss1.isShowing()){
                    popupWindowss1.onDismiss();
                }else {
                    popupWindowss1.showAsDropDown(v);
                }
                break;
            case R.id.line_seach:
                Intent intent=new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        time = formatter.format(curDate);
        timeTv.setText("上次刷新时间:"+time);
        // 下拉刷新操作
        new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                //volleyPostSD();
                // 千万别告诉我刷新完成了哦
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
//上拉加载数据
        new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                //确实已经加载完成了
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 1000);
    }
}
