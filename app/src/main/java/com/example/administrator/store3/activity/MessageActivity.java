package com.example.administrator.store3.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.store3.R;
import com.example.administrator.store3.adapter.SwipeAdapter;
import com.example.administrator.store3.entity.Message;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/18.
 */
public class MessageActivity extends Activity implements View.OnClickListener{
    private List<Message> data=new ArrayList<>();
    private ListView mListView;
    private ImageView imageView0;
    Message message=null;
    private SwipeAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_main);
        initData();
        findView();
    }

    private void findView() {
        imageView0= (ImageView) findViewById(R.id.image0);
        imageView0.setOnClickListener(this);

        mListView= (ListView) findViewById(R.id.gridview);

        //final SwipeAdapter mAdapter=new SwipeAdapter(this,data);
        mAdapter=new SwipeAdapter(this,data);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MessageActivity.this)
                        .setTitle("")
                        .setMessage("是否删除")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                               mAdapter.notifyDataSetChanged();
                            }
                        })
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                data.remove(position);
                                mAdapter.notifyDataSetChanged();
                            }
                        }).show();

                return false;
            }
        });


    }

    private void initData() {
        for (int i=0;i<20;i++){

            if (i%3==0) {
                message = new Message("NBA特报", "骑士获得2016年NBA总冠军", "早上11：36");
                message.setIcon_id(R.drawable.logo);
            }else if (i%3==1){
                message=new Message("NBA新闻","詹姆斯获得FMVP","早上11:58");
                message.setIcon_id(R.drawable.bj);
            }else {
                message=new Message("NBA晚报","骑士获取队史第一个总冠军","昨天晚上");
                message.setIcon_id(R.drawable.launcher_icon);
            }
            data.add(message);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image0:
                MessageActivity.this.finish();
                break;
        }

    }
}
