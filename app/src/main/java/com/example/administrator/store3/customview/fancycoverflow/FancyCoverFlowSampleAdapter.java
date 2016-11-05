/*
 * Copyright 2013 David Schreiber
 *           2013 John Paul Nalog
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.example.administrator.store3.customview.fancycoverflow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.entity.AppreciateJade;
import com.example.administrator.store3.util.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FancyCoverFlowSampleAdapter extends FancyCoverFlowAdapter {

    // =============================================================================
    // Private members
    // =============================================================================

    //private int[] images = {R.drawable.sp_01, R.drawable.cart_sp_01, R.drawable.s_sp_01, R.drawable.sp_01, R.drawable.cart_sp_01};
    // =============================================================================
    // Supertype overrides
    Context context;
    // =============================================================================
    LinkedList<AppreciateJade> lists;
    public FancyCoverFlowSampleAdapter(Context context,LinkedList<AppreciateJade>lists) {
        this.context=context;
        this.lists=lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //获得Item的布局
    @Override
    public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {

        CustomViewGroup customViewGroup = null;
        if (reuseableView != null) {
            customViewGroup = (CustomViewGroup) reuseableView;
            //customViewGroup.getImageView().setBackgroundDrawable(ContextCompat.getDrawable(viewGroup.getContext(),this.getItem(i)));

        } else {
            customViewGroup = new CustomViewGroup(viewGroup.getContext());
            customViewGroup.setLayoutParams(new FancyCoverFlow.LayoutParams(400, 300));
        }
        /*customViewGroup.getImageView().setBackgroundDrawable(ContextCompat.getDrawable(viewGroup.getContext(),this.getItem(i)));
        customViewGroup.getTextView().setText("我走过群山之巅,也踏足过河流低谷,二者都让我受益良多");*/
        customViewGroup.getTextView().setText(lists.get(i).getAppraisal());
        ImageLoader.getInstance().displayImage(lists.get(i).getPicture(),customViewGroup.getImageView());
        return customViewGroup;
    }
    //自定义帧布局
    class CustomViewGroup extends FrameLayout{
        ImageView imageView;
        TextView textView;

        public CustomViewGroup(Context context) {
            super(context);
            this.imageView = new ImageView(context);
            this.textView=new TextView(context);

            FrameLayout.LayoutParams tparams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);//定义显示组件参数
            FrameLayout.LayoutParams tparam=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);//定义显示组件参数
            tparam.setMargins(50,0,45,23);

            this.addView(this.imageView,tparams);
            this.addView(this.textView,tparam);
        }
        private ImageView getImageView() {
            return imageView;
        }
        private TextView getTextView(){
            return textView;}
    }

    }

