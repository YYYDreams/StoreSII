package com.example.administrator.store3.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.store3.R;
import com.example.administrator.store3.StoreApplication;
import com.example.administrator.store3.activity.MapDingWeiActivity;
import com.example.administrator.store3.activity.SearchActivity;
import com.example.administrator.store3.adapter.LV1_Fg1Adapter;
import com.example.administrator.store3.adapter.LV2_Fg1Adapter;
import com.example.administrator.store3.customview.PopupWindowss;
import com.example.administrator.store3.customview.xlistview.XListView;
import com.example.administrator.store3.entity.Store;
import com.example.administrator.store3.entity.StoreLeiBie;
import com.example.administrator.store3.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.administrator.store3.R.color.black;
import static com.example.administrator.store3.R.color.white;

/**
 * Created by Administrator on 2016/4/15.
 * 店铺界面
 */
public class DianPuFragment extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener,XListView.IXListViewListener {
    /*返回按钮*/
    private ImageView Back;
    /*定位按钮*/
    private ImageView Locator;
    /*显示定位位置*/
    private TextView LocatorShow;
    /*店铺分类列表*/
    private ListView listView1_fg1;
    /*店铺列表适配器*/
    private LV2_Fg1Adapter lv2_fg1Adapter;
    /*店铺分类列表适配器*/
    private LV1_Fg1Adapter lv1_fg1Adapter;
    /*店铺类别数组*/
    private ArrayList<StoreLeiBie>list2=new ArrayList<>();
    private ArrayList<StoreLeiBie>list1=new ArrayList<>();
    /*店铺数组*/
    private ArrayList<Store>list3=new ArrayList<>();
    /*时间格式*/
    private SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");//时间格式
    /*时间*/
    private String strs;
    /*访问后台数据的路径*/
    private String urlx= UrlUtil.url+"store/view";
    /*附近按钮*/
    private TextView fujin;
    /*排序按钮*/
    private TextView paixu;
    /*店家按钮*/
    private TextView dianjia;
    /*附近弹窗*/
    private PopupWindowss popupWindowss;
    /*排序弹窗*/
    private PopupWindowss popupWindowss1;
    /*下拉刷新列表*/
    private XListView mListView;    //下拉刷新listview
    /*搜索框*/
    private LinearLayout mLinearLayout;
    /*初始页码*/
    private int pageNumber=1;
    /*总页码*/
    private int totalPages;
    /*附近弹窗内容*/
    private String[]diss={"不限","1km内","2km内","3km内"};
    /*排序弹窗内容*/
    private String[]sort={"推荐","人气","销量","评价"};
    /*异步任务*/
    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    list1.clear();
                    list1= (ArrayList<StoreLeiBie>) msg.obj;
                    //判断adapter是否创建,没有创建就新创建,创建了就直接刷新
                    if(lv1_fg1Adapter==null){
                        lv1_fg1Adapter=new LV1_Fg1Adapter(getContext(),list1);
                        listView1_fg1.setAdapter(lv1_fg1Adapter);
                    }else {
                        lv1_fg1Adapter.notifyDataSetChanged();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    //创建一个弱引用对象,来判断fragment是否回收
    protected WeakReference<View> mRootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //在Fragment onCreateView方法中先判断view是否回收
        if (mRootView == null || mRootView.get() == null){
            View view=inflater.inflate(R.layout.dianpu_mian,null);
            initView(view);
            mRootView = new WeakReference<View>(view);
        }else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        //点击跳转到地图定位页面
        Locator.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getContext(), MapDingWeiActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return mRootView.get();
    }
    private void initView(View view) {
            //初始化控件
            Back= (ImageView)view.findViewById(R.id.image1);
            Back.setOnClickListener(this);
            Locator= (ImageView)view.findViewById(R.id.image2);
            LocatorShow= (TextView) view.findViewById(R.id.text);
            LocatorShow.setText(StoreApplication.getAddrsrt());
            fujin= (TextView) view.findViewById(R.id.fujindedian);
            paixu= (TextView) view.findViewById(R.id.zhinengpaixu);
            mLinearLayout= (LinearLayout) view.findViewById(R.id.line_seach);
            initPopup();
            dianjia= (TextView) view.findViewById(R.id.dianjia);
            dianjia.setBackgroundColor(getContext().getResources().getColor(R.color.bobo));
            dianjia.setOnClickListener(this);
            fujin.setOnClickListener(this);
            paixu.setOnClickListener(this);
           mLinearLayout.setOnClickListener(this);

            listView1_fg1 = (ListView) view.findViewById(R.id.listview1);
            listView1_fg1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                listView1_fg1.setOverScrollMode(View.OVER_SCROLL_NEVER);
            }
            mListView = (XListView) view.findViewById(R.id.listview2);
            volleyPostFL(); //加载店铺分类列表
            volleyPostSD(urlx);  //加载全部店铺列表

