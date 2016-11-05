package com.example.administrator.store3.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.administrator.store3.R;
import com.example.administrator.store3.adapter.SFPageAdapter;
import java.util.ArrayList;

/**
 * 商店首页子页面
 * Created by Administrator on 2016/4/29.
 *
 */
public class StoreFristPage_Fragment extends Fragment {
    ListView listView;
    ArrayList<Drawable>list;
    SFPageAdapter sfPageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.brand_store_fg1,null);
        initData();
        listView= (ListView) v.findViewById(R.id.listview_SFP);
        sfPageAdapter=new SFPageAdapter(getContext(),list);
        listView.setAdapter(sfPageAdapter);
        return v;
    }

    private void initData() {
        list=new ArrayList<>();
        for(int i=0;i<10;i++){
            Drawable drawable= ContextCompat.getDrawable(getContext(),R.drawable.brands_bg);
            list.add(drawable);
        }
    }
}
