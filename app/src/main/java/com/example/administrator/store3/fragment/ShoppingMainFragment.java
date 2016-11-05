package com.example.administrator.store3.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import com.example.administrator.store3.R;
import com.example.administrator.store3.adapter.ShoppingAdapter;
import com.example.administrator.store3.entity.Goods;
import com.example.administrator.store3.entity.Shopping;
import java.util.ArrayList;
import java.util.List;

/**
 * 商城类
 * Created by Administrator on 2016/4/19.
 */

public class ShoppingMainFragment extends Fragment  {
    /*数据*/
    private List<Shopping> datas; //数据源
    /*商城列表适配器*/
    private ShoppingAdapter adapter; //自定义适配器
    /*选择框*/
    public static CheckBox checkBoxAll;
    /*商城列表*/
    private ListView listView;   //ListView控件
    /*总金额显示控件*/
    public static TextView totalMoney;
    private TextView freight;
    /*总数量显示控件*/
    public static Button settlement;
    /*总金*/
    public static float zongjin = 0;
    /*总数*/
    public static int zongshu = 0;
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*加载主布局*/
        View view = inflater.inflate(R.layout.shopping_main, null);
        /*初始化控件*/
        checkBoxAll = (CheckBox) view.findViewById(R.id.cball_choose);
        totalMoney = (TextView) view.findViewById(R.id.tvtotal_money);
        freight = (TextView) view.findViewById(R.id.tvcharges);
        settlement = (Button) view.findViewById(R.id.btn_pay);
        listView = (ListView) view.findViewById(R.id.listView0);
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        // 模拟数据
        datas = new ArrayList<Shopping>();
        Shopping product = null;
        for (int i = 1; i < 3; i++) {
            product = new Shopping();
            product.setDianming("华润万家");
            List<Goods> goodses = new ArrayList<>();
            for (int j = 0; j < Math.random() * 5 + 2; j++) {
                Goods goods = new Goods();
                goods.setGuige("大款--虎");
                goods.setJiage((j + 1) * 1000);
                goods.setNeirong("豪8印象翡翠手镯缅甸玉器玉石玉手镯行规正品带证书 顺丰包邮S16 新品上架火爆来袭");
                goods.setId(i-1);
                goodses.add(goods);
            }
            product.setGoodses((ArrayList<Goods>) goodses);
            datas.add(product);
        }
        adapter = new ShoppingAdapter(datas, getContext());
        listView.setAdapter(adapter);

        //以上就是我们常用的自定义适配器ListView展示数据的方法了
        //解决问题：在哪里处理按钮的点击响应事件，是适配器 还是 Activity或者Fragment，这里是在Activity本身处理接口
        //执行添加商品数量，减少商品数量的按钮点击事件接口回调
        TotalMoneyset();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        checkBoxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxAll.isChecked()) {
                    for (int i = 0; i < datas.size(); i++) {
                        adapter.getProducts().get(i).setSelected(true);
                        for(int j=0; j<adapter.getProducts().get(i).getGoodses().size();j++){
                            adapter.getProducts().get(i).getGoodses().get(j).setSelected(true);
                        }
                    }
                } else {
                    for (int i = 0; i < datas.size(); i++) {
                        adapter.getProducts().get(i).setSelected(false);
                        for(int j=0; j<adapter.getProducts().get(i).getGoodses().size();j++){
                            adapter.getProducts().get(i).getGoodses().get(j).setSelected(false);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                TotalMoneyset();
            }
        });
        return view;
    }
    /*总金总数计算*/
    private void TotalMoneyset() {
        zongshu=0;
        zongjin=0;
        for (int i = 0; i < adapter.getProducts().size(); i++) {
            for(int j=0;j < adapter.getProducts().get(i).getGoodses().size();j++){
                if(adapter.getProducts().get(i).getGoodses().get(j).isSelected()){
                    int jianshu =adapter.getProducts().get(i).getGoodses().get(j).getJianshu();
                    float jiage = adapter.getProducts().get(i).getGoodses().get(j).getJiage();
                    zongjin += jianshu*jiage;
                    zongshu +=1;
                }
            }
        }
        String text = "总金额: " + zongjin;
        String text1 = "去结算(" + zongshu + ")";
        totalMoney.setText(text);
        settlement.setText(text1);
        totalMoney.postInvalidate();
        settlement.postInvalidate();
    }
}




















