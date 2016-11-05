package com.example.administrator.store3.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.store3.R;
import com.example.administrator.store3.StoreApplication;
import com.example.administrator.store3.entity.Master;
import com.example.administrator.store3.entity.MasterSon;
import com.example.administrator.store3.util.UrlUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/11.
 *implements GestureDetector.OnGestureListener
 * 大师详情
 */
public class MasterSonActivity extends Activity implements View.OnTouchListener{
    /*翻页加载第一页*/
    private int pageNumber=1;
    /*翻页加载的总页数*/
    private int totalPages=0;
    /*画廊控件*/
    private ViewFlipper flipper;       //左右滑动页面
   // private GestureDetector detector;  //手势识别核心类
    /*数据初始化*/
    private int[] mIconIDs = new int[]{R.drawable.s_sp_02, R.drawable.s_sp_02, R.drawable.s_sp_02,
           R.drawable.s_sp_02, R.drawable.s_sp_02, R.drawable.s_sp_02};
    /*手指按下屏幕时x坐标点*/
    private int down;
    /*手指离开屏幕时x坐标点*/
    private int up;
    /*此id用于从后台获取加载的对象*/
    private int id;
    /*返回按钮*/
    private ImageView mImageView;
    /*存放页面对象的集合*/
    private List<MasterSon>masterSons= new ArrayList<>();
    /*存放文字的集合*/
    private List<String>picture = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*加载主界面*/
        setContentView(R.layout.masterson_mian);
        /*获取id*/
        Intent intent = getIntent();
        id = intent.getIntExtra("id",1);
        /*通过id从后台加载数据*/
        volleyPostSD();
        /*初始化控件*/
        initView();
    }
    /*初始化控件以及绑定事件监听*/
    private void initView() {
       //detector = new GestureDetector(this);
        flipper= (ViewFlipper) findViewById(R.id.ViewFlipper2);
        for(int i=0;i<picture.size();i++){
            flipper.addView(addTextView(picture.get(i)));//添加view
        }
        //在触摸监听中绑定手势监听
        flipper.getParent().requestDisallowInterceptTouchEvent(true);
        flipper.setOnTouchListener(this);
        mImageView= (ImageView) findViewById(R.id.image_left);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 为viewflipper添加view
     * @param url
     * @return
     */
    private View addTextView(String url) {
        ImageView iv = new ImageView(this);
        //动态设置控件的大小尺寸
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
        //绑定需要设置的控件
        iv.setLayoutParams(lp2);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        //iv.setBackgroundDrawable(ContextCompat.getDrawable(this, id));
        ImageLoader.getInstance().displayImage(url,iv);
        return iv;
    }
    private void volleyPostSD(){
        String url = UrlUtil.url+"master/selectById?id="+id;
        StringRequest request=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        Log.e("str", "str=" + str);
                        JSONObject jsonObject= null;
                        try {
                            jsonObject = new JSONObject(str);
                            JSONObject jsonObject1=jsonObject.getJSONObject("master");
                            totalPages= jsonObject1.getInt("totalPages");
                            JSONArray jsonArray=jsonObject1.getJSONArray("content");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonobject= (JSONObject) jsonArray.get(i);
                                MasterSon master= JSON.parseObject(jsonobject.toString(), MasterSon.class);
                                masterSons.add(master);
                            }
                            JSONArray jsonObject2 = jsonObject.getJSONArray("picture");
                            for(int i=0;i<jsonObject2.length();i++){
                                JSONObject jsonObject3= (JSONObject) jsonObject2.get(i);
                                picture.add(jsonObject3.toString());
                            }
                            //masterAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MasterSonActivity.this, "网络异常", Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //设置请求头部传送参数
                Map<String, String> map = new HashMap<>();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN: {
                //按住事件发生后执行代码的区域
                down = (int) event.getX();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //移动事件发生后执行代码的区域
                up = (int) event.getX();
                if (down - up >= 120) {
                    //当滑动距离向左超过120时,为页面的翻页效果设置左进左出动画效果
                    flipper.setInAnimation(AnimationUtils.loadAnimation(MasterSonActivity.this, R.anim.push_left_in1));
                    flipper.setOutAnimation(AnimationUtils.loadAnimation(MasterSonActivity.this, R.anim.push_left_out1));
                    flipper.showNext();
                    return true;
                }
                if (down - up < -120) {
                    //当滑动距离向右超过120时,为页面的翻页效果设置右进右出动画效果
                    flipper.setInAnimation(AnimationUtils.loadAnimation(MasterSonActivity.this, R.anim.push_right_in1));
                    flipper.setOutAnimation(AnimationUtils.loadAnimation(MasterSonActivity.this, R.anim.push_right_out1));
                    flipper.showPrevious();
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                //松开事件发生后执行代码的区域
                up = (int) event.getX();
                if (down - up >= 120) {
                    //当滑动距离向左超过120时,为页面的翻页效果设置左进左出动画效果
                    flipper.setInAnimation(AnimationUtils.loadAnimation(MasterSonActivity.this, R.anim.push_left_in1));
                    flipper.setOutAnimation(AnimationUtils.loadAnimation(MasterSonActivity.this, R.anim.push_left_out1));
                    flipper.showNext();
                    return true;
                }
                if (down - up < -120){
                    //当滑动距离向右超过120时,为页面的翻页效果设置右进右出动画效果
                    flipper.setInAnimation(AnimationUtils.loadAnimation(MasterSonActivity.this, R.anim.push_right_in1));
                    flipper.setOutAnimation(AnimationUtils.loadAnimation(MasterSonActivity.this, R.anim.push_right_out1));
                    flipper.showPrevious();
                    return true;
                }
                break;
            }
        }
        return true;
    }
}
