package com.example.administrator.store3.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.administrator.store3.R;
import com.example.administrator.store3.StoreApplication;
import com.example.administrator.store3.util.ImageUtil;


/**
 * Created by Administrator on 2016/6/3.
 */
public class MapDingWeiActivity extends Activity {
    /*地图控件*/
    private MapView mapView;
    /*百度地图控件*/
    private com.baidu.mapapi.map.BaiduMap baiduMap;
    /*自定义的布局，作为显示的内容*/
    private View viewCache;
    /*地图标记点*/
    private Marker mMarkerA;
    private Marker mMarkerB;
    private Marker mMarkerC;
    private Marker mMarkerD;
    /*点击地图标记弹窗*/
    private InfoWindow mInfoWindow;
    private InfoWindow mInfoWindow1;
    /*底部弹窗*/
    private PopupWindow popuWindow;
    //是否第一次定位的标志
    private boolean isFirstIn = true;
    // 初始化全局 bitmap 信息，不用时及时 recycle
    /*BitmapDescriptor bdA = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_marka);*/
    /*自定义图标*/
    BitmapDescriptor bdA=null;
    BitmapDescriptor bdB=null;
    BitmapDescriptor bdC=null;
    BitmapDescriptor bdD=null;


    //定义Maker坐标点
    LatLng point= StoreApplication.getlatLng();
    LatLng pointB=new LatLng(22.74584,113.839242);
    LatLng pointC=new LatLng(22.76182,113.832154);
    LatLng pointD=new LatLng(22.74935,113.843523);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*加载页面主布局*/
        setContentView(R.layout.mapdingwei_layout);
        /*初始化控件*/
        initView();

    }
    /*初始化控件以及添加点击事件*/
    private void initView(){
        mapView= (MapView) findViewById(R.id.mapview2);
        baiduMap = mapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(point, 15.2f);
        baiduMap.setMapStatus(msu);
        /*初始化地图标记物*/
        initMarker();
        Button button = new Button(getApplicationContext());
        button.setBackgroundResource(R.drawable.popup);
        button.setText("我的位置:沙井华美居");
        button.setTextSize(13);
        button.setTextColor(Color.BLACK);
        button.setPadding(5, 0, 5, 0);
        mInfoWindow = new InfoWindow(button, point, -45);
        baiduMap.showInfoWindow(mInfoWindow);
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker == mMarkerA) {

                } else if (marker == mMarkerB) {
                    showView(pointB);
                } else if (marker == mMarkerC) {
                    showView(pointC);
                } else if (marker == mMarkerD) {
                    showView(pointD);
                }
                return true;
            }
        });
        //设置监听,控制popuwindow的显示或隐藏
        baiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                if (popuWindow != null && popuWindow.isShowing()) {
                    popuWindow.dismiss();
                    popuWindow = null;
                }
            }
        });


    }
    private void showView(final LatLng latLng) {
        // 一个自定义的布局，作为显示的内容
        viewCache  = LayoutInflater.from(this).inflate(
                R.layout.popuwindow1_layout, null);
        // 设置按钮的点击事件
        popuWindow = new PopupWindow(viewCache, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.status_text));
        popuWindow.setBackgroundDrawable(cd);
        // y)：相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        popuWindow.showAtLocation(mapView, Gravity.BOTTOM, 0, 0);
        // 设置setFocusable(true)，要不然点击弹窗其他地方以及返回键，弹窗都不会退出
        // 也才能让popupWindow里面的布局控件获得点击的事件，否则就被它的父亲view给拦截了
        popuWindow.setFocusable(true);
        popuWindow.setAnimationStyle(R.style.popwin_anim_style);//设置窗口显示的动画效果
        ImageView image = (ImageView) viewCache.findViewById(R.id.iamge_daozheli1);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putDouble("x",latLng.latitude);
                bundle.putDouble("y",latLng.longitude);
                intent.putExtras(bundle);
                intent.setClass(MapDingWeiActivity.this, MapLinePlanActivity.class);
                MapDingWeiActivity.this.startActivity(intent);
            }
        });
    }
    /*初始化地图标记物*/
    private void initMarker() {
        bdA=createBitmapDescriptor(R.drawable.marka,35,45);
        bdB=createBitmapDescriptor(R.drawable.markb,35,45);
        bdC=createBitmapDescriptor(R.drawable.markb,35,45);
        bdD=createBitmapDescriptor(R.drawable.markb,35,45);
        /*标记物设置*/
        MarkerOptions ooA = new MarkerOptions().position(point).icon(bdA)
                .zIndex(9).draggable(true);
        /*将标记物添加到地图上*/
        mMarkerA = (Marker) (baiduMap.addOverlay(ooA));
        MarkerOptions ooB = new MarkerOptions().position(pointB).icon(bdB)
                .zIndex(3).draggable(true);
        mMarkerB = (Marker) baiduMap.addOverlay(ooB);
        MarkerOptions ooC = new MarkerOptions().position(pointC).icon(bdC)
                .zIndex(4).draggable(true);
        mMarkerC = (Marker) baiduMap.addOverlay(ooC);
        MarkerOptions ooD = new MarkerOptions().position(pointD).icon(bdD)
                .zIndex(6).draggable(true);
        mMarkerD = (Marker) baiduMap.addOverlay(ooD);

    }
 //获得标记的图标
    private BitmapDescriptor createBitmapDescriptor(int res,int w,int h) {
        Bitmap bitmap=  BitmapFactory.decodeResource(getResources(),res);
        BitmapDescriptor bd= BitmapDescriptorFactory.fromBitmap(ImageUtil.zoomBitmap(bitmap, w, h));
        return bd;
    }
    @Override
    protected void onPause() {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onResume() {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        mapView.onResume();
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mapView.onDestroy();
        super.onDestroy();
        // 回收 bitmap 资源
        bdA.recycle();
        bdB.recycle();
        bdC.recycle();
        bdD.recycle();
    }
}
