package com.example.administrator.store3.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.store3.R;

/**
 * 横向listview自定义adapter
 */

public class HorizontalListViewAdapter extends BaseAdapter{
	/*图片数组*/
	private int[] mIconIDs;
	/*标题数组*/
	private String[] mTitles;
	/*上下文对象*/
	private Context mContext;
	/*容器管理者对象*/
	private LayoutInflater mInflater;
	/*选择索引*/
	private int selectIndex = -1;
    /*适配器构造方法*/
	public HorizontalListViewAdapter(Context context, String[] titles){
		this.mContext = context;
		this.mTitles = titles;
		mInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		return mTitles.length;
	}
	@Override
	public Object getItem(int position) {
		return mTitles[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if(convertView==null){
			holder = new ViewHolder();
			/*加载item布局*/
			convertView = mInflater.inflate(R.layout.horizon_text, null);
			holder.mTitle=(TextView)convertView.findViewById(R.id.horizon_text);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		if(position == selectIndex){
			convertView.setSelected(true);
		}else{
			convertView.setSelected(false);
		}
		
		holder.mTitle.setText(mTitles[position]);

		return convertView;
	}

	private static class ViewHolder {
		private TextView mTitle ;
	}
//选择返回下标索引
	public void setSelectIndex(int i){
		selectIndex = i;
	}
}