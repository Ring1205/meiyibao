/**
 * @file XListView.java
 * @package me.maxwin.view
 * @create Mar 18, 2012 6:28:41 PM
 * @author Maxwin
 * @description An ListView support (a) Pull down to refresh, (b) Pull up to load more.
 * 		Implement IXListViewListener, and see stopRefresh() / stopLoadMore().
 */
package com.wmcd.myb.wigdet;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.wmcd.myb.R;

/**
 * 下拉刷新，上拉加载更多
 *
 * @author posun
 */
public class XListView extends ListView implements OnScrollListener {

	/**
	 * The M last y.
	 */
	private float mLastY = -1; // save event y
	/**
	 * The M scroller.
	 */
	private Scroller mScroller; // used for scroll back
	//	private OnScrollListener mScrollListener; // user's scroll listener

	/**
	 * The M list view listener.
	 */
// the interface to trigger refresh and load more.
	private IXListViewListener mListViewListener;

	/**
	 * The M header view.
	 */
// -- header view
	private XListViewHeader mHeaderView;
	/**
	 * The M header view content.
	 */
// header view content, use it to calculate the Header's height. And hide it
	// when disable pull refresh.
	private RelativeLayout mHeaderViewContent;
	/**
	 * The M header time view.
	 */
	private TextView mHeaderTimeView;
	/**
	 * The M header view height.
	 */
	private int mHeaderViewHeight; // header view's height
	/**
	 * The M enable pull refresh.
	 */
	private boolean mEnablePullRefresh = true;
	/**
	 * The M pull refreshing.
	 */
	private boolean mPullRefreshing = false; // is refreashing.

	/**
	 * The M footer view.
	 */
// -- footer view
	private XListViewFooter mFooterView;
	/**
	 * The M enable pull load.
	 */
	private boolean mEnablePullLoad;
	/**
	 * The M pull loading.
	 */
	private boolean mPullLoading;
	/**
	 * The M is footer ready.
	 */
	private boolean mIsFooterReady = false;

	/**
	 * The M total item count.
	 */
// total list items, used to detect is at the bottom of listview.
	private int mTotalItemCount;

	/**
	 * The M scroll back.
	 */
// for mScroller, scroll back from header or footer.
	private int mScrollBack;
	/**
	 * The constant SCROLLBACK_HEADER.
	 */
	private final static int SCROLLBACK_HEADER = 0;
	/**
	 * The constant SCROLLBACK_FOOTER.
	 */
	private final static int SCROLLBACK_FOOTER = 1;

	/**
	 * The constant SCROLL_DURATION.
	 */
	private final static int SCROLL_DURATION = 400; // scroll back duration
	/**
	 * The constant PULL_LOAD_MORE_DELTA.
	 */
	private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
	/**
	 * The constant OFFSET_RADIO.
	 */
// at bottom, trigger
														// load more.
	private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
													// feature.

	/**
	 * Instantiates a new X list view.
	 *
	 * @param context the context
	 */
	public XListView(Context context) {
		super(context);
		initWithContext(context);
	}

