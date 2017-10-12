package com.wmcd.myb.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yuantongqin on 2016/11/18.
 */
public class RecyclerScaleUtils {

    /**
     * The M recycler.
     */
    private RecyclerView mRecycler;
    /**
     * The M item count.
     */
    private  int mItemCount;
    /**
     * The constant mDefaultMargin.
     */
    private static int mDefaultMargin = 40;
    /**
     * The M margin.
     */
    private  int mMargin = 0;
    /**
     * The M itemwidth.
     */
    private  int mItemwidth;
    /**
     * The M current position.
     */
    private  int mCurrentPosition = 0;

    /**
     * The M distances.
     */
    private  int mDistances = 0;

    /**
     * Attach to recycler view.
     *
     * @param recyclerView the recycler view
     * @param margin       the margin
     */
    public  void attachToRecyclerView(RecyclerView recyclerView, int margin){
        if(recyclerView == null){
            return;
        }
        mRecycler = recyclerView;
        if(margin <= 0){
            mMargin = mDefaultMargin;
        }else {
            mMargin = margin;
        }
        initView();
        final CustomLinearSnapHelper helper = new CustomLinearSnapHelper();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    if(mDistances == 0 || mDistances == (mItemCount*mItemwidth)){
                        helper.mStateIdle = true;
                    }else{
                        helper.mStateIdle = false;
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(recyclerView.getLayoutManager().getLayoutDirection() == LinearLayoutManager.HORIZONTAL){
                    mDistances += dx;
                    getCurrentPosition();
                    setItemScale();
                }

            }
        });

        helper.attachToRecyclerView(recyclerView);
    }

    /**
     * Init view.
     */
    protected  void initView( ){
        mRecycler.post(new Runnable() {

            @Override
            public void run() {
                mItemCount = mRecycler.getAdapter().getItemCount();
                mItemwidth = mRecycler.getWidth() - 2 * mMargin;
                mRecycler.smoothScrollToPosition(mCurrentPosition);
                setItemScale();
            }
        });
    }

    /**
     * Set item scale.
     */
    public  void setItemScale(){
        View leftView = null;
        View rightView = null;
        if(mCurrentPosition > 0){
            leftView = mRecycler.getLayoutManager().findViewByPosition(mCurrentPosition - 1);
        }
        View currentView = mRecycler.getLayoutManager().findViewByPosition(mCurrentPosition);
        if(mCurrentPosition < (mItemCount - 1)){
            rightView = mRecycler.getLayoutManager().findViewByPosition(mCurrentPosition +1);
        }

        //滑动百分比，左右的都是放大，中间缩小
        float percent = Math.abs((mDistances - mCurrentPosition * mItemwidth*1.0f)/mItemwidth);

        if(leftView != null){
            //这里是缩小原来大小的0.8-1.0 左右0.8，中间1.0   0.8+(percent*0.2)
            leftView.setScaleY(0.8f+(percent*0.2f));
        }
        if(currentView != null){
            currentView.setScaleY(1-0.2f*percent);
        }
        if(rightView != null){
            rightView.setScaleY(0.8f+(percent*0.2f));
        }

    }

    /**
     * Get current position.
     */
    protected  void getCurrentPosition(){
        if(mItemwidth <= 0) return;
        boolean change = false;

        if (Math.abs(mDistances - mCurrentPosition * mItemwidth) >= mItemwidth) {
            change = true;
        }
        if (change) { //以为这里是从0开始的
            mCurrentPosition = mDistances / mItemwidth;
        }

    }

    /**
     * On create view holder.
     *
     * @param parent   the parent
     * @param itemView the item view
     * @param margin   the margin
     */
    public static void onCreateViewHolder(ViewGroup parent, View itemView,int margin) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        if(margin <= 0){
            margin = mDefaultMargin;
        }
        int tp=parent.getWidth();
        lp.width =parent.getWidth() - 2*margin;
        itemView.setLayoutParams(lp);
    }

    /**
     * On create view holder.
     *
     * @param width    the width
     * @param itemView the item view
     * @param margin   the margin
     */
    public static void onCreateViewHolder(int width, View itemView,int margin) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        if(margin <= 0){
            margin = mDefaultMargin;
        }
        lp.width =width - 2*margin;
        itemView.setLayoutParams(lp);
    }


    /**
     * On bind view holder.
     *
     * @param itemView  the item view
     * @param position  the position
     * @param itemCount the item count
     * @param margin    the margin
     */
//这里是处理最左边和最右边的宽度
    public static void onBindViewHolder(View itemView, final int position, int itemCount,int margin) {
        int leftMarin = 0;
        int rightMarin =  0;
        int topMarin = 0;
        int bottomMarin =  0;
        if(position == 0){
            leftMarin = margin;
            rightMarin = 0;
        }else if(position == (itemCount-1)){
            leftMarin = 0;
            rightMarin = margin;
        }
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
        if (lp.leftMargin != leftMarin || lp.topMargin != topMarin || lp.rightMargin != rightMarin || lp.bottomMargin != bottomMarin) {
            lp.setMargins(leftMarin, topMarin, rightMarin, bottomMarin);
            itemView.setLayoutParams(lp);
        }

    }

    /**
     * Sets view margin.
     *
     * @param view   the view
     * @param left   the left
     * @param top    the top
     * @param right  the right
     * @param bottom the bottom
     */
//    setViewMargin(itemView, leftMarin, 0, rightMarin, 0);
    private static void setViewMargin(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom);
            view.setLayoutParams(lp);
        }
    }

    /**
     * Set item position.
     *
     * @param position the position
     */
    public  void setItemPosition(int position){
        mCurrentPosition = position ;
    }

}
