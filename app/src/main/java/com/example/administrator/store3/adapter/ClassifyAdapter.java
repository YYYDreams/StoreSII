package com.example.administrator.store3.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.entity.ClassifyTuPian;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/20.
 */
public class ClassifyAdapter extends BaseAdapter {
    //上下文对象
    private Context context;
    //数据
    private DisplayImageOptions mImgOptionRound;

    private List<ClassifyTuPian> classifyTuPians;

    private ImageLoader imageLoader = ImageLoader.getInstance();
    List<Integer>list = new LinkedList<>();

    public ClassifyAdapter(Context context, List<Integer> list){

        this.context=context;
        this.list=list;
        this.classifyTuPians=classifyTuPians;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }
    //创建View方法
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.classapaptertou,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.image0);
            viewHolder.textView= (TextView) convertView.findViewById(R.id.t_View);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();

        }
       // String url=classifyTuPians.get(position).getImage();

      //  ImageLoader.getInstance().displayImage(url, viewHolder.imageView, mImgOptionRound);

      /*  imageLoader.displayImage();
        imageLoader.loadImage();*/
       // ImageLoader.getInstance().displayImage(url, viewHolder.imageView,mImgOptionRound);
       // ImageLoader.getInstance().loadImage(url, new MyImageListener());
     //  ImageLoader.getInstance().loadImage(url,mImgOptionRound, new MyImageListener());

       //  imageLoader.loadImage(url,mImgOptionRound,new MyImageListener());
        viewHolder.imageView.setBackgroundDrawable(ContextCompat.getDrawable(context, list.get(position)));
      //  viewHolder.textView.setText(classifyTuPians.get(position).getName());

        return convertView;

    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    class MyImageListener extends SimpleImageLoadingListener {
        ViewHolder viewHolder;
        @Override
        public void onLoadingStarted(String imageUri, View view) {

              viewHolder .imageView.setImageResource(R.drawable.loading);

            super.onLoadingStarted(imageUri, view);
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            super.onLoadingFailed(imageUri, view, failReason);
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {
            super.onLoadingCancelled(imageUri, view);
        }


        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            super.onLoadingComplete(imageUri, view, loadedImage);
        }
    }
}
