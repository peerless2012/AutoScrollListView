#  AutoScrollListView
无限轮播并自动滚动的ListView

## 0x00 实际效果
![截图](https://raw.githubusercontent.com/peerless2012/AutoScrollListView/master/ScreenShots/ScreenGif.gif)

## 0x01 已经实现的功能
* 列表自动滚动。
* ListView无限轮播。
* ListView的条目点击和条目长按可以正常响应，滑动事件屏蔽。
* 通过适配器的方式实现固定显示条目的个数。
* ListView滚动的时候尽量屏蔽长按事件。

## 0x02 使用方式
1. 按照普通ListView的方式来使用。
2. 实现`AutoScrollListView.AutoScroll`接口。
   * `getImmovableCount` 返回需要固定显示的条目的个数，此方法能决定ListView的高度。
   * `getListItemHeight(Context context)` 返回单个条目的高度（建议每个条目高度一致）。

__注意：__

* 需要限制每个条目的高度是固定的
* ListView的适配器需要实现`AutoScrollListView.AutoScroll`接口。

## 0x03 原理
原理类似[AutoScrollViewPager](https://github.com/peerless2012/AutoScrollViewPager)。

数据组织方式：

![首尾各增加可视条目个数](https://raw.githubusercontent.com/peerless2012/AutoScrollListView/master/ScreenShots/listview_offm.png)

原有集合的前后各追加可见条目个元素，然后在滚动结束后重新设置合适的位置（如果需要的话）。

## 0x04 关于
Author peerless2012

Email  [peerless2012@126.con](mailto:peerless2012@126.con)

Blog   [https://peerless2012.github.io](https://peerless2012.github.io)