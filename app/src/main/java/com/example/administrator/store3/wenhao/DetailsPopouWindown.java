package com.example.administrator.store3.wenhao;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.store3.R;
import com.example.administrator.store3.activity.ConfirmOrderActivity;
import com.example.administrator.store3.activity.Details;
import com.example.administrator.store3.customview.TagCloudView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/5/11.
 */
public class DetailsPopouWindown implements View.OnClickListener,PopupWindow.OnDismissListener {
    private Context context;
    private PopupWindow popupWindow;
    private final int AddReduce = 1;
    private Button imageButton1;
    private  Button imageButton2;
    private TextView pupTextView,textView;
    private Details details;
    private ImageView imageView;
    private List<String> tag1;
    private List<String> tag2;
    private String []yss={"休闲灰","沉稳藏青","经典黑","高雅白"};
    private String []ccs={"2XL(180/105)","3XL(185/110)","L(170/95)","M(165/90)","XL(175/100)"};
    private TagCloudView tagCloudView8;
    private TagCloudView tagCloudView9;
    public DetailsPopouWindown(Context context,Details details){
        this.context=context;
        this.details=details;

        // 自定义的一个控件作为显示内容
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.goumai_popouwindow, null);

        tag1 = new ArrayList<>();
        tag2 = new ArrayList<>();
        for (int i = 0; i < yss.length; i++) {
            tag1.add(yss[i]);
        }
        for (int i = 0; i < ccs.length; i++) {
            tag2.add(ccs[i]);
        }
        //查找控件
        imageButton1= (Button) contentView.findViewById(R.id.item_btn_sub);
        imageButton2= (Button) contentView.findViewById(R.id.item_btn_add);
        pupTextView= (TextView) contentView.findViewById(R.id.item_product_num);
        textView= (TextView) contentView.findViewById(R.id.t_View2);
        imageView= (ImageView) contentView.findViewById(R.id.image);
        tagCloudView8 = (TagCloudView) contentView.findViewById(R.id.tag_cloud_view_8);
        tagCloudView8.setTags(tag1);
        tagCloudView8.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onTagClick(int position) {
                for(int i=0;i<tag1.size();i++){
                    if(i==position){
                        tagCloudView8.getChildAt(i).setSelected(true);
                    }else {
                        tagCloudView8.getChildAt(i).setSelected(false);
                    }
                }
            }
        });
        tagCloudView9 = (TagCloudView) contentView.findViewById(R.id.tag_cloud_view_9);
        tagCloudView9.setTags(tag2);
        tagCloudView9.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onTagClick(int position) {
                for(int i=0;i<tag2.size();i++){
                    if(i==position){
                        tagCloudView9.getChildAt(i).setSelected(true);
                    }else {
                        tagCloudView9.getChildAt(i).setSelected(false);
                    }
                }

            }
        });
        // 各组件绑定事件
        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        pupTextView.setOnClickListener(this);
        imageView.setOnClickListener(this);
        textView.setOnClickListener(this);


        // 将加载的视图view载入PopubWindow,并且设置popupwindow这个组件的动画效果
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 当popWindow消失时的监听
        popupWindow.setOnDismissListener(this);

    }
    public void showAsDropDown(View v) {
        // showAsDropDown(View anchor)：相对某个控件的位置（正左下方），无偏移
        // showAtLocation(View parent, int gravity, int x, int
        // y)：相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        // 设置setFocusable(true)，要不然点击弹窗其他地方以及返回键，弹窗都不会退出
        // 也才能让popupWindow里面的布局控件获得点击的事件，否则就被它的父亲view给拦截了
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);//设置窗口显示的动画效果
        //更新
        popupWindow.update();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_btn_sub:
                if(!pupTextView.getText().toString().equals("1")){
                    String num_ReduceString=Integer.valueOf(pupTextView.getText().toString())-AddReduce+"";
                    pupTextView.setText(num_ReduceString);

                }else {
                    Toast.makeText(context,"您买入的数量不能低于1",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.item_btn_add:
                if(!pupTextView.getText().toString().equals("1000")){
                    String numString=Integer.valueOf(pupTextView.getText().toString())+AddReduce+"";
                    pupTextView.setText(numString);

                }
                break;
            case R.id.image:
                popupWindow.dismiss();
                break;
            case  R.id.t_View2:
                popupWindow.dismiss();
                Intent intent = new Intent();
                intent.setClass(context, ConfirmOrderActivity.class);
                context.startActivity(intent);
                break;

        }

    }

    // 变回原来的颜色
    @Override
    public void onDismiss() {
        WindowManager.LayoutParams lp=details.getWindow().getAttributes();
        lp.alpha = 1f;
        details.getWindow().setAttributes(lp);
    }
}