        // 设置xlistview可以加载、刷新
           mListView.setPullLoadEnable(true);
           mListView.setPullRefreshEnable(true);

           if(lv2_fg1Adapter==null){//如果adapter为空,就创建
                lv2_fg1Adapter=new LV2_Fg1Adapter(getContext(),list3);
                mListView.setAdapter(lv2_fg1Adapter);
        }else {//不为空就刷新
                lv2_fg1Adapter.notifyDataSetChanged();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                mListView.setOverScrollMode(View.OVER_SCROLL_NEVER);
            }
            // 设置回调函数
           mListView.setXListViewListener(this);
        //给listview添加监听事件
          listView1_fg1.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //将点击的位置信息传给adapter
                dianjia.setBackgroundColor(getContext().getResources().getColor(R.color.white));
                lv1_fg1Adapter.setSelectedItem(position);
                lv1_fg1Adapter.notifyDataSetChanged();//更新adapter
                int ID = list1.get(position).getId();
                urlx= UrlUtil.url+"store/selectByCategory?id="+ID;
                pageNumber=1;
                list3.clear();
                volleyPostSD(urlx);  //根据点击listview1的位置加载listview2的数据
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fujindedian:
                if(popupWindowss.isShowing()){//判断悬浮窗口的显示或关闭
                    popupWindowss.onDismiss();
                }else {
                    popupWindowss.showAsDropDown(v);
                }
                break;
            case R.id.zhinengpaixu: //点击排序按钮显示悬浮窗口
                if(popupWindowss1.isShowing()){
                    popupWindowss1.onDismiss();
                }else {
                    popupWindowss1.showAsDropDown(v);
                }
                break;
            case R.id.image1://点击返回键关闭店铺页面回到主页面
                getFragmentManager().popBackStack();
                break;
            case R.id.dianjia://点击店家加载全部商店信息
                dianjia.setBackgroundColor(getContext().getResources().getColor(R.color.bobo));
                pageNumber=1;
                list3.clear();
                urlx= UrlUtil.url+"store/view";
                volleyPostSD(urlx);
                lv1_fg1Adapter.setSelectedItem(-1);//设置分类列表选中属性
                lv1_fg1Adapter.notifyDataSetChanged();
                break;
            case R.id.line_seach:
                Intent intent=new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
    private void volleyPost(final int position) {
        String url = UrlUtil.url+"store/selectByDistance";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {//s为请求返回的字符串数据
                        //list3.clear();
                        JSONObject jsonObject= null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject jsonObject1=jsonObject.getJSONObject("store");
                            JSONArray jsonArray=jsonObject1.getJSONArray("content");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonobject= (JSONObject) jsonArray.get(i);
                                Store store= JSON.parseObject(jsonobject.toString(), Store.class);
                                list3.add(store);
                            }
                            lv2_fg1Adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getContext(),"网络异常",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
               /* map.put("latStr",String.valueOf(StoreApplication.getlatLng().latitude));
                map.put("lngStr",String.valueOf(StoreApplication.getlatLng().longitude));*/
                map.put("pageNumber",String.valueOf(pageNumber));
                map.put("raidus",diss[position]);
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
        request.setTag("StorePost3");
        //将请求加入全局队列中
        StoreApplication.getHttpQueues().add(request);
    }
    //网络获取数据
    private void volleyPostFL(){
        String url= UrlUtil.url+"storecategory/view";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject >() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {//jsonObject为请求返回的Json格式数据
                        try {
                            JSONArray jsonArray=jsonObject.getJSONArray("storeCategory");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1= (JSONObject) jsonArray.get(i);
                                StoreLeiBie storeLeiBie= JSON.parseObject(jsonObject1.toString(), StoreLeiBie.class);
                                list2.add(storeLeiBie);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                            //用handler将获得的数据传给fragment
                            Message message=new Message();
                            message.obj=list2;
                            message.what=0;
                            handler1.sendMessage(message);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getContext(),"网络异常",Toast.LENGTH_LONG).show();
                    }
                });
        //设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
        request.setTag("StorePost1");
        //将请求加入全局队列中
        StoreApplication.getHttpQueues().add(request);
    }
    private void volleyPostSD(String url){
        mListView.setPullLoadEnable(true);
        StringRequest request=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        Log.e("str","str="+str);
                        JSONObject jsonObject= null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject jsonObject1=jsonObject.getJSONObject("store");
                            totalPages= jsonObject1.getInt("totalPages");
                            JSONArray jsonArray=jsonObject1.getJSONArray("content");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonobject= (JSONObject) jsonArray.get(i);
                                Store store= JSON.parseObject(jsonobject.toString(), Store.class);
                                list3.add(store);
                            }
                            lv2_fg1Adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("e","e="+totalPages);
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
                //设置请求头部传送参数
                Map<String, String> map = new HashMap<>();
               /* map.put("latStr",String.valueOf(StoreApplication.getlatLng().latitude));
                map.put("lngStr",String.valueOf(StoreApplication.getlatLng().longitude));*/
                map.put("pageNumber",String.valueOf(pageNumber));
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //设置请求头部格式
                Map<String,String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };
        //设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
        request.setTag("StorePost2");
        //将请求加入全局队列中
        StoreApplication.getHttpQueues().add(request);
    }
    private void initPopup(){
        popupWindowss = new PopupWindowss(getContext(),R.layout.popup_window,diss);
        popupWindowss1 = new PopupWindowss(getContext(),R.layout.popup_window,sort);
    }
    @Override
    public void onDestroy() {
        StoreApplication.getHttpQueues().cancelAll("StorePost1");
        StoreApplication.getHttpQueues().cancelAll("StorePost2");
        StoreApplication.getHttpQueues().cancelAll("StorePost3");
        popupWindowss.onDismiss();
        popupWindowss1.onDismiss();
        super.onDestroy();
    }
    @Override
    public void onRefresh() {
        mListView.setPullLoadEnable(true);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        strs = formatter.format(curDate);
        mListView.setRefreshTime(strs);  //显示刷新时间
        list3.clear();
        //mListView.stopRefresh();
        // 模拟刷新数据，1s之后停止刷新
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                mListView.stopRefresh();
                volleyPostSD(urlx);
            }
        }, 1000);

    }

    @Override
    public void onLoadMore() {
        pageNumber++;
        if(pageNumber<=totalPages) {//判断当前页数是不是小于等于总页数,
        // 如果是则加载下一页数据
            handler1.postDelayed(new Runnable() {
                // 模拟加载数据，1s之后停止加载
                @Override
                public void run() {
                    mListView.stopLoadMore();
                    volleyPostSD(urlx);
                }
            }, 1000);

        }else {//否则隐藏底部栏,不能加载更多了
            Toast.makeText(getContext(),"亲,已经啦到最底部啦",Toast.LENGTH_SHORT).show();
            mListView.setPullLoadEnable(false);
            mListView.stopLoadMore();
        }
    }
    @Override
    public void onDestroyView() {//当页面销毁时,弹出窗口同样销毁
        popupWindowss.onDismiss();
        popupWindowss1.onDismiss();
        super.onDestroyView();
    }
}
