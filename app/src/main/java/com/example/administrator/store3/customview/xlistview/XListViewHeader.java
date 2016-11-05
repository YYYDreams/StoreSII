/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package com.example.administrator.store3.customview.xlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.store3.R;

public class XListViewHeader extends LinearLayout {
	private LinearLayout mContainer;
	private ImageView mArrowImageView;
	private ProgressBar mProgressBar;
	private TextView mHintTextView;
	private int mState = STATE_NORMAL;

	private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;
	
	private final int ROTATE_ANIM_DURATION = 180;
	
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;

	public XListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public XListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		// 初始情况，设置下拉刷新view高度为0
		LayoutParams lp = new LayoutParams(
				LayoutParams.FILL_PARENT, 0);
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.xlistview_header, null);
		addView(mContainer, lp);
		setGravity(Gravity.BOTTOM);
        //初始化控件
		mArrowImageView = (ImageView)findViewById(R.id.xlistview_header_arrow);
		mHintTextView = (TextView)findViewById(R.id.xlistview_header_hint_textview);
		mProgressBar = (ProgressBar)findViewById(R.id.xlistview_header_progressbar);
        //给箭头的一个动画效果,控制箭头的指向上
		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		//给箭头的一个动画效果,控制箭头指向下
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
	}
/**
* 设置页眉状态
* */
	public void setState(int state) {
		if (state == mState) return ;

		if (state == STATE_REFRESHING) {	// 当状态值为正在刷新
			mArrowImageView.clearAnimation();//清除箭头的动画效果
			mArrowImageView.setVisibility(View.INVISIBLE);//让箭头隐藏
			mProgressBar.setVisibility(View.VISIBLE);//让进度条显示
		} else {	// 显示箭头图片
			mArrowImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.INVISIBLE);//隐藏进度条
		}

		switch(state){
		case STATE_NORMAL://状态值为常规时
			if (mState == STATE_READY) {//如果状态值等于准备刷新时
				mArrowImageView.startAnimation(mRotateDownAnim);//箭头朝下开始动画
			}
			if (mState == STATE_REFRESHING) {//状态值等于正在刷新时
				mArrowImageView.clearAnimation();//清除箭头的动画效果
			}
			mHintTextView.setText(R.string.xlistview_header_hint_normal); //显示刷新状态
			break;
		case STATE_READY://状态值为准备时
			if (mState != STATE_READY) {//如果状态不等于准备状态
				mArrowImageView.clearAnimation();//清除箭头之前的动画效果
				mArrowImageView.startAnimation(mRotateUpAnim);//给箭头添加朝上的动画效果
				mHintTextView.setText(R.string.xlistview_header_hint_ready); //显示当前状态
			}
			break;
		case STATE_REFRESHING://状态值为正在刷新
			mHintTextView.setText(R.string.xlistview_header_hint_loading); //显示当前状态
			break;
			default:
		}

		mState = state;
	}
/**
 * 设置刷新头部的可见高度
 * */
	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LayoutParams lp = (LayoutParams) mContainer
				.getLayoutParams();   //获取mContainer的父布局
		lp.height = height;  //给父布局设置高度
		mContainer.setLayoutParams(lp); //重新赋给mContainer
	}
/**
 * 获得刷新头部高度的一个方法
 * */
	public int getVisiableHeight() {
		return mContainer.getHeight();
	}
}
