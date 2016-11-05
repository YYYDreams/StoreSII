package com.example.administrator.store3.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.BikingRouteOverlay;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.administrator.store3.R;
import com.example.administrator.store3.StoreApplication;
import com.example.administrator.store3.adapter.PopuAdapter;
import com.example.administrator.store3.util.CountUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 * 此类用来展示如何进行驾车、步行、公交路线搜索并在地图使用RouteOverlay、TransitOverlay绘制
 * 同时展示如何进行节点浏览并弹出泡泡
 */
public class MapLinePlanActivity extends Activity implements BaiduMap.OnMapClickListener,
        OnGetRoutePlanResultListener {
    // 浏览路线节点相关
    private Button mBtnPre = null; // 上一个节点
    private Button mBtnNext = null; // 下一个节点
    private TextView textView=null;
    private int nodeIndex = -1; // 节点索引,供浏览节点时使用
    private RouteLine route = null;
    private OverlayManager routeOverlay = null;
    private boolean useDefaultIcon = false;
    private TextView popupText = null; // 泡泡view

    // 地图相关，使用继承MapView的MyRouteMapView目的是重写touch事件实现泡泡处理
    // 如果不处理touch事件，则无需继承，直接使用MapView即可
    private MapView mMapView = null;    // 地图View
    private BaiduMap mBaidumap = null;
    // 搜索相关
    private RoutePlanSearch mSearch = null;    // 搜索模块，也可去掉地图模块独立使用
    private int distance;
    private String distancestr;
    private List<String> stepDetails=new ArrayList<>();
    private int needTime;
    private String needTimestr;
    private ImageView drive;
    private ImageView transit;
    private ImageView walk;
    private PlanNode stNode;
    private PlanNode enNode;
    private ProgressDialog dialog;
    private int FLAG_DISMISS = 1;
    private boolean flag = true;
    private ListView poplist;
    private int flags=0;
    private PopupWindow popuWindow;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == FLAG_DISMISS)
                dialog.dismiss();
                flag=false;
        }

    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maplineplan_layout);
        //设置一个progressdialog的弹窗
        dialog = ProgressDialog.show(this, null, "地图正在加载，请稍候...", true, false);
        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();
                while(flag){
                    try {
                        Thread.sleep(3000);
                        Message msg = mHandler.obtainMessage();
                        msg.what = FLAG_DISMISS;
                        mHandler.sendMessage(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
        // 初始化地图
        mMapView = (MapView) findViewById(R.id.map);
        mBaidumap = mMapView.getMap();
        mBtnPre = (Button) findViewById(R.id.pre);
        mBtnNext = (Button) findViewById(R.id.next);
        drive= (ImageView) findViewById(R.id.drive);
        transit= (ImageView) findViewById(R.id.transit);
        walk= (ImageView) findViewById(R.id.walk);
        mBtnPre.setVisibility(View.INVISIBLE);
        mBtnNext.setVisibility(View.INVISIBLE);
        // 设置起终点信息，对于tranist search 来说，城市名无意义
       // stNode = PlanNode.withCityNameAndPlaceName("北京", "西单");
        stNode = PlanNode.withLocation(StoreApplication.getlatLng());
        Intent intent = getIntent();
        if (intent.hasExtra("x") && intent.hasExtra("y")) {
            // 当用intent参数时，设置中心点为指定点
            Bundle b = intent.getExtras();
            LatLng l = new LatLng(b.getDouble("x"), b.getDouble("y"));
            enNode= PlanNode.withLocation(l) ;
        }
        //enNode = PlanNode.withCityNameAndPlaceName("北京", "龙泽");
        // 地图点击事件处理
        mBaidumap.setOnMapClickListener(this);
        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode).to(enNode));
        drive.setImageResource(R.drawable.dache2);
        mSearch.setOnGetRoutePlanResultListener(this);
        textView= (TextView) findViewById(R.id.xiangqing);
    }

    /**
     * 发起路线规划搜索示例
     *
     * @param v
     */
    public void searchButtonProcess(View v) {
        if(popuWindow!=null){
            popuWindow.dismiss();
        }
        // 重置浏览节点的路线数据
        route = null;
        mBtnPre.setVisibility(View.INVISIBLE);
        mBtnNext.setVisibility(View.INVISIBLE);
        mBaidumap.clear();
        // 处理搜索按钮响应

        // 实际使用中请对起点终点城市进行正确的设定
        if (v.getId() == R.id.drive) {
            // 显示Loading
            dialog.show();
            drive.setImageResource(R.drawable.dache2);
            transit.setImageResource(R.drawable.gongjiao1);
            walk.setImageResource(R.drawable.buxing1);
            mSearch.drivingSearch((new DrivingRoutePlanOption())
                    .from(stNode).to(enNode));
            flags=0;
        } else if (v.getId() == R.id.transit) {
            // 显示Loading
            dialog.show();
            drive.setImageResource(R.drawable.dache1);
            transit.setImageResource(R.drawable.gongjiao2);
            walk.setImageResource(R.drawable.buxing1);
            mSearch.transitSearch((new TransitRoutePlanOption())
                    .from(stNode).city(StoreApplication.getBDLocation().getCity()).to(enNode));
            flags=1;
        } else if (v.getId() == R.id.walk) {
            // 显示Loading
            dialog.show();
            drive.setImageResource(R.drawable.dache1);
            transit.setImageResource(R.drawable.gongjiao1);
            walk.setImageResource(R.drawable.buxing2);
            mSearch.walkingSearch((new WalkingRoutePlanOption())
                    .from(stNode).to(enNode));
            flags=2;
        }else if(v.getId()== R.id.huitui){

            finish();
        }else if(v.getId()== R.id.xiangqing){
            Log.e("000","stepDetails="+stepDetails);
            if(flags==0){
                setpopuwindow("驾车方案",needTimestr,distancestr,stepDetails);
         }else if(flags==1){
                setpopuwindow("公交方案",needTimestr,distancestr,stepDetails);
            }else if(flags==2){
                setpopuwindow("步行方案",needTimestr,distancestr,stepDetails);
            }
        }
    }

    /**
     * 节点浏览示例
     *
     * @param v
     */
    public void nodeClick(View v) {
        if (route == null || route.getAllStep() == null) {
            return;
        }
        if (nodeIndex == -1 && v.getId() == R.id.pre) {
            return;
        }
        // 设置节点索引
        if (v.getId() == R.id.next) {
            if (nodeIndex < route.getAllStep().size() - 1) {
                nodeIndex++;
            } else {
                return;
            }
        } else if (v.getId() == R.id.pre) {
            if (nodeIndex > 0) {
                nodeIndex--;
            } else {
                return;
            }
        }
        // 获取节结果信息
        LatLng nodeLocation = null;
        String nodeTitle = null;
        Object step = route.getAllStep().get(nodeIndex);
        if (step instanceof DrivingRouteLine.DrivingStep) {
            nodeLocation = ((DrivingRouteLine.DrivingStep) step).getEntrance().getLocation();
            nodeTitle = ((DrivingRouteLine.DrivingStep) step).getInstructions();
        } else if (step instanceof WalkingRouteLine.WalkingStep) {
            nodeLocation = ((WalkingRouteLine.WalkingStep) step).getEntrance().getLocation();
            nodeTitle = ((WalkingRouteLine.WalkingStep) step).getInstructions();
        } else if (step instanceof TransitRouteLine.TransitStep) {
            nodeLocation = ((TransitRouteLine.TransitStep) step).getEntrance().getLocation();
            nodeTitle = ((TransitRouteLine.TransitStep) step).getInstructions();
        } else if (step instanceof BikingRouteLine.BikingStep) {
            nodeLocation = ((BikingRouteLine.BikingStep) step).getEntrance().getLocation();
            nodeTitle = ((BikingRouteLine.BikingStep) step).getInstructions();
        }

        if (nodeLocation == null || nodeTitle == null) {
            return;
        }
        // 移动节点至中心
        mBaidumap.setMapStatus(MapStatusUpdateFactory.newLatLng(nodeLocation));
        // show popup
        popupText = new TextView(this);
        popupText.setBackgroundResource(R.drawable.popup);
        popupText.setTextColor(0xFF000000);
        popupText.setText(nodeTitle);
        mBaidumap.showInfoWindow(new InfoWindow(popupText, nodeLocation, 0));
    }

    /**
     * 切换路线图标，刷新地图使其生效
     * 注意： 起终点图标使用中心对齐.
     */
    public void changeRouteIcon(View v) {
        if (routeOverlay == null) {
            return;
        }
        if (useDefaultIcon) {
            ((Button) v).setText("自定义起终点图标");
            Toast.makeText(this,
                    "将使用系统起终点图标",
                    Toast.LENGTH_SHORT).show();

        } else {
            ((Button) v).setText("系统起终点图标");
            Toast.makeText(this,
                    "将使用自定义起终点图标",
                    Toast.LENGTH_SHORT).show();
        }
        useDefaultIcon = !useDefaultIcon;
        routeOverlay.removeFromMap();
        routeOverlay.addToMap();
    }
    //将时间,和距离转换成相应的格式
    public void setFormatter(){
        needTimestr=null;
        needTimestr=CountUtil.timeFormatter(needTime);
        distancestr=null;
        distancestr= CountUtil.distanceFormatter(distance);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
        // 关闭Loading
        dialog.dismiss();
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;
            mBtnPre.setVisibility(View.VISIBLE);
            mBtnNext.setVisibility(View.VISIBLE);
            route = result.getRouteLines().get(0);
            WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaidumap);
            mBaidumap.setOnMarkerClickListener(overlay);
            routeOverlay = overlay;
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();// 将所有Overlay 添加到地图上
            overlay.zoomToSpan();// 缩放地图，使所有Overlay都在合适的视野内 注：该方法只对Marker类型的overlay有效
            getDate(result);
        }

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {
        // 关闭Loading
        dialog.dismiss();

        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;
            mBtnPre.setVisibility(View.VISIBLE);
            mBtnNext.setVisibility(View.VISIBLE);
            route = result.getRouteLines().get(0);
            TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaidumap);
            mBaidumap.setOnMarkerClickListener(overlay);
            routeOverlay = overlay;
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();// 将所有Overlay 添加到地图上
            overlay.zoomToSpan();// 缩放地图，使所有Overlay都在合适的视野内 注：该方法只对Marker类型的overlay有效
            getDate(result);
        }
    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        // 关闭Loading
        dialog.dismiss();
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;
            mBtnPre.setVisibility(View.VISIBLE);
            mBtnNext.setVisibility(View.VISIBLE);
            route = result.getRouteLines().get(0);
            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaidumap);
            routeOverlay = overlay;
            mBaidumap.setOnMarkerClickListener(overlay);
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();// 将所有Overlay 添加到地图上
            overlay.zoomToSpan();// 缩放地图，使所有Overlay都在合适的视野内 注：该方法只对Marker类型的overlay有效
            getDate(result);
        }
    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }
    //显示弹窗
    private void setpopuwindow(String fangshi,String shijian,String juli,List<String>list){
        View view = getLayoutInflater().inflate(R.layout.popup_window1, null);
        TextView gongju= (TextView) view.findViewById(R.id.text);
        TextView haoshi= (TextView) view.findViewById(R.id.shijian);
        TextView licheng= (TextView) view.findViewById(R.id.licheng);
        ImageView imageView= (ImageView) view.findViewById(R.id.imageXX);
        gongju.setText(fangshi);
        haoshi.setText(shijian);
        licheng.setText(juli);
        poplist= (ListView) view.findViewById(R.id.listview_popu1);
        PopuAdapter popuAdapter=new PopuAdapter(this,list);
        poplist.setAdapter(popuAdapter);

        popuWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.status_text));
        popuWindow.setBackgroundDrawable(cd);
        // y)：相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        popuWindow.showAtLocation(mMapView, Gravity.CENTER, 0, 0);
        // 设置setFocusable(true)，要不然点击弹窗其他地方以及返回键，弹窗都不会退出
        // 也才能让popupWindow里面的布局控件获得点击的事件，否则就被它的父亲view给拦截了
        popuWindow.setFocusable(true);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               popuWindow.dismiss();
               if(flags==0){
                   mSearch.drivingSearch((new DrivingRoutePlanOption())
                           .from(stNode).to(enNode));
               }else if(flags==1){
                   mSearch.transitSearch((new TransitRoutePlanOption())
                           .from(stNode).city(StoreApplication.getBDLocation().getCity()).to(enNode));
               }else if(flags==2){
                   mSearch.walkingSearch((new WalkingRoutePlanOption())
                           .from(stNode).to(enNode));
               }
            }
        });

    }
    //获取驱车的距离,时间,和节点信息
    private void getDate(DrivingRouteResult result){
        if (stepDetails.size() != 0)
            stepDetails.clear();
        if (distance != 0)
            distance = 0;
        if (needTime != 0)
            needTime = 0;
        List<DrivingRouteLine> routeLines = result.getRouteLines();  //存放线路的集合
        List<DrivingRouteLine.DrivingStep> steps = routeLines.get(0).getAllStep(); //存放节点信息集合
        // 分为N步
        for (int i = 0; i < steps.size(); i++) {
            String instructions = steps.get(i).getInstructions();
            int direction = steps.get(i).getDirection();
            int distance = steps.get(i).getDistance();
            this.distance += distance; // 叠加每一个step的distance
            String entraceInstructions = steps.get(i)
                    .getEntranceInstructions();
            String title = steps.get(i).getEntrance().getTitle();
            stepDetails.add((i + 1) + "." + instructions);
        }
        needTime = distance / 550;
        setFormatter();
    }
    //获取公交的距离,时间,和节点信息
    private void getDate(TransitRouteResult result){
        if (stepDetails.size() != 0)
            stepDetails.clear();
        if (distance != 0)
            distance = 0;
        if (needTime != 0)
            needTime = 0;
        List<TransitRouteLine> routeLines = result.getRouteLines();  //存放线路的集合
        List<TransitRouteLine.TransitStep> steps = routeLines.get(0).getAllStep(); //存放节点信息集合
        // 分为N步
        for (int i = 0; i < steps.size(); i++) {
            String instructions = steps.get(i).getInstructions();
            int direction = steps.get(i).getDistance();
            int distance = steps.get(i).getDistance();
            this.distance += distance; // 叠加每一个step的distance
           /* String entraceInstructions = steps.get(i)
                    .getEntranceInstructions();*/
            String title = steps.get(i).getEntrance().getTitle();
            stepDetails.add((i + 1) + "." + instructions);
        }
        needTime = distance / 550;
        setFormatter();
    }
    //获取步行的距离,时间,和节点信息
    private void getDate(WalkingRouteResult result){
        if (stepDetails.size() != 0)
            stepDetails.clear();
        if (distance != 0)
            distance = 0;
        if (needTime != 0)
            needTime = 0;
        List<WalkingRouteLine> routeLines = result.getRouteLines();  //存放线路的集合
        List<WalkingRouteLine.WalkingStep> steps = routeLines.get(0).getAllStep(); //存放节点信息集合
        // 分为N步
        for (int i = 0; i < steps.size(); i++) {
            String instructions = steps.get(i).getInstructions();
            int direction = steps.get(i).getDistance();
            int distance = steps.get(i).getDistance();
            this.distance += distance; // 叠加每一个step的distance
            String entraceInstructions = steps.get(i)
                    .getEntranceInstructions();
            String title = steps.get(i).getEntrance().getTitle();
            stepDetails.add((i + 1) + "." + instructions);
        }
        needTime = distance / 550;
        setFormatter();
    }

    // 定制RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }
    }

    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }
    }

    private class MyTransitRouteOverlay extends TransitRouteOverlay {

        public MyTransitRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }
    }

    private class MyBikingRouteOverlay extends BikingRouteOverlay {
        public  MyBikingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }

    }

    @Override
    public void onMapClick(LatLng point) {
        mBaidumap.hideInfoWindow();
    }

    @Override
    public boolean onMapPoiClick(MapPoi poi) {
        return false;
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mSearch.destroy();
        mMapView.onDestroy();
        super.onDestroy();
    }

}
