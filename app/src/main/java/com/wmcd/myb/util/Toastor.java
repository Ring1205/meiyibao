package com.wmcd.myb.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/9/8.
 */
public class Toastor {
    /**
     * The M toast.
     */
    private Toast mToast;
    /**
     * The Context.
     */
    private Context context;

    /**
     * Instantiates a new Toastor.
     *
     * @param context the context
     */
    public Toastor(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Gets singleton toast.
     *
     * @param resId the res id
     * @return the singleton toast
     */
    public Toast getSingletonToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(resId);
        }
        return mToast;
    }

    /**
     * Gets singleton toast.
     *
     * @param text the text
     * @return the singleton toast
     */
    public Toast getSingletonToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(text);
        }
        return mToast;
    }

    /**
     * Gets single long toast.
     *
     * @param resId the res id
     * @return the single long toast
     */
    public Toast getSingleLongToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(context, resId, Toast.LENGTH_LONG);
        }else{
            mToast.setText(resId);
        }
        return mToast;
    }

    /**
     * Gets single long toast.
     *
     * @param text the text
     * @return the single long toast
     */
    public Toast getSingleLongToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        }else{
            mToast.setText(text);
        }
        return mToast;
    }

    /**
     * Gets toast.
     *
     * @param resId the res id
     * @return the toast
     */
    public Toast getToast(int resId) {
        return Toast.makeText(context, resId, Toast.LENGTH_SHORT);
    }

    /**
     * Gets toast.
     *
     * @param text the text
     * @return the toast
     */
    public Toast getToast(String text) {
        return Toast.makeText(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * Gets long toast.
     *
     * @param resId the res id
     * @return the long toast
     */
    public Toast getLongToast(int resId) {
        return Toast.makeText(context, resId, Toast.LENGTH_LONG);
    }

    /**
     * Gets long toast.
     *
     * @param text the text
     * @return the long toast
     */
    public Toast getLongToast(String text) {
        return Toast.makeText(context, text, Toast.LENGTH_LONG);
    }

    /**
     * Show singleton toast.
     *
     * @param resId the res id
     */
    public void showSingletonToast(int resId) {
        getSingletonToast(resId).show();
    }


    /**
     * Show singleton toast.
     *
     * @param text the text
     */
    public void showSingletonToast(String text) {
        getSingletonToast(text).show();
    }

    /**
     * Show single long toast.
     *
     * @param resId the res id
     */
    public void showSingleLongToast(int resId) {
        getSingleLongToast(resId).show();
    }


    /**
     * Show single long toast.
     *
     * @param text the text
     */
    public void showSingleLongToast(String text) {
        getSingleLongToast(text).show();
    }

    /**
     * Show toast.
     *
     * @param resId the res id
     */
    public void showToast(int resId) {
        getToast(resId).show();
    }

    /**
     * Show toast.
     *
     * @param text the text
     */
    public void showToast(String text) {
        getToast(text).show();
    }

    /**
     * Show long toast.
     *
     * @param resId the res id
     */
    public void showLongToast(int resId) {
        getLongToast(resId).show();
    }

    /**
     * Show long toast.
     *
     * @param text the text
     */
    public void showLongToast(String text) {
        getLongToast(text).show();
    }
}
