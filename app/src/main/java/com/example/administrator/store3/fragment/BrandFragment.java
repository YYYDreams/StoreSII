package com.example.administrator.store3.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.store3.R;
import com.example.administrator.store3.StoreApplication;
import com.example.administrator.store3.activity.BrandStoreActivity;
import com.example.administrator.store3.activity.MessageActivity;
import com.example.administrator.store3.activity.SearchActivity;
import com.example.administrator.store3.adapter.BrandAdapter;
import com.example.administrator.store3.customview.xlistview.XListView;
import com.example.administrator.store3.entity.Brand;
import com.example.administrator.store3.util.UrlUtil;
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
 * Created by Administrator on 2016/4/19.
 * 品牌界面
 */
public class BrandFragment extends Fragment implements XListView.IXListViewListener,AdapterView.OnItemClickListener,View.OnClickListener{
    /*搜索框*/
    private LinearLayout mLinearLayout;
    /*可下拉上拉刷新列表*/
    private XListView listView;
    /*时间*/
    private String str;
    /*存放品牌的数组*/
    private List<Brand>list=new ArrayList<>();  //list链表存放brand
    /*返回按钮*/
    private ImageView fanhui;
    /*消息按钮*/
    private ImageView xiaoxi;
    /*推荐按钮*/
    private TextView tuijian;
    /*最新按钮*/
    private TextView zuixin;
    /*销量*/
    private TextView xiaoliang;
    /*列表适配器*/
    private BrandAdapter baseAdapter;
    /*初始页码值*/
    private int pageNumber=1;//加载初始页码
    /*总页码值*/
    private int totalPages; //总页数
    /*异步任务*/
    private Handler handler1 = new Handler();  //handler用于延时效果
    /*时间格式化*/
    private SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");//时间格式
    //创建一个弱引用对象,来判断fragment是否回收
    protected WeakReference<View> mRootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //在Fragment onCreateView方法中先判断view是否回收
        if (mRootView == null || mRootView.get() == null){
            /*加载主界面布局*/
            View view=inflater.inflate(R.layout.brand_mian,null);
            /*初始化控件*/
            setView(view);
            mRootView = new WeakReference<View>(view);
        }else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        return mRootView.get();
    }
    /*初始化控件以及绑定事件监听*/
    private void setView(View view) {
        listView= (XListView) view.findViewById(R.id.brand_listview);
        fanhui = (ImageView) view.findViewById(R.id.fanhui);
        xiaoxi = (ImageView) view.findViewById(R.id.xiaoxi);
        tuijian = (TextView) view.findViewById(R.id.tuijian);
        zuixin = (TextView) view.findViewById(R.id.mingqi);
        xiaoliang = (TextView) view.findViewById(R.id.xinjin);
        mLinearLayout= (LinearLayout) view.findViewById(R.id.line_seach);
        mLinearLayout.setOnClickListener(this);
        tuijian.setOnClickListener(this);
        zuixin.setOnClickListener(this);
        xiaoliang.setOnClickListener(this);
        fanhui.setOnClickListener(this);
        xiaoxi.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            listView.setOverScrollMode(View.OVER_SCROLL_NEVER); //设置listview的滑动模式
        }
        // 设置xlistview可以加载、刷新
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        volleyPostSD(); //加载第一页数据
        if(baseAdapter!=null){//创建adapter更新数据
            baseAdapter.notifyDataSetChanged();
        }else {
            baseAdapter = new BrandAdapter(getContext(),list);
            listView.setAdapter(baseAdapter);
        }
        // 设置回调函数
        listView.setXListViewListener(this);
        listView.setOnItemClickListener(this);
    }
    //向服务器接口发送请求
    private void volleyPostSD(){
        String url= UrlUtil.url+"brand/view";
        StringRequest request=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String json) {
                        try {
                            Log.e("json","json="+json);
                            JSONObject jsonObject=new JSONObject(json);
                            JSONObject jsonObject1=jsonObject.getJSONObject("resultMessage");
                            totalPages = jsonObject1.getInt("totalPages");
                            JSONArray jsonArray=jsonObject1.getJSONArray("content");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonobject= (JSONObject) jsonArray.get(i);
                                Brand brand= JSON.parseObject(jsonobject.toString(), Brand.class);
                                list.add(brand);
                            }
                            baseAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(),"网络异常",Toast.LENGTH_LONG).show();
            }
        }
        ){
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
        request.setTag("brand1");
        //将请求加入全局队列中
        StoreApplication.getHttpQueues().add(request);
    }
    @Override
    public void onRefresh() { //下拉刷新
        pageNumber=1;
        listView.setPullLoadEnable(true);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        str = formatter.format(curDate);
        listView.setRefreshTime(str);  //显示刷新时间
        // 模拟刷新数据，1s之后停止刷新
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.stopRefresh();
                volleyPostSD();
            }
        }, 1000);
    }
    @Override
    public void onLoadMore() { //上拉加载更多
        //list2.clear();
        pageNumber++;
        if(pageNumber<=totalPages){
            handler1.postDelayed(new Runnable() {
                // 模拟加载数据，1s之后停止加载
                @Override
                public void run() {
                    listView.stopLoadMore();
                    volleyPostSD();

                }
            }, 1000);
        }else {
            Toast.makeText(getContext(),"亲,已经啦到最底部啦",Toast.LENGTH_SHORT).show();
            listView.setPullLoadEnable(false);
            listView.stopLoadMore();
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(),BrandStoreActivity.class);
        intent.putExtra("id",list.get(position-1).getId());
        getActivity().startActivity(intent);
    }
    @Override
    public void onDestroy() {
        StoreApplication.getHttpQueues().cancelAll("brand1");
        list.clear();
        list=null;
        super.onDestroy();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fanhui:
                /*点击返回按钮销毁页面*/
                getFragmentManager().popBackStack();
                break;
            case R.id.xiaoxi:
                /*点击消息按钮跳转到消息界面*/
                Intent intent= new Intent();
                intent.setClass(getContext(), MessageActivity.class);
                startActivity(intent);
                break;
            case R.id.tuijian:
                /*点击推荐*/
                tuijian.setTextColor(Color.RED);
                zuixin.setTextColor(Color.GRAY);
                xiaoliang.setTextColor(Color.GRAY);
                break;
            case R.id.mingqi:
                /*点击名气*/
                tuijian.setTextColor(Color.GRAY);
                zuixin.setTextColor(Color.RED);
                xiaoliang.setTextColor(Color.GRAY);
                break;
            case R.id.xinjin:
                /*点击新进*/
                tuijian.setTextColor(Color.GRAY);
                zuixin.setTextColor(Color.GRAY);
                xiaoliang.setTextColor(Color.RED);
                break;
            case R.id.line_seach:
                /*点击搜索框*/
                Intent i=new Intent(getActivity(),SearchActivity.class);
                startActivity(i);
                break;
        }
    }
}
