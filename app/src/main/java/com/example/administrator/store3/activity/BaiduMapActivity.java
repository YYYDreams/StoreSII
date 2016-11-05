package com.example.administrator.store3.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.example.administrator.store3.R;
import com.example.administrator.store3.StoreApplication;
import com.example.administrator.store3.util.ImageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.baidu.navisdk.adapter.PackageUtil.getSdcardDir;

/**
 * Created by Administrator on 2016/6/1.
 * 导航到店地图界面
 */
public class BaiduMapActivity extends Activity{
    /*地图控件*/
    private MapView mapView;
    /*百度地图对象*/
    private com.baidu.mapapi.map.BaiduMap baiduMap;
    /*弹窗框*/
    private View viewCache = null;
    /*计划路线节点状态值*/
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    /*终点节点状态值*/
    public static final String RESET_END_NODE = "resetEndNode";
    /*存放主页面对象的数组*/
    public static List<Activity> activityList = new LinkedList<Activity>();
    /*SD卡路径*/
    private String mSDCardPath = null;
    /*app文件名*/
    private static final String APP_FOLDER_NAME = "Store";
    /*地图终点坐标*/
    BNRoutePlanNode eNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*将此主界面放入集合中*/
        activityList.add(this);
        /*加载主界面*/
        setContentView(R.layout.baidu_layout);
        /*初始化控件*/
        initView();

    }
    /*初始化地图以及绑定事件监听*/
    private void initView() {
        mapView= (MapView) findViewById(R.id.baidu);
        baiduMap=mapView.getMap();
        //空白地图, 基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。
        // 使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
        //baiduMap.setMapType(com.baidu.mapapi.map.BaiduMap.MAP_TYPE_NONE);
        //普通地图
        baiduMap.setMapType(com.baidu.mapapi.map.BaiduMap.MAP_TYPE_NORMAL);
        //卫星地图
        //baiduMap.setMapType(com.baidu.mapapi.map.BaiduMapActivity.MAP_TYPE_SATELLITE);
        //构建Marker图标
        BitmapDescriptor bitmap = createBitmapDescriptor(R.drawable.marka,35,45);
        //定义Maker坐标点
        //LatLng point = new LatLng(39.963175, 116.400244);
        LatLng point= StoreApplication.getlatLng();
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        baiduMap.addOverlay(option);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(point)
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        baiduMap.setMapStatus(mMapStatusUpdate);
        showView();
        //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        InfoWindow mInfoWindow = new InfoWindow(viewCache, point, -47);
        //显示InfoWindow
        baiduMap.showInfoWindow(mInfoWindow);
    }
    //获得标记的图标
    private BitmapDescriptor createBitmapDescriptor(int res,int w,int h) {
        Bitmap bitmap=  BitmapFactory.decodeResource(getResources(), res);
        BitmapDescriptor bd= BitmapDescriptorFactory.fromBitmap(ImageUtil.zoomBitmap(bitmap, w, h));
        return bd;
    }

    private void showView() {
        // 一个自定义的布局，作为显示的内容
        viewCache  = LayoutInflater.from(this).inflate(
                R.layout.popuwindow_layout, null);
        // 设置按钮的点击事件
        ImageView image = (ImageView) viewCache.findViewById(R.id.iamge_daozheli);
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (BaiduNaviManager.isNaviInited()) {
                    routeplanToNavi(BNRoutePlanNode.CoordinateType.BD09LL);
                }
            }
        });
        if (initDirs()) {
            initNavi();
        }
    }

      String authinfo=null;
    private void initNavi() {
        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
                    authinfo = "key校验成功!";
                } else {
                    authinfo = "key校验失败, " + msg;
                }
                BaiduMapActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(BaiduMapActivity.this, authinfo, Toast.LENGTH_LONG).show();
                    }
                });
            }

            public void initSuccess() {
                Toast.makeText(BaiduMapActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                initSetting();
            }

            public void initStart() {
                Toast.makeText(BaiduMapActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
            }

            public void initFailed() {
                Toast.makeText(BaiduMapActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
            }


        },  null, null, null);

    }
   /*初始化sd卡中的文件夹,用于缓存存地图*/
    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    /*线路导航*/
    private void routeplanToNavi(BNRoutePlanNode.CoordinateType coType) {

        BNRoutePlanNode sNode = null;
        BNRoutePlanNode eNode = null;

        switch (coType) {
            case BD09LL: {
                //sNode = new BNRoutePlanNode(116.30784537597782, 40.057009624099436, "百度大厦", null, coType);
                //eNode = new BNRoutePlanNode(116.40386525193937, 39.915160800132085, "北京天安门", null, coType);
                sNode = new BNRoutePlanNode(StoreApplication.getlatLng().longitude, StoreApplication.getlatLng().latitude,"起点",null,coType);
                eNode = new BNRoutePlanNode(114.02942532655421, 22.540103056545544, "终点",null, coType);
                break;
            }
            default:
                break;
        }
        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);
            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode));
        }
    }
    /*地图线路监听者*/
    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
			/**
			 * 设置途径点以及resetEndNode会回调该接口
			 */
            for (Activity ac : activityList) {
                if (ac.getClass().getName().endsWith("BNDemoGuideActivity")) {
                    return;
                }
            }
            Intent intent = new Intent(BaiduMapActivity.this, BaiDuNavActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, mBNRoutePlanNode);
            bundle.putSerializable(RESET_END_NODE,  eNode);
            intent.putExtras(bundle);
            startActivity(intent);

        }

        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub
            Toast.makeText(BaiduMapActivity.this, "算路失败", Toast.LENGTH_SHORT).show();
        }
    }
    /*导航模式设置*/
    private void initSetting(){
        BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
        BNaviSettingManager.setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
    }

}

