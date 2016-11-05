package com.example.administrator.store3.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.administrator.store3.customview.fancycoverflow.FancyCoverFlow;

import com.example.administrator.store3.customview.fancycoverflow.FancyCoverFlowSampleAdapter;
import com.example.administrator.store3.entity.Appreciate;
import com.example.administrator.store3.entity.AppreciateJade;
import com.example.administrator.store3.util.Pub;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 鉴赏翡翠页面
 * Created by Administrator on 2016/5/18.
 *
 */
public class AppreciateJadeActivity extends Activity {
    /*图片画廊控件*/
    private FancyCoverFlow mFancyCoverFlow;
    /*作品说明*/
    private TextView textView;
    /*加载初始页码*/
    private int pageNumber=1;
    /*对象id,通过它来向后台拿到数据*/
    private int id;
    /*对象集合,用于存放对象*/
    private LinkedList<AppreciateJade>linkedList = new LinkedList<>();
    /*图片画廊适配器*/
    private FancyCoverFlowSampleAdapter fancyCoverFlowSampleAdapter;
    private String str="内容";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*加载主界面*/
        this.setContentView(R.layout.appreciate_jade_main);
        /*获取id*/
        Intent intent=getIntent();
        id = intent.getIntExtra("id",0);
        mFancyCoverFlow = (FancyCoverFlow) findViewById(R.id.fancyCoverFlow);
        /*初始化控件*/
        sample();

    }
    private void sample() {
        textView= (TextView) findViewById(R.id.text_jade1);
        volleyPostJX();
        fancyCoverFlowSampleAdapter= new FancyCoverFlowSampleAdapter(this,linkedList);
        this.mFancyCoverFlow.setAdapter(fancyCoverFlowSampleAdapter);
        //设置未选中的图的透明度
        this.mFancyCoverFlow.setUnselectedAlpha(0.3f);
       /* //设置未选中的图的色彩饱和度
        this.mFancyCoverFlow.setUnselectedSaturation(0.0f);*/
        //未被选中的图像的缩放比例
        this.mFancyCoverFlow.setUnselectedScale(0.5f);
        //设置两个图之间的比例
        this.mFancyCoverFlow.setSpacing(-70);
        //设置未选中图像的最大旋转角度
        this.mFancyCoverFlow.setMaxRotation(0);
        //设置未被 选中图像的下沉度
        this.mFancyCoverFlow.setScaleDownGravity(0.8f);
        this.mFancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
    }
    //向服务器接口发送请求
    private void volleyPostJX() {
        String url= Pub.SPOP_URI+"appraisal/selectById?id="+id;
        StringRequest request=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String json) {
                        try {
                            Log.e("json", "json=" + json);
                            JSONObject jsonObject=new JSONObject(json);
                            JSONArray jsonObject1=jsonObject.getJSONArray("img");
                            for(int i=0;i<jsonObject1.length();i++){
                                JSONObject jsonobject= (JSONObject) jsonObject1.get(i);
                                AppreciateJade appreciate= JSON.parseObject(jsonobject.toString(), AppreciateJade.class);
                                linkedList.add(appreciate);
                            }
                            fancyCoverFlowSampleAdapter.notifyDataSetChanged();
                            JSONObject jsonObject2=jsonObject.getJSONObject("appraisal");
                            str = jsonObject2.getString("propaganda");
                            textView.setText(str);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(AppreciateJadeActivity.this, "网络异常", Toast.LENGTH_LONG).show();
            }
        }){
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

}