	/**
	 * Instantiates a new X list view.
	 *
	 * @param context the context
	 * @param attrs   the attrs
	 */
	public XListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWithContext(context);
	}

	/**
	 * Instantiates a new X list view.
	 *
	 * @param context  the context
	 * @param attrs    the attrs
	 * @param defStyle the def style
	 */
	public XListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWithContext(context);
	}

	/**
	 * Init with context.
	 *
	 * @param context the context
	 */
	private void initWithContext(Context context) {
		mScroller = new Scroller(context, new DecelerateInterpolator());
		// XListView need the scroll event, and it will dispatch the event to
		// user's listener (as a proxy).
		super.setOnScrollListener(this);

		// init header view
		mHeaderView = new XListViewHeader(context);
		mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.xlistview_header_content);
		mHeaderTimeView = (TextView) mHeaderView.findViewById(R.id.xlistview_header_time);
		//下拉刷新
		addHeaderView(mHeaderView);

		// init footer view
		mFooterView = new XListViewFooter(context);

		// init header height
		mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				mHeaderViewHeight = mHeaderViewContent.getHeight();
				getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
	}

	/**
	 * Sets adapter.
	 *
	 * @param adapter the adapter
	 */
	@Override
	public void setAdapter(ListAdapter adapter) {
		// make sure XListViewFooter is the last footer view, and only add once.
		if (mIsFooterReady == false) {
			mIsFooterReady = true;
			addFooterView(mFooterView);
		}
		super.setAdapter(adapter);
	}

	/**
	 * enable or disable pull down refresh feature.
	 *
	 * @param enable the enable
	 */
	public void setPullRefreshEnable(boolean enable) {
		mEnablePullRefresh = enable;
	//	if (!mEnablePullRefresh) { // disable, hide the content
			mHeaderViewContent.setVisibility(View.INVISIBLE);
//		} else {
//			mHeaderViewContent.setVisibility(View.VISIBLE);
//		}
	}

	/**
	 * enable or disable pull up load more feature.
	 *
	 * @param enable the enable
	 */
	public void setPullLoadEnable(boolean enable) {
		mEnablePullLoad = enable;
		if (!mEnablePullLoad) {
			mFooterView.hide();
			mFooterView.setOnClickListener(null);
		} else {
			mPullLoading = false;
			mFooterView.show();
			mFooterView.setState(XListViewFooter.STATE_NORMAL);
			// both "pull up" and "click" will invoke load more.
			mFooterView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startLoadMore();
				}
			});
		}
	}

	/**
	 * stop refresh, reset header view.
	 */
	public void stopRefresh() {
		if (mPullRefreshing == true) {
			mPullRefreshing = false;
			resetHeaderHeight();
		}
	}

	/**
	 * stop load more, reset footer view.
	 */
	public void stopLoadMore() {
		if (mPullLoading == true) {
			mPullLoading = false;
			mFooterView.setState(XListViewFooter.STATE_NORMAL);
		}
	}

	/**
	 * set last refresh time
	 *
	 * @param time the time
	 */
	public void setRefreshTime(String time) {
		mHeaderTimeView.setText(time);
	}

	//	private void invokeOnScrolling() {
	//		if (mScrollListener instanceof OnXScrollListener) {
	//			OnXScrollListener l = (OnXScrollListener) mScrollListener;
	//			l.onXScrolling(this);
	//		}
	//	}

	/**
	 * Update header height.
	 *
	 * @param delta the delta
	 */
	private void updateHeaderHeight(float delta) {
		mHeaderView.setVisiableHeight((int) delta + mHeaderView.getVisiableHeight());
		if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
			if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
				mHeaderView.setState(XListViewHeader.STATE_READY);
			} else {
				mHeaderView.setState(XListViewHeader.STATE_NORMAL);
			}
		}
		setSelection(0); // scroll to top each time
	}

	/**
	 * reset header view's height.
	 */
	private void resetHeaderHeight() {
		int height = mHeaderView.getVisiableHeight();
		if (height == 0) // not visible.
			return;
		// refreshing and header isn't shown fully. do nothing.
		if (mPullRefreshing && height <= mHeaderViewHeight) {
			return;
		}
		int finalHeight = 0; // default: scroll back to dismiss header.
		// is refreshing, just scroll back to show all the header.
		if (mPullRefreshing && height > mHeaderViewHeight) {
			finalHeight = mHeaderViewHeight;
		}
		mScrollBack = SCROLLBACK_HEADER;
		mScroller.startScroll(0, height, 0, finalHeight - height, SCROLL_DURATION);
		// trigger computeScroll
		invalidate();
	}

	/**
	 * Update footer height.
	 *
	 * @param delta the delta
	 */
	private void updateFooterHeight(float delta) {
		int height = mFooterView.getBottomMargin() + (int) delta;
		if (mEnablePullLoad && !mPullLoading) {
			if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
													// more.
				mFooterView.setState(XListViewFooter.STATE_READY);
			} else {
				mFooterView.setState(XListViewFooter.STATE_NORMAL);
			}
		}
		mFooterView.setBottomMargin(height);

		//		setSelection(mTotalItemCount - 1); // scroll to bottom
	}

	/**
	 * Reset footer height.
	 */
	private void resetFooterHeight() {
		int bottomMargin = mFooterView.getBottomMargin();
		if (bottomMargin > 0) {
			mScrollBack = SCROLLBACK_FOOTER;
			mScroller.startScroll(0, bottomMargin, 0, -bottomMargin, SCROLL_DURATION);
			invalidate();
		}
	}

	/**
	 * Start load more.
	 */
	private void startLoadMore() {
		mPullLoading = true;
		mFooterView.setState(XListViewFooter.STATE_LOADING);
		if (mListViewListener != null) {
			mListViewListener.onLoadMore();
		}
	}

	/**
	 * On touch event boolean.
	 *
	 * @param ev the ev
	 * @return the boolean
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mLastY == -1) {
			mLastY = ev.getRawY();
		}

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastY = ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float deltaY = ev.getRawY() - mLastY;
			mLastY = ev.getRawY();
			if (getFirstVisiblePosition() == 0 && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
				// the first item is showing, header has shown or pull down.
				updateHeaderHeight(deltaY / OFFSET_RADIO);
				//				invokeOnScrolling();
			} else if (getLastVisiblePosition() == mTotalItemCount - 1 && (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
				// last item, already pulled up or want to pull up.
				updateFooterHeight(-deltaY / OFFSET_RADIO);
			}
			break;
		default:
			mLastY = -1; // reset
			if (getFirstVisiblePosition() == 0) {
				// invoke refresh
				if (mEnablePullRefresh && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
					mPullRefreshing = true;
					mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
					if (mListViewListener != null) {
						mListViewListener.onRefresh();
					}
				}
				resetHeaderHeight();
			} else if (getLastVisiblePosition() == mTotalItemCount - 1) {
				// invoke load more.
				if (mEnablePullLoad && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
					startLoadMore();
				}
				resetFooterHeight();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * Compute scroll.
	 */
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			if (mScrollBack == SCROLLBACK_HEADER) {
				mHeaderView.setVisiableHeight(mScroller.getCurrY());
			} else {
				mFooterView.setBottomMargin(mScroller.getCurrY());
			}
			postInvalidate();
			//			invokeOnScrolling();
		}
		super.computeScroll();
	}

	//	@Override
	//	public void setOnScrollListener(OnScrollListener l) {
	//		mScrollListener = l;
	//	}

	/**
	 * On scroll state changed.
	 *
	 * @param view        the view
	 * @param scrollState the scroll state
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		//		if (mScrollListener != null) {
		//			mScrollListener.onScrollStateChanged(view, scrollState);
		//		}
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {

			if (getFirstVisiblePosition() == 0) {
				// invoke refresh
				if (mEnablePullRefresh && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
					mPullRefreshing = true;
					mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
					if (mListViewListener != null) {
						mListViewListener.onRefresh();
					}
				}
				resetHeaderHeight();
			} else if (getLastVisiblePosition() == mTotalItemCount - 1) {
				// invoke load more.
				//			if (mEnablePullLoad
				//					&& mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
				startLoadMore();
				//			}
				resetFooterHeight();
			}
		}
	}

	/**
	 * On scroll.
	 *
	 * @param view             the view
	 * @param firstVisibleItem the first visible item
	 * @param visibleItemCount the visible item count
	 * @param totalItemCount   the total item count
	 */
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// send to user's listener
		mTotalItemCount = totalItemCount;
		//		if (mScrollListener != null) {
		//			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
		//					totalItemCount);
		//		}

	}

	/**
	 * Sets x list view listener.
	 *
	 * @param l the l
	 */
	public void setXListViewListener(IXListViewListener l) {
		mListViewListener = l;
	}

	/**
	 * you can listen ListView.OnScrollListener or this one. it will invoke
	 * onXScrolling when header/footer scroll back.
	 */
	public interface OnXScrollListener extends OnScrollListener {
		/**
		 * On x scrolling.
		 *
		 * @param view the view
		 */
		public void onXScrolling(View view);
	}

	/**
	 * implements this interface to get refresh/load more event.
	 */
	public interface IXListViewListener {
		/**
		 * On refresh.
		 */
		public void onRefresh();

		/**
		 * On load more.
		 */
		public void onLoadMore();
	}


}
