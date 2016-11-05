package com.example.administrator.store3.adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.entity.Brand;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


/**
 *商店自定义adapter
 * Created by Administrator on 2016/4/19.
 */
public class BrandAdapter extends BaseAdapter {
    /*界面上下文*/
    private Context context;
    /*brand集合*/
    private List<Brand>list;
    /*imagloader属性设置*/
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.drawable.s_sp_02) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.drawable.s_sp_02) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.s_sp_02) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                    // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
            .build(); // 创建配置过得DisplayImageOption对象
    /*适配器构造方法*/
    public BrandAdapter(Context context, List<Brand> list) {
        this.context=context;
        this.list=list;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null){
            /*加载item布局*/
            convertView=View.inflate(context, R.layout.brand_listview_item,null);
            /*初始化item控件*/
            viewHolder=new ViewHolder();
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.brand_image);
            viewHolder.imageView1= (ImageView) convertView.findViewById(R.id.brand_image1);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
    }
        /*给控件添加图片*/
        ImageLoader.getInstance().displayImage(list.get(position).getLogo(), viewHolder.imageView, options);
        ImageLoader.getInstance().displayImage(list.get(position).getLogo(), viewHolder.imageView1, options);
        /*viewHolder.imageView.setImageResource(list[position]);
        viewHolder.imageView1.setImageResource(list[position]);*/
        return convertView;
    }
    class ViewHolder {
        ImageView imageView;
        ImageView imageView1;
    }
}
