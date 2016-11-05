package com.example.administrator.store3.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Picture;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.activity.PictureActivity;
import com.example.administrator.store3.entity.Shopping;
import com.example.administrator.store3.fragment.ShoppingMainFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 * 购物车功能
 *适配器
 */
public class ShoppingAdapter extends BaseAdapter {

    /*集合 ，存放ListView的商品实体类数据*/
    private List<Shopping> products;
    /*上下文*/
    private Context context;
    /*点击监听对象*/
    private View.OnClickListener onCheck;
    /*购物车列表子列表适配器*/
    private ShoppingSonAdapter shoppingSonAdapter;
    /*设置接口用于fragment回调*/
    public void setOnCheck(View.OnClickListener onCheck) {
        this.onCheck = onCheck;
    }
    /*构造方法*/
    public ShoppingAdapter(List<Shopping> products, Context context){
        this.products=products;
        this.context=context;
    }
    /*此方法获取商品数组对象*/
    public List<Shopping> getProducts() {
        return products;
    }
    @Override
    public int getCount() {
        return products.size();
    }
    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder= null;
        if(convertView==null){
            /*加载item布局*/
            convertView=View.inflate(context,R.layout.shoppint_item,null);
            viewHolder=new ViewHolder();
            //初始化控件
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.check);
            viewHolder.dianming = (TextView) convertView.findViewById(R.id.mingzi);
            viewHolder.youjiantou = (ImageView) convertView.findViewById(R.id.youjiantou);
            viewHolder.lv_contain = (ListView) convertView.findViewById(R.id.lv_contain);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //给控件设置数据
        viewHolder.dianming.setText(products.get(position).getDianming());
        viewHolder.checkBox.setChecked(products.get(position).isSelected());
        //给checkbook设置标签,用于判断点击是哪一个item
        viewHolder.checkBox.setTag(position);
        //创建嵌套的listview
        ShoppingSonAdapter shoppingSonAdapter = new ShoppingSonAdapter(context,this);
        for (int i=0;i<products.get(position).getGoodses().size();i++){
            //动态添加item的数量
            shoppingSonAdapter.addItem(products.get(position).getGoodses().get(i));
        }
        //给listview设置adapter
        viewHolder.lv_contain.setAdapter(shoppingSonAdapter);
        MyLisener myLisener = new MyLisener(position,shoppingSonAdapter);
        viewHolder.checkBox.setOnClickListener(myLisener);
        viewHolder.dianming.setOnClickListener(myLisener);
        //计算listview的高度用于显示展开
        setListViewHeightBasedOnChildren(viewHolder.lv_contain);
        return  convertView;
    }

    //此方法用于计算listview的高度
    private void setListViewHeightBasedOnChildren(ListView listView) {
        android.widget.ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
   //此类用于监听大的item的checkbox状态
    class MyLisener implements View.OnClickListener{
        int position;
        ShoppingSonAdapter shoppingSonAdapter;
        public MyLisener(int posit,ShoppingSonAdapter shoppingSonAdapter) {
            this.position=posit;
            this.shoppingSonAdapter=shoppingSonAdapter;
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.check:
                    if(products.get(position).isSelected()){ //点击判断当前数据是否为选中状态
                        products.get(position).setSelected(false); //如果为选中状态设置为不选中状态
                        for(int i=0;i<products.get(position).getGoodses().size();i++){
                            products.get(position).getGoodses().get(i).setSelected(false); //将子listview的所有数据都设置为不选中状态
                        }
                        /*如果全选为选中状态,则将其变为不选中状态*/
                        if(ShoppingMainFragment.checkBoxAll.isChecked()){
                            ShoppingMainFragment.checkBoxAll.setChecked(false);
                        }
                    }else {//当前数据如果为不选中状态
                        products.get(position).setSelected(true);//则将数据设置为选中状态
                        for(int i=0;i<products.get(position).getGoodses().size();i++){
                            products.get(position).getGoodses().get(i).setSelected(true);//将子listview的所有数据设置为选中状态
                        }
                    }
                    this.shoppingSonAdapter.notifyDataSetChanged();//更新adapter
                    break;
                case R.id.mingzi:
                    Intent intent = new Intent();
                    intent.setClass(context, PictureActivity.class);
                    context.startActivity(intent);
                    break;
            }

        }
    }
    //一个控件实体类
    class  ViewHolder{
        private CheckBox checkBox;
        private TextView dianming;
        private ImageView youjiantou;
        private ListView lv_contain;
    }
}
