package com.peerless2012.autoscrolllistview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.widget.ListViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
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
	
	private static final int DEFAULT_MINIMUM_VELOCITY_DIPS = 315;
    private static final int DEFAULT_MAXIMUM_VELOCITY_DIPS = 1575;
	
	private final static int DALY_TIME = 3000;

	private LoopRunnable mLoopRunnable;
	
	private boolean mAnimating = false;
	
	private Scroller mScroller;
	
	public AutoScrollListView(Context context) {
		this(context, null);
	}
	
	public AutoScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mLoopRunnable = new LoopRunnable();
		mScroller = new Scroller(context, new AccelerateInterpolator());
	}
	
	public AutoScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
		super.setAdapter(adapter);
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
		}else {
			mAnimating = false;
			Log.i("AutoScrollListView", "compute not finish");
			int dY = mScroller.getCurrY() - preY;
			ListViewCompat.scrollListBy(this, dY);
			preY = mScroller.getCurrY();
			invalidate();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction() != MotionEvent.ACTION_MOVE) {
			return super.onTouchEvent(ev);
		}else {
			return false;
		}
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
}
