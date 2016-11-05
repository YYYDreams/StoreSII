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
 * 未使用优惠券界面
 */
public class OtherPresenterFragment extends Fragment{
    private ListView listView;
    private DianPuYouhuiAdapter dianPuYouhuiAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bierenzengsong_fg,null);
        listView= (ListView) view.findViewById(R.id.listview_youhui_wsy);
        dianPuYouhuiAdapter=new DianPuYouhuiAdapter(getContext(),1);
        listView.setAdapter(dianPuYouhuiAdapter);
        return view;
    }
}
