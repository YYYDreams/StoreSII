package com.example.administrator.store3;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.PlanNode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/4/21.
 * application类
 */
public class StoreApplication extends Application {
    /*坐标点*/
    private static PlanNode stNode; //坐标点
    /*volley队列*/
    private static RequestQueue queues;
    /*定位百度SDK的核心类*/
    public static LocationClient mLocationClient;//定位SDK的核心类
    /*定位监听器*/
    public BDLocationListener mMyLocationListenernew=new MyLocationListener();//定义监听类
    /*经纬度*/
    public static LatLng latLng;
    /*地址*/
    public static String addrsrt;
    /*百度本地类*/
    public static BDLocation mBDLocation;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    addrsrt= (String) msg.obj;
                    break;
                case 2:
                    latLng= (LatLng) msg.obj;
                    break;
                case 3:
                    mBDLocation= (BDLocation) msg.obj;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());//初始化百度地图
        queues= Volley.newRequestQueue(getApplicationContext());
        mLocationClient=new LocationClient(this);//百度地图核心类初始化;
        InitLocation();
        mLocationClient.registerLocationListener(mMyLocationListenernew);//百度核心类注册定位接口
        mLocationClient.start();//开启定位
    }
    //获得网络请求队列
    public static RequestQueue getHttpQueues(){
        return  queues;
    }
    //获得坐标点
    public static PlanNode getStNode() {
        return stNode;
    }
    //设置坐标点
    public static void setStNode(PlanNode stNode) {
        StoreApplication.stNode = stNode;
    }
    //百度定位设置
    private void InitLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=0;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
    //百度定位定位接口
    public class MyLocationListener implements BDLocationListener {
        double latitude;
        double longitude;
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            if (location.getLocType() == BDLocation.TypeGpsLocation){//通过GPS定位
                 latitude = location.getLatitude();//获取纬度
                longitude=location.getLongitude();//获取经度
                sb.append(location.getAddrStr());//获得当前地址
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){//通过网络连接定位
                sb.append(location.getAddrStr());//获得当前地址
                latitude = location.getLatitude();//获取纬度
                longitude=location.getLongitude();//获取经度
            }
            LatLng latLngs=new LatLng(latitude,longitude);
            //String addrsrts=sb.toString().substring(2);
            String addrsrts=sb.toString();
            logString(addrsrts);
            logLatLng(latLngs);
            logBDL(location);
           // volleyGet(latitude,longitude);
        }
    }
    private void volleyGet(double latitude,double longitude){
        String url="http://api.map.baidu.com/ag/coord/convert?from=0&to=4&"+"x="+latitude+"&y="+longitude;
        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
                    JSONObject jsonObject1= (JSONObject) jsonArray.get(1);
                    JSONObject jsonObject2= (JSONObject) jsonArray.get(2);
                    String x= (String) jsonObject1.get("x");
                    String y= (String) jsonObject2.get("y");
                    LatLng latLngs=new LatLng(Double.valueOf(getFromBASE64(x)),Double.valueOf(getFromBASE64(y)));
                    logLatLng(latLngs);
                    Log.e("lat", "lats=" + latLngs);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "网络异常", Toast.LENGTH_LONG).show();
            }
        });
    }
    // 将 BASE64 编码的字符串 s 进行解码
    public static String getFromBASE64(String s) {
        if (s == null) return null;
        //BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = Base64.decode(s,1);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }
    private void logBDL(BDLocation location) {
        Message message=new Message();
        message.what=3;
        message.obj=location;
        handler.sendMessage(message);
    }

    private void logLatLng(LatLng latLngs) {
        this.latLng=latLngs;
        Message message=new Message();
        message.what=2;
        message.obj=latLngs;
        handler.sendMessage(message);
    }

    private void logString(String addrsrts) {
        this.addrsrt=addrsrts;
        Message message=new Message();
        message.what=1;
        message.obj=addrsrts;
        handler.sendMessage(message);

    }

    /* *
     * 显示请求字符串
     * @param str
     */
    public static String getAddrsrt() {
        return addrsrt;
    }
    public static void cancel(){
        mLocationClient.stop();
    }
    public static LatLng getlatLng(){
        return latLng;
    }
    public static BDLocation getBDLocation(){
        return mBDLocation;
    }
}
