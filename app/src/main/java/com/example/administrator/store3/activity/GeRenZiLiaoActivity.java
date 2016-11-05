package com.example.administrator.store3.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.util.BitmapUtil;
import com.example.administrator.store3.wenhao.popw;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 个人资料
 * Created by Administrator on 2016/5/24.
 */
public class GeRenZiLiaoActivity extends Activity implements View.OnClickListener{
    //自定义的弹出框类
    popw menuWindow;
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
    private ImageView imageView,mImageView;
    private LinearLayout mLinearLayout0;
    private TextView mTextView,mTextView0;
    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gerenziliao_main);
        initViews();
        systems();
    }

    private void initViews() {
        imageView= (ImageView) findViewById(R.id.image0);
        mImageView= (ImageView) findViewById(R.id.image);
        mTextView= (TextView) findViewById(R.id.text_set);
        mTextView0= (TextView) findViewById(R.id.textView_date);
        mLinearLayout0= (LinearLayout) findViewById(R.id.line_password);
        imageView.setOnClickListener(this);
        mImageView.setOnClickListener(this);
        mTextView.setOnClickListener(this);
        mTextView0.setOnClickListener(this);
        mLinearLayout0.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image0:
                this.finish();
                break;
            case R.id.image:
                //实例化SelectPicPopupWindow
                menuWindow=new popw(GeRenZiLiaoActivity.this,itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(GeRenZiLiaoActivity.this.findViewById(R.id.main),
                        Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);//设置layout在PopupWindow中显示的位置
                break;
            case R.id.line_password:
                Intent intent=new Intent(this,ChangeActivity.class);
                startActivity(intent);
                break;
            case R.id.text_set:
                change_sex();
                break;
            case R.id.textView_date:
                data();
                break;
        }
    }
    //获取系统时间
    public void systems(){
        final Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);
        mTextView0.setText(mYear + "-" + (mMonth+1) + "-" + mDay);
    }
    //出生日期
  public void data(){
       showDialog(DATE_DIALOG_ID);
        updateDisplay();

    }
    private void updateDisplay(){
        mTextView0.setText(new StringBuilder()
                       .append(mYear).append("-")
                       .append(mMonth+1).append("-")
                       .append(mDay).append(" "));
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth)
        {

            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };

    protected Dialog onCreateDialog(int id) {
        switch (id)
        {
            case DATE_DIALOG_ID:
               // return new DatePickerDialog(this,mDateSetListener, mYear, mMonth, mDay);
                return new DatePickerDialog(this,mDateSetListener, 1989, 12, 01);
        }
        return null;
    }
    //性别选择
    public void change_sex(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this); //定义一个AlertDialog
        String[] strarr = {"男","女","保密","其他"};
        builder.setItems(strarr, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface arg0, int arg1)
            {
                String sex = "2";
                // 自动生成的方法存根
                if (arg1 == 0) {//男
                    sex = "男";
                    //获取界面的男的值
                    mTextView.setText(sex.toString());
                }else if(arg1==1){//女
                    sex = "女";
                    mTextView.setText(sex.toString());
                }else if(arg1==2){//女
                    sex = "保密";
                    mTextView.setText(sex.toString());
                } else {
                    sex="其他";
                    mTextView.setText(sex.toString());
                }
            }
        });
        builder.show();
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
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, tempuri);
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
                    intent.setType("image/jpeg/png");//设置数据类型

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
        return dateFormat.format(date) + ".jpg"+".png";
    }
    @Override
    protected void onResume() {
        System.out.println("onResume");
//	    // 切换屏幕方向会导致activity的摧毁和重建
//        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            System.out.println("屏幕切换");
//        }
        super.onResume();
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
            // Log.i("finalfile", finalfile.getAbsolutePath());
            Drawable drawable = new BitmapDrawable(photo);

            /**
             * 下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上 传到服务器，QQ头像上传采用的方法跟这个类似
             */
		/*	  ByteArrayOutputStream stream = new ByteArrayOutputStream();
			 photo.compress(Bitmap.CompressFormat.JPEG, 60, stream);
            byte[] b = stream.toByteArray(); // 将图片流以字符串形式存储下来&nbsp;
			 tp = new String(Base64Coder.encodeLines(b));
			//  这个地方大家可以写下给服务器上传图片的实现，直接把tp直接上传就可以了， 服务器处理的方法是服务器那边的事了，吼吼&nbsp;
			 // 如果下载到的服务器的数据还是以Base64Coder的形式的话，可以用以下方式转换 为我们可以用的图片类型就OK啦...吼吼
			  Bitmap dBitmap = BitmapFactory.decodeFile(tp);
             Drawable drawable1 = new BitmapDrawable(dBitmap);*/
            mImageView.setImageDrawable(drawable);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("onActivityResult");
        switch (requestCode) {
            // 如果是直接从相册获取
            case PHOTO_PICKED_WITH_DATA:
                if (data != null && data.getData() != null) {
                    startPhotoZoom(data.getData());
                    //System.out.println("data.getData()"+data.getData());
                }
                break;
            // 如果是调用相机拍照时
            case CAMERA_WITH_DATA:
                Log.i("resultCode", resultCode+"");
                if(resultCode==0){
                    return;
                }
                Log.i("createnum", createnum+"");
                startPhotoZoom(tempuri);
                break;
            // 取得裁剪后的图片
            case CUT_PHOTO:
                /**
                 * 非空判断大家一定要验证，如果不验证的话， 在剪裁之后如果发现不满意，要重新裁剪，丢弃
                 * 当前功能时，会报NullException，只 在这个地方加下，大家可以根据不同情况在合适的 地方做判断处理类似情况 &nbsp;
                 */
                Log.i("CUT_PHOTO", resultCode + "");
//			if(resultCode==0){
//				return;
//			}
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
    protected void onSaveInstanceState(Bundle outState) {
        System.out.println("onSaveInstanceState");
        outState.putString("temppath", temppath);
        outState.putSerializable("finalfile", finalfile);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        System.out.println("onRestoreInstanceState");
        temppath=savedInstanceState.getString("temppath");
        finalfile=(File) savedInstanceState.getSerializable("finalfile");
        super.onRestoreInstanceState(savedInstanceState);
    }


}
