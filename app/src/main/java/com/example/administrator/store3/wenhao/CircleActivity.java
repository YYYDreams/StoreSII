/*
package com.example.administrator.store3.wenhao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.store3.R;
import com.example.administrator.store3.customview.CircleMenuLayout;


*/
/**
 * <pre>
 * @author zhy
 * http://blog.csdn.net/lmj623565791/article/details/43131133
 * </pre>
 *//*

public class CircleActivity extends Activity
{

	private CircleMenuLayout mCircleMenuLayout;


	private String[] mItemTexts = new String[] { "南玉 ", "品牌", "店铺",
			"鉴赏", "大师", "活动" };

	private int[] mItemImgs = new int[] { R.drawable.home_mbank_1_normal,
			R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
			R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
			R.drawable.home_mbank_6_normal };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		//自已切换布局文件看效果
		setContentView(R.layout.activity_main);

		mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
		mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);



		mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {


			@Override
			public void itemClick(View view, int pos) {

			}

			@Override
			public void itemCenterClick(View view) {

			}
		});


	}
}
*/
