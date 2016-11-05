package com.example.administrator.store3.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.store3.R;
import com.example.administrator.store3.activity.SearchActivity;
import com.example.administrator.store3.adapter.ClassifyAdapter;
import com.example.administrator.store3.adapter.ClassifyAdapterwenzi;
import com.example.administrator.store3.customview.fenyejiazai.PullToRefreshLayout;
import com.example.administrator.store3.customview.staggeredgridview.StaggeredGridView;
import com.example.administrator.store3.entity.ClassifyTuPian;
import com.example.administrator.store3.entity.ClassifyWenZi;
import com.example.administrator.store3.libs.ILoadingLayout;
import com.example.administrator.store3.libs.PullToRefreshBase;
import com.example.administrator.store3.util.Pub;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * 分类的类
 * Created by Administrator on 2016/4/19.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ClassifyMainFragment extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener,
        PullToRefreshLayout.OnRefreshListener {
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");//时间格式
    private String time;
    private ClassifyAdapter adapter;
    private ListView listView1;
    private List<ClassifyWenZi> classifyUtil = new ArrayList<>();
    int[] drowable;
    List<Integer> list = new LinkedList<>();
    //上拉刷新添加头部
    private StaggeredGridView mGridView;
    private ClassifyAdapterwenzi adapterwenzi;
   /* private List<ClassifyTuPian> classifyTuPians=new ArrayList<>();*/
    private int pageNumber = 1;
    private long category = 11;
    private String urlx = Pub.SPOP_URI + "product/viewByProductCategory";
    private TextView mTextView, timeTv, textView;
    private  View vs;
    boolean a=false;
    //创建一个弱引用对象,来判断fragment是否回收
    protected WeakReference<View> mRootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //在Fragment onCreateView方法中先判断view是否回收
        if (mRootView == null || mRootView.get() == null) {
            View view = inflater.inflate(R.layout.classify_main, null);
            initData();
            //设置listview
            initListView(view);
            // 查找控件
            initDataAndView(view);
            mRootView = new WeakReference<>(view);
            //获取文字json数据
            // getJSONVolley();
            //获取图片json数据
            //   getPicture();
        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        return mRootView.get();
    }
    //设置刷新的状态
    private void initDataAndView(View view) {
     ((PullToRefreshLayout) view.findViewById(R.id.refresh_view))
                .setOnRefreshListener(this);
        mGridView = (StaggeredGridView) view.findViewById(R.id.grid_view);
        //设置头布局
        vs = View.inflate(getActivity(), R.layout.list_head_view, null);
        mGridView.addHeaderView(vs);

        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState){
                    case SCROLL_STATE_IDLE:
                        //空闲的时候显示
                        if (getScrollY() > 200) {
                            mGridView.removeHeaderView(vs);
                            a=true;
                        }
                        if(getScrollY() <= 200){
                            if(a){
                                mGridView.addHeaderView(vs);
                            }
                            a=false;
                        }
                }
            }
            /**
             * 获取当前滑动的高度
             * */
            public int getScrollY() {
                View c = mGridView.getChildAt(0);
                if (c == null) {
                    return 0;
                }
                int firstVisiblePosition = mGridView.getFirstVisiblePosition();
                int top = c.getTop();
                return -top + firstVisiblePosition * c.getHeight() ;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        adapter = new ClassifyAdapter(getContext(), list);
        mGridView.setAdapter(adapter);
    }



    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView:
                Intent intent1 = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent1);
                break;
            case R.id.t_View0:
                // classifyTuPians.clear();
                list.clear();
                adapter.notifyDataSetChanged();
                pageNumber = 0;
                adapterwenzi.setSelectedItem(-1);
                adapterwenzi.notifyDataSetChanged();
                new GetDataTask().execute();
                break;
        }
    }
    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        //获取当前的时间
        Date date = new Date(System.currentTimeMillis());
        time = formatter.format(date);
        timeTv.setText("上次刷新时间:" + time);
        //下拉刷新
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //volleyPostSD();
                // 千万别告诉我刷新完成了哦
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);

            }
        }.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        vs.setVisibility(View.VISIBLE);

        //上拉加载数据
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //确实已经加载完成了
             pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                new GetDataTask().execute();

            }
        }.sendEmptyMessageDelayed(0, 1000);

    }
    private class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // getPicture();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //mPullToRefreshHeadGridView.onRefreshComplete();

        }
    }

    //listview的显示和查找控件
    private void initListView(View view) {
        timeTv = (TextView) view.findViewById(R.id.time_sx);
        mTextView = (TextView) view.findViewById(R.id.t_View0);
        mTextView.setOnClickListener(this);
        textView = (TextView) view.findViewById(R.id.textView);
        textView.setOnClickListener(this);
        listView1 = (ListView) view.findViewById(R.id.lView1);
        adapterwenzi = new ClassifyAdapterwenzi(getContext(), classifyUtil, 1);
        listView1.setAdapter(adapterwenzi);
        listView1.setOnItemClickListener(this);
    }
    /**
     * 获取商品列名
     */
    private void getJSONVolley() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Pub.SPOP_URI + "productcategory/view";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonarray = response.getJSONArray("message");
                            for (int i = 1; i < jsonarray.length(); i++) {
                                JSONObject jo = (JSONObject) jsonarray.get(i);
                                ClassifyWenZi cutil = JSON.parseObject(jo.toString(), ClassifyWenZi.class);
                                classifyUtil.add(cutil);
                            }
                            adapterwenzi.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
    /**
     * 获取所有商品
     */
    private void getPicture() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = Pub.SPOP_URI + "product/view";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("tag", "response=" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("product");
                    JSONArray jsonArray = jsonObject1.getJSONArray("content");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jo = (JSONObject) jsonArray.get(i);
                        ClassifyTuPian ctupian = JSON.parseObject(jo.toString(), ClassifyTuPian.class);
                        //classifyTuPians.add(ctupian);

                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            //发送参数
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> registerParams = new HashMap<>();
                registerParams.put("pageNumber", String.valueOf(pageNumber));
                registerParams.put("category", String.valueOf(category));
                return registerParams;
            }

            //请求头
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        stringRequest.setTag("POST");
        requestQueue.add(stringRequest);
    }

    /**
     * 按照分类查找商品
     */
    private void getProductCategory(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("product");
                    JSONArray jsonArray = jsonObject1.getJSONArray("content");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jo = (JSONObject) jsonArray.get(i);
                        ClassifyTuPian ctupian = JSON.parseObject(jo.toString(), ClassifyTuPian.class);
                        //  classifyTuPians.add(ctupian);
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "网络异常,按照分类查找商品出错", Toast.LENGTH_LONG).show();
            }
        }) {
            //发送参数
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> registerParams = new HashMap<>();
                registerParams.put("pageNumber", String.valueOf(pageNumber));
                registerParams.put("category", String.valueOf(category));
                return registerParams;
            }

            //请求头
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        stringRequest.setTag("POST");
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        adapterwenzi.setSelectedItem(position);
        adapterwenzi.notifyDataSetChanged();
        category = classifyUtil.get(position).getId();
        //int ID = classifyUtil.get(position).getId();
        urlx = Pub.SPOP_URI + "product/viewByProductCategory?category=" + category;
        Log.e("QQaa", "urlx=" + classifyUtil.toString());
        //  classifyTuPians.clear();
        list.clear();
        pageNumber = 1;
        getProductCategory(urlx);
        // Log.e("QQ", "urlx=" + classifyTuPians.toString());

    }
    //初始化假数据数据
    private void initData() {
        drowable = new int[]{
                R.drawable.qwer, R.drawable.qwer,
                R.drawable.next_, R.drawable.next_, R.drawable.next_,
                R.drawable.qwer, R.drawable.qwer, R.drawable.qwer,
                R.drawable.qwer, R.drawable.qwer, R.drawable.qwer,
                R.drawable.next_, R.drawable.next_, R.drawable.next_,
                R.drawable.qwer, R.drawable.qwer, R.drawable.qwer,
                R.drawable.next_, R.drawable.next_, R.drawable.next_,
                R.drawable.qwer, R.drawable.qwer, R.drawable.qwer,
                R.drawable.next_, R.drawable.next_, R.drawable.next_};
        for (int i = 0; i < 26; i++) {
            list.add(drowable[i]);
        }
    }
}
