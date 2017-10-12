package com.wmcd.myb.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/5/15 0015.
 */
public class AnimatorUtil {
    /**
     * The constant isReadyh.
     */
    private static boolean isReadyh = true;
    /**
     * The constant isReadyw.
     */
    private static boolean isReadyw = true;
    /**
     * The constant isOver.
     */
    public static boolean isOver = true;

    /**
     * Anim height to view.
     *
     * @param v        the v
     * @param start    the start
     * @param end      the end
     * @param isToShow the is to show
     * @param duration the duration
     */
// 高度渐变的动画；
    public static void animHeightToView(final View v, final int start, final int end, final boolean isToShow,
                                        long duration) {

        ValueAnimator va = ValueAnimator.ofInt(start, end);
        final ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int h = (int) animation.getAnimatedValue();
                layoutParams.height = h;
                v.setLayoutParams(layoutParams);
                v.requestLayout();
            }
        });

        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isReadyh = false;
                isOver = false;
                if (isToShow) {
                    v.setVisibility(View.VISIBLE);
                }
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!isToShow) {
                    v.setVisibility(View.GONE);
                }
                isReadyh = true;
                isOver = true;

            }
        });
        va.setDuration(duration);
        va.start();
    }

    /**
     * Anim height to view.
     *
     * @param context  the context
     * @param v        the v
     * @param isToShow the is to show
     * @param duration the duration
     */
    public static void animHeightToView(final Activity context, final View v, final boolean isToShow, final long duration) {
        if (isReadyh) {
            if (isToShow) {
                // 显示：通过上下文对象context获取可见度属性为 gone 的 view 的高度；
                Display display = context.getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                v.measure(size.x, size.y);
                int end = v.getMeasuredHeight();
                animHeightToView(v, 0, end, isToShow, duration);
            } else {
                // 隐藏：从当前高度变化到0，最后设置不可见；
                animHeightToView(v, v.getLayoutParams().height, 0, isToShow, duration);
            }
        }
    }


    /**
     * Anim width to view.
     *
     * @param v        the v
     * @param start    the start
     * @param end      the end
     * @param isToShow the is to show
     * @param duration the duration
     */
/*
    * 宽度
    * */
    public static void animWidthToView(final View v, final int start, final int end, final boolean isToShow,
                                       long duration) {

        ValueAnimator va = ValueAnimator.ofInt(start, end);
        final ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int w = (int) animation.getAnimatedValue();
                layoutParams.width = w;
                v.setLayoutParams(layoutParams);
                v.requestLayout();
            }
        });

        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isOver = false;
                isReadyw = false;
                if (isToShow) {
                    v.setVisibility(View.VISIBLE);
                }
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!isToShow) {
                    v.setVisibility(View.GONE);
                }
                isReadyw = true;
                isOver = true;
            }
        });
        va.setDuration(duration);
        va.start();
    }

    /**
     * Anim width to view.
     *
     * @param context  the context
     * @param v        the v
     * @param isToShow the is to show
     * @param duration the duration
     */
    public static void animWidthToView(final Activity context, final View v, final boolean isToShow, final long duration) {
        if (isReadyw) {
            if (isToShow) {
                // 显示：通过上下文对象context获取可见度属性为 gone 的 view 的高度；
                Display display = context.getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                v.measure(size.x, size.y);
                int end = (int) (v.getMeasuredWidth() - UiUtils.dpToPixels(25, context));
                animWidthToView(v, 0, end, isToShow, duration);

            } else {
                // 隐藏：从当前宽度变化到0，最后设置不可见；
                animWidthToView(v, v.getLayoutParams().width, 0, isToShow, duration);
            }
        }
    }
}
