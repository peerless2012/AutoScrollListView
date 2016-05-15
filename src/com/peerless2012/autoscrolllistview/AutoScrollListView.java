package com.peerless2012.autoscrolllistview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.widget.ListViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;

/**
* @Author peerless2012
* @Email peerless2012@126.com
* @DateTime 2016年5月14日 下午10:05:10
* @Version V1.0
* @Description: 自动滚动的ListView
*/
public class AutoScrollListView extends ListView {
	
	private final static int DALY_TIME = 3000;

	private LoopRunnable mLoopRunnable;
	
	private boolean mAnimating = false;
	
	private Scroller mScroller;
	
	private InnerAdapter mInnerAdapter;
	
	private ListAdapter mOutterAdapter;
	
	private InnerOnItemClickListener mInnerOnItemClickListener;
	
	private OnItemClickListener mOutterOnItemClickListener;
	
	private InnerOnItemLongClickListener mInnerOnItemLongClickListener;
	
	private OnItemLongClickListener mOutterOnItemLongClickListener;
	
	private boolean mAutoScroll = false;
	
	public AutoScrollListView(Context context) {
		this(context, null);
	}
	
	public AutoScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mLoopRunnable = new LoopRunnable();
		mScroller = new Scroller(context, new AccelerateInterpolator());
		mInnerAdapter = new InnerAdapter();
	}
	
	public AutoScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (mAutoScroll && mOutterAdapter != null) {
			AutoScroll autoScroll = (AutoScroll)mOutterAdapter;
			int height = autoScroll.getListItemHeight(getContext()) * autoScroll.getImmovableCount() 
					+ (autoScroll.getImmovableCount() - 1) * getDividerHeight();
			super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
		}else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		mAutoScroll = adapter instanceof AutoScroll;
		mOutterAdapter = adapter;
		super.setAdapter(mInnerAdapter);
	}
	
	@Override
	public void setOnItemClickListener(OnItemClickListener listener) {
		if (mInnerOnItemClickListener == null) {
			mInnerOnItemClickListener = new InnerOnItemClickListener();
		}
		mOutterOnItemClickListener = listener;
		super.setOnItemClickListener(mInnerOnItemClickListener);
	}
	
	@Override
	public void setOnItemLongClickListener(OnItemLongClickListener listener) {
		if (mInnerOnItemLongClickListener == null) {
			mInnerOnItemLongClickListener = new InnerOnItemLongClickListener();
		}
		mOutterOnItemLongClickListener = listener;
		super.setOnItemLongClickListener(mInnerOnItemLongClickListener);
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		Log.i("AutoScrollListView", "onAttachedToWindow");
		postDelayed(mLoopRunnable, DALY_TIME);
		mAnimating = true;
	}
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		Log.i("AutoScrollListView", "onDetachedFromWindow");
		removeCallbacks(mLoopRunnable);
	}
	
	int preY = 0;
	@Override
	public void computeScroll() {
		Log.i("AutoScrollListView", "computeScroll");
		if (!mScroller.computeScrollOffset()) {
			Log.i("AutoScrollListView", "compute finish");
			if (mAnimating) {
				Log.i("AutoScrollListView", "compute ignore runnable");
				return;
			}
			Log.i("AutoScrollListView", "compute send runnable");
			removeCallbacks(mLoopRunnable);
			postDelayed(mLoopRunnable, DALY_TIME);
			mAnimating = true;
			preY = 0;
			checkPosition();
		}else {
			mAnimating = false;
			Log.i("AutoScrollListView", "compute not finish");
			int dY = mScroller.getCurrY() - preY;
			ListViewCompat.scrollListBy(this, dY);
			preY = mScroller.getCurrY();
			invalidate();
		}
	}
	
	private void checkPosition() {
		int targetPosition = 0;
		int firstVisiblePosition = getFirstVisiblePosition();
		if (firstVisiblePosition == 0) {
			targetPosition = mInnerAdapter.getCount() -2;
		}
		int lastVisiblePosition = getLastVisiblePosition();
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction() != MotionEvent.ACTION_MOVE) {
			return super.onTouchEvent(ev);
		}else {
			return false;
		}
	}
	
	/**
	 * 开始自动滚动
	 */
	public void startAutoScroll() {
		if (!mScroller.isFinished()) {
			mScroller.abortAnimation();
		}
		removeCallbacks(mLoopRunnable);
		mAnimating = false;
		post(mLoopRunnable);
	}
	
	/**
	 * 停止自动滚动
	 */
	public void stopAutoScroll() {
		if (!mScroller.isFinished()) {
			mScroller.abortAnimation();
		}
		removeCallbacks(mLoopRunnable);
		mAnimating = false;
	}
	
	class LoopRunnable implements Runnable{

		@Override
		public void run() {
			Log.i("AutoScrollListView", "run");
			mAnimating = true;
			View childAt = getChildAt(0);
			mScroller.startScroll(0, 0, 0, childAt.getMeasuredHeight() + getDividerHeight());
			invalidate();
		}
		
	}
	
	class InnerAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mOutterAdapter == null ? 0 : 
				(mAutoScroll ? mOutterAdapter.getCount() + 2/*((AutoScroll)mOutterAdapter).getImmovableCount()*/:mOutterAdapter.getCount());
		}

		@Override
		public Object getItem(int position) {
			return mOutterAdapter.getItem((int)getItemId(position));
		}

		@Override
		public long getItemId(int position) {
			if (mAutoScroll) {
				if (position == 0) {
					return mOutterAdapter.getCount() - 1;
				}else if (position == getCount() - 1) {
					return 0;
				}
				return position - 1;
			}else {
				return position;
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return mOutterAdapter.getView((int)getItemId(position), convertView, parent);
		}
		
	}
	
	class InnerOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (mOutterOnItemClickListener != null && mInnerAdapter != null) {
				mOutterOnItemClickListener.onItemClick(parent, view, (int)mInnerAdapter.getItemId(position), id);
			}
		}
		
	}
	
	class InnerOnItemLongClickListener implements OnItemLongClickListener{

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			if (mOutterOnItemLongClickListener != null && mInnerAdapter != null) {
				return mOutterOnItemLongClickListener.onItemLongClick(parent, view, (int)mInnerAdapter.getItemId(position), id);
			}
			return false;
		}
		
	}
	
	public interface AutoScroll{
		/**
		 * 返回屏幕可见个数
		 * @return 可见个数
		 */
		public int getImmovableCount();
		
		/**
		 * 获取条目高度
		 * @return
		 */
		public int getListItemHeight(Context context);
	}
}
