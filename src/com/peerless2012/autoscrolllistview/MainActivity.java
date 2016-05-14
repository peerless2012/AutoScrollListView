package com.peerless2012.autoscrolllistview;

import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{
	private AutoScrollListView mListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (AutoScrollListView) findViewById(R.id.list_view);
		mListView.setAdapter(new MyAdapter());
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), "条目点击",
						Toast.LENGTH_SHORT).show();
			}
		});
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), "条目长按",
						Toast.LENGTH_SHORT).show();
				return true;
			}
		});
	}
	
	class MyAdapter extends BaseAdapter{
		private Random random = new Random();
		private LayoutInflater mLayoutInflater;
		@Override
		public int getCount() {
			return 100;
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
		
		class ViewHolder{
			public TextView tvTitle;
			public TextView tvContent;
		}
		
	}
}
