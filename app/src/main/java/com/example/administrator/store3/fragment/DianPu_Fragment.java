package com.example.administrator.store3.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.adapter.ILikeDPAdapter;

/**
 * Created by Administrator on 2016/5/24.
 */
public class DianPu_Fragment extends Fragment {
    /*可下拉上拉列表*/
    private ListView listView;
    /*适配器*/
    private ILikeDPAdapter iLikeDPAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dianpu_fragment,null);
        listView= (ListView) view.findViewById(R.id.dianpu_list);
        iLikeDPAdapter=new ILikeDPAdapter(getContext());
        listView.setAdapter(iLikeDPAdapter);
        return view;
    }
}
