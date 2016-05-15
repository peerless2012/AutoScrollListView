package com.peerless2012.autoscrolllistview;

import java.util.Random;

import com.peerless2012.autoscrolllistview.AutoScrollListView.AutoScroll;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
* @Author peerless2012
* @Email peerless2012@126.com
* @DateTime 2016年5月14日 下午10:48:51
* @Version V1.0
* @Description: 自动滚动适配器
*/
public class AutoScrollAdapter extends BaseAdapter implements AutoScroll{

	private Random random = new Random();
	private LayoutInflater mLayoutInflater;
	@Override
	public int getCount() {
		return 5;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			if (mLayoutInflater == null) {
				mLayoutInflater = LayoutInflater.from(parent.getContext());
			}
			convertView = mLayoutInflater.inflate(R.layout.list_item, parent,false);
			viewHolder = new ViewHolder();
			viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		convertView.setBackgroundColor(Color.argb(100, random.nextInt(255), random.nextInt(255), random.nextInt(255)));
		viewHolder.tvTitle.setText(position + "   标题");
		return convertView;
	}

	@Override
	public int getListItemHeight(Context context) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources().getDisplayMetrics());
	}

	@Override
	public int getImmovableCount() {
		return 3;
	}
	
	class ViewHolder{
		public TextView tvTitle;
		public TextView tvContent;
	}
	
}
