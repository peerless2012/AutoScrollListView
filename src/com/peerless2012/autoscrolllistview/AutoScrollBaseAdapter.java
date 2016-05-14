package com.peerless2012.autoscrolllistview;

import android.widget.BaseAdapter;

/**
* @Author peerless2012
* @Email peerless2012@126.com
* @DateTime 2016年5月14日 下午10:48:51
* @Version V1.0
* @Description: 自动滚动适配器
*/
public abstract class AutoScrollBaseAdapter extends BaseAdapter {

	/**
	 * 返回ListView固定高度，建议小于一屏。
	 * @return 固定高度的条目数
	 */
	public abstract int getImmovableCount();
	
	
}
