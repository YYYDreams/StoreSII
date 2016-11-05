package com.example.administrator.store3.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.store3.R;
import com.example.administrator.store3.adapter.CircleMenuAdapter;
import com.example.administrator.store3.activity.LoginActivity;
import com.example.administrator.store3.activity.MessageActivity;
import com.example.administrator.store3.customview.CircleMenuLayout;
import com.example.administrator.store3.util.CircleMenuItem;
import com.example.administrator.store3.util.Pub;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/16.
 *
 * 首页页面
 */
public class MainFragment extends Fragment implements View.OnClickListener{
    /*圆形菜单*/
    private CircleMenuLayout mCircleMenuLayout;
    /*登陆和消息按钮*/
    private ImageView imageView,imageView6;
    /*session值*/
    private final String session=Pub.mSESSION;
    /*圆形菜单内容*/
    private String[] mItemTexts = new String[]{"店铺", "大师", "品牌",
            "活动", "鉴赏", "南玉"};
    /*圆形菜单内容图标*/
    private int[] mItemImgs = new int[]{R.drawable.home_mbank_1_normal,
            R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
            R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
            R.drawable.home_mbank_6_normal};
    /*店铺页面*/
    private DianPuFragment dianPuFragment;
    /*大师页面*/
    private MasterFragment masterFragment;
    /*品牌页面*/
    private BrandFragment brandFragment;
    /*活动页面*/
    private OperativeFragment operativeFragment;
    /*鉴赏页面*/
    private AppreciateFragment appreciateFragment;
    /*南玉页面*/
    private NanyuFragment nanyuFragment;
    /*文件存储*/
    private SharedPreferences sp;
    /*圆形菜单中间图标*/
    private ImageView centerImg;
    /*亮色矩阵*/
    public final float[] BT_SELECTED = new float[] {1,0,0,0,50,0,1,0,0,50,0,0,1,0,50,0,0,0,1,0};
    /*暗色矩阵*/
    public final float[] BT_NOT_SELECTED = new float[] {1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0};
    /*圆形菜单选项数组*/
    private List<CircleMenuItem> mMenuItems = new ArrayList<CircleMenuItem>();
    /*圆形菜单适配器*/
    private CircleMenuAdapter circleMenuAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*加载主布局*/
        View view = inflater.inflate(R.layout.activity_main, null);
        /*初始化控件*/
        imageView = (ImageView) view.findViewById(R.id.iV0);
        imageView6= (ImageView) view.findViewById(R.id.image6);
        View centerView = LayoutInflater.from(getContext()).inflate(R.layout.circle_menu_item_center,null,false);
        centerImg = (ImageView) centerView.findViewById(R.id.logo);
        imageView6.setOnClickListener(this);
        setLoq();
        dianPuFragment = new DianPuFragment();
        masterFragment = new MasterFragment();
        brandFragment = new BrandFragment();
        operativeFragment = new OperativeFragment();
        appreciateFragment = new AppreciateFragment();
        nanyuFragment = new NanyuFragment();
        centerImg.setOnTouchListener(new View.OnTouchListener() {
          @Override
          public boolean onTouch(final View v, MotionEvent event) {
              if (event.getAction() == MotionEvent.ACTION_DOWN) {
                  v.getBackground().setColorFilter(
                          new ColorMatrixColorFilter(BT_SELECTED));
                  v.setBackgroundDrawable(v.getBackground());
                  new Handler() {
                      @Override
                      public void handleMessage(Message msg) {
                          v.getBackground().setColorFilter(
                                  new ColorMatrixColorFilter(BT_NOT_SELECTED));
                         v.setBackgroundDrawable(v.getBackground());
                        v.invalidate();
                      }
                  }.sendEmptyMessageDelayed(0, 1500);
              } else if (event.getAction() == MotionEvent.ACTION_UP) {
                  v.getBackground().setColorFilter(
                          new ColorMatrixColorFilter(BT_NOT_SELECTED));
                  v.setBackgroundDrawable(v.getBackground());
                  v.invalidate();
              } else if (event.getAction() == MotionEvent.ACTION_MOVE){
                    float x = event.getRawX(); // 获取相对于屏幕左上角的 x 坐标值
                    float y = event.getRawY(); // 获取相对于屏幕左上角的 y 坐标值
                    RectF rect = calcViewScreenLocation(v);
                    boolean isInViewRect = rect.contains(x, y);
                    if(!isInViewRect){
                        v.getBackground().setColorFilter(
                                new ColorMatrixColorFilter(BT_NOT_SELECTED));
                        v.setBackgroundDrawable(v.getBackground());
                        v.invalidate();
                    }
                }
                return true;
            }
        });

