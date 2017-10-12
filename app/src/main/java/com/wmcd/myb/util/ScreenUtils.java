package com.wmcd.myb.util;

import android.content.Context;

/**
 * Created by yuantongqin on 2016/11/22.
 */
public class ScreenUtils {

    /**
     * Dip 2 px int.
     *
     * @param context the context
     * @param dpValue the dp value
     * @return the int
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Px 2 dip int.
     *
     * @param context the context
     * @param pxValue the px value
     * @return the int
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
