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
import com.example.administrator.store3.entity.Conpon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/23.
 * 已过期优惠券界面
 *
 */
public class ShopPrivilegeFragment extends Fragment {
    ListView listView;
    DianPuYouhuiAdapter dianPuYouhuiAdapter;
    List<Conpon> list=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dianpuyouhui_fg,null);
        listView= (ListView) view.findViewById(R.id.listview_youhui);
        dianPuYouhuiAdapter=new DianPuYouhuiAdapter(getContext(),0);
        listView.setAdapter(dianPuYouhuiAdapter);
        return view;
    }
}
