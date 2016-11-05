package com.example.administrator.store3.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.activity.AfterServiceActivity;
import com.example.administrator.store3.activity.BackWayActivity;
import com.example.administrator.store3.util.BitmapUtil;
import com.example.administrator.store3.widget.popw;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/11.
 * 退货页面
 */
public class TuiHuoFragment extends Fragment implements View.OnClickListener{
    //自定义的弹出框类
    private popw menuWindow;
    private  String temppath;
    private Uri tempuri;
    private File finalfile;
    private File tempFile;
    /* 用来标识请求照相功能 */
    private static final int CAMERA_WITH_DATA = 50;
    /* 用来标识请求gallery */
    private static final int PHOTO_PICKED_WITH_DATA = 60;
    /* 用来标识裁剪的返回 */
    private static final int CUT_PHOTO = 70;
    private int createnum=0;
    private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/dotOrderImage");
    private TextView next;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private Button sub;
    private Button add;
    private Button photo;
    private TextView jianshu;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private int num =1;
    int str =1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*加载主布局*/
        View view = inflater.inflate(R.layout.tuihuo_fglayout,null);
        /*初始化控件*/
        initView(view);
        if(savedInstanceState!=null){
            temppath=savedInstanceState.getString("temppath");
            finalfile=(File) savedInstanceState.getSerializable("finalfile");
        }
        return view;
    }
    /*初始化控件以及添加事件监听*/
    private void initView(View view) {
        next = (TextView) view.findViewById(R.id.xiayibu);
        next.setOnClickListener(this);
        textView1 = (TextView) view.findViewById(R.id.yuanzhifu);
        textView2 = (TextView) view.findViewById(R.id.tuikuanzhizhanghu);
        textView3 = (TextView) view.findViewById(R.id.tuikuanzhiyinhangka);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        sub = (Button) view.findViewById(R.id.btn_sub);
        add = (Button) view.findViewById(R.id.btn_add);
        sub.setOnClickListener(this);
        add.setOnClickListener(this);
        jianshu = (TextView) view.findViewById(R.id.product_num);
        photo = (Button) view.findViewById(R.id.shangchuanzhaopian);
        photo.setOnClickListener(this);
        imageView1 = (ImageView) view.findViewById(R.id.image1);
        imageView2 = (ImageView) view.findViewById(R.id.image2);
        imageView3 = (ImageView) view.findViewById(R.id.image3);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.xiayibu:
                /*点击下一步跳转页面*/
                Intent intent = new Intent();
                intent.setClass(getContext(), BackWayActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.yuanzhifu:
                /*点击原支付返回*/
                textView1.setBackground(getResources().getDrawable(R.drawable.biankuang_1));
                textView2.setBackground(getResources().getDrawable(R.drawable.biankuang));
                textView3.setBackground(getResources().getDrawable(R.drawable.biankuang));
                break;
            case R.id.tuikuanzhizhanghu:
                /*点击退款至账户*/
                textView1.setBackground(getResources().getDrawable(R.drawable.biankuang));
                textView2.setBackground(getResources().getDrawable(R.drawable.biankuang_1));
                textView3.setBackground(getResources().getDrawable(R.drawable.biankuang));
                break;
            case R.id.tuikuanzhiyinhangka:
                /*点击退款至银行卡*/
                textView1.setBackground(getResources().getDrawable(R.drawable.biankuang));
                textView2.setBackground(getResources().getDrawable(R.drawable.biankuang));
                textView3.setBackground(getResources().getDrawable(R.drawable.biankuang_1));
                break;
            case R.id.btn_sub:
                /*点击减,数据减一*/
                str--;
                jianshu.setText(str+"");
                jianshu.postInvalidate();
                break;
            case R.id.btn_add:
                /*点击加,数据加一*/
                str++;
                jianshu.setText(str+"");
                jianshu.postInvalidate();
                break;
            case R.id.shangchuanzhaopian:
                //实例化SelectPicPopupWindow
                menuWindow=new popw(getActivity(),itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(getActivity().findViewById(R.id.ll_after),
                        Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);//设置layout在PopupWindow中显示的位置
                break;
        }
    }
    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo:
                { 	Intent intent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                    // 下面这句指定调用相机拍照后的照片存储的路径
                    if (!PHOTO_DIR.exists()) {
                        boolean iscreat = PHOTO_DIR.mkdirs();// 创建照片的存储目录
                    }
                    tempFile = new File(PHOTO_DIR,
                            getPhotoFileName());
//    						if (!tempFile.exists()) {
//    							try {
//    								tempFile.createNewFile();
//    							} catch (IOException e) {
//
//    								e.printStackTrace();
//    							}
//    						}
                    temppath=tempFile.getAbsolutePath();
                    tempuri=Uri.fromFile(tempFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            tempuri);

                    startActivityForResult(intent, CAMERA_WITH_DATA);
                }
                break;
                case R.id.btn_pick_photo:
                {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    /**
                     * 下面这句话，与其它方式写是一样的效果，如果：
                     * intent.setData(MediaStore.Images
                     * .Media.EXTERNAL_CONTENT_URI);
                     * intent.setType(""image/*");设置数据类型
                     * 如果朋友们要限制上传到服务器的图片类型时可以直接写如
                     * ："image/jpeg 、 image/png等的类型"
                     */
                    intent.setDataAndType(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            "image/*");

                    startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
                }
                break;
                default:
                    break;
            }
        }
    };
    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }
    /**
     * 保存裁剪之后的图片数据 &nbsp;
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            finalfile=new File(PHOTO_DIR,
                    getPhotoFileName());
            BitmapUtil.saveImg(photo, finalfile);
            Drawable drawable = new BitmapDrawable(photo);

            /**
             * 下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上 传到服务器，QQ头像上传采用的方法跟这个类似
             */

			/*
			 * ByteArrayOutputStream stream = new ByteArrayOutputStream();
			 * photo.compress(Bitmap.CompressFormat.JPEG, 60, stream); byte[] b
			 * = stream.toByteArray(); // 将图片流以字符串形式存储下来&nbsp; tp = new
			 * String(Base64Coder.encodeLines(b));
			 * 这个地方大家可以写下给服务器上传图片的实现，直接把tp直接上传就可以了， 服务器处理的方法是服务器那边的事了，吼吼&nbsp;
			 * 如果下载到的服务器的数据还是以Base64Coder的形式的话，可以用以下方式转换 为我们可以用的图片类型就OK啦...吼吼
			 * Bitmap dBitmap = BitmapFactory.decodeFile(tp); Drawable drawable
			 * = new BitmapDrawable(dBitmap);
			 */
            if(num<4){
                if(num==1){
                    imageView1.setVisibility(View.VISIBLE);
                    imageView1.setImageDrawable(drawable);
                }else if(num==2){
                    imageView2.setVisibility(View.VISIBLE);
                    imageView2.setImageDrawable(drawable);
                }else if(num==3){
                    imageView3.setVisibility(View.VISIBLE);
                    imageView3.setImageDrawable(drawable);
                    this.photo.setVisibility(View.GONE);
                }
                num+=1;
            }
        }
    }
    /**
     * 裁剪图片方法实现 &nbsp;
     *
     * @param uri
     */

    public void startPhotoZoom(Uri uri) {
		/*
		 * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * 直接在里面Ctrl+F搜：CROP ，之前没仔细看过，其实安卓系统早已经有自带图片裁剪功能, 是直接调本地库的
		 */
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CUT_PHOTO);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // 如果是直接从相册获取
            case PHOTO_PICKED_WITH_DATA:
                if (data != null && data.getData() != null) {
                    startPhotoZoom(data.getData());
                }
                break;
            // 如果是调用相机拍照时
            case CAMERA_WITH_DATA:
                if(resultCode==0){
                    return;
                }
                startPhotoZoom(tempuri);
                break;
            // 取得裁剪后的图片
            case CUT_PHOTO:
                /**
                 * 非空判断大家一定要验证，如果不验证的话， 在剪裁之后如果发现不满意，要重新裁剪，丢弃
                 * 当前功能时，会报NullException，只 在这个地方加下，大家可以根据不同情况在合适的 地方做判断处理类似情况 &nbsp;
                 */
                if (data != null) {
                    setPicToView(data);
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        System.out.println("onSaveInstanceState");
        outState.putString("temppath", temppath);
        outState.putSerializable("finalfile", finalfile);
        super.onSaveInstanceState(outState);
    }


}
