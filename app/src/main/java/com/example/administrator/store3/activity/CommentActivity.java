package com.example.administrator.store3.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.store3.R;
import com.example.administrator.store3.adapter.CommentAdapter;
import com.example.administrator.store3.entity.CommentBean;
import com.example.administrator.store3.entity.ReplyBean;
import com.example.administrator.store3.util.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends Activity {
	private Button commentButton;		//评论按钮
	private EditText commentEdit;		//评论输入框
	private TextView senderNickname;	//发表者昵称
	private TextView sendTime;			//发表的时间
	private TextView sendContent;		//发表的内容
	private TextView commentText;		//底部评论
	private ImageView commentImg,imageView;		//评论的图片
	private NoScrollListView commentList;//评论数据列表
	private LinearLayout bottomLinear;	//底部分享、评论等线性布局
	private LinearLayout commentLinear;	//评论输入框线性布局
    private ImageView senderImg;        //发送者图片
	private int count;					//记录评论ID
	private int position;				//记录回复评论的索引
	private int[] imgs;					//图片资源ID数组
	private boolean isReply;			//是否是回复
	private String comment = "";		//记录对话框中的内容
	private CommentAdapter adapter;
	private List<CommentBean> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.comment_main);
		//初始化UI界面
		initViews();
		//初始化数据
		init();
	}
	/**
	 * 初始化UI界面
	 */
	private void initViews(){
		commentButton = (Button) findViewById(R.id.commentButton);
		commentEdit = (EditText) findViewById(R.id.commentEdit);
		commentText = (TextView) findViewById(R.id.commentText);
        commentList = (NoScrollListView) findViewById(R.id.commentList);
		bottomLinear = (LinearLayout) findViewById(R.id.bottomLinear);
		commentLinear = (LinearLayout) findViewById(R.id.commentLinear);
		imageView= (ImageView) findViewById(R.id.image_fanhhui);

		ClickListener cl = new ClickListener();
		commentButton.setOnClickListener(cl);
		commentText.setOnClickListener(cl);
		imageView.setOnClickListener(cl);
	}
	/**
	 * 初始化数据
	 */
	private void init(){
		adapter = new CommentAdapter(this, getCommentData(),R.layout.comment_item,handler);
		commentList.setAdapter(adapter);
	}
	/**
	 * 获取评论列表数据
	 */
	private List<CommentBean> getCommentData(){
		imgs = new int[]{R.drawable.logo,R.drawable.logo,
				R.drawable.logo,R.drawable.logo};
		list = new ArrayList<>();
		count = imgs.length;
		return list;
	}
	/**
	 * 获取回复列表数据
	 */
	private List<ReplyBean> getReplyData(){
		List<ReplyBean> replyList = new ArrayList<>();
		return replyList;
	}
	/**
	 * 显示或隐藏输入法
	 */
	private void onFocusChange(boolean hasFocus){
		final boolean isFocus = hasFocus;
	(new Handler()).postDelayed(new Runnable() {
		public void run() {
			InputMethodManager imm = (InputMethodManager)
					commentEdit.getContext().getSystemService(INPUT_METHOD_SERVICE);
			if(isFocus)  {
				//显示输入法
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}else{
				//隐藏输入法
				imm.hideSoftInputFromWindow(commentEdit.getWindowToken(),0);
			}
		}
	  }, 100);
	}
	/**
	 * 判断对话框中是否输入内容
	 */
	private boolean isEditEmply(){
		comment = commentEdit.getText().toString().trim();
		if(comment.equals("")){
			Toast.makeText(getApplicationContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		commentEdit.setText("");
		return true;
	}
	/**
	 * 发表评论
	 */
	private void publishComment(){
		CommentBean bean = new CommentBean();
		bean.setId(count);
		bean.setCommentImgId(imgs[count % 4]);
		bean.setCommentNickname("昵称" + count);
		bean.setCommnetAccount("12345"+count);
		bean.setCommentContent(comment);
		list.add(bean);
		count++;
		adapter.notifyDataSetChanged();
	}
	/**
	 * 回复评论
	 */
	private void replyComment(){
		ReplyBean bean = new ReplyBean();
		bean.setId(count+10);
		bean.setCommentNickname(list.get(position).getCommentNickname());
		bean.setReplyNickname("我是回复的人");
		bean.setReplyContent(comment);
		adapter.getReplyComment(bean, position);
		adapter.notifyDataSetChanged();
	}
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what == 10){
				isReply = true;
				position = (Integer)msg.obj;
				commentLinear.setVisibility(View.VISIBLE);
				bottomLinear.setVisibility(View.GONE);
				onFocusChange(true);
			}
		}
	};
	/**
	 * 事件点击监听器
	 */
	private final class ClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.image_fanhhui:
					finish();
					break;
			case R.id.commentButton:	//发表评论按钮
				if(isEditEmply()){		//判断用户是否输入内容
					if(isReply){
						replyComment();
					}else{
						publishComment();
					}
					bottomLinear.setVisibility(View.VISIBLE);
					commentLinear.setVisibility(View.GONE);
					onFocusChange(false);
				}
				break;
			case R.id.commentText:		//底部评论按钮
				isReply = false;
				commentLinear.setVisibility(View.VISIBLE);
				bottomLinear.setVisibility(View.GONE);
				onFocusChange(true);
				break;
			}
		}
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		//判断控件是否显示
		if(commentLinear.getVisibility() == View.VISIBLE){
			commentLinear.setVisibility(View.GONE);
			bottomLinear.setVisibility(View.VISIBLE);
		}
	}


}
