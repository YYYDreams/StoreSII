package com.example.administrator.store3.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.adapter.DianPuYouhuiAdapter;

/**
 * Created by Administrator on 2016/5/23.
 * 已使用优惠券界面
 */
public class DueTimeFragment extends Fragment {
    /*优惠券列表*/
    private ListView listView;
    /*店铺优惠适配器*/
    private DianPuYouhuiAdapter dianPuYouhuiAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*加载主界面*/
        View view=inflater.inflate(R.layout.daoqishijian_fg,null);
        /*初始化*/
        listView= (ListView) view.findViewById(R.id.listview_youhui_ysy);
        dianPuYouhuiAdapter=new DianPuYouhuiAdapter(getContext(),2);
        listView.setAdapter(dianPuYouhuiAdapter);
        return view;
    }
}
