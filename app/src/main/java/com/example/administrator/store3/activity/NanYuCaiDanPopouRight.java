package com.example.administrator.store3.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.administrator.store3.R;
import com.example.administrator.store3.adapter.NanYuCaiDanPopouRight_Adapter;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/5/31.
 * 南玉页面的菜单栏的右侧弹框
 */
public class NanYuCaiDanPopouRight {
    private Context context;
    private ArrayList<String> itemList;
    private ListView listView;
    private NanYuCaiDanPopouRight_Adapter nanYuCaiDanPopouRight_adapter;
    private PopupWindow popupWindow ;

    public NanYuCaiDanPopouRight(Context context) {
        this.context = context;
        itemList = new ArrayList<>(10);
        View view = LayoutInflater.from(context).inflate(R.layout.nanyucaidantankuang_main, null);

        //设置listview
        listView = (ListView) view.findViewById(R.id.lView1);
     /*   listView.setAdapter(new NanYuCaiDanPopouRight_Adapter(context));
        listView.setFocusableInTouchMode(true);
        listView.setFocusable(true);*/
        listView.setAdapter(nanYuCaiDanPopouRight_adapter);
    }
}
