package com.example.administrator.store3.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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
import com.example.administrator.store3.activity.MessageActivity;
import com.example.administrator.store3.activity.SearchActivity;
import com.example.administrator.store3.adapter.MasterAdapter;
import com.example.administrator.store3.entity.Master;
import com.example.administrator.store3.libs.ILoadingLayout;
import com.example.administrator.store3.libs.PullToRefreshBase;
import com.example.administrator.store3.libs.PullToRefreshGridView;
import com.example.administrator.store3.util.NetworkUtils;
import com.example.administrator.store3.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 大师界面
 * Created by Administrator on 2016/4/20.
 */
public class MasterFragment extends Fragment implements View.OnClickListener,PullToRefreshBase.OnRefreshListener2<GridView>,AdapterView.OnItemClickListener {
    int pageNumber=1;
    int totalPages=0;
    private TextView tuijian;
    private TextView mingqi;
    private TextView xinjin;
    private List<Master>list=new ArrayList<>();
    private PullToRefreshGridView pullToRefreshGridView;
    private MasterAdapter mMasterAdapter;
    private ImageView fanhui;
    private ImageView xiaoxi;
    private LinearLayout mLinearLayout;
    ImageView imageView;
    TextView textView;
    //创建一个弱引用对象,来判断fragment是否回收
    protected WeakReference<View> mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //在Fragment onCreateView方法中先判断view是否回收
            if (mRootView == null || mRootView.get() == null){
                View view=inflater.inflate(R.layout.master_mian,null);
                setView(view);
                mRootView = new WeakReference<>(view);
            }else {
                ViewGroup parent = (ViewGroup) mRootView.get().getParent();
                if (parent != null) {
                    parent.removeView(mRootView.get());
                }
            }
        return mRootView.get();
    }
    @TargetApi(Build.VERSION_CODES.M)
    private void setView(View view) {
        tuijian = (TextView) view.findViewById(R.id.tuijian);
        mingqi = (TextView) view.findViewById(R.id.mingqi);
        xinjin = (TextView) view.findViewById(R.id.xinjin);
        fanhui = (ImageView) view.findViewById(R.id.fanhui);
        xiaoxi = (ImageView) view.findViewById(R.id.xiaoxi);
        mLinearLayout= (LinearLayout) view.findViewById(R.id.line_seach);
        pullToRefreshGridView = (PullToRefreshGridView) view.findViewById(R.id.pull_refresh_grid);
        tuijian.setOnClickListener(this);
        mingqi.setOnClickListener(this);
        xinjin.setOnClickListener(this);
        fanhui.setOnClickListener(this);
        xiaoxi.setOnClickListener(this);
        mLinearLayout.setOnClickListener(this);
        volleyPostSD();
        if(mMasterAdapter==null){
            mMasterAdapter=new MasterAdapter(getContext(),list);
            pullToRefreshGridView.setAdapter(mMasterAdapter);
        }else {
            mMasterAdapter.notifyDataSetChanged();
        }
        //设置刷新的状态
        initIndicator();
        pullToRefreshGridView.setOnRefreshListener(this);
        pullToRefreshGridView.setOnItemClickListener(this);
    }
    private void volleyPostSD(){
        String url = UrlUtil.url+"master/view";
        StringRequest request=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        Log.e("str","str="+str);
                        JSONObject jsonObject= null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject jsonObject1=jsonObject.getJSONObject("master");
                            totalPages= jsonObject1.getInt("totalPages");
                            JSONArray jsonArray=jsonObject1.getJSONArray("content");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonobject= (JSONObject) jsonArray.get(i);
                                Master master= JSON.parseObject(jsonobject.toString(), Master.class);
                                list.add(master);
                            }
                            mMasterAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
               // Toast.makeText(getContext(), "网络异常", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //设置请求头部传送参数
                Map<String, String> map = new HashMap<>();
                /*map.put("latStr",String.valueOf(StoreApplication.getlatLng().latitude));
                map.put("lngStr",String.valueOf(StoreApplication.getlatLng().longitude));*/
                map.put("pageNumber",pageNumber+"");
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
    Intent intent= new Intent();
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tuijian:
                tuijian.setTextColor(Color.RED);
                mingqi.setTextColor(Color.GRAY);
                xinjin.setTextColor(Color.GRAY);
                break;
            case R.id.mingqi:
                tuijian.setTextColor(Color.GRAY);
                mingqi.setTextColor(Color.RED);
                xinjin.setTextColor(Color.GRAY);
                break;
            case R.id.xinjin:
                tuijian.setTextColor(Color.GRAY);
                mingqi.setTextColor(Color.GRAY);
                xinjin.setTextColor(Color.RED);
                break;
            case R.id.fanhui:
                getFragmentManager().popBackStack();
                break;
            case R.id.xiaoxi:
                intent.setClass(getContext(), MessageActivity.class);
                startActivity(intent);
                break;
            case R.id.line_seach:
                intent=new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<GridView> refreshView) {
        pageNumber++;
        pullToRefreshGridView.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(final PullToRefreshBase<GridView> refreshView) {
        pageNumber++;
        // 显示加载时的时间
        String label = DateUtils.formatDateTime(
                getActivity().getApplicationContext(),
                System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME
                        | DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_ABBREV_ALL);

        // Update the LastUpdatedLabel
        refreshView.getLoadingLayoutProxy()
                .setLastUpdatedLabel(label);
        // 下拉刷新操作
        new GetDataTask().execute();
    }
    //设置刷新的状态
    private void initIndicator() {
        //设置MODE
        pullToRefreshGridView.setMode(PullToRefreshBase.Mode.BOTH);

        ILoadingLayout startLabels = pullToRefreshGridView
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel(getString(R.string.pull_to_refresh));// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel(getString(R.string.refreshing));// 刷新时
        startLabels.setReleaseLabel(getString(R.string.release_to_refresh));// 下来达到一定距离时，显示的提示
        ILoadingLayout endLabels = pullToRefreshGridView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel(getString(R.string.pullup_to_load));// 刚上拉时，显示的提示
        endLabels.setRefreshingLabel(getString(R.string.loading));// 刷新时
        endLabels.setReleaseLabel(getString(R.string.release_to_load));// 下来达到一定距离时，显示的提示
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    private class GetDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                volleyPostSD();
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            pullToRefreshGridView.onRefreshComplete();
        }
    }

}