        //获取sessionid
        sp=getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.iV0:
                        if (Pub.mSESSION.length() <= 0) {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        } else if (Pub.mSESSION.length() > 0) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("")//设置对话框标题
                                    .setMessage("确认退出登录？")//设置显示的内容
                                            // .setView(R.layout.diglog)
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                        //添加确定按钮
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                            getLogins();
                                            Pub.mSESSION = "";
                                            //退出是保存session
                                            sp.edit().putString("SESSION", Pub.mSESSION).commit();
                                            imageView.setImageDrawable(getResources().getDrawable(R.drawable.denglu));
                                            imageView.invalidate();

                                        }
                                    }).show();
                        }

                }
            }
        });
        initData(mItemTexts, mItemImgs);
        //中心视图
        if(circleMenuAdapter==null){
            circleMenuAdapter = new CircleMenuAdapter(mMenuItems);

        }else {
            circleMenuAdapter.notifyDataSetChanged();
        }

        mCircleMenuLayout = (CircleMenuLayout) view.findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setAdapter(new CircleMenuAdapter(mMenuItems));
        mCircleMenuLayout.setCenterView(centerView);
        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                switch (pos) {
                    /*点击圆形菜单item,跳转页面*/
                    case 0:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_content, dianPuFragment).addToBackStack(null).commit();
                        break;
                    case 1:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_content, masterFragment).addToBackStack(null).commit();
                        break;
                    case 2:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_content, brandFragment).addToBackStack(null).commit();
                        break;
                    case 3:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_content, operativeFragment).addToBackStack(null).commit();
                        break;
                    case 4:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_content, appreciateFragment).addToBackStack(null).commit();
                        break;
                    case 5:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_content, nanyuFragment).addToBackStack(null).commit();
                }
            }

            @Override
            public void itemCenterClick(View view) {
                try {
                    mCircleMenuLayout.post(971f);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }
    /*初始化数据*/
    private void initData(String[] mItemTexts, int[] mItemImgs) {
        if(mMenuItems!=null){
            mMenuItems.clear();
        }
        if (mItemImgs==null && mItemTexts==null){
            throw new IllegalArgumentException("文本和图片不能为空");
        }
        int count = mItemImgs==null ? mItemTexts.length: mItemImgs.length;
        if (mItemImgs!=null && mItemTexts!=null){
            count = Math.min(mItemImgs.length,mItemTexts.length);
        }
        for (int i=0;i<count;i++){
            mMenuItems.add(new CircleMenuItem(mItemImgs[i],mItemTexts[i]));
        }
    }

    /**
     * 计算指定的 View 在屏幕中的坐标。
     */
    public static RectF calcViewScreenLocation(View view) {
        int[] location = new int[2];
        // 获取控件在屏幕中的位置，返回的数组分别为控件左顶点的 x、y 的值
        view.getLocationOnScreen(location);
        return new RectF(location[0], location[1], location[0] + view.getWidth(),
                location[1] + view.getHeight());
    }

    //切换图片的方法
    private void setLoq() {
        if (Pub.mSESSION.length()<=0) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.denglu));
            imageView.invalidate();
        } else if (Pub.mSESSION.length()>0) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.tuichu));
            imageView.invalidate();
        }
    }
    /**
     * 登出
     */
    private void getLogins(){
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        String url_logout=Pub.SPOP_URI+"logout/logout";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
               url_logout, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //使用JSONObject给response转换编码
                    JSONObject jsonObject = new JSONObject(response);
                    String resultMessage= (String) jsonObject.get("resultMessage");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }){

         /*   @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if(Pub.mSESSION!=null && Pub.mSESSION.length()>0){
                    HashMap<String,String> headers=new HashMap<>();
                    headers.put("cookie",Pub.mSESSION);
                    Log.e("3", " Pub.mSESSION=" + Pub.mSESSION);
                    return headers;
                }else {
                    return super.getHeaders();
                }
            }*/
        };
        stringRequest.setTag("POST");
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image6:
                Intent intent=new Intent(getActivity(), MessageActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }
}
