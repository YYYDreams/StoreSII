package com.example.administrator.store3;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.store3.fragment.ClassifyMainFragment;
import com.example.administrator.store3.fragment.LogisticsMainFragment;
import com.example.administrator.store3.fragment.MainFragment;
import com.example.administrator.store3.fragment.MyMainFragment;
import com.example.administrator.store3.fragment.ShoppingMainFragment;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 *app主界面
 *
 ***/

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    /*主页子界面*/
    private MainFragment mainFragment;
    /*分类子界面*/
    private ClassifyMainFragment classifyMainFragment;
    /*购物车子界面*/
    private ShoppingMainFragment shoppingMainFragment;
    /*我的子界面*/
    private MyMainFragment myMainFragment;
    /*物流子界面*/
    private LogisticsMainFragment logisticsMainFragment;
    /*首页按钮*/
    private ImageView shouye;
    /*分类按钮*/
    private ImageView fenlei;
    /*购物车按钮*/
    private ImageView gouwuche;
    /*我的按钮*/
    private ImageView wode;
    /*物流按钮*/
    private ImageView wuliu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*加载主界面*/
        setContentView(R.layout.activity_main1);
        /*初始化控件*/
        initView();
        /*初始化imageLoader*/
        initImageLoader();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    /*初始化控件以及绑定事件监听*/
    private void initView() {
        shouye = (ImageView) findViewById(R.id.shouye);
        fenlei = (ImageView) findViewById(R.id.fenlei);
        gouwuche = (ImageView) findViewById(R.id.gouwuche);
        wode = (ImageView) findViewById(R.id.wode);
        wuliu = (ImageView) findViewById(R.id.wuliu);
        mainFragment=new MainFragment();
        classifyMainFragment=new ClassifyMainFragment();
        shoppingMainFragment=new ShoppingMainFragment();
        myMainFragment=new MyMainFragment();
        logisticsMainFragment=new LogisticsMainFragment();
        //显示主Fragment页面
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mainFragment).commit();



    }

    private void initImageLoader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.s_sp_02) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.s_sp_02) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.s_sp_02) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                        // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(50 * 1024 * 1024)//缓存到文件的最大数据
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCacheExtraOptions(720, 1280) // maxwidth, max
                        // height，即保存的每个缓存文件的最大长宽
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }



    @Override
    public void onClick(View v) {//点击跳转
        switch (v.getId()){
            case R.id.shouye:
                /*点击首页按钮,跳转首页*/
                shouye.setImageResource(R.drawable.shouye2);
                fenlei.setImageResource(R.drawable.fenlei);
                wode.setImageResource(R.drawable.wode);
                wuliu.setImageResource(R.drawable.wuliu);
                gouwuche.setImageResource(R.drawable.gouwuche);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mainFragment).commit();//切换'首页'页面
                break;
            case R.id.fenlei:
                /*点击分类按钮,跳转分类页面*/
                shouye.setImageResource(R.drawable.shouye);
                fenlei.setImageResource(R.drawable.fenlei2);
                wode.setImageResource(R.drawable.wode);
                wuliu.setImageResource(R.drawable.wuliu);
                gouwuche.setImageResource(R.drawable.gouwuche);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_content, classifyMainFragment).commit();//切换'分类'页面
                break;
            case R.id.gouwuche:
                /*点击购物车按钮,跳转购物车*/
                shouye.setImageResource(R.drawable.shouye);
                fenlei.setImageResource(R.drawable.fenlei);
                wode.setImageResource(R.drawable.wode);
                wuliu.setImageResource(R.drawable.wuliu);
                gouwuche.setImageResource(R.drawable.gouwuche1);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_content, shoppingMainFragment).commit();//切换'购物车'页面
                break;
            case R.id.wode:
                /*点击我的按钮,跳转我的*/
                shouye.setImageResource(R.drawable.shouye);
                fenlei.setImageResource(R.drawable.fenlei);
                wode.setImageResource(R.drawable.wode2);
                wuliu.setImageResource(R.drawable.wuliu);
                gouwuche.setImageResource(R.drawable.gouwuche);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_content, myMainFragment).commit();//切换'我的'页面
                break;
            case R.id.wuliu:
                /*点击物流按钮,跳转物流*/
                shouye.setImageResource(R.drawable.shouye);
                fenlei.setImageResource(R.drawable.fenlei);
                wode.setImageResource(R.drawable.wode);
                wuliu.setImageResource(R.drawable.wuliu2);
                gouwuche.setImageResource(R.drawable.gouwuche);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_content, logisticsMainFragment).commit();//切换到'物流'页面
                break;
        }
    }
}
