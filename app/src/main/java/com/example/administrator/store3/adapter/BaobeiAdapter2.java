package com.example.administrator.store3.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.fragment.BaoBeiFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/5/25.
 * 失效宝贝适配器
 */
public class BaobeiAdapter2 extends BaseAdapter{
    /*页面上下文*/
    private Context context;
    /*图片集合*/
    private int[]image;
    /*宝贝选择框数组*/
    private List<Boolean>list;
    /*checkbox的显示与隐藏*/
    private boolean display;
    /*适配器构造方法,用来传值*/
    public BaobeiAdapter2(Context context,int[]image,List<Boolean>list) {
        this.context = context;
        this.image=image;
        this.list=list;
    }
    /*此方法能通过外界设置适配器选择框的显示与隐藏*/
    public void setcheckDisplay(boolean display){
        this.display=display;
    }
    /*此方法能让外界得到适配器的数据集合*/
    public List<Boolean> getList() {
        return list;
    }

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public Object getItem(int position) {
        return image[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            /*item布局初始化*/
            convertView=View.inflate(context, R.layout.shixiaobaobei_item,null);
            /*控件初始化*/
            viewHolder=new ViewHolder();
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.image_dianfanbao);
            viewHolder.pingpai= (TextView) convertView.findViewById(R.id.dianming);
            viewHolder.shangping= (TextView) convertView.findViewById(R.id.shangpingming);
            viewHolder.jiage= (TextView) convertView.findViewById(R.id.jiage);
            viewHolder.zhaoxiangsi= (Button) convertView.findViewById(R.id.zhaoxiangsi);
            viewHolder.checkBox= (CheckBox) convertView.findViewById(R.id.checkBox2);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if(display){
            /*状态值为true那么选择框为显示状态*/
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            viewHolder.checkBox.setChecked(list.get(position));
            viewHolder.checkBox.setTag(position);
            /*给选择框添加监听*/
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        BaoBeiFragment.num++;
                        list.set(position, true);
                    } else {
                        list.set(position, false);
                        if (BaoBeiFragment.num > 0) {
                            BaoBeiFragment.num--;
                        } else {
                            BaoBeiFragment.num = 0;
                        }
                    }
                    BaoBeiFragment.button.setText("删除" + "(" + String.valueOf(BaoBeiFragment.num) + ")");
                }
            });
        }else {
            BaoBeiFragment.num=0;
            viewHolder.checkBox.setVisibility(View.GONE);
        }
        return convertView;
    }
    /*控件实体类*/
    class ViewHolder{
        ImageView imageView;
        TextView pingpai;
        TextView shangping;
        TextView jiage;
        Button zhaoxiangsi;
        CheckBox checkBox;
    }
}
