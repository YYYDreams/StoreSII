package com.example.administrator.store3.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import com.baidu.navisdk.adapter.BNRouteGuideManager;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviBaseCallbackModel;
import com.baidu.navisdk.adapter.BaiduNaviCommonModule;
import com.baidu.navisdk.adapter.NaviModuleFactory;
import com.baidu.navisdk.adapter.NaviModuleImpl;
import com.example.administrator.store3.R;
import java.util.ArrayList;
import java.util.List;

/**
 * 诱导界面
 */

public class BaiDuNavActivity extends Activity  {
    /*获取类名*/
    private final String TAG = BaiDuNavActivity.class.getName();
    /*起点百度坐标*/
    private BNRoutePlanNode mBNRoutePlanNode = null;
    /*终点百度坐标*/
    private BNRoutePlanNode eNode=null;
    /*百度导航公共模块*/
    private BaiduNaviCommonModule mBaiduNaviCommonModule = null;
    /*
    * 对于导航模块有两种方式来实现发起导航。 1：使用通用接口来实现 2：使用传统接口来实现
    */
    // 是否使用通用接口
    private boolean useCommonInterface = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*将此页面存入数组中*/
        BaiduMapActivity.activityList.add(this);
        /*创建异步处理对象*/
        createHandler();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        }
        View view = null;
        if (useCommonInterface) {
            //使用通用接口
            mBaiduNaviCommonModule = NaviModuleFactory.getNaviModuleManager().getNaviCommonModule(
                    NaviModuleImpl.BNaviCommonModuleConstants.ROUTE_GUIDE_MODULE, this,
                    BNaviBaseCallbackModel.BNaviBaseCallbackConstants.CALLBACK_ROUTEGUIDE_TYPE, mOnNavigationListener);
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onCreate();
                view = mBaiduNaviCommonModule.getView();
            }
        } else {
            //使用传统接口
            view = BNRouteGuideManager.getInstance().onCreate(this,mOnNavigationListener);
        }

        if (view != null) {
            //加载导航界面
            setContentView(view);
        }
         //获取传过来的值
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                //获得起点和终点
                mBNRoutePlanNode = (BNRoutePlanNode) bundle.getSerializable(BaiduMapActivity.ROUTE_PLAN_NODE);
                eNode= (BNRoutePlanNode) bundle.getSerializable(BaiduMapActivity.RESET_END_NODE);
            }
        }
    }
/**
 * 界面显示准备阶段
 * */
    @Override
    protected void onResume() {
        super.onResume();
        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onResume();//百度导航公共模块开始准备
            }
        } else {
            BNRouteGuideManager.getInstance().onResume();
        }

        if (hd != null) {
            hd.sendEmptyMessageAtTime(MSG_SHOW, 2000);
        }
    }
    /**
     * 界面暂停阶段
     *
     * */

    protected void onPause() {
        super.onPause();

        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onPause(); //百度导航公共模块暂停
            }
        } else {
            BNRouteGuideManager.getInstance().onPause();
        }
    }
    /**
     * 界面销毁阶段
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onDestroy();//百度导航公共模块销毁
            }
        } else {
            BNRouteGuideManager.getInstance().onDestroy();
        }
        BaiduMapActivity.activityList.remove(this);  //activity资源移出

    }
    /**
     * 界面停止阶段
    */
    @Override
    protected void onStop() {
        super.onStop();
        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onStop();   //百度导航公共模块停止
            }
        } else {
            BNRouteGuideManager.getInstance().onStop();
        }
    }
    /**
     * 返回键设置
     */

    @Override
    public void onBackPressed() {
        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onBackPressed(false);
            }
        } else {
            BNRouteGuideManager.getInstance().onBackPressed(false);
        }
    }
    /**
     * 百度地图公共模块属性设置
      */
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onConfigurationChanged(newConfig);
            }
        } else {
            BNRouteGuideManager.getInstance().onConfigurationChanged(newConfig);
        }
    }
    private static final int MSG_SHOW = 1;
    private static final int MSG_HIDE = 2;
    private static final int MSG_RESET_NODE = 3;
    private Handler hd = null;

    private void createHandler() {
        if (hd == null) {
            hd = new Handler(getMainLooper()) {
                public void handleMessage(android.os.Message msg) {
                    if (msg.what == MSG_SHOW) {
                        addCustomizedLayerItems();
                    } else if (msg.what == MSG_HIDE) {
                        BNRouteGuideManager.getInstance().showCustomizedLayer(false);
                    } else if (msg.what == MSG_RESET_NODE) {
                        BNRouteGuideManager.getInstance().resetEndNodeInNavi(
                                eNode
                        );
                    }
                };
            };
        }
    }
    /**
     * 添加自定义栏
     * */
    private void addCustomizedLayerItems() {
        List<BNRouteGuideManager.CustomizedLayerItem> items = new ArrayList<BNRouteGuideManager.CustomizedLayerItem>();
        BNRouteGuideManager.CustomizedLayerItem item1 = null;
        if (mBNRoutePlanNode != null) {
            item1 = new BNRouteGuideManager.CustomizedLayerItem(mBNRoutePlanNode.getLongitude(), mBNRoutePlanNode.getLatitude(),
                    mBNRoutePlanNode.getCoordinateType(), getResources().getDrawable(R.drawable.ic_launcher),
                    BNRouteGuideManager.CustomizedLayerItem.ALIGN_CENTER);
            items.add(item1);

            BNRouteGuideManager.getInstance().setCustomizedLayerItems(items);
        }
        BNRouteGuideManager.getInstance().showCustomizedLayer(true);
    }
    /**
     * 地图导航监听
     */
    private BNRouteGuideManager.OnNavigationListener mOnNavigationListener = new BNRouteGuideManager.OnNavigationListener() {

        @Override
        public void onNaviGuideEnd() {
            finish();
        }

        @Override
        public void notifyOtherAction(int actionType, int arg1, int arg2, Object obj) {

            if (actionType == 0) {
                Log.i(TAG, "notifyOtherAction actionType = " + actionType + ",导航到达目的地！");
            }

            Log.i(TAG, "actionType:" + actionType + "arg1:" + arg1 + "arg2:" + arg2 + "obj:" + obj.toString());
        }
    };
}
