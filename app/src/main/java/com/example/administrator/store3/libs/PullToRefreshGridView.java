/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.example.administrator.store3.libs;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.example.administrator.store3.R;
/**
 * gridview下拉刷新类
 *
 * */

public class PullToRefreshGridView extends PullToRefreshAdapterViewBase<GridView> {

    //PullToRefreshGridView的构造方法
	public PullToRefreshGridView(Context context) {
		super(context);
	}

	public PullToRefreshGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullToRefreshGridView(Context context, Mode mode) {
		super(context, mode);
	}

	public PullToRefreshGridView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);
	}

	@Override
	public final Orientation getPullToRefreshScrollDirection() {
		return Orientation.VERTICAL;//gridview的布局设置为竖直方向
	}

	@Override
	protected final GridView createRefreshableView(Context context, AttributeSet attrs) {
		final GridView gv;
		if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {//先判断sdk最低版本是否大于设置的最低版本
			gv = new InternalGridViewSDK9(context, attrs);//大于等于则创建SDk9的gridview
		} else {
			gv = new InternalGridView(context, attrs);//小于则创建普通的gridview
		}

		// Use Generated ID (from res/values/ids.xml)
		gv.setId(R.id.gridview); //绑定xml
		return gv;
	}
/**
 * 此类部类是一个自定义gridview,用于实现下拉刷新的gridview
 * */
	class InternalGridView extends GridView implements EmptyViewMethodAccessor {

		public InternalGridView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void setEmptyView(View emptyView) {
			PullToRefreshGridView.this.setEmptyView(emptyView);
		}

		@Override
		public void setEmptyViewInternal(View emptyView) {
			super.setEmptyView(emptyView);
		}
	}

	@TargetApi(9)
	final class InternalGridViewSDK9 extends InternalGridView {

		public InternalGridViewSDK9(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
				int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

			final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
					scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

			// Does all of the hard work...
			OverscrollHelper.overScrollBy(PullToRefreshGridView.this, deltaX, scrollX, deltaY, scrollY, isTouchEvent);

			return returnValue;
		}
	}
}
