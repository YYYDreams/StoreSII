package com.example.administrator.store3.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import com.example.administrator.store3.R;
import com.example.administrator.store3.activity.Details;
import com.example.administrator.store3.adapter.ZuixinAdapter;
import java.util.ArrayList;
/**
 * 最新碎片
 * Created by Administrator on 2016/4/15.
 */
public class ZuixinFragment extends Fragment implements View.OnClickListener{
    private ArrayList<String> mNameList = new ArrayList<>();
    private ArrayList<Drawable> mDrawableList = new ArrayList<>();
    private Context mContext;
    private GridView mGridView;
    private ZuixinAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmene_zuixin,null);
        mContext=getActivity();
        mGridView= (GridView) view.findViewById(R.id.gridView1);

        for(int i=0;i<=10;i++){
            Drawable drawable=getResources().getDrawable(R.drawable.sp1);
            mDrawableList.add(drawable);
        }
        for(int i=0;i<=10;i++){
         String string="￥22"+""+i;
            mNameList.add(string);
        }
        //关联adapter
        mAdapter=new ZuixinAdapter(mContext,mNameList,mDrawableList);
        mGridView.setAdapter(mAdapter);
        mAdapter.setOnMenoy(this);
        return view;
    }
//设置监听事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.t_View111:
                Intent intent0=new Intent(mContext,Details.class);
                startActivity(intent0);
                break;
        }

    }
}
