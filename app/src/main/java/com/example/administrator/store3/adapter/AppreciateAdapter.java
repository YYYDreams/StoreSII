package com.example.administrator.store3.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.entity.Appreciate;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


/**
 * 活动自定义adapter
 * Created by Administrator on 2016/4/19.
 */
public class AppreciateAdapter extends BaseAdapter {
    /*主页面上下文*/
    private Context context;
    /*活动数组*/
    private List<Appreciate>picture;
    /*ImagLoder自定义设置*/
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.drawable.s_sp_02) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.drawable.s_sp_02) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.s_sp_02) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                    // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
            .build(); // 创建配置过得DisplayImageOption对象
    /*构造方法*/
    public AppreciateAdapter(Context context, List<Appreciate>picture) {
        this.context=context;
        this.picture=picture;
    }

    @Override
    public int getCount() {
        return picture.size();
    }

    @Override
    public Object getItem(int position) {
        return picture.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.appreciate_listview_itme,null);
            viewHolder=new ViewHolder();
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.image_app);
            viewHolder.textView= (TextView) convertView.findViewById(R.id.mingzi);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
           
        }
        //viewHolder.imageView.setBackgroundResource(picture.get(position));
        /*为控件加载数据*/
        ImageLoader.getInstance().displayImage(picture.get(position).getPageCountStart(),viewHolder.imageView,options);
        viewHolder.textView.setText(picture.get(position).getTitle());
        return convertView;
    }
    /*控件实体类*/
    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
