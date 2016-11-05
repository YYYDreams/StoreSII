package com.example.administrator.store3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.store3.R;
import com.example.administrator.store3.util.ImageUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class ClassifyAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<Integer> list = new LinkedList<>();
    private Context mContext;
    private LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener;

    public static final int ITEM_TYPE_CONTENT = 1;
    private int mHeaderCount=1;//头部View个数


    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setmOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener=onItemClickListener;
    }
    public ClassifyAdapters(Context context,List<Integer> list){
        this.mContext=context;
        this.list=list;
        inflater=LayoutInflater.from(mContext);
    }

    //内容长度
    public int getContentItemCount(){
        return  list.size();
    }
    //判断当前是否是HeadView
    public  boolean isHeaderView(int position){
        return mHeaderCount!=0&&position<mHeaderCount;
    }
    //判断当前item类型
    @Override
    public int getItemViewType(int position) {

            return ITEM_TYPE_CONTENT;

    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType==ITEM_TYPE_CONTENT){
           return new MyViewHolder(inflater.inflate(R.layout.classapaptertou,parent,false));
        }
        return null;
    }
    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
//  BitmapWorkerTask.loadBitmap(list.get(position),viewHolder.imageView,context);
        if (holder instanceof MyViewHolder){
            ((MyViewHolder) holder).imageView.setImageBitmap(ImageUtil.readBitMap(mContext,list.get(position)));

        }

        if (mOnItemClickListener!=null){
            //设置点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=holder.getLayoutPosition();
                    mOnItemClickListener.onClick(position);
                }
            });
            //设置长按点击事件
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos =holder.getLayoutPosition();
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return getContentItemCount();
    }
   //内容ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public MyViewHolder(View view) {
            super(view);
            imageView= (ImageView) view.findViewById(R.id.image);
            textView= (TextView) view.findViewById(R.id.text);
        }
    }

    public void addData(int position){
        list.add(position);
        notifyItemInserted(position);
        notifyItemRangeChanged(position,list.size());
    }
    public void removeData(int position){
        list.remove(position);
        notifyDataSetChanged();
    }
}
