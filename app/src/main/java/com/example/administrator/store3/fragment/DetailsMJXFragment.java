package com.example.administrator.store3.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.store3.R;

/**
 * Created by Administrator on 2016/5/7.
 */
public class DetailsMJXFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("Tag", " DetailsMJXFragment");
        View view=inflater.inflate(R.layout.detailsmaijiaxiu,null);
        return view;

    }
}
