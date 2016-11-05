package com.example.administrator.store3.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/5/12.
 *字体工具类
 */
public class TextSetUtil {
    /**
     * ΢���ź�
     * @param context
     * @param tv
     */
    public static void setYaHeiText(Context context, TextView tv){
        //�õ�AssetManager
        AssetManager mgr =context.getAssets();
        //���·���õ�Typeface
        Typeface tf= Typeface.createFromAsset(mgr, "fonts/yahei.TTF");
        //��������
        tv.setTypeface(tf);
        //tv.setTextColor(Color.BLACK);
    }

    /**
     * 宋体
     * @param context
     * @param tv
     */
    public static void setSongTiText(Context context, TextView tv){
        //获得AssetManager
        AssetManager mgr =context.getAssets();
        //Typeface
        Typeface tf= Typeface.createFromAsset(mgr, "fonts/lishu.TTF");
        //��������
        tv.setTypeface(tf);
        //tv.setTextColor(Color.BLACK);
    }

    /**
     *
     * 楷体
     * @param context
     * @param tv
     */
    public static void setKaiTiText(Context context, TextView tv){

        AssetManager mgr =context.getAssets();

        Typeface tf= Typeface.createFromAsset(mgr, "fonts/kaiti.TTF");
        tv.setTypeface(tf);
       // tv.setTextColor(Color.BLACK);
    }

    /**
     * 黑体
     * @param context
     * @param tv
     */
    public static void setHeiTiText(Context context, TextView tv){
        //�õ�AssetManager
        AssetManager mgr =context.getAssets();
        //���·���õ�Typeface
        Typeface tf= Typeface.createFromAsset(mgr, "fonts/heiti.TTF");
        //��������
        tv.setTypeface(tf);
        //tv.setTextColor(Color.BLACK);
    }

    /**
     * 幼圆
     * @param context
     * @param tv
     */
    public static void setYouYuanText(Context context, TextView tv){
        AssetManager mgr =context.getAssets();
        Typeface tf= Typeface.createFromAsset(mgr, "fonts/youyuan.TTF");
        tv.setTypeface(tf);
       // tv.setTextColor(Color.BLACK);
    }
}
