package com.example.administrator.store3.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.store3.StoreApplication;
import com.example.administrator.store3.activity.AppreciateJadeActivity;
import com.example.administrator.store3.R;
import com.example.administrator.store3.activity.MessageActivity;
import com.example.administrator.store3.activity.PictureActivity;
import com.example.administrator.store3.activity.SearchActivity;
import com.example.administrator.store3.adapter.AppreciateAdapter;
import com.example.administrator.store3.customview.xlistview.XListView;
import com.example.administrator.store3.entity.Appreciate;
import com.example.administrator.store3.entity.Brand;
import com.example.administrator.store3.libs.ILoadingLayout;
import com.example.administrator.store3.libs.PullToRefreshBase;
import com.example.administrator.store3.libs.PullToRefreshListView;
import com.example.administrator.store3.util.NetworkUtils;
import com.example.administrator.store3.util.Pub;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/4/20.
 * 鉴赏界面
 */
public class AppreciateFragment extends Fragment implements AdapterView.OnItemClickListener,XListView.IXListViewListener,View.OnClickListener,AbsListView.OnScrollListener {
    /*返回按钮*/
    private ImageView fanhui;
    /*消息按钮*/
    private ImageView xiaoxi;
    /*名玉鉴定按钮*/
    private TextView jianding;
    /*名玉赏析按钮*/
    private TextView shangxi;
    /*下拉刷新列表*/
    private XListView listView;
    /*鉴赏列表适配器*/
    private AppreciateAdapter mAdapter;
    /*鉴赏对象数组*/
    private List<Appreciate> list=new ArrayList<>();
    /*事件字符串*/
    private String str;
    /*初始页码*/
    private int pageNumber=1;
    /*总页数*/
    private int totalPages;
    /*handler用于延时效果*/
    private Handler handler1 = new Handler();
    /*时间格式*/
    private SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
    /*布局容器*/
    private LinearLayout linearLayout, linearLayout2,mLinearLayout;
    /*网络刷新按钮*/
    private Button button;
    //创建一个弱引用对象,来判断fragment是否回收
    protected WeakReference<View> mRootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null || mRootView.get() == null){
            /*没有创建页面或者页面被回收,加载主布局*/
            View view=inflater.inflate(R.layout.appreciate_mian,null);
            /*初始化控件*/
            setView(view);
            mRootView = new WeakReference<>(view);
        }else {
            /*页面没有被回收,直接获取布局*/
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        return mRootView.get();
    }
    /**
     * 初始化控件
     * */
    private void setView(View view) {
        fanhui = (ImageView) view.findViewById(R.id.fanhui);
        listView= (XListView) view.findViewById(R.id.appreciate_listview);
        linearLayout = (LinearLayout) view.findViewById(R.id.ll_jianshang);
        linearLayout2 = (LinearLayout) view.findViewById(R.id.ll_wangluo);
        mLinearLayout= (LinearLayout) view.findViewById(R.id.line_seach);
        button = (Button) view.findViewById(R.id.shuaxinwangluo);
        button.setOnClickListener(this);
        jianding = (TextView) view.findViewById(R.id.btn_myjs);
        jianding.setBackgroundColor(getContext().getResources().getColor(R.color.bobo));
        shangxi = (TextView) view.findViewById(R.id.btn_gysx);
        shangxi.setBackgroundColor(getContext().getResources().getColor(R.color.huise));
        jianding.setOnClickListener(this);
        shangxi.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            listView.setOverScrollMode(View.OVER_SCROLL_NEVER); //设置listview的滑动模式
        }
        // 设置xlistview可以加载、刷新
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true); volleyPostJX(); //加载第一页数据
        if(mAdapter!=null){//创建adapter更新数据
            mAdapter.notifyDataSetChanged();
        }else {
            mAdapter=new AppreciateAdapter(getContext(),list);
            listView.setAdapter(mAdapter);
        }
        if(NetworkUtils.isNetAvailable(getContext())){
            linearLayout2.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

        }else {
            listView.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.VISIBLE);
        }
        /**
         * 设置回调函数
          */
        listView.setXListViewListener(this);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);

        fanhui.setOnClickListener(this);
        xiaoxi = (ImageView) view.findViewById(R.id.xiaoxi);
        xiaoxi.setOnClickListener(this);
        mLinearLayout.setOnClickListener(this);

    }
    @Override
    public void onRefresh() { //下拉刷新
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(NetworkUtils.isNetAvailable(getContext())){
                    pageNumber=1;
                    listView.setPullLoadEnable(true);
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    str = formatter.format(curDate);
                    listView.setRefreshTime(str);  //显示刷新时间
                    listView.stopRefresh();
                    list.clear();
                    volleyPostJX();
                    /*// 模拟刷新数据，1s之后停止刷新
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listView.stopRefresh();
                            list.clear();
                            volleyPostJX();
                        }
                    }, 1000);*/
                }else {
                    listView.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.VISIBLE);
                    listView.setPullLoadEnable(true);
                    listView.stopRefresh();
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    str = formatter.format(curDate);
                    listView.setRefreshTime(str);
                }
            }
        }, 1000);
    }
    @Override
    public void onLoadMore() { //上拉加载更多
        //list2.clear();
        if(NetworkUtils.isNetAvailable(getContext())){
            pageNumber++;
            listView.setPullLoadEnable(true);
            if(pageNumber<=totalPages){
                handler1.postDelayed(new Runnable() {
                    // 模拟加载数据，1s之后停止加载
                    @Override
                    public void run() {
                        listView.stopLoadMore();
                        volleyPostJX();
                    }
                }, 1000);
            }else {
                Toast.makeText(getContext(),"亲,已经啦到最底部啦",Toast.LENGTH_SHORT).show();
                listView.setPullLoadEnable(false);
                listView.stopLoadMore();
            }
        }else {
            listView.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.VISIBLE);
            listView.setPullLoadEnable(true);
            handler1.postDelayed(new Runnable() {
                // 模拟加载数据，1s之后停止加载
                @Override
                public void run() {
                    listView.stopLoadMore();
                }
            }, 1000);
        }
    }
    //向服务器接口发送请求
    private void volleyPostJX() {
        String url= Pub.SPOP_URI+"appraisal/view";
        StringRequest request=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String json) {
                        try {
                            Log.e("json", "json=" + json);
                            JSONObject jsonObject=new JSONObject(json);
                            JSONObject jsonObject1=jsonObject.getJSONObject("resultMessage");
                            totalPages = jsonObject1.getInt("totalPages");
                            JSONArray jsonArray=jsonObject1.getJSONArray("content");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonobject= (JSONObject) jsonArray.get(i);
                               Appreciate appreciate= JSON.parseObject(jsonobject.toString(), Appreciate.class);
                                list.add(appreciate);
                            }
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "网络异常", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("pageNumber",String.valueOf(pageNumber));
                return map;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };
        //设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
        request.setTag("Appreciate");
        //将请求加入全局队列中
        StoreApplication.getHttpQueues().add(request);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(),AppreciateJadeActivity.class);
        intent.putExtra("id", list.get(position - 1).getId());
        getActivity().startActivity(intent);
    }
    @Override
    public void onDestroy() {
        StoreApplication.getHttpQueues().cancelAll("Appreciate");
        list.clear();
        list=null;
        super.onDestroy();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fanhui:
                /*点击返回键,销毁主界面*/
                getFragmentManager().popBackStack();
                break;
            case R.id.xiaoxi:
                /*点击消息按钮,跳转到消息页面*/
                Intent intent= new Intent();
                intent.setClass(getContext(), MessageActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_myjs:
                /*点击名玉鉴定,加载名玉鉴定相关数据*/
                jianding.setBackgroundColor(getContext().getResources().getColor(R.color.bobo));
                shangxi.setBackgroundColor(getContext().getResources().getColor(R.color.huise));
                list.clear();
                volleyPostJX();
                break;
            case R.id.btn_gysx:
                /*点击名玉赏析,加载名玉赏析相关数据*/
                jianding.setBackgroundColor(getContext().getResources().getColor(R.color.huise));
                shangxi.setBackgroundColor(getContext().getResources().getColor(R.color.bobo));
                list.clear();
                volleyPostJX();
                break;
            case R.id.shuaxinwangluo:
                if(NetworkUtils.isNetAvailable(getContext())){//判断网络是否连接
                    linearLayout2.setVisibility(View.GONE);  //如果有网络连接,无网络提示界面隐藏
                    listView.setVisibility(View.VISIBLE);  //显示数据控件显示
                    if(mAdapter!=null){//创建adapter更新数据
                        mAdapter.notifyDataSetChanged();
                    }else {
                        listView.setAdapter(mAdapter);
                    }
                }
                break;
            case R.id.line_seach:
                Intent i=new Intent(getActivity(), SearchActivity.class);
                startActivity(i);
                break;
        }
    }
    /**
     * 获取当前滑动的高度
     * */
    public int getScrollY() {
        View c = listView.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight() ;
    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState){
            case SCROLL_STATE_IDLE:
                //空闲的时候显示
                if (getScrollY() > 100){
                    linearLayout.setVisibility(View.GONE);
                }
                if (getScrollY()<5){
                    linearLayout.setVisibility(View.VISIBLE);
                }
        }
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
