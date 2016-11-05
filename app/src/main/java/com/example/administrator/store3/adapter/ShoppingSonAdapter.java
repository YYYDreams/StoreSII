package com.example.administrator.store3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.store3.R;
import com.example.administrator.store3.entity.Goods;
import com.example.administrator.store3.fragment.ShoppingMainFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/1.
 * 购物车列表子列表适配器
 */
public class ShoppingSonAdapter extends BaseAdapter {
    /*商品数组*/
    private ArrayList<Goods> list;
    /*上下文对象*/
    private Context context;
    /*布局管理者*/
    private LayoutInflater mInflater;
    /*购物车列表适配器*/
    private ShoppingAdapter shoppingAdapter;
    /*构造方法*/
    public ShoppingSonAdapter(Context context ,ShoppingAdapter shoppingAdapter) {
        this.list = new ArrayList<>();
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.shoppingAdapter= shoppingAdapter;
    }
    public void addItem(Goods item){
        this.list.add(item);
    }

    public void clear(){
        this.list.clear();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            /*加载item布局*/
            convertView = mInflater.inflate(R.layout.lv_contain_item, null);
            viewHolder = new ViewHolder();
            //初始化item的控件
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBoxs);
            viewHolder.tupian = (ImageView) convertView.findViewById(R.id.image0);
            viewHolder.neirong = (TextView) convertView.findViewById(R.id.neirong);
            viewHolder.guige = (TextView) convertView.findViewById(R.id.guige);
            viewHolder.jiage = (TextView) convertView.findViewById(R.id.item_product_price);
            viewHolder.jia = (TextView) convertView.findViewById(R.id.item_btn_add);
            viewHolder.jian = (TextView) convertView.findViewById(R.id.item_btn_sub);
            viewHolder.shuliang = (TextView) convertView.findViewById(R.id.item_product_num);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //给控件赋值
        viewHolder.neirong.setText(list.get(position).getNeirong());
        viewHolder.jiage.setText("¥" + list.get(position).getJiage() + "");
        viewHolder.guige.setText("规格:" + list.get(position).getGuige());
        viewHolder.shuliang.setText(list.get(position).getJianshu() + "");
        viewHolder.checkBox.setChecked(list.get(position).isSelected());
        MyLisener myLisener = new MyLisener(position);
        //给按钮设置监听
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).isSelected()){
                    list.get(position).setSelected(false);
                    shoppingAdapter.getProducts().get(list.get(position).getId()).setSelected(false);
                    ShoppingMainFragment.checkBoxAll.setChecked(false);
                    shoppingAdapter.notifyDataSetChanged();
                }else {
                    list.get(position).setSelected(true);
                }
                notifyDataSetChanged();
            }
        });

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*点击按钮减,购物车列表总金和总数做减法*/
                int num = list.get(position).getJianshu();
                if(list.get(position).getJianshu()>0){
                    list.get(position).setJianshu(num - 1);
                    notifyDataSetChanged();
                    float jiage = list.get(position).getJiage();
                    if(finalViewHolder.checkBox.isChecked()){
                        ShoppingMainFragment.zongjin -= jiage;
                        ShoppingMainFragment.totalMoney.setText("总金额: " + ShoppingMainFragment.zongjin + "");
                        ShoppingMainFragment.totalMoney.postInvalidate();
                    }
                }
            }
        });
        viewHolder.jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*点击按钮加,购物车列表纵筋和总数做加法*/
                int num = list.get(position).getJianshu();
                list.get(position).setJianshu(num+1);
                notifyDataSetChanged();
                float jiage = list.get(position).getJiage();
                if(finalViewHolder.checkBox.isChecked()){
                    ShoppingMainFragment.zongjin+=jiage;
                    ShoppingMainFragment.totalMoney.setText("总金额: " + ShoppingMainFragment.zongjin + "");
                    ShoppingMainFragment.totalMoney.postInvalidate();
                }

            }

        });
        viewHolder.checkBox.setOnCheckedChangeListener(myLisener);
        return convertView;
    }

    class MyLisener implements CompoundButton.OnCheckedChangeListener{
        int position;

        public MyLisener(int position) {
            this.position= position;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            /*选择框事件监听,当状态为选择时代码处理以及状态为不选择时的代码处理*/
            int jianshu =list.get(position).getJianshu();
            float jiage = list.get(position).getJiage();
            if(isChecked){
                ShoppingMainFragment.zongjin += jianshu*jiage;
                ShoppingMainFragment.zongshu += 1;
                ShoppingMainFragment.totalMoney.setText("总金额: "+ShoppingMainFragment.zongjin+"");
                ShoppingMainFragment.settlement.setText("去结算(" + ShoppingMainFragment.zongshu + ")");
                ShoppingMainFragment.totalMoney.postInvalidate();
                ShoppingMainFragment.settlement.postInvalidate();
            }else {
                ShoppingMainFragment.zongjin -= jianshu*jiage;
                ShoppingMainFragment.zongshu -= 1;
                ShoppingMainFragment.totalMoney.setText("总金额: " + ShoppingMainFragment.zongjin+"");
                ShoppingMainFragment.settlement.setText("去结算(" + ShoppingMainFragment.zongshu + ")");
                ShoppingMainFragment.totalMoney.postInvalidate();
                ShoppingMainFragment.settlement.postInvalidate();
            }
        }
    }
    /*实体类*/
    class ViewHolder{
        CheckBox checkBox;
        ImageView tupian;
        TextView neirong;
        TextView guige;
        TextView jiage;
        TextView jian;
        TextView jia;
        TextView shuliang;
    }
}
