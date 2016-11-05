package com.example.administrator.store3.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.activity.MessageActivity;

/**
 * Created by Administrator on 2016/4/19.
 */
public class LogisticsMainFragment extends Fragment implements View.OnClickListener{
    private ImageView mImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.logistics_main,null);
        setViews(view);
        return view;
    }
      //通过ID查找相应的控件
      private void setViews(View view) {
        mImageView= (ImageView) view.findViewById(R.id.image1);
          mImageView.setOnClickListener(this);
      }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image1:
                Intent intent=new Intent(getActivity(), MessageActivity.class);
                startActivity(intent );
        }

    }
}
