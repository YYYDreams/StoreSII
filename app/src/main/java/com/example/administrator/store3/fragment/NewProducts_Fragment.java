package com.example.administrator.store3.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.store3.R;

/**
 * 最新产品
 * Created by Administrator on 2016/4/29.
 */
public class NewProducts_Fragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.brand_store_fg3,null);
        return v;
    }
}
