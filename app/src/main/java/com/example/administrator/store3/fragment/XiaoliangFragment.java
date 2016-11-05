package com.example.administrator.store3.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.store3.R;

/**
 * Created by Administrator on 2016/4/15.
 */
/*
 *销量碎片
  *  */
public class XiaoliangFragment extends Fragment {
    private ListView listView;
    private  View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragmene_xiaoliang,null);
        return  view;


    }
}
